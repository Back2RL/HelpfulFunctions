
#include <stdlib.h>
#include <vector>
#include <stdio.h>
#include <limits>
#include "CPUtime.h"

#define FOREACH
#define ITERATOR
#define HAND

using namespace std;
#define NUM (100000000L)

int main(int argc, char **argv) {

	vector<double> results;
	vector<long> zahlen;
	for (long i = 0; i < NUM; ++i) {
		zahlen.push_back(NUM - i);
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
	printf("For-Each: elapsed = %lf\n", timer.getSeconds());
	results.push_back(timer.getSeconds());
#endif

#ifdef ITERATOR
	timer.startTimer();
	for (vector<long>::iterator it = zahlen.begin(); it != zahlen.end(); ++it) {
		if (*it % 10000000 == 0) {
			printf("%ld\n", *it);
		}
	}
	timer.stopTimer();
	printf("Iterator: elapsed = %lf\n", timer.getSeconds());
	results.push_back(timer.getSeconds());
	{
		timer.startTimer();
		vector<long>::iterator end = zahlen.end();
		for (vector<long>::iterator it = zahlen.begin(); it != end; ++it) {
			if (*it % 10000000 == 0) {
				printf("%ld\n", *it);
			}
		}
		timer.stopTimer();
		printf("Iterator opt.: elapsed = %lf\n", timer.getSeconds());
		results.push_back(timer.getSeconds());
	}
#endif

#ifdef HAND
	timer.startTimer();
	for (unsigned long i = 0; i < zahlen.size(); ++i) {
		if (zahlen[i] % 10000000 == 0) {
			printf("%ld\n", zahlen[i]);
		}
	}
	timer.stopTimer();
	printf("Hand: elapsed = %lf\n", timer.getSeconds());
	results.push_back(timer.getSeconds());
	{
		timer.startTimer();
		long end = zahlen.size();
		for (long i = 0; i < end; ++i) {
			if (zahlen[i] % 10000000 == 0) {
				printf("%ld\n", zahlen[i]);
			}
		}
		timer.stopTimer();
		printf("Hand opt.: elapsed = %lf\n", timer.getSeconds());
		results.push_back(timer.getSeconds());
	}
	{
		timer.startTimer();
		unsigned long end = zahlen.size();
		for (unsigned long i = 0; i < end; ++i) {
			const long &zahl = zahlen[i];
			if (zahl % 10000000 == 0) {
				printf("%ld\n", zahl);
			}
		}
		timer.stopTimer();
		printf("Hand opt. with ref: elapsed = %lf\n", timer.getSeconds());
		results.push_back(timer.getSeconds());
	}

#endif

	double min = numeric_limits<double>::max();
	double max = 0;
	for (double &time:results) {
		if (time < min) min = time;
		if (time > max) max = time;
	}
	printf("ratio best/worst: %lf\n", min / max);

	return EXIT_SUCCESS;
} 

