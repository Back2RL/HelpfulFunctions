#include "summe.h"
#include "input.h"

int summe(void){
	int summandA = get_input("Erster Summand: ");
	int summandB = get_input("Zweiter Summand: ");

	return summandA + summandB;
}
