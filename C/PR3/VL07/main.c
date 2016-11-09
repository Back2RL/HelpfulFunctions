#include <stdio.h>

/*
* Funktionen als Parameter übergeben
*/

int add(int a, int b){
	return a + b;
}

int doAdd(int a, int b, int (*functionName)(int a,int b)){
	return (*functionName)(a,b);
}

int main(void){
	printf("result = %d\n", doAdd(1,2,&add));
	return 0;
}
