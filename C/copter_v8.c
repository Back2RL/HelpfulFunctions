/**
 *	Mi 18. Nov 13:03:45 CET 2015
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 * 
 *	-----------------------------------------
 *	Programmbeschreibung
 */

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <string.h>
#include <ctype.h>
#include "gauss.c"
#include <math.h>
#include <string.h>
#include <ctype.h>

#define G 9.81
#define MAXLEN 100
#define MAXLWERTE 10

double matelements(double werte[2][MAXLWERTE], int j, int n);
double stvektelements(double werte[2][MAXLWERTE], int potenz, int n);
double FQS(double werte[2][MAXLWERTE], double c[3], int n);
double funktion(double c[3], double x);
double funktion2(double c[3], double x);
double copter_funktion(int n, char *text, double m);
void scanString(char *prompt, char *text, int maxlen);
double scanDouble(char *prompt);
int scanInt(char *prompt);
double readValue(char *string);
int matrixausgabe(double *a, int size, int zeilen);
void readFile(char *file, char *desc, char *descv);

int main(int argc, char *argv[])
{

	char mdesc[2][MAXLEN] = {"#Motordescription (text)",""};
	char pdesc[2][MAXLEN] = {"#Propellerdescription (text)",""};
	char psdesc[2][MAXLEN] = {"#size [in] (eg. 8x4)",""};
	char wbdesc[2][MAXLEN] = {"#weight battery [kg]",""};
	char wrvdesc[2][MAXLEN] = {"#weight receiver + voltmeter [kg]",""};
	char wmspdesc[2][MAXLEN] = {"#weight motor + speedcontroller + propeller [kg]",""};
	char wcdesc[2][MAXLEN] = {"#weight camera [kg]",""};
	char wfdesc[2][MAXLEN] = {"#weight fuselage (center piece) [kg]",""};
	char wadesc[2][MAXLEN] = {"#weight arms [kg]",""};
	char wtdesc[2][MAXLEN] = {"#weight tilt mechanism + servo [kg]",""};
	char again[MAXLEN];
	double bsize;
	double I;
	double m[7];
	double mges;
	double flightTime;
	int n;
	int nmax;

	if (argc < 2) {
		fprintf(stderr, "Fehlender Dateiname! Aufruf des Programms:\ncopter.exe data.txt\n");
		exit(1);
	}


	readFile(argv[1], mdesc[0], mdesc[1]);

	readFile(argv[1], pdesc[0], pdesc[1]);

	readFile(argv[1], psdesc[0], psdesc[1]);

	readFile(argv[1], wbdesc[0], wbdesc[1]);
	m[0] = readValue(wbdesc[1]);

	readFile(argv[1], wrvdesc[0], wrvdesc[1]);
	m[1] = readValue(wrvdesc[1]);

	readFile(argv[1], wmspdesc[0], wmspdesc[1]);
	m[2] = readValue(wmspdesc[1]);

	readFile(argv[1], wcdesc[0], wcdesc[1]);
	m[3] = readValue(wcdesc[1]);

	readFile(argv[1], wfdesc[0], wfdesc[1]);
	m[4] = readValue(wfdesc[1]);

	readFile(argv[1], wadesc[0], wadesc[1]);
	m[5] = readValue(wadesc[1]);

	readFile(argv[1], wtdesc[0], wtdesc[1]);
	m[6] = readValue(wtdesc[1]);


	while(1) {
		nmax = scanInt("\nMaximale Motorenanzahl: ");
		bsize = scanDouble("Akkukapazitaet [mAh]:");
		printf("\n");		
		printf("Anzahl | I(ges) | m(ges) | Schwebeflugdauer\n");			
		printf("-------------------------------------------\n");
		for ( n = 1; n <= nmax; n++ ) {
			if ( (n % 2) != 0) {

				mges = m[0] + m[1] + n * (m[2] + m[5]) + m[3] + m[4] + 1 * m[6];
			}
			else {

				mges = m[0] + m[1] + n * (m[2] + m[5]) + m[3] + m[4] + 0 * m[6];
			}	

			I = copter_funktion(1,argv[1], mges/n);

			flightTime = ((0.8 * (bsize / 1000)) / (I*n) ) * 60.0; 			
			printf("%6d |%6.1lfA |%6.0lfg |\t%5.1lfmin\n", n, I*n,  mges*1000, flightTime );			
		};

		scanString("\nNochmal? (j/n) : ", again, MAXLEN);
		if (strcasecmp(again, "n") == 0)
			exit(0); // programm stopped by user
	}

	return 0;
}

double readValue(char *string)
{
	int rc;
	double zahl;

	rc = sscanf(string,"%lf\n", &zahl);
	if (rc > 1 ) {
		printf("Zu viele Werte!\n");
		zahl = 0;
	}
	if (rc < 1 ) {
		zahl = 0;
	}
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


void readFile(char *file, char *desc, char *descv)
{
	FILE *inputfile;
	int rc;
	int v;
	char line[MAXLEN];

	if (inputfile = fopen(file, "r"), inputfile == NULL) {
		perror(file);
		exit(1);
	}


	v = 0;
	while(fgets(line, MAXLEN, inputfile) != NULL) {
		if( v == 1) {
			rc = strncmp(line, "#", 1) ;
			if (rc == 0){
				v = 0;
				strcpy (descv, "---\n\0");
				continue;
			}
			else {
				v = 0;
				strcpy (descv, line);
				continue;
			}
		}

		rc = strncmp(line, desc, strlen(desc) );
		if (rc == 0) {
			v = 1;
			continue;
		}
	}
	rc = strcmp(descv, "\n\0") ;
	if (rc == 0){
		strcpy (descv, "---\n\0");
	}

	printf("%s\n%s", desc, descv);

	if(!feof(inputfile)) {
		perror(file);
		exit(1);
	}

	/* Datei schließen */
	fclose(inputfile);

}

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

double copter_funktion(int n, char *text, double m)
{

	FILE *datei;
	char zeile[MAXLEN], *start;
	int  ns, rc, i, k, j;
	double  drc, x, xalt, y, z, t, messwerte[2][MAXLWERTE], matrix[3][4], A[3][3], Ai[3][3], b[3], xi[3]; 

	if (datei = fopen(text, "r"), datei == NULL) {
		perror(text);
		exit(1);
	}

	/*----------Einlesen-der-Daten-------------*/
	n = 0;
	while(fgets(zeile, MAXLEN, datei) != NULL) {
		/* Zeile verarbeiten (sie hat ein \n am Ende, wenn komplett eingelesen) */

		start = zeile;
		while(isspace(*start))
			start++;
		if(*start == '\0' || *start == '#')
			continue;

		rc = sscanf( zeile, "%lf %lf %lf", &t, &y, &x);
		if(rc != 3)
			continue;


		messwerte[0][n] = x / 1000;
		messwerte[1][n] = y;

		//printf("Eingelesen: ( %7.4lf , %7.4lf )\n",messwerte[0][n], messwerte[1][n]);
		n++;
	}

	/* Gab es einen Fehler oder sind wir am Dateiende (EOF) */
	if(!feof(datei)) {
		perror(text);
		exit(1);
	}

	//printf("\nDas waren %d Messwertpaare.\n", n);
	/* Datei schließen */
	fclose(datei);
	/*-----------------------------------------*/

	//printf("\nBerechnete Matrix:\n\n");
	/*-----------------------------------------*/
	for ( j = 0; j < 4; j++) {
		matrix[0][j] = matelements( messwerte, 4-j, n);
		//		if ( j != 3)
		//			//printf("(1;%d) %10.4lf\t", j+1, matrix[0][j]);
	}

	for ( i = 1; i < 3 ;i++) {
		//		//printf("\n");
		for ( j = 0; j < 3-(i-1) ;j++ ) {
			matrix[i][j] = matrix[i-1][j+1];
			//				//printf("(%d;%d) %10.4lf\t", i+1, j+1, matrix[i][j]);
		}
	}

	matrix[2][2] = n;
	//		//printf("(3;3) %10.4lf\n", matrix[2][2]);

	for ( i = 0; i < 3 ;i++) {
		for ( j = 0; j < 3; j++) {
			A[i][j] = matrix[i][j];
		}
	}

	matrixausgabe(&A[0][0], 3, 3);
	/*-----------------------------------------*/

	//printf("\nBerechneter Störvektor:\n\n");
	/*-----------------------------------------*/
	for( i = 0; i < 3 ;i++ ) {
		b[i] = stvektelements(messwerte, 2-i, n);
		//			//printf("(1;%d) %10.4lf\n", i+1, b[i]);
	}
	matrixausgabe(&b[0], 1, 3);
	/*-----------------------------------------*/

	/*Gleichungsystem lösen (Cramersche Regel)*/
	//printf("\nBerechneter Vektor x:\n\n");
	/*-----------------------------------------*/
	if( (drc = gauss(&A[0][0], 3, 3)) != 0) {
		//	//printf("\ndrc= %10.4lf\n", drc);

		for ( k = 0; k < 3; k++) {

			for ( i = 0; i < 3 ;i++) {
				for ( j = 0; j < 3; j++) {
					if(k == j) {
						Ai[i][j] = b[i];
					}
					else
					{
						Ai[i][j] = A[i][j];
					}
				}
			}

			//			matrixausgabe(&Ai[0][0], 3, 3);
			xi[k] =  gauss(&Ai[0][0], 3, 3) / drc;
		}

		/* Ausgabe*/
		matrixausgabe(&xi[0], 1, 3);
		//printf("\nDie Parabelgleichung lautet:\n\n");

		//printf("f(x) = %.4lfx² ", xi[0]);
		if ( xi[1] < 0) {
			//printf("%.4lfx ", xi[1]);
		}
		else {
			//printf("+ %.4lfx ", xi[1]);
		}

		if ( xi[2] < 0) {
			//printf("%.4lf\n", xi[2]);
		}
		else {
			//printf("+ %.4lf\n", xi[2]);
		}
		//printf("\nFQS = %.4lf\n", FQS(messwerte, xi, n));

		/* Startwert setzen */
		//printf("\nNullstellen:\n\n");
		n--;
		ns = 0;
		for (z = 0; z < messwerte[0][n]; z += 1E-1) {
			/*Vorzeichenwechsel?*/

			if(funktion( xi, z) * funktion( xi, z+1E-1) > 0)
				continue;

			x = z + 1E-1;
			xalt = z ;
			do{
				x -= funktion( xi, x) * (x - xalt) / (funktion( xi, x) - funktion( xi, xalt));
				xalt = x;
			}
			while(fabs(funktion( xi, x) - funktion( xi, xalt)) / fabs(funktion( xi, xalt)) >= 1E-7);

			ns++;
			//printf("x(%d) = %10.4lf\n", ns, x);
		}
		if( ns == 0)
			;			//printf("Es existieren keine Nullstellen im Suchbereich.\n");
	}
	else {
		//printf("Die Determinate der Matrix A ist null, das Gleichungssystem ist nicht eindeutig lösbar!\n");
		exit(1);
	}

	z = (xi[0] * pow(m,2)) + (xi[1] * m)+(xi[2]);
	return z;
}

double FQS(double werte[2][MAXLWERTE], double c[3], int n)
{
	int i;
	double sum;

	sum = 0;
	for (i = 0; i < n; i++) {
		sum += pow( werte[1][i] - funktion( c, werte[0][i]), 2);
	}

	return sum;
}

double funktion(double c[3], double x)
{
	return c[0] * pow( x, 2) +  c[1] * x + c[2];
}

int matrixausgabe(double *a, int size, int zeilen)
{
	int i, j;

	for ( i = 0; i < zeilen ;i++) {
		for ( j = 0; j < size; j++) {
			//printf("(%d;%d) %10.4lf\t", i+1, j+1, a[i*size + j]);
		}
		//printf("\n");
	}

	return 1;
}

double matelements(double werte[2][MAXLWERTE], int j, int n)
{

	int i;
	double wert;

	wert = 0;
	for( i=0 ; i < n; i++) {

		wert += (double) pow( werte[0][i],j);
	}

	return wert;
}

double stvektelements(double werte[2][MAXLWERTE], int potenz, int n)
{

	int i;
	double wert;

	wert = 0;
	for( i = 0 ; i < n; i++) {

		wert += (double) pow( werte[0][i], potenz) * werte[1][i];
	}

	return wert;
}
