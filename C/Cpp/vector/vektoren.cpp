#include <stdexcept>
#include <cstdio>
#include "Headers/Vektor.h"
#include "Headers/CPUtime.h"
//  Windows
#define TARGETTIME 30
using namespace std;

#ifdef _WIN32

#include <Windows.h>

double get_wall_time() {
	LARGE_INTEGER time, freq;
	if (!QueryPerformanceFrequency(&freq)) {
		//  Handle error
		return 0;
	}
	if (!QueryPerformanceCounter(&time)) {
		//  Handle error
		return 0;
	}
	return (double) time.QuadPart / freq.QuadPart;
}

double get_cpu_time() {
	FILETIME a, b, c, d;
	if (GetProcessTimes(GetCurrentProcess(), &a, &b, &c, &d) != 0) {
		//  Returns total user time.
		//  Can be tweaked to include kernel times as well.
		return
			(double) (d.dwLowDateTime |
					((unsigned long long) d.dwHighDateTime << 32)) * 0.0000001;
	} else {
		//  Handle error
		return 0;
	}
}

//  Posix/Linux
#else
#include <time.h>
#include <sys/time.h>
double get_wall_time(){
	struct timeval time;
	if (gettimeofday(&time,NULL)){
		//  Handle error
		return 0;
	}
	return (double)time.tv_sec + (double)time.tv_usec * .000001;
}
double get_cpu_time(){
	return (double)clock() / CLOCKS_PER_SEC;
}
#endif

// Hauptprogramm
int main(int argc, char *argv[]) {
	clock_t time = clock(); //processor time consumed by the program
	cout << "This Computer performs "<< CLOCKS_PER_SEC << " clocks per second." << endl;

	CPUtime timer;
	timer.startTimer();

	//  Start Timers
	double wall0 = get_wall_time();
	double cpu0 = get_cpu_time();
	int counts = 0;

	bool bCanDisplayAgain = true;
	while(timer.getSeconds()<TARGETTIME) {	
		++counts;	
		if(timer.getNumClocks() % timer.getClocksPerSecond() == 0)  {
			if(bCanDisplayAgain) {
				printf("%f seconds\n",timer.getSeconds());
				bCanDisplayAgain = false;
			}
		}
		else {
			bCanDisplayAgain = true;
		}

		//  Perform some computation.
		double sum = 0;
		/*
#pragma omp parallel for reduction(+ : sum)
for (long long i = 1; i < 1000000000; i++) {
sum += log((double) i);
}
		 */
		// ein Objekt der Klasse Vektor
		Vektor v1; //1. Konstruktor verwendet (Standardwerte, also (0,0,0))

		v1.setX(1);
		v1.setY(2);
		v1.setZ(3);

#ifdef debug
		cout.setf(ios::fixed);
		cout.precision(6);
#endif

		double d = v1.length();
#ifdef debug
		cout << "Länge von v1: " << d << endl;
#endif

		double x = v1.getX();
		double y = v1.getY();
		double z = v1.getZ();
#ifdef debug
		cout << "Vektor v1 = (" << x << "," << y << "," << z << ")" << endl;
#endif

		Vektor v2 = Vektor(5, 7, 8); //1. Konstruktor verwendet
		d = v2.length();
#ifdef debug
		cout << "Länge von v2: " << d << endl;
#endif

		Vektor v3 = v1; // 2. Konstruktor copy verwendet
		d = v3.length();
#ifdef debug
		cout << "Länge von v3: " << d << endl;
#endif

		Vektor v4 = v2 + v1;
		d = v4.length();
#ifdef debug
		cout << "Länge von v4: " << d << endl;
#endif

		double a = v2 * v1;
		//	Vektor v5 = v1 * 7;
		Vektor v5 = 7 * v1;

#ifdef debug
		cout << "v1: " << v1 << endl;
		cout << "v2: " << v2 << endl;
		cout << "v3: " << v3 << endl;
		cout << "v4: " << v4 << endl;
		cout << "v5: " << v5 << endl;

		cout << "v2 * v1 =  " << a << endl;
		cout << "(v2) normalized =  " << v2.getNormalized() << endl;
#endif
		v2.normalize();
#ifdef debug
		cout << "(v2) normalized =  " << v2 << endl;
		cout << "(v1) normalized =  " << v1.getNormalized() << endl;
		cout << "v1 dot v2 =  " << Vektor::dotProduct(v1.getNormalized(), v2) << endl;
#endif

		Vektor zero;
#ifdef debug
			cout << "zero normalized =  " << zero.getNormalized() << endl;
#endif
int *numbers;
try {
	numbers = new int[50];
	if(numbers != NULL) delete[] numbers;
} catch (bad_alloc e) {
	printf("numbers : %s\n", e.what());
}

timer.stopTimer();
}

time = clock() - time;
timer.stopTimer();
cout << "It took " << time << " clocks or " << (float) time / CLOCKS_PER_SEC << " CPU-seconds" << endl;
printf("%f seconds\n", timer.getSeconds());
cout << timer.getNumClocks() << " &  " << timer.getSeconds() << endl;

//  Stop timers
double wall1 = get_wall_time();
double cpu1 = get_cpu_time();

cout << "Wall Time = " << wall1 - wall0 << endl;
cout << "CPU Time  = " << cpu1 - cpu0 << endl;

//  Prevent Code Elimination
cout << endl;
//cout << "Sum = " << sum << endl;

cout << "loop ran "<< counts << " times in "<< TARGETTIME<<" CPU-seconds"<< endl;
return 0;
}
