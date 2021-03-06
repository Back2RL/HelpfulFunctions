#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <signal.h>
#include <errno.h> /* immer am Ende */

void abfangen(int signal);
void reset(int sig);
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

	while (1) {	
		/* Was soll passieren wenn SIGINT eintrifft? */
		if (signal(SIGINT, abfangen) == SIG_ERR) {
			perror("SIGINT");
			exit(10);
		}

		if (signal(SIGQUIT, reset) == SIG_ERR) {
			perror("SIGINT");
			summe = 0.0;
			n = 0.0;
			exit(11);
		}

		zahl	= scanDouble("Summand: ");
		summe += zahl;
		n++;
		printf("Summe = %lf ; Anzahl Summanden = %d\n", summe, n);

	}

	return 0;
}

void abfangen(int sig)
{

	excode++;			
	printf("\nStrg-C wurde %dx gedrueckt.\n", excode);
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
	printf("Summand:  ");
	fflush(stdout);

	return;
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

