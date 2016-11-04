#include <stdio.h>
#include <stdlib.h>

#define ARRAY_LEN 10
#define NAME_LEN 40
typedef struct {
	char name[NAME_LEN+1];
	int personalnummer;
	float gehalt;
} angestellter;

void allesAusgeben(angestellter *array[], const int anzahl){
	int i = 0;
	for (i=0; i<anzahl ; i++) {
		if(array[i] == NULL) continue;
		printf("Eintrag %d: %s %d %.2f\n",
				i, array[i]->name, array[i]->personalnummer,
				array[i]->gehalt);
	}
}
void stelleLeeren(angestellter *array[], const int stelle, const int anzahl){
	if(stelle < 0 || stelle >= anzahl) {
		printf("Index ungültig!");
		return;
	}
	free(array[stelle]);
	array[stelle] = NULL;
	printf("Stelle %d gelöscht\n", stelle);
}

int main(void){

	int i;
	angestellter *array[ARRAY_LEN] = {NULL};

	do{
		printf("--------------------\n");
		printf("Index angeben [0-9]: ");
		scanf("%d", &i);
		i %= ARRAY_LEN;
		/* Puffer leeren */
		do {} while (getchar() != '\n');

		if(array[i] != NULL){
			stelleLeeren(array, i, ARRAY_LEN);	
		} else {
			angestellter *toSet = (angestellter*) malloc(sizeof(angestellter));
			printf("Name: ");
			scanf("%49[^\n]",toSet->name);
			/*printf("%s\n", toSet->name);*/

			printf("Personalnummer: ");
			scanf("%d",&toSet->personalnummer);
			/*printf("%d\n",toSet->personalnummer);*/

			/* Puffer leeren */
			do {} while (getchar() != '\n');

			printf("Gehalt: ");
			scanf("%f",&toSet->gehalt);
			/*printf("%f\n",toSet->gehalt);*/

			array[i] = toSet;
			toSet = NULL;	
		}
		printf("Ausgabe: \n");
		allesAusgeben(array, ARRAY_LEN);

		printf("\nFortfahren (1) Beenden (0)?: ");
		scanf("%d", &i);
		/* Puffer leeren */
		do {} while (getchar() != '\n');
	}while(i != 0);

		return 0;
}
