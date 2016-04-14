//
// Created by Leo on 14.04.2016.
//

#ifndef VECTOR_CPUTIME_H
#define VECTOR_CPUTIME_H

#include <ctime>

using namespace std;

class CPUtime {
public:
    clock_t getStart(void) const;

    void setStart(clock_t start);

private:
    clock_t start;
public:
    clock_t getEnd(void) const ;

    void setEnd(clock_t end);

private:
    clock_t end;

public:
    CPUtime(void);

    CPUtime(clock_t start);

    ~CPUtime(void) {}

    int getClocksPerSecond(void) const;

    void startTimer(void);

    void stopTimer(void) ;

    int getNumClocks(void) const ;

    double getSeconds(void) const ;

};

#endif //VECTOR_CPUTIME_H
