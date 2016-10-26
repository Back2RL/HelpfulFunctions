#include <stdio.h>

void tausche_int(int *a, int *b){
	int temp = *a;
	*a = *b;
	*b = temp;
}
void tausche_intPtr(int **a, int **b){
	int *temp = *a;
	*a = *b;
	*b = temp;
}

int main(void) {
	int i = 1; int j = 2;
	int *ip = &i;
	int *jp = &j;

	printf("Speicher vertauschen\n");
	printf("i = %d, j = %d\n", i, j); /* Gibt 1 und 2 aus */
	tausche_int(&i, &j);
	printf("i = %d, j = %d\n", i, j); /* Gibt 2 und 1 aus */

	printf("Zeiger auf Speicher vertauschen\n");
	printf("ip= %d, jp= %d\n", *ip, *jp);
	printf("i = %d, j = %d\n", i, j);
	tausche_intPtr(&ip, &jp);
	printf("ip= %d, jp= %d\n", *ip, *jp);
	printf("i = %d, j = %d\n", i, j); 
	return 0;
}

