#include <stdio.h>
#include <string.h>

/* Definition mystrcat */
void mystrcat(char* s1, char* s2){
	int positionS1 = 0;
	int i = 0;
	
	/* Stringende von s1 finden */
	while(s1[positionS1] != '\0'){
		++positionS1;
	}
	
	/* alle Zeichen von s2 in s1 einfügen 
	* ab der gefundenen Stringendeposition */
	while(s2[i] != '\0'){
		s1[positionS1+i] = s2[i];
		++i;
	}
	/* neues Stringende setzen */
	s1[positionS1+i] = '\0';
}

int main(void) {
	char buffer[30];
	char nonempty[] = "Informatik";
	char empty[] = "";
	char exclaim[] = "!";

	strcpy(buffer, "Angewandte ");
	mystrcat(buffer, empty);
	printf("buffer = %s\n", buffer);

	mystrcat(buffer, nonempty);
	printf("buffer = %s\n", buffer);

	mystrcat(buffer, exclaim);
	printf("buffer = %s\n", buffer);

	strcpy(buffer, "");
	mystrcat(buffer, nonempty);
	printf("buffer = %s\n", buffer);

	return 0;
}
