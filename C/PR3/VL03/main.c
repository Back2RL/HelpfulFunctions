#include <stdio.h>

#define DEBUG 0
#define LAENGE 100
#define SIZE 10

void qinit(int array[], int n);

int main(void){
	int test[SIZE][SIZE];
	int i,j = 0;
	int quadrate[LAENGE]; 
	/*
	   int i = 0;
	   for (i = 1; i < LAENGE + 1 ; ++i){
	   quadrate[i] = i*i;
#if DEBUG == 1
printf("%d\n", quadrate[i]);
#endif
}

	 */
	qinit(quadrate, LAENGE);

	for(i = 0; i < SIZE ; ++i){
		for(j = 0; j < SIZE; ++j){
			test[i][j] = i*SIZE + j + 1;
			printf("%d\n",test[i][j]);
		}

	}

return 0;
}


void qinit(int array[], int n){

	int i = 0;

	for (i = 1; i < n + 1 ; ++i){
		array[i] = i*i;
#if DEBUG == 1
		printf("%d\n", array[i]);
#endif
	}

}
