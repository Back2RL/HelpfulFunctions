#include <iostream>
#include <iomanip>

#include "Termin.h"
#include "Zeit.h"

int main()
{
	Termin t = Termin(12,0,"Testat");
	Zeit z = Zeit(10,54);

	cout << t << endl;
	cout << z << endl;
	


	return 0;
}
