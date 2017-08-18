//
// Created by leonard on 19.06.17.
//

#ifndef VECTORITERATINGSPEEDTEST_ITERATOR_H
#define VECTORITERATINGSPEEDTEST_ITERATOR_H


#include <vector>
#include <cstdio>

inline void it(std::vector<long> &zahlen, const long &print){
	printf("Function: %s\n", __FUNCTION__);
	for (std::vector<long>::iterator it = zahlen.begin(); it != zahlen.end(); ++it) {
		if (*it % print == 0) {
			printf("%ld\n", *it);
		}
	}
}

#endif //VECTORITERATINGSPEEDTEST_ITERATOR_H
