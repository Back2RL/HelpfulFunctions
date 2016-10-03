#include <stdio.h>
#include "math.h"

#define DEBUG




int main(void){
#ifdef DEBUG
	printf("Hello World!\n");
#endif

	printf("Max of 2 and 3 is = %f\n", max(2.0,3.0));
	
	return 0;
}
