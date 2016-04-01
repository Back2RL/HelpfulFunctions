#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <signal.h>
#include <errno.h> /* immer am Ende */

void abfangen(int signal);
void reset(int sig);
void ende(int sig);
double scanDouble(char *prompt);


int excode;
double summe;
int n;

int main(int argc, char *argv[])
{
	double zahl;

	excode = 0;
	summe = 0;
	n = 0;
	zahl = 1.0;

	while (zahl != 0.0) {	
		/* Was soll passieren wenn SIGINT eintrifft? */
		if (signal(SIGINT, abfangen) == SIG_ERR) {
			perror("SIGINT");
			exit(10);
		}

		if (signal(SIGQUIT, reset) == SIG_ERR) {
			perror("SIGINT");
			exit(11);
		}

		if (signal(SIGTERM, ende) == SIG_ERR) {
			perror("SIGINT");
			exit(12);
		}

		printf("------------------------------------------------\n");
		zahl	= scanDouble("Summand: ");
		excode = 0;
		if( zahl != 0.0) {		
			summe += zahl;
			n++;
		}
		printf("Summe = %7.2lf ; Anzahl Summanden = %5d\n", summe, n);

	}
	printf("ENDE\n");
	return 0;
}

void abfangen(int sig)
{
	if (2-excode !=0)
	printf("\nStrg-C noch %dx druecken zum Beenden.\n", 2 - excode);
	excode++;
	if(excode == 3) {
		exit(excode);
	}
	else {
		printf("Summand:  ");
		fflush(stdout);
	}
	return;
}

void reset(int sig)
{
	summe = 0;
	n = 0;
	printf("\nReset durchgefuehrt.\n");
	printf("------------------------------------------------\n");
	printf("Summe = %7.2lf ; Anzahl Summanden = %5d\n", 0.0, 0);
	printf("Summand:  ");
	fflush(stdout);

	return;
}

void ende(int sig)
{
	printf("Summe = %7.2lf ; Anzahl Summanden = %5d\n", summe, n);
	printf("ENDE\n");
	exit(SIGTERM);
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
	printf("Gelesen wurde: %7.2lf\n", zahl);

	return zahl;
}

