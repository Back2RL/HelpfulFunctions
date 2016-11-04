#include <stdio.h>
#include <string.h>
#include <stdlib.h>

struct angestellter {
	char name[41];
	struct angestellter *chef;
	struct angestellter *besterFreund;
};

typedef struct angestellter angestellter;


int main(void){

	angestellter *obelix = (angestellter*) malloc(sizeof(struct angestellter));
	strcpy(obelix->name, "Obelix");

	printf("%s\n", obelix->name);


	return 0;
}
