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
		fprintf(stderr, "Hostname bitte übergeben");
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
	printf("=======\n");

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
	int err;
	int play;
	int gamerounds;
	char buffer[MAXLEN];
	char playername[MAXLEN];
	char opponentplayer[MAXLEN];
	char secret[MAXLEN];
	/*char tmp[MAXLEN];*/

	/* stdout ungepuffert, damit write(1,..) und printf() genutzt werden können */
	setbuf(stdout, NULL);

	/*printf("Client gestartet\n");*/
	if (signal(SIGPIPE, SIG_IGN) == SIG_ERR) {
		perror("signal SIGPIPE");
		/* Hier Fehler Funktion aufrufen */
		exit(1);
	}
	/* Warten auf den Start der Kommunikation */
	waitforserverreply(sd,1);


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
	/* Ausgabe auf Bildschirm 
	   writeline(1, buffer);
	   */

	/* Hallo mit ok bestätigen */
	ok(sd);

	printf("Gib deinen Spielernamen ein!\n");
	printf("Dein Spielername: ");

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

		/* Es kam eine Nachricht vom Server */
		if (FD_ISSET(sd, &bereit)) {
			/* Erst einmal empfangen (kann auch EOF sein!) */
			if (empfangen( sd, buffer, MAXLEN) <= 0) {
				/* Server ist nicht mehr erreichbar - Ende der Kommunikation */
				break;
			}
			/* Kam "quit" -> quit-Funktion! */
			if (strcasecmp(buffer, "quit\n") == 0) {
				/* hier quit-Funktion aufrufen */
				exit(1);
			}
			/* Ausgabe der Nachricht auf den Bildschirm
			   if ( write(1, buffer, strlen(buffer)) < 0 ) {
			   perror("write");
			   exit(1);
			   }*/
			continue;		
		}

		/* Der Spieler hat etwas eingegeben und mit '\n' bestätigt */
		if (FD_ISSET(0, &bereit)) {
			/* von Eingabe lesen */
			rc = readline(0, buffer, MAXLEN-1);
			/* Fehler -> Ausgabe */
			if (rc < 0) {
				perror("readline");
				exit(1);
			}
			/* String erstellen */
			*(buffer+rc) = (char) 0;

			/* Prüfen ob gültiger Name eingegeben wurde */
			/* Prüfen auf Länge */
			if (strlen(buffer) == 1) {
				printf("Bitte einen gültigen Spielernamen angeben: ");
				continue;
			}

			/* Spielernamen sichern */
			strcpy(playername, buffer);
			/* Namen senden */
			str_senden(sd, "NAME: ", playername, rc);
			/* die Einleseschleife beenden */
			break;
		}
	}/*-----Ende Spielernamen-Einlese-Schleife-----*/	

	/* Warten auf "OK" vom Server (Gegenspieler) */
	waitforserverreply(sd,1);
	
	if(empfangen(sd, buffer, MAXLEN) <= 0) {
		exit(1);
	}

	/* Überprüfen ob wirklich "OK" empfangen wurde */
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

	/* mit "OK" bestätigen */
	ok(sd);

	/* Sichern des Gegenspielernamens */
	strcpy(opponentplayer, &buffer[6]);
	opponentplayer[strlen(opponentplayer) - 1] = '\0';

	/* Namen des Gegenspielers ausgeben */	
	printf("Dein Gegenspieler nennt sich: %s.\n", opponentplayer);	

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
	if( sscanf(buffer, "%*s %d", &gamerounds) < 1) {
		perror("not rounds");
		exit(1);
	}
	/* dem Spieler die Rundenanzahl mitteilen */
	printf("Es sind maximal %d Runden Vereinbart.\n\n", gamerounds);	

	/* Geheimzahl einlesen */

	play = 1;
	while(play) {
		printf("Gib deine Geheimzahl ein (4 unterschiedliche Ziffern): ");	
		while(1) {
			/* Variable um einen Fehler zu behandeln */

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

				rc = readline(0, secret, MAXLEN);
				if (rc < 0) { /* Fehler -> Ausgabe */
					perror("readline");
					exit(1);
				}
				*(secret+rc) = (char) 0; /* String erstellen */

				err = checknumber(secret);				
				if(err == 1) {
					printf("Gib deine Geheimzahl ein (4 unterschiedliche Ziffern): ");	
					err = 0;
					continue;
				}

				if (strcasecmp(secret, "exit\n") == 0) 
					continue;
				if (strcasecmp(secret, "quit\n") == 0) 
					continue;
				if (strcasecmp(secret, "ok\n") == 0) 
					continue;
				if (strcasecmp(secret, "\n") == 0) 
					continue;
				if (strcasecmp(secret, "\0") == 0) 
					continue;

				/* Geheimzahl senden */
				str_senden(sd, "GEHEIMZAHL: ", secret, rc);
				break;
			}
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
	printf("Erraten Sie die Geheimzahl Ihres Gegenspielers!\n");
	printf("Ihr Versuch: ");
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
				printf("Erraten Sie die Geheimzahl Ihres Gegenspielers!\n");
				printf("Ihr Versuch: ");
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
			if (rc < 0) { /* Fehler -> Ausgabe */
				perror("readline");
				exit(1);
			}
			*(buffer+rc) = '\0';

			if(rundenende) {
				printf("rundenende\n");
				if (strncasecmp(buffer, "j\n", 2) == 0) {
					/* String senden */
					if(senden(sd, "bereit\n") < 0) {
						perror("senden");	
						exit(1);
					}
	waitforserverreply(sd,1);

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

			rc = checknumber(buffer);				
			if(rc == 1) {
				printf("Das war keine gültige 4-stellige Zahl!\n");
				printf("Keine Ziffer darf doppelt vorkommen!\n");
				printf("Bitte geben Sie eine 4-stellige Geheimzahl ein: ");	
				rc = 0;
				continue;
			}

			/* Geheimzahl senden */
			str_senden(sd, "ZAHL: ", buffer, 5);

	waitforserverreply(sd,1);
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

int checknumber(char secret[MAXLEN])
{
	int i,j, rc;
	int zahl;
	int ziffer[4];

	for(i=0; i < 4; i++) {
		if(!isdigit(secret[i]))
			return 1;
	}

	/* War die Eingabe eine 4-stellige Zahl? */
	if ( secret[4] != '\n' )
		/* Fehler */
		return 1;

	rc = sscanf( secret, "%d", &zahl);
	if (rc != 1 ) 
		/* Fehler */
		return 1;

	/* ist es wirklich eine 4-stellige Integerzahl */
	ziffer[0] = zahl / 1000;
	if( ziffer[0] > 9 || ziffer[0] < 0) {
		/* Fehler */
		return 1;
	}
	else {
		ziffer[1] = zahl % 1000 / 100;
		ziffer[2] = zahl % 1000 % 100 / 10;
		ziffer[3] = zahl % 1000 % 100 % 10;
		/*	printf("%d\n", ziffer[0]);
			printf("%d\n", ziffer[1]);
			printf("%d\n", ziffer[2]);
			printf("%d\n", ziffer[3]);
			*/
		/* Überprüfen auf doppelte Ziffern */
		for( i = 0; i <= 3; i++) {
			for( j = 0; j <= 3; j++) {
				if ( i == j ) 
					continue;
				if( ziffer[i] == ziffer[j] ) {
					/* Fehler */
					return 1;
				}
			}
		}
	}

	return 0;
	//----------------------------------
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

void waitforserverreply(int sd, int spieler) 
{
	int rc;
	int timeout = 0;
	int abbruch;
	char buffer[MAXLEN];
	fd_set bereit;

	timeout = 0;
	abbruch = 0;

	while(1) {
		struct timeval zeit;

		FD_ZERO(&bereit);
		FD_SET( 0, &bereit);
		FD_SET( sd, &bereit);


		zeit.tv_sec = 1;
		zeit.tv_usec = 0;

		rc = select(MAX(0,sd) + 1, &bereit, NULL, NULL, &zeit);
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
			timeout++;
			if(timeout > TIMEOUT) {
				printf("\nDer Server hat nach %d Sekunden immernoch nicht geantwortet!\n",TIMEOUT);
				printf("Abbrechen/aufgeben? (j/n): ");
				timeout = 0;
				abbruch = 1;
			}

			continue;
		}
		if (FD_ISSET(0, &bereit)) {
			/* stdin auslesen */	
			rc = readline(0, buffer, MAXLEN);
			if (rc < 0) {
				perror("readline");
				exit(1);
			}
			/* String erstellen */
			*(buffer+rc) = (char) 0;

			if(abbruch) {
				abbruch = 0;
				printf("%s\n", buffer);
				if (strcasecmp(buffer, "j\n") == 0) {
					/* Fehlerfunktion */
					exit(1);
				}
			}
			else {
				/* Tue nichts, Eingabe ist nicht erlaubt */
				printf("Derzeit ist keine Eingabe erlaubt, es wird auf den Gegenspieler gewartet.\n");
			}
			continue;
		}

		break;
	}
	printf("\n");

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
