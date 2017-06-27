#include "Iterator.h"

void it(std::vector<long> &zahlen, const long &print) {
    printf("Function: %s\n", __FUNCTION__);
    for (std::vector<long>::iterator it = zahlen.begin(); it != zahlen.end(); ++it) {
        if (*it % print == 0) {
            printf("%ld\n", *it);
        }
    }
}