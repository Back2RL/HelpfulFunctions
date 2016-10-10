#include "structs.h"
#include <stdio.h>

int main(void){
	struct MyStruct test;
	test.myInt = 0;

	printf("Hello World\n");
	printf("MyInt = %d\n",test.myInt);

	return 0;
}


