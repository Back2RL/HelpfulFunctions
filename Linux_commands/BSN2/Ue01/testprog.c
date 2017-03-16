#include <stdio.h>
#include "libs/file1.h"
#include "libs/file2.h"


int scanInt(char *prompt);

int main(int argc, char *argv[]){

	int zahl = scanInt("Bitte eine Zahl eingeben: ");
	printf("Das 1. Ergebnis: %d\n", addSeven(zahl));
	printf("Das 2. Ergebnis: %d\n", addTwo(zahl));

	return 0;
} 

int scanInt(char *prompt)
{
	int rc;
	int zahl;

	do {
		printf("%s ", prompt); /* Prompt */
		rc = scanf("%d", &zahl);
		while( getchar() != '\n')
			continue;
	}
	while(rc != 1);
	printf("Gelesen wurde: %d\n", zahl);

	return zahl;
}

