#include "Vektor.h"

using namespace std;

// die Memberfunktionen der Klasse Vektor
// Konstruktoren (hat keinen return-Wert)
Vektor::Vektor(const double &x, const double &y, const double &z)
{
	this->x = x;
	this->y = y;
	this->z = z;
}

Vektor::Vektor(const Vektor &v) // copy-Konstruktor
{
//	x = v.getX();
//	y = v.getY();
//	z = v.getZ();
	x = v.x;
	y = v.y;
	z = v.z;
}

double Vektor::getX(void) const
{
	return x;
}

void Vektor::setX(const double &x)
{
	this->x = x;
}

double Vektor::getY(void) const
{
	return y;
}

void Vektor::setY(const double &y)
{
	this->y = y;
}
double Vektor::getZ(void) const
{
	return z;
}

void Vektor::setZ(const double &z)
{
	this->z = z;
}

double Vektor::length(void) const
// const bedeutet: in der Memberfunktion werden keine Variablen geändert (nur Lesezugriff)
{
	return sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
}


Vektor operator+( const Vektor &a, const Vektor &b)
{
	return Vektor(a.x + b.x, a.y + b.y, a.z + b.z);
}

ostream& operator<<(ostream &os, const Vektor &a)
{
	os << "(" << a.x << "," << a.y << "," << a.z << ")";	
	return os;
}

double operator*(const Vektor &a, const Vektor &b)
{
	return a.x * b.x + a.y * b.y + a.z * b.z;
}

Vektor operator*(const Vektor &a, const double &d)
{
	return Vektor(a.x * d, a.y * d, a.z * d);
}
Vektor operator*(const double &d,const Vektor &a)
{
	return a * d;
}
