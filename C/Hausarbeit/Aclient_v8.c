// vim: ts=4 sw=4
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <ctype.h>
#include <string.h>
#include <sys/types.h>
#include <netdb.h>
#include <sys/select.h>
#include <signal.h>
#include <stdio_ext.h>
#include <errno.h>
#include "readline.c"

#include "include/snp.h"


int main(int argc, char *argv[])
{
	int sd;
	struct sockaddr_in adresse;	/* Internet-Adress-Struktur */
	u_short port = 12345;	/* Default Serverportnr.: 12345 */
	struct hostent *server; /* Zeiger für Server-Informationn */
	char *servername;
	int rc;
	int optchar;

	opterr = 0;
	while(optchar = getopt(argc, argv, "p:"), optchar != -1) {
		switch(optchar) {
			case 'p': /* Portnummer setzen */
				rc = sscanf(optarg, "%hu", &port);
				if (rc != 1)
					usage(argv[0]);
				break;
			default:
				usage(argv[0]);
		}
	}
	if (optind >= argc) {
		fprintf(stderr, "Hostname bitte übergeben\n");
		exit(1);
	}
	servername = argv[optind];

	sd = socket(AF_INET, SOCK_STREAM, 0);
	if (sd < 0) {
		perror("socket");
		exit(1);
	}
	printf("Server: %s\n", servername);
	printf("Port  : %hu\n", port);
	printf("=======\n\n");

	/* Adress-Struktur belegen */
	memset(&adresse, 0, sizeof(adresse));
	adresse.sin_family = AF_INET;
	adresse.sin_port = htons(port);
	server = gethostbyname(servername);

	if (server == NULL) {
		perror(servername);
		exit(1);
	}
	adresse.sin_addr = *(struct in_addr *) server->h_addr;

	if (connect(sd, (struct sockaddr *)&adresse, sizeof(adresse)) < 0) {
		perror("connect");
		exit(1);
	}

	/* starten des Spiels Mastermind */
	mastermind(sd);

	/* nach Spielende Socket schließen */
	close(sd);

	/* Main-Funktion Ende */
	return 0;

}

void usage(char *progname) /* Bedienungsanleitung */
{
	printf("Usage: %s [Optionen] Hostname\n", progname);
	printf("Optionen:\n");
	printf("\t-p #\tPortnummer setzen (default: 12345)\n");
	exit(1);
}

void mastermind(int sd) /* Die Spiel-Funktion */
{
	int rc;
	int play;
	char buffer[MAXLEN];
	char playername[MAXLEN];
	char tmp[MAXLEN];
	char opponentplayer[MAXLEN];
	char secret[MAXLEN];
	fd_set bereit;
	int gamerounds;

	/* stdout ungepuffert, damit write(1,..) und printf() genutzt werden können */
	setbuf(stdout, NULL);
	printf("Client gestartet\n");

	if (signal(SIGPIPE, SIG_IGN) == SIG_ERR) {
		perror("signal SIGPIPE");
		exit(1);
	}

	/* Warten auf den Start der Kommunikation */
	while(1) {
		struct timeval zeit;

		FD_ZERO(&bereit);
		FD_SET( sd, &bereit);

		/* Aktualisierungsintervall */
		zeit.tv_sec = 0;
		zeit.tv_usec = 500000;

		rc = select(sd + 1, &bereit, NULL, NULL, &zeit);
		if (rc < 0) {
			if (errno != EINTR) {
				perror("select");
				exit(1);
			}
			continue;
		}
		if (rc == 0) {
			write(1, ".", 1);
			continue;
		}
		printf("\n");
		break;
	}

	/* Der Socket ist nun bereit zur Kommunikation */
	/* Begrüßung vom Server empfangen */
	if(empfangen(sd, buffer, MAXLEN) <= 0) {
		exit(1);
	}
	/* Auf Fehler überprüfen */
	if (strcasecmp(buffer, "hallo\n") != 0) {
		perror("No Hallo");	
		exit(1);
	}
	/* Ausgabe auf Bildschirm */
	writeline(1, buffer);

	/* Hallo mit ok bestätigen */
	ok(sd);


	printf("Bitte einen Spielernamen angeben: ");

	while(1) {
		fd_set bereit;

		FD_ZERO(&bereit);
		FD_SET( 0, &bereit);
		FD_SET(sd, &bereit);

		rc = select(MAX( 0, sd) + 1 , &bereit, NULL, NULL, NULL);
		if (rc < 0) {
			if (errno == EINTR) 
				continue;
			perror("select");
			exit(1);
		}

		if (rc == 0)
			continue;

		if (FD_ISSET(sd, &bereit)) {
			/* Erst einmal empfangen (kann auch EOF sein!) */
			if (empfangen( sd, buffer, MAXLEN) <= 0) {
				/* Server ist nicht mehr erreichbar - Ende der Kommunikation */
				break;
			}
			if (strcasecmp(buffer, "quit\n") == 0) {
				exit(1);
			}
			if ( write(1, buffer, strlen(buffer)) < 0 ) {
				perror("write");
				exit(1);
			}
			continue;		
		}

		if (FD_ISSET(0, &bereit)) {
			/* von Eingabe lesen */
			rc = readline(0, buffer, MAXLEN-1);
			if (rc < 0) { /* Fehler -> Ausgabe */
				perror("readline");
				exit(1);
			}
			*(buffer+rc) = (char) 0; /* String erstellen */

			/* Prüfen ob gültiger Name eingegeben wurde */
			/* Prüfen ob quit eingegeben wurde */
			if (strcasecmp(buffer, "quit\n") == 0) { 
				printf("Bitte einen gültigen Spielernamen angeben: ");
				continue;
			}
			/* Prüfen auf Länge */
			if (strlen(buffer) == 1) {
				printf("Bitte einen gültigen Spielernamen angeben: ");
				continue;
			}
			/* Prüfen auf blanks im Namen */
			if( sscanf(buffer,"%s%s", playername, tmp) != 1) {
				printf("Bitte einen gültigen Spielernamen angeben: ");
				continue;
			}

			printf("Angegebener Name: %s", playername);
			/* Namen senden */
			str_senden(sd, "NAME: ", playername, rc);

			break;
		}
	}
	/* Warten auf "OK" vom Server (Gegenspieler) */
	waitforserverreply(sd,1);
	if(empfangen(sd, buffer, MAXLEN) <= 0) {
		exit(1);
	}

	/* Auf Fehler überprüfen */
	if (strcasecmp(buffer, "ok\n") != 0) {
		perror("not OK");	
		exit(1);
	}

	/* Warten auf Gegenspielername */
	waitforserverreply(sd,0);
	if(empfangen(sd, buffer, MAXLEN) <= 0) {
		exit(1);
	}

	/* Auf Fehler überprüfen */
	if (strncasecmp(buffer, "name:", 5) != 0) {
		perror("not name");	
		exit(1);
	}

	/* mit "OK" bestaetigen */
	ok(sd);

	/* Speichern des Gegenspielernamens */
	if( sscanf(buffer, "%*s %s", opponentplayer) < 0) {
		perror("not name");
		exit(1);
	}
	/* Namen des Gegenspielers ausgeben */	
	printf("Dein Gegenspieler heisst: %s\n", opponentplayer);	

	/* Warten auf Anzahl der Runden */
	waitforserverreply(sd,0);
	if(empfangen(sd, buffer, MAXLEN) <= 0) {
		exit(1);
	}

	/* Auf Fehler überprüfen */
	if (strncasecmp(buffer, "runden:", 5) != 0) {
		perror("not rounds");	
		exit(1);
	}

	/* mit "OK" bestaetigen */
	ok(sd);

	/* Speichern der Rundenzahl */
	if( sscanf(buffer, "%*s %d", &gamerounds) < 0) {
		perror("not rounds");
		exit(1);
	}
	/* dem Spieler die Rundenanzahl mitteilen */
	printf("Es können bis zu %d Runden gespielt werden.\n", gamerounds);	

	/* Geheimzahl einlesen */
	printf("Bitte geben Sie eine 4-stellige Geheimzahl ein: ");	

	play = 1;
	while(play) {
		while(1) {
			fd_set bereit;

			FD_ZERO(&bereit);
			FD_SET( 0, &bereit);
			FD_SET(sd, &bereit);

			rc = select(MAX( 0, sd) + 1 , &bereit, NULL, NULL, NULL);
			if (rc < 0) {
				if (errno == EINTR) 
					continue;
				perror("select");
				exit(1);
			}

			if (rc == 0)
				continue;

			if (FD_ISSET(sd, &bereit)) {
				/* Erst einmal empfangen (kann auch EOF sein!) */
				if (empfangen( sd, buffer, MAXLEN) <= 0) {
					/* Server ist nicht mehr erreichbar - Ende der Kommunikation */
					break;
				}
				if (strcasecmp(buffer, "quit\n") == 0) {
					exit(1);
				}
				if ( write(1, buffer, strlen(buffer)) < 0 ) {
					perror("write");
					exit(1);
				}
				continue;		
			}

			if (FD_ISSET(0, &bereit)) {

				rc = readline(0, secret, 5);
				if (rc < 0) { /* Fehler -> Ausgabe */
					perror("readline");
					exit(1);
				}
				/* War die Eingabe eine Zahl? */
				if ( secret[4] != '\n' ) {
					printf("Das war keine 4-stellige Zahl!");
					printf("Bitte geben Sie eine 4-stellige Geheimzahl ein: ");	
					continue;
				}

			}

			*(secret+rc-1) = (char) 0; /* String erstellen */
			if (strcasecmp(secret, "quit\n") == 0) 
				break;


			/* Geheimzahl senden */
			str_senden(sd, "GEHEIMZAHL: ", secret, rc);

			/* Fehlerabfrage keine Zahl?*/
			break;
		}

		waitforserverreply(sd,1);
		if(empfangen(sd, buffer, MAXLEN) <= 0) {
			exit(1);
		}
		printf("%s\n",buffer);
		if (strcasecmp(buffer, "ok\n") != 0) {
			perror("not ok");	
			exit(1);
		}

		waitforserverreply(sd,0);
		if(empfangen(sd, buffer, MAXLEN) <= 0) {
			exit(1);
		}
		printf("%s\n",buffer);
		if (strcasecmp(buffer, "start\n") != 0) {
			perror("not start");	
			exit(1);
		}
		/* mit "OK" bestaetigen */
		ok(sd);

		play = runde(sd);
	}
}


/* hier wird der Rundenablauf gesteuert */
int runde(int sd) 
{
	int rc;
	int rundenende;
	char buffer[MAXLEN];

	rundenende = 0;
	while(1) {

		fd_set bereit;

		FD_ZERO(&bereit);
		FD_SET( 0, &bereit);
		FD_SET(sd, &bereit);

		/* Eingabe vom Nutzer oder Nachricht vom Server abwarten */
		rc = select(MAX( 0, sd) + 1 , &bereit, NULL, NULL, NULL);
		if (rc < 0) {
			if (errno == EINTR) 
				continue;
			perror("select");
			exit(1);
		}

		if (rc == 0)
			continue;

		/* Es kommt eine Nachricht vom Server */
		if (FD_ISSET(sd, &bereit)) {

			if (empfangen( sd, buffer, MAXLEN) <= 0) {
				/* Server ist nicht mehr erreichbar - Ende der Kommunikation */
				break;
			}
			/* Ausgabe der Nachricht vom Server auf den Bildschirm */	
			if ( write(1, buffer, strlen(buffer)) < 0 ) {
				perror("write");
				exit(1);
			}

			/* es kommt quit */
			if (strcasecmp(buffer, "quit\n") == 0) {
				perror("Der Server ist nicht mehr erreichbar!");
				exit(1);
			}

			/* überprüfen ob Gewonnen/Verloren/Remis */
			if (strcasecmp(buffer, "gewonnen\n") == 0) { 
				printf("Sie haben gewonnen!\n");
				ok(sd);
			}
			if (strcasecmp(buffer, "verloren\n") == 0) {   
				printf("Sie haben verloren!\n");
				ok(sd);
			}
			if (strcasecmp(buffer, "remis\n") == 0) {
				printf("Sie haben ein unentschieden erspielt!\n");
				ok(sd);
			}
			if (strncasecmp(buffer, "versuch:", 8) == 0) {
				printf("vom Server: \"%s\"\n",buffer);
				/* mit "OK" bestaetigen */
				ok(sd);
			}
			if (strncasecmp(buffer, "ergebnis:", 9) == 0) {
				printf("vom Server: \"%s\"\n",buffer);
				/* mit "OK" bestaetigen */
				ok(sd);
			}
			if (strcasecmp(buffer, "noch einmal?\n") == 0) {
				printf("vom Server: \"%s\"\n",buffer);
				rundenende = 1;
				/* mit "OK" bestaetigen */
				ok(sd);
			}
			continue;		
		}
		if (FD_ISSET(0, &bereit)) {

			rc = readline(0, buffer, 5);
			printf("%d\n", rc);
			if (rc < 0) { /* Fehler -> Ausgabe */
				perror("readline");
				exit(1);
			}
			if(rundenende) {
				printf("rundenende\n");
				if (strncasecmp(buffer, "j\n", 2) == 0) {
					/* String senden */
					if(senden(sd, "bereit\n") < 0) {
						perror("senden");	
						exit(1);
					}
					return 1;


				}
				if (strncasecmp(buffer, "n\n", 2) == 0) {
					/* String senden */
					if(senden(sd, "quit\n") < 0) {
						perror("senden");	
						exit(1);
					}
					return 0;
				}

				else {
					printf("Falsche Eingabe, erwarte (j/n)\n");
					continue;
				}
			}

			/* Geheimzahl senden */
			str_senden(sd, "ZAHL: ", buffer, rc);

			waitforserverreply(sd,0);
			if(empfangen(sd, buffer, MAXLEN) <= 0) {
				exit(1);
			}
			printf("vom Server: \"%s\"\n",buffer);
			if (strcasecmp(buffer, "ok\n") != 0) {
				perror("client: not ok");	
				exit(1);
			}
		}

	}

	/* gebe 1 zurück um eine weitere Runde zu starten */
	return 1;
}

void str_senden(int sd, char topic[MAXLEN], char data[MAXLEN], int rc)
{
	char buffer[MAXLEN];

	*(data+rc-1) = (char) 0; /* String erstellen */

	strncpy(buffer, topic,MAXLEN);
	strncat(buffer, data, MAXLEN - strlen(data) - 2);
	strncat(buffer, "\n", MAXLEN - strlen(buffer) - 1 );

	/* String senden */
	if(senden(sd, buffer) < 0) {
		perror("senden");	
		exit(1);
	}

}

int ok(int sd)
{
	if(senden(sd, "OK\n") < 0) {
		perror("Ok senden");	
		exit(1);
	}
	return 0;
}

int waitforserverreply(int sd, int spieler) 
{
	fd_set bereit;
	int rc;

	//printf("\n");
	while(1) {
		struct timeval zeit;

		FD_ZERO(&bereit);
		FD_SET( sd, &bereit);
			
		
		zeit.tv_sec = 1;
		zeit.tv_usec = 0;

		rc = select(sd + 1, &bereit, NULL, NULL, &zeit);
		if (rc < 0) {
			if (errno != EINTR) {
				perror("select");
				exit(1);
			}
			continue;
		}
		
		if (rc == 0) {
			if(spieler) { /* wird ein eiziges Mal aufgerufen */
				printf("\nWarte auf Gegenspieler\n");
				spieler = 0;
			}
			write(1, "-", 1);
			continue;
		}
		break;
	}
	printf("\n");
	return 0;

}

/* Empfangen einer Zeile über einen Deskriptor
 * Rückgabe: Zeichenanzahl > 0 / 0 bei EOF / -1 bei Fehler
 */
int empfangen(int in, char *buffer, int maxlen)
{
	int rc;

	rc = readline(in, buffer, (unsigned int) maxlen-1);
	if (rc <= 0) { /* Fehler oder EOF */
		if (rc < 0) /* Fehler -> Ausgabe */
			perror("read");
		return rc;
	}
	*(buffer+rc) = (char) 0; /* String erstellen */
	return rc;
}

/* Senden einer Zeichenkette über einen Deskriptor
 * Rückgabe: 0 für okay / -1 bei Fehler oder EOF (EPIPE)
 */
int senden(int out, char *buffer)
{
	int rc;

	rc = (int) write(out, buffer, strlen(buffer));
	if (rc < 0) {
		if (errno != EPIPE) /* Wenn nicht EPIPE: Fehlerausgabe */
			perror("write");
		return -1;
	}
	return 0;
}
