#include <stdlib.h>
#include <string.h>
#include <stdio.h>


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

double scanDouble(char *prompt)
{
	int rc;
	double zahl;

	do {
		printf("%s ", prompt); /* Prompt */
		rc = scanf("%lf", &zahl);
		while( getchar() != '\n')
			continue;
	}
	while(rc != 1);
	printf("Gelesen wurde: %.2lf\n", zahl);

	return zahl;
}

void scanString(char *prompt, char *text, int maxlen)
{
	char *nl;

	do {
		printf("%s", prompt);
		if (fgets(text, maxlen, stdin) == NULL) {
			exit(1); /* Fehler oder EOF -> Abbruch */
		}
		/* Newline entfernen */
		nl = strchr(text, '\n');
		if (nl != NULL)
			*nl = (char)0;
	}
	while (strlen(text) == 0);
}

