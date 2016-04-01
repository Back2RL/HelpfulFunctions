#include <iostream>
#include <iomanip>
#include <cmath>

#include "Vektor.h"

using namespace std;

// Hauptprogramm
int main (int argc, char *argv[])
{
	// ein Objekt der Klasse Vektor
	Vektor v1; //1. Konstruktor verwendet (Standardwerte, also (0,0,0))

	v1.setX(1);
	v1.setY(2);
	v1.setZ(3);

	cout.setf(ios::fixed);
	cout.precision(3);


	double d = v1.length();
	cout << "Länge von v1: " << d << endl;
	
	double x = v1.getX();
	double y = v1.getY();
	double z = v1.getZ();
	cout << "Vektor v1 = (" << x <<"," << y << "," << z << ")" << endl;

	Vektor v2 = Vektor(5,7,8); //1. Konstruktor verwendet
	d = v2.length();
	cout << "Länge von v2: " << d << endl;

	Vektor v3 = v1; // 2. Konstruktor copy verwendet
	d = v3.length();
	cout << "Länge von v3: " << d << endl;
	
	Vektor v4 = v2 + v1;
	d = v4.length();
	cout << "Länge von v4: " << d << endl;
	
	double a = v2 * v1;
//	Vektor v5 = v1 * 7;
	Vektor v5 = 7 * v1;

	cout << "v1: " << v1 << endl;
	cout << "v2: " << v2 << endl;
	cout << "v3: " << v3 << endl;
	cout << "v4: " << v4 << endl;
	cout << "v5: " << v5 << endl;
	cout << "v2 * v1 =  " << a << endl;
	
	return 0;
}
