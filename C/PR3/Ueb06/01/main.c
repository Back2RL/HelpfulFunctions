#include <stdio.h>

void ausgabe_mit_einrueck(int array[], const int len);
void ausgabe_ohne_einrueck(int array[], const int len){
	printf("%d\n", array[0]);
	if(len > 1){
		ausgabe_mit_einrueck(&array[1], len - 1);
	}
}

void ausgabe_mit_einrueck(int array[], const int len){
	printf("%10d\n", array[0]);
	if(len > 1){
		ausgabe_ohne_einrueck(&array[1], len - 1);
	}
}

int main(void) {
	int array[] = {1,2,3,4,5,6,7,8,9};

	ausgabe_ohne_einrueck(array, sizeof(array) / sizeof(array[0]));

	return 0;
}
