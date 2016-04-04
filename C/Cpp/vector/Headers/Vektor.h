#ifndef Vektor_H
#define Vektor_H

#include <iostream>
#include <cmath>

#define SMALL_NUMBER 1E-9

#ifdef DEBUG
#define DEBUG true
#else
#define DEBUG false
#endif

using namespace std;

// die Klasse Vektor
class Vektor {
public:
    // 1. Konstruktor mit Standardwerten (hat keinen Returnwert)
    Vektor(const double &x = 0, const double &y = 0, const double &z = 0);

    Vektor(const Vektor &v); // 2. Konstruktor zum kopieren

    double getX(void) const;

    virtual ~Vektor() {if(DEBUG) std::cout << "Vektor destroyed" << endl; }

    void setX(const double &x);

    double getY(void) const;

    void setY(const double &y);

    double getZ(void) const;

    void setZ(const double &z);

    double length(void) const;

    static double dotProduct(const Vektor &v1, const Vektor &v2);

    void normalize(void);

    Vektor getNormalized(void) const;

    bool isZeroVector(void) const;

    //friend gestattet Zugriff auch auf private Objekte
    friend Vektor operator+(const Vektor &a, const Vektor &b);

    //Ausgabe eines Vektors als stream
    friend ostream &operator<<(ostream &os, const Vektor &a);

    friend double operator*(const Vektor &a, const Vektor &b);

    //Komutativgesetz muss auch berücksichtigt werden
    friend Vektor operator*(const Vektor &a, const double &d);

    friend Vektor operator*(const double &d, const Vektor &a);

    friend Vektor operator/(const Vektor &a, const double &d);

    friend Vektor operator/(const double &d, const Vektor &a);

private:
    double x;
    double y;
    double z;
};

#endif