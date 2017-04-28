#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <netdb.h>
#include <signal.h>
#include <errno.h>
#include <readline.h>

#define    MAXLEN 256

void usage(char *progname);

void chat(int sd);

int empfangen(int sd, char *buffer, int maxlen);

int senden(int sd, char *buffer);

int main(int argc, char *argv[]) {
	int sd;
	struct sockaddr_in adresse; /* Internet-Adress-Struktur */
	u_short port = 48556;
	struct hostent *server; /* Zeiger für Server-Informationn */
	char *servername;
	int rc;
	int optchar;

	opterr = 0;
	while (optchar = getopt(argc, argv, "p:"), optchar != -1) {
		switch (optchar) {
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

	if (connect(sd, (struct sockaddr *) &adresse, sizeof(adresse)) < 0) {
		perror("connect");
		exit(1);
	}

	chat(sd);

	close(sd);
	return 0;
}

void usage(char *progname) {
	printf("Usage: %s [Optionen] Hostname\n", progname);
	printf("Optionen:\n");
	printf("\t-p #\tPortnummer setzen (default: 48556)\n");
	exit(1);
}

void chat(int sd) {
	int rc;
	char buffer[MAXLEN];
	fd_set bereit;

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
	while (1) {
		struct timeval zeit;

		FD_ZERO(&bereit);
		FD_SET(sd, &bereit);

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
	if (empfangen(sd, buffer, MAXLEN) <= 0) {
		exit(1);
	}

}

/* Empfangen einer Zeile über einen Deskriptor
 * Rückgabe: Zeichenanzahl > 0 / 0 bei EOF / -1 bei Fehler
 */
int empfangen(int in, char *buffer, int maxlen) {
	int rc;

	rc = readline(in, buffer, maxlen - 1);
	if (rc <= 0) { /* Fehler oder EOF */
		if (rc < 0) /* Fehler -> Ausgabe */
			perror("read");
		return rc;
	}
	*(buffer + rc) = (char) 0; /* String erstellen */
	return rc;
}

/* Senden einer Zeichenkette über einen Deskriptor
 * Rückgabe: 0 für okay / -1 bei Fehler oder EOF (EPIPE)
 */
int senden(int out, char *buffer) {
	int rc;

	errno = 0;
	rc = (int) write(out, buffer, strlen(buffer));
	if (rc == -1 || errno != 0) {
		if (errno != EPIPE) /* Wenn nicht EPIPE: Fehlerausgabe */
			perror("write");
		return -1;
	}
	return 0;
}
