#include <iostream>
#include <cmath>

using namespace std;

// die Klasse Vektor
class Vektor
{
	public:
		// 1. Konstruktor mit Standardwerten (hat keinen Returnwert)
		Vektor(const double &x = 0, const double &y = 0, const double &z = 0);
		Vektor(const Vektor &v); // 2. Konstruktor zum kopieren

		double getX(void) const;
		void setX(const double &x);
		double getY(void) const;
		void setY(const double &y);
		double getZ(void) const;
		void setZ(const double &z);

		double length(void) const;

		//friend gestattet Zugriff auch auf private Objekte
		friend Vektor operator+(const Vektor &a, const Vektor &b);
	
		//Ausgabe eines Vektors als stream
		friend ostream& operator<<(ostream &os, const Vektor &a);

		friend double operator*(const Vektor &a, const Vektor &b);

		//Komutativgesetz muss auch berücksichtigt werden
		friend Vektor operator*(const Vektor &a, const double &d);
		friend Vektor operator*(const double &d, const Vektor &a);

	private:
		double x, y, z;
};

