

#include "Vektor.h"
#include <stdlib.h>
#include <stdio.h>

Vektor create(char data[], int nElems, int elemSize) {
    Vektor result;
    int i = 0;
    int j = 0;
    int charCount = (elemSize / sizeof(char));


    result.elems = nElems;
    result.elemSize = elemSize;
    result.data = (char **) malloc(nElems * sizeof(char *));

    /*
    printf("Chars for 1 Elem = %d\n", charCount);
    printf("Elems = %d\n", nElems);
    printf("ElemSize = %d\n", elemSize);
    printf("ArraySize = %d\n", sizeof(result.data));
    */
    for (i = 0; i < nElems; ++i) {
        result.data[i] = (char *) malloc(elemSize);

        for (j = 0; j < charCount; ++j) {
            result.data[i][j]= data[(i * charCount) + j];
        }
    }
    return result;
}

Vektor copy(Vektor original) {
    Vektor copyVec;
    int i = 0;
    int j = 0;
    int charCount = (original.elemSize / sizeof(char));

    copyVec.elems = original.elems;
    copyVec.elemSize = original.elemSize;
    copyVec.data = (char **) malloc(original.elems * sizeof(char *));

    for (i = 0; i < original.elems; ++i) {
        copyVec.data[i] = (char *) malloc(original.elemSize);

        for (j = 0; j < charCount; ++j) {
            copyVec.data[i][j]= original.data[i][j];
        }
    }
    return copyVec;
}

void delete(Vektor v) {
    int i = 0;
    for (i = 0; i < v.elems; ++i) {
        free(*(v.data + i));
        *(v.data + i) = NULL;
    }
    free(v.data);
    v.data = NULL;
    v.elems = 0;
    v.elemSize = 0;
}

void process(Vektor v, void (*f)(char *elemPtr, int elemSize)) {
    int i = 0;

    for (i = 0; i < v.elems; ++i) {
        f(*(v.data + i), v.elemSize);
    }
}

