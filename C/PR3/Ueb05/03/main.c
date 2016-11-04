#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINES 100
#define NAMELEN 20
struct knoten;
typedef struct knoten knoten;
struct knoten {
	char name[NAMELEN + 1];
	knoten* parent;
};


int main(void){
	int rc = 0;
	int line = 0;
	int i,j = 0;

	knoten knots[MAX_LINES];
	knoten parents[MAX_LINES];

	for(line = 0; line < MAX_LINES; ++line){
		rc = 0;
		rc = scanf("%s",&knots[line].name[0]);
		rc += scanf("%s", &parents[line].name[0]);
		printf("%s %s\n", &knots[line].name[0], &parents[line].name[0]);
		if(rc != 2) {
			break;
		}
	}

	printf("%d Zeilen eingelesen\n", line);

	for(i = 0; i < line; ++i){
		for(j = 0; j < line; ++j){
			if(rc = strcmp(parents[i].name,knots[j].name), rc == 0){
				knots[i].parent = &knots[j];
				break;
			}
		}		
	}

	printf("Anzahl Knoten: %d\n", line);
	for(i = 0; i < line; ++i){
		if(knots[i].parent == NULL){
			printf("*%s -> -\n", knots[i].name);
		}else {
			printf(" %s -> %s\n", knots[i].name, knots[i].parent->name);
		}

	}
	return 0;
}


