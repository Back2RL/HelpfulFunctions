//
// Created by leonard on 19.06.17.
//

#ifndef VECTORITERATINGSPEEDTEST_HAND_H
#define VECTORITERATINGSPEEDTEST_HAND_H


#include <vector>
#include <cstdio>

inline void hand(std::vector<long> &zahlen, const long &print){
	printf("Function: %s\n", __FUNCTION__);
	for (unsigned long i = 0; i < zahlen.size(); ++i) {
		if (zahlen[i] % print == 0) {
			printf("%ld\n", zahlen[i]);
		}
	}
}

#endif //VECTORITERATINGSPEEDTEST_HAND_H
