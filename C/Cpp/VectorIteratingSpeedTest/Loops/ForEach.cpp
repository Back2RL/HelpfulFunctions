#include "ForEach.h"

void forEach(std::vector<long> &zahlen, const long &print) {
    for (const long &zahl:zahlen) {
        if (zahl % print == 0) {
            printf("%ld\n", zahl);
        }
    }
}