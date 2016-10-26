#include <stdio.h>

char* find(char s[], char c){
	char *cp = NULL;
	int i = 0;
	for(i = 0; s[i] != '\0'; ++i){
		if(s[i] == c){
			printf("%d\n", i);
			cp = &s[i];
		}
	}
	return cp;
}

int main(void){

	char word[] = "Hans Dieter Ebeling";
	char *ePos = find(&word[0], 'e');
	printf("%c, %p, %p\n", *ePos, &word[0], ePos);
	*ePos = '_';
	printf("%s\n", &word[0]);

	return 0;
}
