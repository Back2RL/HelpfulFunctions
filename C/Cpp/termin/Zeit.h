#ifndef Zeit_H
#define Zeit_H

#include <iostream>

using namespace std;

class Zeit {
public:
	// Kontruktor(en)
	Zeit(unsigned int h = 0, unsigned int m = 0);
	Zeit(const Zeit &z);

	unsigned int getH(void);
	unsigned int getM(void);
	void setH(unsigned int h);
	void setM(unsigned int m);

	friend ostream& operator<< (ostream &out, const Zeit &z);
	friend Zeit operator+ (const Zeit &z1, const Zeit &z2); // Zeit + Zeit -> Zeit
	friend Zeit& operator+= (Zeit &z1, const Zeit &z2);
	friend Zeit operator+ (const Zeit &z, unsigned int m); // Zeit + min -> Zeit
	friend int operator- (const Zeit &z1, const Zeit &z2); // Zeit - Zeit -> min

protected:
	unsigned int h, m; // Instanzvariablen!

};
#endif
