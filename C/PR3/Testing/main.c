#include <stdio.h>
#include "math.h"

#define DEBUG




int main(void){
	int number = 0;
#ifdef DEBUG
	printf("Hello World!\n");
#endif
	printf("Zahl : ");
	scanf("%d",&number);


	printf("Max of %d and 3 is = %f\n",number, max(number,3.0));

	return 0;
}
