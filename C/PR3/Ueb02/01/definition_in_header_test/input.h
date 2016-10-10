
#include "input.h"
#include <stdio.h>

char get_char(const char * const prompt);
char get_input(const char * const prompt);

char get_char(const char * const prompt){
	char read = '\0';
	int rc = 0;

	do{
		printf("%s", prompt);
		rc = scanf("%c", &read);
		/* zusaetzliche Zeichen weglesen */
		while(getchar() != '\n'){
			continue;
		}
	} while(rc != 1);

	return read;
} 

char get_input(const char * const prompt){
	int read = 0;
	int rc = 0;

	do{
		printf("%s", prompt);
		rc = scanf("%d", &read);
		/* zusaetzliche Zeichen weglesen */
		while(getchar() != '\n'){
			continue;
		}
	} while(rc != 1);

	return read;
} 
