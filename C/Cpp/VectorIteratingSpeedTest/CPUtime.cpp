#include "CPUtime.h"

/* more precise:
auto begin = std::chrono::high_resolution_clock::now();
auto end = std::chrono::high_resolution_clock::now();	
printf("Duration = %ld nanoseconds", std::chrono::duration_cast<std::chrono::nanoseconds>(end - begin).count());		
*/

clock_t CPUtime::getStart() const {
    return start;
}

void CPUtime::setStart(clock_t start) {
    CPUtime::start = start;
}

clock_t CPUtime::getEnd() const {
    return end;
}

void CPUtime::setEnd(clock_t end) {
    CPUtime::end = end;
}

CPUtime::CPUtime() {
    this->start = clock();
}

CPUtime::CPUtime(clock_t start) {
    this->start = start;
}


int CPUtime::getClocksPerSecond() const {
    return CLOCKS_PER_SEC;
}

void CPUtime::startTimer() {
    start = clock();
}

void CPUtime::stopTimer() {
    end = clock();
}

int CPUtime::getNumClocks() const {
    return (int) (end - start);
}

double CPUtime::getSeconds() const {
    return getNumClocks() / (double) (CLOCKS_PER_SEC);
}
