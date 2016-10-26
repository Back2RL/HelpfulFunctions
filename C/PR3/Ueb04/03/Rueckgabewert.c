#include <stdio.h>

char* foo(char* s1, char* s2) {
	char *start, *left, *right;
	for (start = s1; *start != '\0'; ++start) {
		for (left = start, right = s2;
	*left != '\0' && right != '\0' && *left == *right;
				++left, ++right)
			;
		if (*right == '\0')
			return start;
	}
	return NULL;
}


int main(void){
char a[] = "Hanswerndo";

printf("%c\n", *(char*) foo(&a[0], &a[5]));
return 0;
}
