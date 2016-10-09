#include "differenz.h"
#include "input.h"

int differenz(void){
	int minuend = get_input("Minuend: ");
	int subtrahend = get_input("Subtrahend: ");

	return minuend - subtrahend;
}
