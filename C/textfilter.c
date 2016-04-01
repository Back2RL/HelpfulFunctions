#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <ctype.h>
#include <string.h>
#include "readline.c"

#include "./files/include/snp.h"

#define MAXLEN	80

void usage(char *pname);
int readline(int fd, char *buffer, unsigned int maxlen);

int main(int argc, char *argv[])
{
	int rc;
	int upper = 0, lower = 0;
	int n = 0; /* nur jedes n-te Zeichen. Wenn 0, dann alle */
	int optchar;
	int i;
	char buffer[MAXLEN], *cp;

	opterr = 0;
	while(optchar = getopt(argc, argv, "uln:"), optchar != -1) {
		switch(optchar) {
			case 'u':
				upper = 1;
				break;
			case 'l':
				lower = 1;
				break;
			case 'n':
				rc = sscanf(optarg, "%d", &n);
				if (rc != 1 || n <= 0)
					usage(argv[0]);
				break;
			default:
				usage(argv[0]);
				exit(1);
		}
	}
	if (upper && lower)
		usage(argv[0]);

	printf("Eingabe bitte:\n");
	while (rc = readline(0, buffer, MAXLEN-1), rc > 0) {
		buffer[rc] = (char)0;

		i = 0;
		cp = buffer;

		while (*cp) {
			if (lower)
				*cp = (char)tolower(*cp);
			else if (upper)
				*cp = (char)toupper(*cp);

			if (n > 0) {
				if (i % n == 0)
					write(1, cp, 1);
				i++;
				i = i % n;
			}
			else
				write(1, cp, 1);
			cp++;
		}
		write(1, "\n", 1);
	}
	if (rc < 0) {
		perror("read");
		exit(1);
	}
	return 0;
}

void usage(char *pname)
{
	char *slash;

	slash = strrchr(pname, '/');
	if (slash != NULL)
		pname = slash + 1;
	fprintf(stderr, "%s: Ausgabeprogramm\n", pname);
	fprintf(stderr, "\t-u: Uppercase\n");
	fprintf(stderr, "\t-l: Lowercase\n");
	fprintf(stderr, "\t-n #: nur jedes #-te Zeichen (# > 0)\n");
	exit(1);
}

