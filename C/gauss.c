#include <stdio.h>
#include <stdlib.h>
#include <math.h>

/* Berechnet die Determinante einer 2D-Matrix (quadr.)
 * mit dem Gauss-Algorithmus
 *
 * Argumente:
 *    double *matrix   ein Zeiger auf das erste Element
 *    int size         Größe des Arrays (sizeof() einer Zeile/Spalte)
 *    int n            Rang der Matrix (belegte Elemente!)
 *
 * Die Nutzung der doppelte Indexe (matrix[i][j]) funktioniert nicht,
 * da matrix nur ein Zeiger auf einen double ist. Allerdings kann der
 * Zeiger als eindimensionales Array genutzt werden. Dazu ist dann
 * der Index zu berechnen: i*size + j
 *
 * Aufruf dieser Funktion:
 *   double m[20][20];
 *   // m-Werte werden aus der datei eingelesen.
 *   // Annahme: es wurden nur 3 Zeilen mit 3 Werten eingelsen
 *   double d = gauss(&m[0][0], 20, 3);
 */
double gauss(double *a, int size, int n)
{
	double *m; /* Speicher für die Kopie */
	int k, z, s; /* Indexe */
	double d; /* für das Vorzeichen */

	/* Speicher für eine Matrix-Kopie holen */
	m = calloc((size_t)(n*n), sizeof(double));

	/* matrix kopieren */
	for (z = 0; z < n; z++) {
		for (s = 0; s < n; s++) {
			m[z*n + s] = a[z*size + s];
		}
	}

	/* für den Vorzeichenwechsel bei Vertauschen von Zeilen */
	d = 1;
	
	/* Bildet eine Dreiecksmatrix */
	for (z = 1; z < n; z++) {
		if (fabs(m[(z-1)*n + (z-1)]) < 1E-7) {
			for (k = z; k < n; k++) {
				if (fabs(m[k*n + (z-1)]) < 1E-7) {
					continue;
				}
				for (s = 0; s < n; s++) {
					double temp = m[(z-1)*n + s];
					m[(z-1)*n + s] = m[k*n + s];
					m[k*n + s] = temp;
				}
				d = -d;
				break;
			}
		}
		/* Steht auf der Hauptdiagonale eine Null?  => abbrechen */
		if (fabs(m[(z-1)*n + (z-1)]) < 1E-7) {
			d = 0;
			break;
		}
		for (k = z; k < n; k++) {
			double q;
			q = -m[k*n + (z-1)] / m[(z-1)*n + (z-1)];
			for (s = z-1; s < n; s++) {
				m[k*n + s] += m[(z-1)*n + s] * q;
			}
		}
	}
	/* Stand auf der Hauptdiagonale eine Null?  => fertig */
	if (d == 0) {
		free(m); /* Speicher wieder freigeben */
		return 0.0;
	}

	/* Multiplikation der Hauptdiagonalen mit dem Vorzeichen d */
	for (k = 0; k < n; k++) {
		d *= m[k*n + k];
	}
	/* Speicher wieder freigeben */
	free(m);
	return d;
}

