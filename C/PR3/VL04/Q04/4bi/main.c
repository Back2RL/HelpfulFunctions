#include <stdio.h>

#define ANZAHL 100

/* 4bii */
void qinit(int* array, int length){
	int i = 0;
	for(i = 1; i <= length; ++i){
		array[i-1] = i*i;
		printf("%d\n", array[i-1]);
	}
}

int main(void){
	int quadrate[ANZAHL];

	

	int i = 0;
	for(i = 1; i <= ANZAHL; ++i){
		quadrate[i-1] = i*i;
		printf("%d\n", quadrate[i-1]);
	}

/* 4bii */
	qinit(quadrate, ANZAHL);

	return 0;
}
