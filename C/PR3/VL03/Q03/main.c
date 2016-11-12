#include <stdio.h>

int main(int argc, char* args[]){
	int b = 0;
	int i = 0;

	for(i = 0; i < 100 && b == 0; ++i){
		if(i*i - 3 > 90){
			printf("%d\n", i);
			b = 1;
		}
	}
	return 0;
}
