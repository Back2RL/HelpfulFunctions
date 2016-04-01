#include <iostream>
#include <iomanip>
#include <string>

#include "Termin.h"

using namespace std;

Termin::Termin(unsigned int h, unsigned int m, const string &s) : Zeit(h,m)
{
	this->text = string(s);
}

Termin::Termin(const Termin &t) : Zeit(t.h, t.m)
{
	text = string(t.text);
}

void Termin::setText(string s)
{
	this->text = string(s);
}

string Termin::getText(void)
{
	return text;
}

ostream& operator << (ostream &os, const Termin &t)
{
	os << (Zeit) t << " -> " << t.text;
	return os;
}
