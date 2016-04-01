#include <iomanip>
#include "Zeit.h"

Zeit::Zeit(const unsigned int &h, const unsigned int & m)
{
	unsigned int hnew = h +  m / 60;
	this->h = hnew % 24;
	this->m = m % 60;
}

ostream& operator<<(ostream &os, const Zeit &zeit)
{
	cout.fill('0');
	os << setw(2) << zeit.h << ":" << setw(2) << zeit.m; 
	return os;
}

Zeit operator+(const Zeit &a, const Zeit &b)
{
	return Zeit(a.h + b.h, a.m + b.m);
}

void operator+=(Zeit &a, const Zeit &b)
{
	a = a + b;
}

Zeit operator+(const Zeit &a, const unsigned m)
{
	return Zeit(a.h, a.m + m);
}

int operator-(const Zeit &a, const Zeit &b)
{
	int t1 = int(a.h * 60 + a.m);
	int t2 = int(b.h * 60 + b.m);
	return t1 - t2;
}

void Zeit::setH( const unsigned int &h)
{
	this->h = h % 24;
}

void Zeit::setM( const unsigned int &m)
{
	Zeit::setH( this->h + m / 60);	
	this->m = m % 60;
}

