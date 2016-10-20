#include <stdio.h>

int main(int argc, char* args[]){
	printf("Speichergrößen:\n");
	printf("Short   = %lu\n",sizeof((short int) 1));
	printf("Integer = %lu\n",sizeof((int) 1));
	printf("Long    = %lu\n",sizeof(1L));
	printf("Float   = %lu\n",sizeof(1.0f));
	printf("Double  = %lu\n",sizeof(1.0));
	printf("LongDbl = %lu\n",sizeof(1.0L));
	{
		char* pntr = NULL;
		char c = 'a';
		printf("Char    = %lu\n",sizeof(c));
		printf("Char*   = %lu\n",sizeof(pntr));
		printf("Char*   = %p\n",&c);
	}


	return 0;
} 
