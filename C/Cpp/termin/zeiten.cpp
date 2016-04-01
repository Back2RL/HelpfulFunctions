#include <iostream>
#include <iomanip>

#include "Zeit.h"

using namespace std;

// Testprogramm für Zeit-Objekte
int main(int argc, char *argv[])
{
	// Leerer Konstruktur
	Zeit z1;

	cout << z1 << endl; // -> 00:00

	z1.setH(2);
	z1.setM(71);

	cout << "z1(2,71) -> " << z1 << endl; // -> 03:11

	// Neues Objekt erzeugen (Konstruktor Aufruf)
	z1 = Zeit(5,96);

	cout << "z1(5,96) -> " << z1 << endl; // -> 06:36

	// Copy-Konstruktor
	Zeit z2 = z1;
	cout << "z2=z1 -> " << z2 << endl; // -> 06:36

	// Addition Zeit = Zeit + Zeit
	z2 = z1 + z2;
	cout << "z2=z1+z2 -> " << z2 << endl; // -> 13:12

	// Addition Zeit += Zeit
	z2 += z1;
	cout << "z2+=z1 ->" << z2 << endl; // -> 19:48

	Zeit z3;

	// Addition Zeit + Minuten
	z3 = z2 + 25;
	cout << "z3=z2+25 -> " << z3 << endl; // -> 20:13

	int dauer;

	cout << endl << "Dauer = Zeit - Zeit" << endl;
	cout << "z1: " << z1 << endl; // -> 06:36
	cout << "z2: " << z2 << endl; // -> 19:48
	cout << "z3: " << z3 << endl; // -> 20:13

	dauer = z3 - z2;
	cout << "z3-z2 -> " << dauer << endl; // -> 25
	dauer = z1 - z3;
	cout << "z1-z3 -> " << dauer << " -> " << Zeit(0,dauer) << endl; // -> -817 -> 14:39

	return 0;
}

