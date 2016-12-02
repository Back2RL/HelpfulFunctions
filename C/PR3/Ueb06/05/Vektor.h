
#ifndef INC_05_VEKTOR_H
#define INC_05_VEKTOR_H

typedef struct{
    int elems;
    int elemSize;
    char** data;
} Vektor;


Vektor create(char data[], int nElems, int elemSize);
Vektor copy(Vektor original);
void delete(Vektor v);
void process(Vektor v, void (*f)(char* elemPtr, int elemSize));



#endif
