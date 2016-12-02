#include <stdlib.h>
#include <stdio.h>
#include "DVektor.h"

DVektor create(double data[], int nElems) {
    DVektor result;
    int i = 0;
    result.elems = nElems;
    result.values = (double *) malloc(nElems * sizeof((double) 0.0));

    for (i = 0; i < nElems; ++i) {
        *(result.values + i) = data[i];
    }
    return result;
}

DVektor copy(DVektor original) {
    DVektor copyVec;
    int dataSize = original.elems * sizeof((double) 0.0);
    int i = 0;
/*
    printf("Size = %d\n", dataSize);
    printf("Elems = %d\n", original.elems);
    */

    copyVec.elems = original.elems;
    copyVec.values = (double *) malloc(dataSize);

    for (i = 0; i < original.elems; ++i) {
        *(copyVec.values + i) = *(original.values + i);
    }
    return copyVec;
}

void delete(DVektor v) {
    v.elems = 0;
    free(v.values);
    v.values = NULL;
}

int add(DVektor destination, DVektor source) {
    int i = 0;
    if (destination.elems != source.elems) {
        return 0;
    }

    for (i = 0; i < destination.elems; ++i) {
        *(destination.values + i) += *(source.values + i);
    }
    return 1;
}

void process(DVektor v, void (*f)(double *elemPtr)) {
    int i = 0;

    for (i = 0; i < v.elems; ++i) {
        f(v.values + i);
    }
}
