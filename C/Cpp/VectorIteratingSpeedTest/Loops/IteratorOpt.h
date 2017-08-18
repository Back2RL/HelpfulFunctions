//
// Created by leonard on 19.06.17.
//

#ifndef VECTORITERATINGSPEEDTEST_ITERATOROPT_H
#define VECTORITERATINGSPEEDTEST_ITERATOROPT_H


#include <vector>
#include <cstdio>

inline void itOpt(std::vector<long> &zahlen, const long &print){
	printf("Function: %s\n", __FUNCTION__);
	std::vector<long>::iterator end = zahlen.end();
	for (std::vector<long>::iterator it = zahlen.begin(); it != end; ++it) {
		if (*it % print == 0) {
			printf("%ld\n", *it);
		}
	}
}

#endif //VECTORITERATINGSPEEDTEST_ITERATOROPT_H
