#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <ctype.h>
#include <string.h>

#include "snp.h"

#define MAXLEN	80

int main(int argc, char *argv[])
{
	char buffer[MAXLEN];
	int rc;
	int i;
	int n;
	int dot;
	char *cp;
	int wasdigit;
	char number[MAXLEN];
	char line[MAXLEN];
	//double zahl;
	char c;


	wasdigit = 0;

	printf("Eingabe bitte:\n");


	while (rc = readline(0, buffer, MAXLEN-1), rc > 0) {

		if( rc > 1) {

			buffer[rc-1] = (char)0; /* -1 überschreibt das \n */

			printf("\"%s\" wurde gelesen.\n", buffer);

			printf("Ausgabe:\n");
			cp = buffer;


			for( i = 0; i < rc-1 ; i++) {
				if ( !isdigit(*cp)) {
					/*es ist keine Zahl*/
					strncat(line, cp , 1);

				}
				else {
					/*es ist eine Zahl*/
					while( isdigit(*cp) || *cp == '.') {

						printf("Zahl erkannt\n");
						cp++;
						if(*cp == '.')
							break;
					}
					while( isdigit(*cp)) {
						printf("Zahl erkannt\n");
						cp++;
					}

				}
				cp++;

			}










			printf("\"%s\"\n", line);

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
