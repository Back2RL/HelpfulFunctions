#include "IteratorOpt.h"

void itOpt(std::vector<long> &zahlen, const long &print) {
    printf("Function: %s\n", __FUNCTION__);
    std::vector<long>::iterator end = zahlen.end();
    for (std::vector<long>::iterator it = zahlen.begin(); it != end; ++it) {
        if (*it % print == 0) {
            printf("%ld\n", *it);
        }
    }
}