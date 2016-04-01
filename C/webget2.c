#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>

#include "snp.h"

#define	MAXLEN	128

void usage(char *progname);

int main(int argc, char *argv[])
{
	int sd;
	int rc;
	struct sockaddr_in adresse;
	struct hostent *server;
	char buffer[MAXLEN];
	int verbose = 0;
	u_short port = 80;
	char host[MAXLEN] = "";
	char defuri[MAXLEN] = "/index.html";
	char *uri = defuri; /* was anzuzeigen ist (Unified Resource Identifier URI) */
	int optchar;

	opterr = 0;
	while(optchar = getopt(argc, argv, "p:Hh:"), optchar != -1) {
		switch(optchar) {
			case 'H': /* verbose? */
				verbose = 1;
				break;
			case 'h': /* Servername host übernehmen */
				strncpy(host, optarg, MAXLEN-1);
				break;
			case 'p': /* Portnummer übernehmen */
				rc = sscanf(optarg, "%hu", &port);
				if (rc != 1) {
					printf("Fehler: Die Portnummer ist nicht angegeben oder keine Zahl\n");
					usage(argv[0]);
				}
				break;
			default:
				usage(argv[0]);
		}
	}

	if (*host == (char)0) {
		usage(argv[0]);
	}

	if (argc > optind) {
		uri = argv[optind]; /* Ein Zeiger! */
	}

	printf("URL: %s:%hu/%s\n", host, port, *uri == '/' ? uri+1 : uri);

	sd = socket(AF_INET, SOCK_STREAM, 0);
	if (sd < 0) {
		perror("socket");
		exit(1);
	}

	bzero(&adresse, sizeof(adresse));

	adresse.sin_family = AF_INET;
	adresse.sin_port = htons(port);
	server = gethostbyname(host);
	if (server == NULL) {
		// if (h_errno == HOST_NOT_FOUND)
			fprintf(stderr, "%s: Host not found\n", host);
		exit(1);
	}
	adresse.sin_addr = *(struct in_addr *) server->h_addr;

	rc = connect(sd, (struct sockaddr *) &adresse, sizeof(adresse));
	if (rc < 0) {
		perror("connect");
		exit(1);
	}
	printf("connected\n");

	/* Aufbau des HTTP-Befehls */
	/* Dies erzeugt eine zeichenkette mit MAXLEN Zeichen, da %*s
	   eine Zeichenkette mit vielen Leerzeichen erzeugt :-(
	 */
	// sprintf(buffer, "GET %*s HTTP/1.0\r\n\r\n", MAXLEN-18, uri);

	/* Besser: mit strcpy() und strcat() :-) */
	strcpy(buffer, "GET ");
	strncat(buffer, uri, MAXLEN-18);
	strcat(buffer, " HTTP/1.0\r\n\r\n");

	/* Schreiben an den Server */
	rc = write (sd, buffer, strlen(buffer));
	if (rc < 0) {
		perror("write");
		exit(1);
	}

	/* Lesen vom Server */
	printf("==== BEGIN DATA ====\n");
	while (1) {
		rc = readline(sd, buffer, MAXLEN-1);
		if (rc < 0) {
			perror("read");
			exit(1);
		}
		if (rc == 0)
			break;

		buffer[rc] = (char)0; /* make a string */
		if (verbose == 0 && strcmp(buffer, "\r\n") == 0) {
			verbose = 1;
			continue;
		}

		if (verbose)
			write(1, buffer, rc);
	}
	printf("==== END DATA =====\n");

	close(sd);
	return 0;
}

void usage(char *progname)
{
	printf("Usage: %s [Optionen] [uri]\n", progname);
	printf("Optionen:\n");
	printf("\t-h host\tServernamen (host) setzen\n");
	printf("\t-p #\tPortnummer # setzen (default: 80)\n");
	printf("\t-H\tAnzeige auch der HTTP-Daten\n");
	printf("uri\tAnzuzeigende URI (default: /index.html)\n");
	printf("Der Servername (Option -h) muss angegeben werden!\n");
	exit(1);
}
