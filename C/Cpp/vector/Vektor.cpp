#include "Headers/Vektor.h"

using namespace std;

// die Memberfunktionen der Klasse Vektor
// Konstruktoren (hat keinen return-Wert)
Vektor::Vektor(const double &x, const double &y, const double &z) {
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

double Vektor::getX(void) const {
    return x;
}

void Vektor::setX(const double &x) {
    this->x = x;
}

double Vektor::getY(void) const {
    return y;
}

void Vektor::setY(const double &y) {
    this->y = y;
}

double Vektor::getZ(void) const {
    return z;
}

void Vektor::setZ(const double &z) {
    this->z = z;
}

double Vektor::length(void) const
// const bedeutet: in der Memberfunktion werden keine Variablen geändert (nur Lesezugriff)
{
    return sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
}

double Vektor::dotProduct(const Vektor &v1, const Vektor &v2) {
    return v1.getX() + v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
}

void Vektor::normalize(void) {
    Vektor norm = getNormalized();
    x = norm.getX();
    y = norm.getY();
    z = norm.getZ();
}

/*
 * @return Vektor with length of 1.0
 */
Vektor Vektor::getNormalized(void) const {
    if (!isZeroVector()) {
        double len = length();
        return Vektor(x, y, z) / len;
    }
    __throw_invalid_argument("Vektor is ZeroVector");
    return Vektor(0, 0, 0);
}

bool Vektor::isZeroVector(void) const {
    return length() <= SMALL_NUMBER;
}


Vektor operator+(const Vektor &a, const Vektor &b) {
    return Vektor(a.x + b.x, a.y + b.y, a.z + b.z);
}

ostream &operator<<(ostream &os, const Vektor &a) {
    os << "(" << a.x << "," << a.y << "," << a.z << ")";
    return os;
}

double operator*(const Vektor &a, const Vektor &b) {
    return a.x * b.x + a.y * b.y + a.z * b.z;
}

Vektor operator*(const Vektor &a, const double &d) {
    return Vektor(a.x * d, a.y * d, a.z * d);
}

Vektor operator*(const double &d, const Vektor &a) {
    return a * d;
}

Vektor operator/(const Vektor &a, const double &d) {
    return Vektor(a.x / d, a.y / d, a.z / d);
}

Vektor operator/(const double &d, const Vektor &a) {
    return a / d;
}
