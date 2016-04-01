#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "readline.c"
#include <ctype.h>

#define MAXLEN 1000

void usage(char *program);

int main(int argc, char *argv[]) {

	char input[MAXLEN];
	char output[MAXLEN];
	int tupper;
	int tlower;
	int charnout;
	int zahl = 0;
	int optchar;
	int rc;
	int pos;
	char cp;

	if (argc < 2)
		usage(argv[0]);

	opterr = 0;
	tupper = tlower = charnout = 0;

	while (optchar = getopt(argc, argv, "ulhn:"), optchar != -1) {
		switch(optchar) {
			case 'u':
				if (tlower != 1)
					tupper = 1;
				break;

			case 'l':
				if (tupper != 1)
					tlower = 1;
				break;

			case 'h':
				usage(argv[0]);
				break;
			case 'n':
				if (optarg == NULL)
					usage(argv[0]);

				rc = sscanf(optarg, "%d", &zahl);
				if (rc != 1)
					usage(argv[0]);

				break;

			default: /* Falsche Option übergeben */
				usage(argv[0]);
		}
	}
	/*
	   printf("%d\n", tupper);
	   printf("%d\n", tlower);
	   printf("%d\n", zahl);
	   */


	while(1) {	
		rc = readline(0, input, sizeof(input) - 1);
		if (rc < 0) {
			perror("read");
			return 1;
		}

		input[rc-1] = '\0';

		//printf("%s\n", input);




		pos = 0;
		while (rc = read(input[pos], &cp, 1), rc >= 1) {

			if(tupper) {
				cp = (char) toupper(cp);
				if(rc < 1) {
					perror("toupper");
					exit(1);
				}
			}
			output[pos++] = cp;
		printf("%c\n", cp);
			
		}

		output[pos-1] = '\0';
		printf("%s\n", output);

	}








	return 0;
}


void usage(char *program) 
{
	printf("help\n");	

	exit(1);
}

