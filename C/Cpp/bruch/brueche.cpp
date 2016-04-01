#include <iostream>
#include <iomanip>
#include <stdexcept> // include exception handling

#include "Bruch.h"

using namespace std;


int main()
{
	Bruch b1 = Bruch(2, 6);
	Bruch b2 = Bruch(2, 7);
	Bruch b3 = Bruch(2,3) * Bruch(6,10);
	Bruch erg;

	cout << "b1: " << b1 << endl;
	cout << "b2: " << b2 << endl;
	cout << "b3: " << b3 << endl;

	// illegal, da nenner 0 ist (exception!!!)
	try {
		Bruch b4 = Bruch(1,0);
		cout << "b4: " << b4 << endl;
	}
	catch(invalid_argument& e) {
	//catch(exception& e) { (Wenn ich nicht weiß welche exception kommt)
		cout << "oops: b4 kann nicht erzeugt werden!" << endl;
		cout << e.what() << endl;
	}
	//catch(...) man kann auch auf verschiedene exceptions prüfen
	//{}

	// Berechnungen
	erg = b1 + b2;
	cout << b1 << " + " << b2 << " = " << erg << " --gekürzt--> ";
	erg.kuerzen();
	cout << erg << endl;

	erg = b3 - b2;
	cout << b3 << " - " << b2 << " = " << erg << " --gekürzt--> ";
	erg.kuerzen();
	cout << erg << endl;

	erg = b1 * b3;
	cout << b1 << " * " << b3 << " = " << erg << " --gekürzt--> ";
	erg.kuerzen();
	cout << erg << endl;

	erg = b1 / b2;
	cout << b1 << " / " << b2 << " = " << erg << " --gekürzt--> ";
	erg.kuerzen();
	cout << erg << endl;

	erg = (b1 * b2) / b3;
	cout << "(" << b1 << " * " << b2 << ") / " << b3 << " = " << erg << " --gekürzt--> ";
	erg.kuerzen();
	cout << erg << endl;

	b1 = Bruch(24, 144);
	b2 = Bruch(7, 42);
	b3 = Bruch(1, 4);

	cout << b1 << " == " << b2 << " -> sind";
	if (b1 == b2)
		cout << " gleich" << endl;
	else
		cout << " ungleich" << endl;

	cout << b1 << " == " << b3 << " -> sind";
	if (b1 == b3)
		cout << " gleich" << endl;
	else
		cout << " ungleich" << endl;

	cout << b3.kgV(3L,9L) << endl;
	cout << Bruch::kgV(3L,9L) << endl;
	cout << Bruch::ggT(3L,9L) << endl;

	return 0;
}
