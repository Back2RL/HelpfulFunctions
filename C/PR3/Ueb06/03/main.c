#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <errno.h>

void help(void){
	printf("Benutzung: ./plus <zahl> <zahl>\n");
}

int readInt(char* s){
	int base = 10;
	char* endptr;
	long val;
	errno = 0;
	val = strtol(s,&endptr, base);
	/* Check for various possible errors */

	if (errno == ERANGE || (val >= INT_MAX || val <= INT_MIN)){
		printf("Kann '%s' nicht in Zahl umwandeln: numeric result out of range\n", s);
		exit(-1);
	}
	if((errno != 0 && val == 0) || endptr == s ) {
		printf("Kann '%s' nicht in Zahl umwandeln: Falsches Format\n", s);
		exit(-1);
	}

	/* If we got here, strtol() successfully parsed a number */
	return  (int) val;
}


int main(int argc, char* argv[]){
	int a,b;

	if(argc != 3) {
		help();
		return -1;
	}

	a = readInt(argv[1]);
	b = readInt(argv[2]);
	printf("%d + %d = %d\n", a,b,a+b);


	return 0;
}

