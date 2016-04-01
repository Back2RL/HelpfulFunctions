// vim: ts=4 sw=4
#include <iostream>
#include <iomanip>
#include <cstdlib>
#include <cmath>

#define len 10

using namespace std;


double fx(double &fxwert, double &x, double &a, double &b, double &c);
double fpx(double &fpxwert, double &x, double &a, double &b, double &c);
double readdouble(char name[len], double &variable);


int main()
{

	double xu, xo, dx, x, a, b, c;
	double fxwert;
	double fpxwert;

	::readdouble((char*) "X(unten)", xu);
	do {
		::readdouble((char*) "X(oben)", xo);
	}
	while (xo <= xu);
	do {
		::readdouble((char*) "d", dx);
	}
	while (dx < pow(10.0, -3.0) || dx > 0.5);
	do {
		::readdouble((char*) "a", a);
	}
	while (a == 0.0);
	::readdouble((char*) "b", b);
	::readdouble((char*) "c", c);

	cout.setf(ios::fixed);
	cout << endl << setw(9) << "x" << " |" << setw(9) << "f(x)";
	cout << " |" << setw(9) << "f'(x)" << endl;
	cout.fill('-');
	cout << setw(11) << "+" << setw(10) << "-" << "+" << setw(10) << "-" << endl;
	cout.fill(' ');

	cout.precision(3);

	for (x = xu; x <= xo; x += dx) {

		::fx(fxwert, x, a, b, c);
		cout << setw(9) << x << " |" << setw(9) << fxwert;
		::fpx(fpxwert, x, a, b, c);
		cout << " |" << setw(9) << fpxwert << endl;

	} 

	return 0;
}


double fx(double &fxwert, double &x, double &a, double &b, double &c)
{
	fxwert = a*::pow(x, 2.0) + b*x + c;
	return fxwert;
}

double fpx(double &fpxwert, double &x, double &a, double &b, double &c)
{
	double f1wert = fpxwert;
	double f2wert = fpxwert;
	double x1 = x+1E-7;
	double x2 = x-1E-7;

	::fx(f1wert,x1,a,b,c);
	::fx(f2wert,x2,a,b,c);
	fpxwert = (f1wert - f2wert) / (2E-7);
	return fpxwert;
}

double readdouble(char name[len], double &variable)
{
	bool rc;
	{
		cout << "Geben Sie "<< name << " ein: ";
		cin.clear();
		cin >> variable;

		rc = cin.good();
		cin.clear();
		while(cin.get() != '\n')
			continue;
	}
	while (rc != true);
		return variable;	

}
