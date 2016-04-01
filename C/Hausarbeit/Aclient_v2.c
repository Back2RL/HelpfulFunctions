// vim: ts=4 sw=4
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <sys/select.h>
#include <signal.h>
#include "readline.c"
#include <errno.h>

#include "snp.h"

#define MAX(a,b)	((a) > (b)) ? (a) : (b)
#define	MAXLEN 256

void usage(char *progname);
void mastermind(int sd);
int empfangen(int sd, char *buffer, int maxlen);
int senden(int sd, char *buffer);
int ok(int sd);
int waitforserverreply(int sd); 

int main(int argc, char *argv[])
{
	int sd;
	struct sockaddr_in adresse; /* Internet-Adress-Struktur */
	u_short port = 12345;
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
		exit(2);
	}
	adresse.sin_addr = *(struct in_addr *) server->h_addr;

	if (connect(sd, (struct sockaddr *)&adresse, sizeof(adresse)) < 0) {
		perror("connect");
		exit(1);
	}

	mastermind(sd);

	close(sd);
	return 0;
}

void usage(char *progname)
{
	printf("Usage: %s [Optionen] Hostname\n", progname);
	printf("Optionen:\n");
	printf("\t-p #\tPortnummer setzen (default: 12345)\n");
	exit(1);
}

void mastermind(int sd)
{
	int rc;
	char buffer[MAXLEN];
	char playername[MAXLEN];
	char opponentplayer[MAXLEN];
	char secret[MAXLEN];
	fd_set bereit;
	int gamerounds;
	int n = 0;


	/* stdout ungepuffert, damit write(1,..) und printf() genutzt werden können */
	setbuf(stdout, NULL);
	printf("Client gestartet\n");

	if (signal(SIGPIPE, SIG_IGN) == SIG_ERR) {
		perror("signal SIGPIPE");
		exit(2);
	}

	/* Warten auf den Start der Kommunikation
	 * Wir nutzen select um jede Sekunde einen Punkt auszugeben ...
	 */
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
			write(1, ".", 1);
			continue;
		}
		printf("\n");
		break;
	}

	/* Der Socket ist nun bereit zur Kommunikation */

	if(empfangen(sd, buffer, MAXLEN) <= 0) {
		exit(1);
	}
	if (strcasecmp(buffer, "hallo\n") != 0) {
		perror("No Hallo");	
		exit(1);
	}

	rc = (int) write(1, buffer, strlen(buffer));
	if (rc < 0) {
		perror("write");
		exit(1);
	}

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

			rc = readline(0, buffer, MAXLEN-1);
			if (rc < 0) { /* Fehler -> Ausgabe */
				perror("readline");
				exit(1);
			}
			*(buffer+rc) = (char) 0; /* String erstellen */
			if (strcasecmp(buffer, "quit\n") == 0) 
				break;

			sscanf(buffer,"%s", playername);
			printf("Angegebener Name: %s", playername);
			strncpy(buffer, "name: ",MAXLEN);
			strncat(buffer, playername, MAXLEN - strlen(playername) - 2);
			strncat(buffer, "\n", MAXLEN - strlen(buffer) - 1);

			/* Spielernamen senden */
			if(senden(sd, buffer) < 0) {
				perror("senden");	
				exit(1);
			}
			/* Warten auf "OK" vom Server */
			waitforserverreply(sd);
			if(empfangen(sd, buffer, MAXLEN) <= 0) {
				exit(1);
			}
			if (strcasecmp(buffer, "ok\n") != 0) {
				perror("not OK");	
				exit(1);
			}
			/* Warten auf Gegenspielername */
			waitforserverreply(sd);
			if(empfangen(sd, buffer, MAXLEN) <= 0) {
				exit(1);
			}
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
			printf("Dein Gegenspieler heisst: %s\n", opponentplayer);	
			/* Warten auf Anzahl der Runden */
			waitforserverreply(sd);
			if(empfangen(sd, buffer, MAXLEN) <= 0) {
				exit(1);
			}
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
			printf("Es sind %d Runden angesetzt.\n", gamerounds);	
			printf("Bitte geben Sie eine 4-stellige Geheimzahl ein: ");	
			while(rc != 5 ) {
			rc = readline(0, secret, 5);
			if (rc < 0) { /* Fehler -> Ausgabe */
				perror("readline");
				exit(1);
			}
			}
			*(secret+rc) = (char) 0; /* String erstellen */
			if (strcasecmp(secret, "quit\n") == 0) 
				break;

			strncpy(buffer, "geheimzahl: ",MAXLEN);
			strncat(buffer, secret, MAXLEN - strlen(secret) - 2);
			strncat(buffer, "\n", MAXLEN - strlen(buffer) -1);

			/* Spielernamen senden */
			if(senden(sd, buffer) < 0) {
				perror("senden");	
				exit(1);
			}
		}
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

int waitforserverreply(int sd) 
{
	fd_set bereit;
	int rc;

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
			write(1, ".", 1);
			continue;
		}
		printf("\n");
		break;
	}
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
