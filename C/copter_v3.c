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

struct input {
	double V;
	double A;
	double g;
	struct input *next;
};
double matelements(double werte[2][MAXLWERTE], int j, int n);
double stvektelements(double werte[2][MAXLWERTE], int potenz, int n);
int matrixausgabe(double *a, int size, int zeilen);
double FQS(double werte[2][MAXLWERTE], double c[3], int n);
double funktion(double c[3], double x);
double funktion2(double c[3], double x);
double copter_funktion(int n, char *text, double m);


double function(struct input *root);
double scanDouble(char *prompt);
int scanInt(char *prompt);
void scanString(char *prompt, char *text, int maxlen);
int main(int argc, char *argv[])
{

	FILE *inputfile;
	char line[MAXLEN];
	struct input *root, *curr;
	double v[3];
	double Imax;
	double I;
	double Fgmax;
	double m;
	double mges;
	double power;
	int n;
	int i;
	int nmax;
	int rc;

	/* Dateiname sicherstellen (Argument!) */
	if (argc < 2) {
		fprintf(stderr, "Fehlender Dateiname! Aufruf des Programms:\ncopter.exe data.txt\n");
		exit(1);
	}

	/* Datei öffnen */
	if (inputfile = fopen(argv[1], "r"), inputfile == NULL) {
		/* hier Vorlagedatei erstellen */
		perror(argv[1]);

		exit(1);
	}

	/* Zeilenweise einlesen bis zum EOF */
	i = 0;
	root = NULL;

	while(fgets(line, MAXLEN, inputfile) != NULL) {
		rc = sscanf(line, "%lf %lf %lf", &v[0], &v[1], &v[2]);
		if (rc != 3)
			continue;

		if (root == NULL) {
			root = (struct input *) malloc(sizeof(struct input));
			root->V = v[0];
			root->A = v[1];
			root->g = v[2];
			root->next = NULL;
			curr = root;
		}
		else {
			curr->next = (struct input *) malloc(sizeof(struct input));
			curr = curr->next;
			curr->V = v[0];
			curr->A = v[1];
			curr->g = v[2];
			curr->next = NULL;
		}
		i++;
	}

	/* Gab es einen Fehler oder sind wir am Dateiende (EOF) */
	if(!feof(inputfile)) {
		perror(argv[1]);
		exit(1);
	}

	/* Datei schließen */
	fclose(inputfile);

	printf("%d Stichprobensaetze wurden erfolgreich eingelesen.\n", i);

	if (i < 2) {
		fprintf(stderr, "Hinweis: Zu wenig Messwerte für eine genaue Auswertung\n");
		curr->next = (struct input *) malloc(sizeof(struct input));
		curr = curr->next;
		curr->V = root->V;
		curr->A = 0;
		curr->g = 0;
		curr->next = NULL;
	}

	nmax = scanInt("Maximale Motorenanzahl: ");
	m = scanDouble("Gesamtgewicht ohne Motoren und Regler (in g): ");
	m /=1000;

	while(1) {

		Fgmax =  750; //scanDouble("Maximaler Schub pro Motor (in g): ");
		Imax = 13; //scanDouble("Stromverbrauch eines Motors (in A): ");

		Fgmax /=1000;


		for ( n = 1; n <= nmax; n++ ) {



			mges = m + ( (double) n * ( 0.103 ));

			I = copter_funktion(1,argv[1], mges/n);
			printf("Iges( %2d ) = %4.1lfA\t\tm( %2d ) = %5.0lfg\n", n, I*n, n, mges*1000);			
			//	I = (mges * Imax) / (Fgmax * n);
		/*	power = mges / (Fgmax * n);
			
			
			
			
			I = pow(power,1.5)  * Imax;

			printf("Iges( %2d ) = %4.1lfA\t\tm( %2d ) = %5.0lfg", n, I*n, n, mges*1000);			
			printf("\tpower = %3.0lf\n", power * 100);
*/

		};
		printf("\n");

		printf("Again? (j/n): ");
		if (fgets(line, MAXLEN, stdin) == NULL)
			;

		if (strcasecmp(line, "n\n") == 0)
			break;


	}







	return 0;
}




/* Funktionen */
double function(struct input *root)
{
	int n;
	double mw;
	struct input *curr;

	curr = root;
	mw = n = 0;
	while(curr != NULL) {
		n++;
		mw += curr->V * curr->A;
		curr = curr->next;
	}
	return mw/n;
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
