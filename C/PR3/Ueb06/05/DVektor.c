#include "DVektor.h"

DVektor create(double data[], int nElems){
	DVektor result;
	int i = 0;
	result.values = (double*) malloc(nElems * sizeof((double) 0.0));

	for(i = 0; i < nElems; ++i){
		*(result.values + i) = data[i];
	}
	return result;
}

DVektor copy(DVektor original){


}
void delete(DVektor v);
int add(DVektor destination, DVector source);
void process(DVektor v, void (*f)(double* elemPtr));
