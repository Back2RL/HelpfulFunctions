#include <iostream>
#include <iomanip>

#include "Zeit.h"

using namespace std;

Zeit::Zeit(unsigned int h, unsigned int m)
{
	this->h = h + m / 60;
	this->h %= 24;
	this->m = m % 60;
}

Zeit::Zeit(const Zeit &z)
{
	h = z.h;
	m = z.m;
}

// Dies ist eine methode in der Klasse (Namespace) Zeit
unsigned int Zeit::getH()
{
	return h;
}

unsigned int Zeit::getM()
{
	return m;
}

void Zeit::setH(unsigned int h)
{
	this->h = h % 24; // this: Zeiger auf das Objekt/Instanz => Instanzvariable h
}

void Zeit::setM(unsigned int m)
{
	h += m / 60;
	h %= 24;
	this->m = m % 60;
}

ostream& operator << (ostream &out, const Zeit &z)
{
	out << setw(2) << setfill('0') << z.h << ':' << setw(2) << setfill('0') << z.m;
	return out;
}

// Zeit + Zeit -> Zeit
Zeit operator+ (const Zeit &z1, const Zeit &z2)
{
	return Zeit(z1.h + z2.h, z1.m + z2.m);
}

Zeit& operator+= (Zeit &z1, const Zeit &z2)
{
	z1.h += z2.h;
	z1.m += z2.m;

	z1.h += z1.m / 60;
	z1.m = z1.m % 60;
	z1.h = z1.h % 24;

	return z1;
}

// Zeit + min -> Zeit
Zeit operator+ (const Zeit &z, unsigned int m)
{
	Zeit neu = z;

	neu.h += m / 60;

	neu.m += m % 60;
	neu.h += neu.m / 60;

	neu.h %= 24;
	neu.m %= 60;
	return neu;
}

// Zeit - Zeit -> min
int operator- (const Zeit &z1, const Zeit &z2)
{
	int m1, m2; // Zeit in min

	m1 = z1.h * 60 + z1.m;
	m2 = z2.h * 60 + z2.m;
	return m1 - m2;
}
