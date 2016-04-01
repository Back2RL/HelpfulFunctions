#ifndef Zeit_H
#define Zeit_H

#include <iostream>

using namespace std;

class Zeit
{
public:

	Zeit(const unsigned int &h = 0, const unsigned int & m = 0);
	
	friend ostream& operator<<(ostream &os, const Zeit &zeit);
	void setH( const unsigned int &h);	
	void setM( const unsigned int &m);	
	
	friend Zeit operator+(const Zeit &a, const Zeit &b);
	friend void operator+=(Zeit &a, const Zeit &b);
	friend Zeit operator+(const Zeit &a, const unsigned m);
	friend int operator-(const Zeit &a, const Zeit &b);

private:
		unsigned int h, m;

};
#endif
