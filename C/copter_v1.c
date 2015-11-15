#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define G 9.81

double scanDouble(char *prompt);
int scanInt(char *prompt);

int main(int argc, char *argv[])
{

	double Imax;
	double I;
	double Fgmax;
	double m;
	double mges;
	double power;
	int n;
	int nmax;

	nmax = scanInt("Maximale Motorenanzahl: ");
	m = scanDouble("Gesamtgewicht ohne Motoren und Regler (in g): ");
	m /=1000;

	while(1) {

		Fgmax =  scanDouble("Maximaler Schub pro Motor (in g): ");
		Imax = scanDouble("Stromverbrauch eines Motors (in A): ");

		Fgmax /=1000;


		for ( n = 1; n <= nmax; n++ ) {



			mges = m + ( (double) n * ( 0.05 + 0.05 ));

			//	I = (mges * Imax) / (Fgmax * n);


			power = mges / (Fgmax * n);
			
			I = pow(power,1.5)  * Imax;

		printf("Iges( %2d ) = %4.1lfA\t\tm( %2d ) = %5.0lfg", n, I*n, n, mges*1000);			
		printf("\tpower = %3.0lf\n", power * 100);


		};
		printf("\n");


	}







	return 0;
}




/* Funktionen */


double scanDouble(char *prompt)
{
	int rc;
	double zahl;

	do {
		printf("%s", prompt);
		rc = scanf("%lf", &zahl);
		while (getchar() != '\n')
			continue;
	}
	while (rc != 1);

	return zahl;
}

int scanInt(char *prompt)
{
	int rc;
	int zahl;

	do {
		printf("%s", prompt);
		rc = scanf("%d", &zahl);
		while (getchar() != '\n')
			continue;
	}
	while (rc != 1);

	return zahl;
}

