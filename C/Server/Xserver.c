// vim: ts=4 sw=4
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <signal.h>
#include <sys/wait.h>
#include <readline.h>

#define MAX(a, b)    ((a) > (b)) ? (a) : (b)
#define    MAXLEN    128

void do_service(int sd[2]);

void abfangen(int sig);

int empfangen(int in, char *buffer, int maxlen);

int senden(int out, char *buffer);

int main(int argc, char *argv[]) {
	int socketDescriptor;
	int rc;
	struct sockaddr_in adresse;
	u_short port = 48556;
	int talkDescriptor[2]; /* Talk-Sockets */
	int nClients; /* Anzahl der Clients */
	int flag;

	/* Socket anlegen */
	errno = 0;
	socketDescriptor = socket(AF_INET, SOCK_STREAM, 0);
	if (socketDescriptor == -1 || errno != 0) {
		perror("Fehler beim Erstellen des Sockets");
		exit(EXIT_FAILURE);
	}

	flag = 1;
	errno = 0;
	if (setsockopt(socketDescriptor, SOL_SOCKET, SO_REUSEADDR, &flag, sizeof(flag)) == -1 || errno != 0) {
		perror("setsockopt-REUSEADDR");
	}

	/* Socket an alle Schnittstellen binden */
	errno = 0;
	if (memset(&adresse, 0, sizeof(adresse)) == NULL || errno != 0) {
		perror("Fehler beim Setzen des Speichers auf 0");
	}

	adresse.sin_family = AF_INET;
	adresse.sin_port = htons(port);
	adresse.sin_addr.s_addr = INADDR_ANY;

	errno = 0;
	if (bind(socketDescriptor, (struct sockaddr *) &adresse, sizeof(adresse)) == -1 || errno != 0) {
		perror("Fehler beim Binder der Adresse an den Socket");
		exit(EXIT_FAILURE);
	}
	printf("Server an Port %hu gebunden\n", port);

	/* Client-Warteschlange aktivieren */
	errno = 0;
	if(listen(socketDescriptor, 5) == -1 || errno !=0){
		perror("listen");
		exit(EXIT_FAILURE);
	}

	/* Ende der Kindprozesse abfangen */
	if (signal(SIGCHLD, abfangen) == SIG_ERR) {
		perror("Signal SIGCHLD");
		exit(1);
	}

	/* Forken, wenn 2 Clients Kontakt mit uns haben! */
	nClients = 0;

	while (1) {
		/* Warten auf 2 Clients */
		while (nClients != 2) {
			/* Erst einmal warten auf den 1. Client */
			talkDescriptor[nClients] = accept(socketDescriptor, NULL, NULL);
			if (talkDescriptor[nClients] < 0) {
				if (errno == EINTR)
					continue;

				perror("accept");
				exit(1);
			}
			/* Wir haben 1 Client */
			nClients++;

			/* Warten auf den 2. Client
			 * Es kann aber passieren, dass der erste Client sich wieder
			 * abmeldet. Also muss ein select() ausgeführt werden, um
			 * das auch mitzubekommen!
			 */
			/* Solange wir nur einen Client haben ... */
			while (nClients == 1) {
				fd_set bereit;
				char buffer[MAXLEN];

				FD_ZERO(&bereit);
				FD_SET(socketDescriptor, &bereit);
				FD_SET(talkDescriptor[0], &bereit);

				rc = select(talkDescriptor[0] + 1, &bereit, NULL, NULL, NULL);
				if (rc < 0) {
					if (errno == EINTR)
						continue;
					perror("select");
					exit(1);
				}
				if (rc == 0)
					continue;

				/* Der 1. Client hat sich gemeldet
				 * Versuchen zu lesen, was er gesendet hat. Wenn wir
				 * EOF erkennen, hat er sich abgemeldet und wir haben
				 * nun keinen Client mehr!
				 */
				if (FD_ISSET(talkDescriptor[0], &bereit)) {
					rc = (int) read(talkDescriptor[0], buffer, MAXLEN);
					if (rc <= 0) { /* Fehler oder EOF */
						if (rc < 0)
							perror("read"); /* Fehlerausgabe */
						/* Verbindung schließen und Anzahl der Clients dekrementieren */
						close(talkDescriptor[0]);
						nClients--;
					}
				}

				/* Ein 2. Client hat Kontakt aufgenommen */
				if (FD_ISSET(socketDescriptor, &bereit)) {
					talkDescriptor[nClients] = accept(socketDescriptor, NULL, NULL);
					if (talkDescriptor[nClients] < 0) {
						if (errno == EINTR)
							continue;

						perror("accept");
						exit(1);
					}
					/* Anzahl der Clients erhöhen. Kann nun 2 oder auch 1 sein! */
					nClients++;
				}
			} /* while (nClients == 1) */
		} /* while (nClients != 2) */

		/* Wir haben genau 2 Clients. Nun forken, damit es losgeht */
		rc = fork();
		if (rc < 0) {
			perror("fork");
			exit(1);
		}

		if (rc == 0) { /* Kindprozess */
			/* Signal SIGCHLD zurücksetzen! */
			if (signal(SIGCHLD, SIG_DFL) == SIG_ERR) {
				perror("Signal SIGCHLD");
				exit(1);
			}

			/* Kontaktsocket schließen */
			close(socketDescriptor);

			/* Service ausführen mit den beiden Deskriptoren */
			do_service(talkDescriptor);
			exit(0);
		}
		/* Vaterprozess */
		/* Beide Talk-Sockets schließen */
		close(talkDescriptor[0]);
		close(talkDescriptor[1]);
		/* Wir haben nun keinen Client mehr. Also warten auf das nächste Paar */
		nClients = 0;
	} /* while (1) */
}

/* Crossover-Server
 * ================
 * Datenaustausch von Client zu Client (Crossover)
 * Protokoll:
 * Nur der Client, der dran ist, darf etwas senden, was dann weiter an
 * den anderen Client gesendet wird.
 * Hierzu wird an einen Client READY und an den anderen WAIT gesendet.
 * Wer dran ist, wird in der Variablen dran gespeichert (Index für td[])
 */
void do_service(int td[2]) {
	char buffer[MAXLEN];
	int rc;
	int dran = 0; /* der 1. Client soll beginnen! */

	if (signal(SIGPIPE, SIG_IGN) == SIG_ERR) {
		perror("signal SIGPIPE");
		exit(2);
	}

	/* Client 1 ist dran, Client 2 soll warten */
	if (senden(td[0], "Bereit zum Empfang\n") < 0)
		return;
	if (senden(td[1], "Bitte warten\n") < 0)
		return;

	/* Beide Clients können jederzeit etwas senden, obwohl gemäß Protokoll
	 * nur einer von beiden senden darf!
	 * Deshalb überwachen wir beide Deskriptoren mit selelct()
	 */
	while (1) {
		fd_set bereit;

		FD_ZERO(&bereit);
		FD_SET(td[0], &bereit);
		FD_SET(td[1], &bereit);

		rc = select(MAX(td[0], td[1]) + 1, &bereit, NULL, NULL, NULL);
		if (rc < 0) {
			if (errno == EINTR)
				continue;
			perror("select");
			exit(1);
		}
		if (rc == 0)
			continue;

		/* Client 1 hat sich gemeldet
		 * entweder etwas gesendet oder aber den Socket geschlossen
		 */
		if (FD_ISSET(td[0], &bereit)) {
			/* Erst einmal empfangen (kann auch EOF sein!) */
			rc = empfangen(td[0], buffer, MAXLEN);
			if (rc <= 0) {
				/* Client 1 ist nicht mehr erreichbar - Ende der Kommunikation */
				break;
			}
			if (dran != 0) {
				if (senden(td[0], "FEHLER: Du bist nicht dran - Bitte warten\n") < 0)
					break;
			} else {
				if (strcasecmp(buffer, "OVER\n") == 0) {
					dran = 1;
					if (senden(td[1], "Bereit zum Empfang\n") < 0)
						break;
					if (senden(td[0], "Bitte warten\n") < 0)
						break;
				}
				if (dran == 0) {
					if (senden(td[1], buffer) < 0)
						break;
				}
			}
		}
		/* Client 2 hat sich gemeldet
		 * entweder etwas gesendet oder aber den Socket geschlossen
		 */
		if (FD_ISSET(td[1], &bereit)) {
			/* Erst einmal empfangen (kann auch EOF sein!) */
			rc = empfangen(td[1], buffer, MAXLEN);
			if (rc <= 0) {
				/* Client 2 ist nicht mehr erreichbar - Ende der Kommunikation */
				break;
			}
			if (dran != 1) {
				if (senden(td[1], "FEHLER: Du bist nicht dran - Bitte warten\n") < 0)
					break;
			} else {
				if (strcasecmp(buffer, "OVER\n") == 0) {
					dran = 0;
					if (senden(td[0], "Bereit zum Empfang\n") < 0)
						break;
					if (senden(td[1], "Bitte warten\n") < 0)
						break;
				}
				if (dran == 1) {
					if (senden(td[0], buffer) < 0)
						break;
				}
			}
		}
	}
	/* Ende der Komunikation
	 * Senden von ENDE an beide Clients.
	 * Ist einer der Sockets bereits geschlossen, so soll das jetzt egal sein!
	 */
	senden(td[0], "ENDE!\n");
	senden(td[1], "ENDE!\n");
}

void abfangen(int sig) {
	/* wait muss erfolgreich sein, da wir gerade ein Signal erhalten haben */
	int pid = wait(NULL);

	fprintf(stderr, "Kind %d beendet\n", pid);
}

/* Empfangen einer Zeile über einen Deskriptor */
int empfangen(int in, char *buffer, int maxlen) {
	int rc;

	rc = readline(in, buffer, (unsigned int) (maxlen - 1));
	if (rc <= 0) { /* Fehler oder EOF */
		if (rc < 0) /* Fehler -> Ausgabe */
			perror("read");
		return rc;
	}
	*(buffer + rc) = (char) 0; /* String erstellen */
	printf("empfangen von %d -> %s", in, buffer);
	return rc;
}

/* Senden einer Zeichenkette über einen Deskriptor
 * Rückgabe: 0 für okay / -1 bei Fehler oder EOF (EPIPE)
 */
int senden(int out, char *buffer) {
	int rc;

	printf("senden an %d -> %s", out, buffer);
	rc = (int) write(out, buffer, strlen(buffer));
	if (rc < 0) {
		if (errno != EPIPE) /* Wenn nicht EPIPE: Fehlerausgabe */
			perror("write");
		return -1;
	}
	return 0;
}
