// vim: ts = 4 sw = 4
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

#include "include/snp.h"

int excode = 0;
int sd;
char opponentplayer[MAXLEN];
char playername[MAXLEN];
char secret[MAXLEN];

/*------------------------------------------------------------------------------------*/
int main(int argc, char *argv[])
{
	struct sockaddr_in adresse;	/* Internet-Adress-Struktur */
	u_short port = 12345;		/* Default Serverportnr.: 12345 */
	struct hostent *server; 	/* Zeiger für Server-Information */
	char *servername;
	int rc;
	int optchar;

	/* SIGINT abfangen */
	if (signal(SIGINT, abfangen) == SIG_ERR) {
		perror("SIGINT");
		ende(-1);
	}
	/* SIGQUIT ignorieren */
	if (signal(SIGQUIT, SIG_IGN) == SIG_ERR) {
		perror("SIGQUIT");
		ende(-1);
	}
	/* SIGPIPE abfangen */
	if (signal(SIGPIPE, ende) == SIG_ERR) {
		perror("SIGPIPE");
		ende(-1);
	}

	/* Optionen auslesen */
	opterr = 0;
	while(optchar = getopt(argc, argv, "p:"), optchar !=  -1) {
		switch(optchar) {
			case 'p': /* Portnummer setzen */
				rc = sscanf(optarg, "%hu", &port);
				if (rc !=  1)
					usage(argv[0]);
				break;
			default:
				usage(argv[0]);
		}
	}
	if (optind >= argc) {
		fprintf(stderr, "Hostname bitte übergeben\n");
		perror("Hostname");
		usage(argv[0]);
		ende(-1);
	}
	servername = argv[optind];

	/* Socket erstellen */
	sd = socket(AF_INET, SOCK_STREAM, 0);
	if (sd < 0) {
		perror("socket");
		ende(-1);
	}
	printf("Server: %s\n", servername);
	printf("Port  : %hu\n", port);
	printf("=============\n");

	/* Adress-Struktur belegen */
	memset(&adresse, 0, sizeof(adresse));
	adresse.sin_family = AF_INET;
	adresse.sin_port = htons(port);
	server = gethostbyname(servername);

	if (server == NULL) {
		perror(servername);
		ende(-1);
	}
	adresse.sin_addr = *(struct in_addr *) server->h_addr;

	/* Verbindung zum Server erstellen */
	if (connect(sd, (struct sockaddr *)&adresse, sizeof(adresse)) < 0) {
		perror("connect");
		usage(argv[0]);
		ende(-1);
	}

	/* starten des Spiels Mastermind */
	mastermind(sd);

	/* nach Spielende Socket schließen */
	close(sd);
	
	/* Program beenden */
	ende(0);

	/* Main-Funktion Ende */
	return 0;

}
/*------------------------------------------------------------------------------------*/

void usage(char *progname) /* Bedienungsanleitung */
{
	printf("Aufruf: %s [Optionen] Hostname\n", progname);
	printf("Optionen:\n");
	printf("\t-h #\tdiese Hilfe anzeigen\n");
	printf("\t-p #\tPortnummer setzen (default: 12345)\n");
	exit(0);
}
/*------------------------------------------------------------------------------------*/

void mastermind(int sd) /* Die Spiel-Funktion */
{
	int rc;
	int err;
	int gamerounds;
	char buffer[MAXLEN];
	/*char tmp[MAXLEN];*/

	/* stdout ungepuffert, damit write(1, ..) und printf() genutzt werden können */
	setbuf(stdout, NULL);

	/* Warten auf den Start der Kommunikation */
	waitforserverreply(sd, 0, buffer);
	
	/* Auf Fehler überprüfen */
	if (strcasecmp(buffer, "HALLO\n") !=  0) {
		perror("Hallo");	
		ende(-1);
	}

	/* Hallo mit ok bestätigen */
	ok(sd);

	/* Spielernamen einlesen beginnt: */
	printf("Gib deinen Spielernamen ein!\n");
	printf("Dein Spielername: ");

	while(1) {
		fd_set bereit;

		FD_ZERO(&bereit);
		FD_SET(0, &bereit);
		FD_SET(sd, &bereit);

		/* Prüfen ob Socket oder stdin bereit sind */
		rc = select(MAX(0, sd) + 1, &bereit, NULL, NULL, NULL);
		/* Auf Fehler überprüfen */
		if (rc < 0) {
			if (errno == EINTR) 
				continue;
			perror("select");
			ende(-1);
		}

		/* While-Schleife fortsetzen falls nicht bereit war */
		if (rc == 0)
			continue;

		/* Es kam eine Nachricht vom Server (Socket) */
		if (FD_ISSET(sd, &bereit)) {
			/* empfangen (kann auch EOF sein!) */
			if (empfangen(sd, buffer, MAXLEN) <= 0) {
				/* Server ist nicht mehr erreichbar - Ende */
				perror("empfangen");
				ende(-1);
			}
			continue;		
		}

		/* Der Spieler hat etwas eingegeben und mit '\n' bestätigt */
		if (FD_ISSET(0, &bereit)) {
			/* von Eingabe lesen */
			rc = readline(0, buffer, MAXLEN-1);
			/* Fehler -> Ende */
			if (rc < 0) {
				perror("readline");
				ende(-1);
			}
			/* String erstellen */
			*(buffer+rc) = '\0';

			/* Prüfen ob gültiger Name eingegeben wurde */
			/* Prüfen auf Länge */
			if (strlen(buffer) == 1) {
				printf("Bitte einen gültigen Spielernamen angeben: ");
				/* Nochmal einlesen */
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

	/* Warten auf "OK" vom Server */
	waitforserverreply(sd, 0, buffer);

	/* Überprüfen ob wirklich "OK" empfangen wurde */
	if (strcasecmp(buffer, "ok\n") !=  0) {
		perror("OK");	
		ende(-1);
	}

	/* Warten auf Gegenspielername */
	waitforserverreply(sd, 0, buffer);

	/* Auf Fehler überprüfen */
	if (strncasecmp(buffer, "name: ", 6) !=  0) {
		perror("name");	
		ende(-1);
	}

	/* mit "OK" bestätigen */
	ok(sd);

	/* Sichern des Gegenspielernamens */
	strcpy(opponentplayer, &buffer[6]);

	/* Newline vom Namen entfernen */
	opponentplayer[strlen(opponentplayer) - 1] = '\0';

	/* Namen des Gegenspielers ausgeben */	
	printf("Dein Gegenspieler nennt sich: %s.", opponentplayer);	

	/* Warten auf Anzahl der Runden */
	waitforserverreply(sd, 0, buffer);

	/* Auf Fehler überprüfen */
	if (strncasecmp(buffer, "runden: ", 6) !=  0) {
		perror("Runden");	
		ende(-1);
	}

	/* mit "OK" bestaetigen */
	ok(sd);

	/* Speichern der Rundenzahl */
	if (sscanf(buffer, "%*s %d", &gamerounds) < 1) {
		perror("not rounds");
		ende(-1);
	}

	/* dem Spieler die Rundenanzahl mitteilen */
	printf("Es sind maximal %d Runden vereinbart.\n\n", gamerounds);	

	/* Runden beginnt */
	while(1) {
		/* Geheimzahl einlesen */
		printf("Gib deine Geheimzahl ein (4 unterschiedliche Ziffern): ");	
		while(1) {

			fd_set bereit;

			FD_ZERO(&bereit);
			FD_SET(0, &bereit);
			FD_SET(sd, &bereit);

			rc = select(MAX(0, sd) + 1, &bereit, NULL, NULL, NULL);
			if (rc < 0) {
				if (errno == EINTR) 
					continue;
				perror("select");
				ende(-1);
			}

			if (rc == 0)
				continue;

			if (FD_ISSET(sd, &bereit)) {
				if (empfangen(sd, buffer, MAXLEN) <= 0) {
					perror("empfangen");
					ende(-1);
				}
				continue;		
			}

			if (FD_ISSET(0, &bereit)) {
				rc = readline(0, secret, MAXLEN);
				if (rc < 0) {
					perror("readline");
					ende(-1);
				}
				*(secret+rc) = '\0';
				
				/* Eingelesenen String auf korrekte Zahl prüfem */
				err = checknumber(secret);				
				if (err == 1) {
					printf("Gib deine Geheimzahl ein (4 unterschiedliche Ziffern): ");	
					err = 0;
					/* Bei Fehler neu einlesen */
					continue;
				}
				
				/* Geheimzahl senden */
				str_senden(sd, "GEHEIMZAHL: ", secret, rc);

				/* Einles Schleife beenden */
				break;
			}
		} /* Geheimzahl-Einlesen-Schleife Ende */

		waitforserverreply(sd, 1, buffer);
		if (strcasecmp(buffer, "ok\n") !=  0) {
			perror("ok");	
			ende(-1);
		}
		
		/* Warte auf Nachricht um Raterunde zu starten */
		waitforserverreply(sd, 0, buffer);
		if (strcasecmp(buffer, "start\n") !=  0) {
			perror("start");	
			ende(-1);
		}

		/* "Start" mit "OK" bestaetigen */
		ok(sd);

		printf("Es kann los gehen!\n");
		printf("==================\n\n");

		/* Raterunde beginnt */
		if (!runde(sd))
			break;
	} /* Spiel wird beendet wenn runde nicht 1 zurückgibt */

	/* Mastermindfunktion Ende -> zurück zur Main-Funktion -> Programmende */
	return;
}
/*------------------------------------------------------------------------------------*/

/* hier wird der Rundenablauf gesteuert */
int runde(int sd) 
{
	int rc, err;	/* Returncodes/Fehler */
	int erg[3];	/* Ergebnis des Ratens, z.B.: {1234,3,1} */
	int rundenende;	/* Wenn 1 wird Abfrage für neue Runde aufgerufen */
	char buffer[MAXLEN];

	rundenende = 0;
	printf("Errate die Geheimzahl von %s!\n", opponentplayer);
	printf("Gib deinen Versuch ein (4 unterschiedliche Ziffern): ");
	while(1) {

		fd_set bereit;

		FD_ZERO(&bereit);
		FD_SET(0, &bereit);
		FD_SET(sd, &bereit);

		/* Eingabe vom Nutzer oder Nachricht vom Server abwarten */
		rc = select(MAX(0, sd) + 1, &bereit, NULL, NULL, NULL);
		if (rc < 0) {
			if (errno == EINTR) 
				continue;
			perror("select");
			ende(SIGTERM);
		}

		if (rc == 0)
			continue;

		if (FD_ISSET(sd, &bereit)) {
			if (empfangen(sd, buffer, MAXLEN) <= 0) {
				perror("empfangen");
				ende(-1);
			}

			/* es kommt quit */
			if (strcasecmp(buffer, "quit\n") == 0) {
				perror("Der Server ist nicht mehr erreichbar!");
				ende(-1);
			}

			/* überprüfen ob Gewonnen/Verloren/Remis */
			if (strcasecmp(buffer, "gewonnen\n") == 0) { 
				printf("\n\nYuppieh - Du hast gewonnen!\n");
				printf("=========== \n");
				/* mit OK bestätigen */
				ok(sd);
			}
			if (strcasecmp(buffer, "verloren\n") == 0) {   
				printf("\n\nSchade - Du hast verloren...\n");
				printf("=========== \n");
				/* mit OK bestätigen */
				ok(sd);
			}
			if (strcasecmp(buffer, "remis\n") == 0) {
				printf("\n\nUnentschieden - Beide haben es gleichzeitig geschafft!\n");
				printf("=========== \n");
				/* mit OK bestätigen */
				ok(sd);
			}
			/* Gegenspielerversuch empfangen -> Ausgabe */
			if (strncasecmp(buffer, "versuch:", 8) == 0) {
				/* Gegenspielerversuch auslesen */
				rc = sscanf(buffer, "%*s %d", &erg[0]);
				if (rc != 1) {
					perror("versuch empfangen");
					ende(-1);
				}
			/* Ausgabe des Versuchs */
				printf("\t%s hat geraten: %04d ", opponentplayer, erg[0]); 
				printf("(Deine Geheimzahl lautet: %s)\n", secret);

				/* mit "OK" bestaetigen */
				ok(sd);
			}
			/* Das Ergebnis des eigenen Versuchs empfangen */
			if (strncasecmp(buffer, "ergebnis:", 9) == 0) {
				/* Zahlen auslesen */
				rc = sscanf(buffer, "%*s %d %d %d", &erg[0], &erg[1], &erg[2]);
				if (rc !=  3) {
					perror("ergebnis");
					ende(-1);
				}
				/* Ausgabe des Ergebnis vom Server */
				printf("\tDein Versuch %04d ergab (richtig: Anzahl / Stelle): %d / %d\n", erg[0], erg[1], erg[2]);  
				/* mit "OK" bestaetigen */
				ok(sd);

				printf("\nGib deinen neuen Versuch ein (4 unterschiedliche Ziffern): ");
			}
			/* Abfrage für neue Runde empfangen */
			if (strcasecmp(buffer, "noch einmal?\n") == 0) {
				printf("Willst du noch einmal spielen (j/n)? ");
				/* eine Runde hat ein Ergebnis */
				rundenende = 1;
				/* mit "OK" bestaetigen */
				ok(sd);
			}
			continue;		
		}
		
		/* stdin ist bereit */
		if (FD_ISSET(0, &bereit)) {
			rc = readline(0, buffer, MAXLEN);
			if (rc < 0) {
				perror("readline");
				ende(-1);
			}

			if (rc > 5){
				printf("Gib deinen Versuch ein (4 unterschiedliche Ziffern): ");
				continue;
			}
			*(buffer+rc) = '\0';
			
			/* bei Rundenende wird (j/n) als Eingabe erwartet */
			if (rundenende) {
				if (strncasecmp(buffer, "j\n", 2) == 0) {
					/* String senden */
					if (senden(sd, "bereit\n") < 0) {
						perror("senden");	
						ende(-1);
					}
					/* Auf anderen Spieler warten */
					waitforserverreply(sd, 1, buffer);
					printf("\n======> Eine neue Runde beginnt <======\n\n");
					/* gebe 1 zurück um eine weitere Runde zu starten */
					return 1;
				}

				if (strncasecmp(buffer, "n\n", 2) == 0) {
					/* gebe 0 zurück um keine weitere Runde zu starten */
					return 0;
				}
				else {
					printf("Falsche Eingabe, erwarte (j/n)\n");
					continue;
				}
			}
			
			/* eingelesene Zahl auf Richtigkeit */
			err = checknumber(buffer);				
			if (err == 1) {
				/* Bei Fehler neu einlesen */
				printf("Gib deinen Versuch ein (4 unterschiedliche Ziffern): ");
				err = 0;
				continue;
			}
			/* Bei korrekter Zahl -> an den Server senden */
			else {

				/* Geheimzahl senden */
				str_senden(sd, "ZAHL: ", buffer, 5);
				/* Antwort abwarten */
				waitforserverreply(sd, 1, buffer);
				if (strcasecmp(buffer, "ok\n") !=  0) {
					perror("ok");	
					ende(-1);

				}
			}
		}
	} /* Zahl-einlesen-Schleife */
} /* Raterunde - Ende */
/*------------------------------------------------------------------------------------*/

int checknumber(char *buffer)
{
	int i, j;
	int ziffer[4];

	for(i = 0; i < 4; i++) {
		if (!isdigit(buffer[i]))
			return 1;
		ziffer[i] = buffer[i];
	}

	/* War die Eingabe eine 4-stellige Zahl? */
	if (buffer[4] !=  '\n')
		/* Fehler */
		return 1;

	/* Überprüfen auf doppelte Ziffern */
	for(i = 0; i <= 3; i++) {
		for(j = 0; j <= 3; j++) {
			if (i == j) 
				continue;
			if (ziffer[i] == ziffer[j]) {
				/* Sobald eine Zahl doppelt ist einen Fehler zurückgeben */
				return 1;
			}
		}
	}
	return 0;
}
/*------------------------------------------------------------------------------------*/

void str_senden(int sd, char topic[MAXLEN], char data[MAXLEN], int rc)
/* baut einen String (string = "topic data") und sendet ihn an den Server */
{
	char buffer[MAXLEN];

	*(data+rc-1) = '\0'; /* String erstellen */

	strncpy(buffer, topic, MAXLEN);
	strncat(buffer, data, MAXLEN - strlen(data) - 2);
	strncat(buffer, "\n", MAXLEN - strlen(buffer) - 1);

	/* String senden */
	if (senden(sd, buffer) < 0) {
		perror("senden");	
		ende(-1);
	}

}
/*------------------------------------------------------------------------------------*/

void ok(int sd)
{
	if (senden(sd, "OK\n") < 0) {
		perror("Ok senden");	
		ende(-1);
	}
}
/*------------------------------------------------------------------------------------*/

void waitforserverreply(int sd, int spieler, char *buffer) 
/* wartet auf Nachricht vom Server und empfängt diese anschließend */
{
	int rc;
	int timeout; /* verschtrichende Zeit */
	int abbruch;
	fd_set bereit;

	timeout = 0;
	abbruch = 0;

	while(1) {
		struct timeval zeit;

		FD_ZERO(&bereit);
		FD_SET(0, &bereit);
		FD_SET(sd, &bereit);

		zeit.tv_sec = 1;
		zeit.tv_usec = 0;

		rc = select(MAX(0, sd) + 1, &bereit, NULL, NULL, &zeit);
		if (rc < 0) {
			if (errno !=  EINTR) {
				perror("select");
				ende(-1);
			}
			continue;
		}

		if (rc == 0) {
			if (spieler) { 
				/* wird ein einziges Mal aufgerufen wenn spieler 1 gesetzt wurde */
				printf("Warte auf %s!\n", opponentplayer);
				spieler = 0;
			}
			/* Ausgabe eines Wartezeichens (einmal pro Sekunde) */
			write(1, "-", 1);
			timeout++;
			
			/* Bei zu langer Wartezeit dem Spieler dies mitteilen */
			if (timeout > TIMEOUT) {
				printf("\nDer Server hat nach %d Sekunden immernoch nicht geantwortet!\n", TIMEOUT);
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
				ende(-1);
			}
			/* String erstellen */
			*(buffer+rc) = '\0';

			if (abbruch) {
				abbruch = 0;
				if (strcasecmp(buffer, "j\n") == 0) {
					/* Fehlerfunktion */
					ende(0);
				}
			}
			else {
				/* Tue nichts, Eingabe ist nicht erlaubt */
				printf("Bitte warten!\n");
			}
			continue;
		}

		/* Server hat etwas gesendet */
		if (FD_ISSET(sd, &bereit)) {
			if (empfangen(sd, buffer, MAXLEN) <= 0) {
				perror("empfangen");
				ende(-1);

			}
			break;
		}
	}
	printf("\n");

}
/*------------------------------------------------------------------------------------*/

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
	*(buffer+rc) = '\0'; /* String erstellen */
	if (strcasecmp(buffer, "quit\n") == 0) {
		/* Fehlerfunktion */
		ok(sd);
		ende(1);
	}
	return rc;
}
/*------------------------------------------------------------------------------------*/

/* Senden einer Zeichenkette über einen Deskriptor
 * Rückgabe: 0 für okay / -1 bei Fehler oder EOF(EPIPE)
 */
int senden(int out, char *buffer)
{
	int rc;

	rc = (int) write(out, buffer, strlen(buffer));
	if (rc < 0) {
		if (errno !=  EPIPE) /* Wenn nicht EPIPE: Fehlerausgabe */
			perror("write");
		return rc;
	}
	return 0;
}
/*------------------------------------------------------------------------------------*/

void ende(int sig)
{
	char buffer[MAXLEN];

	if (sig == 0) {
		/* normales Ende */
		printf("\nProgramm beendet\n");
	}
	else if (sig > 0) {
		if (sig == SIGPIPE) {
			printf("\nFehler: Server existiert nicht!\n");
		}
		else {
			/* Ende durch Server oder Gegenspieler verursacht */
			printf("\nAbbruch: Server hat das Spiel beendet oder Gegner hat aufgegeben!\n");
		}
	}
	else {
		/* bei negativen Zahlen -> Fehler -> vom Server verabschieden */
		printf("\nAbbruch: Fehler!\n");
		if (senden(sd, "quit\n") < 0) {
			perror("senden");	
			exit(-2);
		}

		waitforserverreply(sd, 0, buffer);
		if (strcasecmp(buffer, "ok\n") !=  0) {
			perror("not ok");	
			exit(-2);
		}
	}

	fflush(stdout);
	/* Socket schließen */
	close(sd);
	/* Ende */
	exit(0);
}
/*------------------------------------------------------------------------------------*/


void abfangen(int sig)
/* SIGINT abfangen */
{
	if (2-excode !=  0)
		printf("\nStrg-C noch %dx druecken zum Beenden.\n", 2 - excode);
	excode++;
	if (excode == 3) {
		ende(0);
	}
	else {
		fflush(stdout);
	}
	return;
}
/*------------------------------------------------------------------------------------*/
