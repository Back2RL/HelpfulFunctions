#include "HandOptRef.h"

void handOptRef(std::vector<long> &zahlen, const long &print) {
    printf("Function: %s\n", __FUNCTION__);
    const unsigned long end = zahlen.size();
    for (unsigned long i = 0; i < end; ++i) {
        long &zahl = zahlen[i];
        if (zahl % print == 0) {
            printf("%ld\n", zahl);
        }
    }
}