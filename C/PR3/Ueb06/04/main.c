#include <stdio.h>

double mehrwertsteuer(double nettobetrag){
	printf("Mwst. vom Netto: %.2f\n", 0.19 * nettobetrag);
	return 0;
}

double bruttobetrag(double nettobetrag){
	printf("Brutto vom Netto: %.2f\n", nettobetrag * 1.19);
	return 0;
}

double nettobetrag(double bruttobetrag){
	printf("Netto vom Brutto: %.2f\n", bruttobetrag / 1.19);

	return 0;
}

int main(int argc, char* args[]){
	double (*functions[3])(double a);
	int rc = 0;
	int choice = 0;
	double value = 0.0;

	functions[0] = mehrwertsteuer;
	functions[1] = bruttobetrag;
	functions[2] = nettobetrag;


	do{
		printf("Ihre Eingabe\n\t<function> [<betrag>]\n");

		printf("Bedeutung von <funktion>: 0=Mwst. vom Netto,");
		printf(" 1=Brutto vom Netto, 2=Netto vom Brutto, 3=Ende\n");

		printf("z. B. 0 99.95  (für die Berechnung");
		printf(" der Mehrwertsteuer von 99.95 netto}\n");

		printf(">");
		rc = scanf("%d", &choice);
		if(rc == 1 && choice == 3) break;
		rc += scanf("%lf", &value);
		while(getchar() != '\n') continue;

		if(choice >= 0 && choice <3 && rc == 2){
			(*functions[choice])(value);
		}
		printf("\n");

	}while(choice != 3);



	return 0;
}
