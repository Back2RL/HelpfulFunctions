#ifndef Bruch_H
#define Bruch_H
#include <iostream>
#include <iomanip>
#include <stdexcept> // include exception handling

using namespace std;

class Bruch 
{
public:
		Bruch(const long &z = 1L, const long &n = 1L);
		Bruch(const Bruch &b);

		friend ostream& operator<<(ostream &os, const Bruch &a);
		friend Bruch operator*(const Bruch &a, const Bruch &b);
		friend Bruch operator+(const Bruch &a, const Bruch &b);
		friend Bruch operator-(const Bruch &a, const Bruch &b);
		friend Bruch operator/(const Bruch &a, const Bruch &b);
		friend bool operator==(const Bruch &a, const Bruch &b);
		static long ggT(const long a, const long b);
		static long kgV(const long a, const long b);
		void kuerzen(void); 

protected:
		long z, n;

private:
};


#endif
