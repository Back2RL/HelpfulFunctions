#include <stdio.h>

int main(int argc, char* argv[]){
	int a = 0;
	int rc = 0;
	int i,j = 0;

	do{
		printf("Bitte positive ganze Zahl eingeben: ");
		rc = scanf("%d",&a);
		while(getchar() != '\n'){
			continue;
		}
	} while(a <= 0 || rc != 1);

	for(i = 0; i < 10; ++i){
		for(j = 0; j < 10; ++j){
			if(i == 0){
				printf(" ");
			}
			printf("%d", i*10+j);
			if((i*10+j) % a == 0){
				printf("# ");
			} else {
				printf(" ");
			}	
		}
		printf("\n");
	}
	return 0;
}
