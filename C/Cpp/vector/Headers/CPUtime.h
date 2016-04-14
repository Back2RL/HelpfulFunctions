//
// Created by Leo on 14.04.2016.
//

#ifndef VECTOR_CPUTIME_H
#define VECTOR_CPUTIME_H

#include <ctime>

using namespace std;

class CPUtime {
public:
    clock_t getStart() const {
        return start;
    }

    void setStart(clock_t start) {
        CPUtime::start = start;
    }

private:
    clock_t start;
public:
    clock_t getEnd() const {
        return end;
    }

    void setEnd(clock_t end) {
        CPUtime::end = end;
    }

private:
    clock_t end;

public:
    CPUtime() {
        this->start = clock();
    }

    CPUtime(clock_t start) {
        this->start = start;
    }

    ~CPUtime() { }

    int getClockPerSecond() const {
        return CLOCKS_PER_SEC;
    }

    void startTimer() {
        start = clock();
    }

    void stopTimer() {
        end = clock();
    }

    int getNumClocks() const {
        return (int)(end - start);
    }

    double getSeconds() const {
        return  getNumClocks() / (double)(CLOCKS_PER_SEC);
    }

};

#endif //VECTOR_CPUTIME_H
