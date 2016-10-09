#include "math.h"
#include "input.h"
#include "differenz.h"
#include "summe.h"
#include <stdio.h>

int main(void){
	berechne();
	return 0;
}

void berechne(void){
	char rc = '\0';

	do{
		printf("Ihre Wahl:\n");
		/* Eingabe auslesen */
		rc = get_char("<S>umme oder <D>ifferenz? ");

	}while(rc != 'S' && rc != 'D');

	if(rc == 'S'){
		printf("Ergebnis: %d\n",summe());
	} else if(rc == 'D'){
		printf("Ergebnis: %d\n",differenz());
	}
}
