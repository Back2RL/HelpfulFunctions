#include <stdio.h>
#include <string.h>

int main(void) {
	char eingabe[32];
	char passwort[32] = "GeHeIm"; /* Dem Programmnutzer unbekannt */
	int i = 0;

	/* Ggf. muessen Sie die Reihenfolge der beiden vorstehenden
	   Array-Definitionen umkehren, um den unten beschriebenen Effekt
	   zu beobachten (plattformabhaengig). Auf den Pool-PCs ist die
	   obige Reihenfolge die richtige, um den gewuenschten Effekt zu
	   beobachten.
	 */
	printf("Bitte Passwort fuer den Hochsicherheitsbereich \
			eingeben:\n");
	/* Alle Zeichen (auch Leerzeichen etc.) bis Zeilenende lesen */
	/* scanf("%[^\n]", eingabe); */
	/* verbessert: */
	 scanf("%31[^\n]", eingabe);

	for (i=0; i<64; i++) {
		printf("%3d (%p): %02X %c\n",
				i, eingabe+i, (unsigned char)eingabe[i], eingabe[i]);
	}


	if (!strncmp(eingabe, passwort, strlen(passwort))) {
		printf("Passwort korrekt - Willkommen im \
				Hochsicherheitsbereich!\n");
		return 0;
	} else {
		printf("Passwort falsch - Zugang verweigert!\n");
		return -1;
	}
}
