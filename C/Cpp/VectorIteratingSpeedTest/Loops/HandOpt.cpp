#include "HandOpt.h"

void handOpt(std::vector<long> &zahlen, const long &print) {
    printf("Function: %s\n", __FUNCTION__);
    const unsigned long end = zahlen.size();
    for (unsigned long i = 0; i < end; ++i) {
        if (zahlen[i] % print == 0) {
            printf("%ld\n", zahlen[i]);
        }
    }
}