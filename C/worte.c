#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

void usage(char *);
int readline(int fd, char *buffer, int maxlen);
char *getword(char *start, char *wort, int maxlen);

int main(int argc, char *argv[])
{
	char zeile[1024];
	char wort[1024];
	char *next;
	int rc;
	int uppercase = 0, lowercase = 0;
	int buffered = 0;
	int datei = 0; /* Standardeingabe */
	int optchar;
	
	opterr = 0;

	while (optchar = getopt(argc, argv, "c:b"), optchar != -1) {
		switch(optchar) {
			case 'c':
				if (optarg == NULL)
					usage(argv[0]);
				if (strcmp(optarg, "u") == 0)
					uppercase = 1;
				else if (strcmp(optarg, "l") == 0)
					lowercase = 1;
				else {
					fprintf(stderr,"Illegal argument %s\n", optarg);
					usage(argv[1]);
				}
				break;
			case 'b':
				buffered = 1;
				break;
			default:
				usage(argv[0]);
				exit(1);
		}
	}
	if (optind < argc) {
		/* Datei argv[optind] ist zu öffnen */
		datei = open(argv[optind], O_RDONLY);
		if (datei < 0) {
			perror(argv[optind]);
			exit(1);
		}
	}

	while (1) {
		rc = readline(datei, zeile, sizeof(zeile) - 1);
		if (rc < 0) {
			perror("read");
			return 1;
		}
		if (rc == 0)
			break;

		zeile[rc] = (char)0;

		next = zeile;
		do {
			next = getword(next, wort, sizeof(wort));
			if (*wort != (char)0) {
				if (uppercase) {
					char *cp = wort;

					while (*cp != (char)0) {
						*cp = toupper(*cp);
						cp++;
					}
				}
				else if(lowercase) {
					char *cp = wort;

					while (*cp != (char)0) {
						*cp = tolower(*cp);
						cp++;
					}
				}

				if (buffered)
					printf("%s\n", wort);
				else {
					write(1, wort, strlen(wort));
					write(1, "\n", 1);
				}
			}
		}
		while (*next != (char)0);
	}
	return 0;
}

void usage(char *name)
{
	fprintf(stderr, "Usage: %s [Options]\n", name);
	fprintf(stderr, "Options:\n");
	fprintf(stderr, "-c x\tcase x=u: uppercase x=l: lowercase\n");
	fprintf(stderr, "-b\tbuffered I/O\n");
	exit(1);
}

int readline(int fd, char *buffer, int maxlen)
{
	char c;
	int rc;
	int n = 0;

	while (n < maxlen) {
		rc = read(fd, &c, 1);
		if (rc < 0)
			return rc; /* Fehler */
		if (rc == 0)
			break; /* EOF */
		/* 1 Zeichen c wurde eingelesen */

		*(buffer + n) = c;
		n++;

		if (c == '\n')
			break;
	}
	return n;
}

/*
 * Liefert das 1. Wort einer Zeichenkette (start) im
 * 2. Argument (wort). Wird kein Wort gefunden, so wird
 * eine leere Zeichenkette geliefert!
 * Rückgabewert: Zeiger auf den Anfang des nächsten Wortes.
 */
char *getword(char *start, char *wort, int maxlen)
{
	int len = 0;

	*wort = (char) 0;

	while (isspace(*start))
		start++;
	if (*start == (char)0)
		return start;

	while (!isspace(*start) && len < maxlen) {
		*(wort + len) = *start;
		len++;
		start++;
	}
	*(wort + len) = (char) 0;

	while (isspace(*start))
		start++;
	return start;
}
