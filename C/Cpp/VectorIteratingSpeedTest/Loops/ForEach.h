//
// Created by leonard on 19.06.17.
//

#ifndef VECTORITERATINGSPEEDTEST_FOREACH_H
#define VECTORITERATINGSPEEDTEST_FOREACH_H


#include <vector>
#include <cstdio>

inline void forEach(std::vector<long>& zahlen, const long& print){
	printf("Function: %s\n", __FUNCTION__);
	for (const long &zahl:zahlen) {
		if (zahl % print == 0) {
			printf("%ld\n", zahl);
		}
	}
}

#endif //VECTORITERATINGSPEEDTEST_FOREACH_H
