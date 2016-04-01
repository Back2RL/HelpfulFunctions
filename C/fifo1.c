#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#include "readline.c"

#define MAXLEN 80


int main(int argc, char *argv[])
{
	int rc;
	int in, out;
	char buffer[MAXLEN];
	char fifo2name[MAXLEN];

	if (argc < 2) {
		fprintf(stderr, "Bitte FIFO-Name übergeben\n");
		exit(1);
	}

	umask(0);
	rc = mkfifo(argv[1], 0666);
	if (rc < 0) {
		perror(argv[1]);
		exit(1);
	}

	printf("Öffnen der FIFO %s\n", argv[1]);
	in = open(argv[1], O_RDONLY);
	if (in < 0) {
		perror(argv[1]);
		exit(1);
	}

	/* Lesen des Namens der 2. FIFO */
	rc = readline(in, fifo2name, MAXLEN-1);
	if (rc < 0) {
		perror("readline");
		exit(1);
	}
	
	if (rc == 0) {
		/* EOF */
		exit(1);
	}
	
	if (rc < 2 ) {
		fprintf(stderr, "Fifoname 2 ist zu kurz\n");
		exit(1);
	}

	fifo2name[rc-1] = (char)0;

	printf("Öffnen der FIFO %s\n", fifo2name);

	out = open(fifo2name, O_WRONLY);
	if (out < 0) {
		perror(fifo2name);
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
	rc = unlink(argv[1]);
	if (rc < 0) {
		perror(argv[1]);
		exit(1);
	}
	return 0;
}
