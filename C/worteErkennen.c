#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <ctype.h>
#include <string.h>

#include "snp.h"

#define MAXLEN	80

int istEineZahl(char *buffer);

int main(int argc, char *argv[])
{
	char buffer[MAXLEN];
	int rc;
	char *cp;
	char wort[MAXLEN] = "";
	double zahl;

	printf("Eingabe bitte:\n");

	while (rc = readline(0, buffer, MAXLEN-1), rc > 0) {
		if( rc > 1) {
			/* buffer in  einen String umwandeln */
			buffer[rc-1] = (char)0; /* -1 überschreibt das '\n' */

			printf("\"%s\" wurde gelesen.\n", buffer);

			cp = buffer;

			while (*cp) {
				rc = sscanf(cp, "%s", wort);
				printf("Wort: [%s]\n", wort);

				if (istEineZahl(wort)) {
					rc = sscanf(wort, "%lf", &zahl);
					if (rc == 1)
						printf("Zahl+1: %lf\n", zahl+1);
				}

				while(!isspace(*cp) && *cp)
					cp++;
				if (*cp)
					cp++;
			}
		}
		else {
			printf("Es wurde nichts eingegeben.\n");
		}
	}

	if(rc < 0) {
		perror("read");
		exit(1);
	}
	return 0;
}

int istEineZahl(char *buffer)
{
	int istZahl = 1;

	if (*buffer == '-' || *buffer == '+')
		buffer++;

	while (*buffer) {
		if (*buffer == '.') {
			buffer++;
			break;
		}
		if (!isdigit(*buffer)) {
			istZahl = 0;
			break;
		}
		buffer++;
	}
	if (istZahl == 0)
		return 0;

	while (*buffer) {
		if (!isdigit(*buffer)) {
			istZahl = 0;
			break;
		}
		buffer++;
	}
	return istZahl;
}
