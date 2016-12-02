
typedef struct{
    int elems;
	double* values;
} DVektor;


DVektor create(double data[], int nElems);
DVektor copy(DVektor original);
void delete(DVektor v);
int add(DVektor destination, DVektor source);
void process(DVektor v, void (*f)(double* elemPtr));
