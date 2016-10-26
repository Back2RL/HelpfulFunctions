#include <stdio.h>

#define LEN 5

int main(void){
	int a[] = {1,2,3,4,5};
	int b[LEN];
	int *ap = &a[0];
	int *bp = &b[LEN-1];
	int i = 0;

	/* in umgekehrter Reihenfolge in Array b kopieren */
	for(i = 0; i < LEN; ++i, ++ap, --bp){
		*bp = *ap;
	}

	/* Ausgabe Array b */
	bp = &b[0];
	printf("%d", *bp);
	++bp;
	for(i = 1; i<LEN; ++i, ++bp){
		printf(", %d", *bp);
	}
	printf("\n");

	return 0;
}
