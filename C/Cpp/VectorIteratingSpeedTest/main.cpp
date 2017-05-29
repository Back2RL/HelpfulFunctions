
#include <stdlib.h>
#include <vector>
#include <stdio.h>
#include "CPUtime.h"

#define FOREACH
#define ITERATOR
#define HAND

using namespace std;

int main(int argc, char **argv) {

	vector<long> zahlen;
	for (long i = 0; i < 100000000L; ++i) {
		zahlen.push_back(100000000L - i);
	}
	printf("gefüllt\n");

	CPUtime timer = CPUtime();


#ifdef FOREACH
	timer.startTimer();
	for (long &zahl:zahlen) {
		if (zahl % 10000000 == 0) {
			printf("%ld\n", zahl);
		}
	}
	timer.stopTimer();
	printf("elapsed = %lf\n", timer.getSeconds());
#endif

#ifdef ITERATOR
	timer.startTimer();
	for (vector<long>::iterator it = zahlen.begin(); it != zahlen.end(); ++it) {
		if (*it % 10000000 == 0) {
			printf("%ld\n", *it);
		}
	}
	timer.stopTimer();
	printf("elapsed = %lf\n", timer.getSeconds());
#endif

#ifdef HAND
	timer.startTimer();
	for (unsigned long i = 0; i < zahlen.size(); ++i) {
		if (zahlen[i] % 10000000 == 0) {
			printf("%ld\n", zahlen[i]);
		}
	}
	timer.stopTimer();
	printf("elapsed = %lf\n", timer.getSeconds());
#endif
	return EXIT_SUCCESS;
} 

