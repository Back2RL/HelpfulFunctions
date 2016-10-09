#include "input.h"

int readInt(const char * const prompt){
	int read = 0;
	int rc = 0;
	do{
		printf("%s", prompt);
		rc = scanf("%d", &read);
		
		while(getchar() != '\n'){
			continue;
		}
	} while(rc != 1);
	return read;
} 
