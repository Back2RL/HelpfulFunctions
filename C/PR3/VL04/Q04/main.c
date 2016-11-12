#include <stdio.h>

int main(void){

	int array[10][10];

	int i = 0;
	for(i = 1; i<= 10*10; ++i){
		*array[i-1] = i*i;
		printf("%d\n",*array[i-1]);
	}


	return 0;
}
