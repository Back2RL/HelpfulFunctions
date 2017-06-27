#include "ForEach.h"

void forEach(std::vector<long> &zahlen, const long &print) {
    printf("Function: %s\n", __FUNCTION__);
    for (const long &zahl:zahlen) {
        if (zahl % print == 0) {
            printf("%ld\n", zahl);
        }
    }
}