#include <stdio.h>
#include <stdlib.h>

#define DBG_MSG 0
#if DBG_MSG == 1
	#define MSG(message) printf("%s\n",message);
#else 
	#define MSG(message)
#endif

int main(void){


	int *i = (int*) malloc(sizeof(int));
	double *zahl = (double*) malloc(sizeof(float));

	*i = 42;
	*zahl = *i;

	free(i);
	free(zahl);

	i = NULL;
	zahl = NULL;

	MSG("Hallo");

	return 0;
}
