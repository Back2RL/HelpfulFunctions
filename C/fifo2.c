#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include "readline.c"

#define MAXLEN 80


int main(int argc, char *argv[])
{
	int rc;
	int in, out;
	char buffer[MAXLEN];

	if (argc < 3) {
		fprintf(stderr, "Bitte zwei FIFO-Namen übergeben\n");
		exit(1);
	}

	umask(0);
	rc = mkfifo(argv[2], 0666);
	if (rc < 0) {
		perror(argv[2]);
		exit(1);
	}

	printf("Öffnen der FIFO %s\n", argv[1]);
	out = open(argv[1], O_WRONLY);
	if (out < 0) {
		perror(argv[1]);
		exit(1);
	}

	/* Schreiben des Namens der 2. FIFO */
	rc = write(out, argv[2], strlen(argv[2]));
	if (rc < 0) {
		perror("write");
		exit(1);
	}
	rc = write(out, "\n", 1);
	if (rc < 0) {
		perror("write");
		exit(1);
	}

	printf("Öffnen der FIFO %s\n", argv[2]);
	in = open(argv[2], O_RDONLY);
	if (in < 0) {
		perror(argv[2]);
		exit(1);
	}

	/* Schreib-/Leseschleife oder Lese-/Schreibschleife */
	printf("Lesen aus der FIFO\n");
	while (1) {
		rc = readline(0, buffer, MAXLEN);
		if (rc < 0) {
			perror("read");
			exit(1);
		}
		if (rc == 0)
			break;
		rc = write(out, buffer, (unsigned int) rc);
		if (rc < 0) {
			perror("write");
			exit(1);
		}

		rc = readline(in, buffer, MAXLEN);
		if (rc < 0) {
			perror("read");
			exit(1);
		}
		if (rc == 0)
			break;
		rc = write(1, buffer, (unsigned int) rc);
		if (rc < 0) {
			perror("write");
			exit(1);
		}
	}

	close(in); /* 1. FIFO schließen */
	close(out); /* 2. FIFO schließen */

	printf("Entfernen der FIFO\n");
	rc = unlink(argv[2]);
	if (rc < 0) {
		perror(argv[2]);
		exit(1);
	}
	return 0;
}
