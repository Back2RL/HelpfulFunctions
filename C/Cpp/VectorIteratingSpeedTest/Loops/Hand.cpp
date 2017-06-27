#include "Hand.h"

void hand(std::vector<long> &zahlen, const long &print) {
    printf("Function: %s\n", __FUNCTION__);
    for (unsigned long i = 0; i < zahlen.size(); ++i) {
        if (zahlen[i] % print == 0) {
            printf("%ld\n", zahlen[i]);
        }
    }
}