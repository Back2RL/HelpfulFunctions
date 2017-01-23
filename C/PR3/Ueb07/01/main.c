#include <stdio.h>

void print(void) {
	printf("Hallo\n"); 
}

void print2(int i){
	printf("%d\n", i);
}


void times(int n, void (*fn)(void)){
	int i = 0;
	for(i = 0; i < n; ++i){
		fn();
	}
}

void times2(int n, void (*fn)(int i)){
	int i = 0;
	for(i = 0; i < n; ++i){
		fn(i);
	}
}


int main(void){

	times(3, &print);
	times2(3, &print2);

	return 0;

}
