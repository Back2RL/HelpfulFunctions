
#include <stdlib.h>
#include <vector>
#include <stdio.h>
#include <limits>
#include "CPUtime.h"
#include "Loops/ForEach.h"
#include "Loops/Hand.h"
#include "Loops/HandOpt.h"
#include "Loops/HandOptRef.h"
#include "Loops/Iterator.h"
#include "Loops/IteratorOpt.h"

#define FOREACH
#define ITERATOR
#define ITERATOROPT
#define HAND
#define HANDOPT
#define HANDOPTREF

using namespace std;
#define NUM (100000000L)
#define PRINT 10000000L

int main(int argc, char **argv) {

    printf("%s %s : %s\n\t%4d : %s\n", __DATE__, __TIME__, __FILE__, __LINE__, __FUNCTION__);

    vector<double> results;
    vector<long> zahlen;
    for (long i = 0; i < NUM; ++i) {
        zahlen.push_back(NUM - i);
    }
    printf("gefüllt\n");

    CPUtime timer = CPUtime();

    printf("--------------------------------------\n");
#ifdef FOREACH
    timer.startTimer();
    forEach(zahlen, PRINT);
    timer.stopTimer();
    printf("For-Each: elapsed = %lf\n", timer.getSeconds());
    results.push_back(timer.getSeconds());
#endif
    printf("-------------------------------------\n");

#ifdef ITERATOR
    timer.startTimer();
    it(zahlen, PRINT);
    timer.stopTimer();
    printf("Iterator: elapsed = %lf\n", timer.getSeconds());
    results.push_back(timer.getSeconds());
#endif
    printf("------------------------------------\n");
#ifdef ITERATOROPT
    {
        timer.startTimer();
        itOpt(zahlen, PRINT);
        timer.stopTimer();
        printf("Iterator opt.: elapsed = %lf\n", timer.getSeconds());
        results.push_back(timer.getSeconds());
    }
#endif
    printf("-----------------------------------\n");

#ifdef HAND
    timer.startTimer();
    hand(zahlen, PRINT);
    timer.stopTimer();
    printf("Hand: elapsed = %lf\n", timer.getSeconds());
    results.push_back(timer.getSeconds());
#endif
    printf("----------------------------------\n");
#ifdef HANDOPT
    {
        timer.startTimer();
        handOpt(zahlen, PRINT);
        timer.stopTimer();
        printf("Hand opt.: elapsed = %lf\n", timer.getSeconds());
        results.push_back(timer.getSeconds());
    }
#endif
    printf("--------------------------------\n");
#ifdef HANDOPTREF
    {
        timer.startTimer();
        handOptRef(zahlen, PRINT);
        timer.stopTimer();
        printf("Hand opt. with ref: elapsed = %lf\n", timer.getSeconds());
        results.push_back(timer.getSeconds());
    }

#endif
    printf("------------------------------\n");

    double min = numeric_limits<double>::max();
    double max = 0;
    for (double &time:results) {
        if (time < min) min = time;
        if (time > max) max = time;
    }
    printf("ratio best/worst: %lf\n", min / max);

    return EXIT_SUCCESS;
} 

