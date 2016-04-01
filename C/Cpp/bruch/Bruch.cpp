#include "Bruch.h"


using namespace std;


Bruch::Bruch(const long &z, const long &n) 
{
	//exception!!!
	if(n == 0L)
		throw std::invalid_argument("Nenner ist 0");
	this->z = z;
	this->n = n;	
}

Bruch::Bruch(const Bruch &a)
{
	z = a.z;
	n = a.n;		
}

ostream& operator<<(ostream &os, const Bruch &a)
{
	os << a.z << "/" << a.n;	
	return os;
}

Bruch operator*(const Bruch &a, const Bruch &b)
{
	return Bruch(a.z * b.z, a.n * b.n); 
}

Bruch operator+(const Bruch &a, const Bruch &b)
{
	return Bruch(a.z * b.n + b.z * a.n, a.n * b.n); 
}

Bruch operator-(const Bruch &a, const Bruch &b)
{
	return Bruch(a.z * b.n - b.z * a.n, a.n * b.n); 
}

Bruch operator/(const Bruch &a, const Bruch &b)
{
	return Bruch(a.z * b.n, a.n * b.z); 
}

bool operator==(const Bruch &a, const Bruch &b)
{
	if(a.z * b.n == b.z * a.n)
		return true;
	return false;
}

long Bruch::kgV(const long a, const long b)
{
	return (a*b) / Bruch::ggT(a,b);
}

long Bruch::ggT(const long a, const long b)
{
	long r;
	long la = a;
	long lb = b;

	do {
		r = la % lb;
		la = lb;
		lb = r;
	}
	while(r != 0L);

	return la;
}


void Bruch::kuerzen(void) 
{
	long ggT = Bruch::ggT(this->z, this->n);
	this->z = this->z / ggT ;
	this->n = this->n / ggT ;
}
