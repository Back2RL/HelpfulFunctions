#ifndef Termin_H
#define Termin_H

#include <iostream>
#include <string>

#include "Zeit.h"

using namespace std;

class Termin : public Zeit
{
	protected:
		string text;
	public:
		Termin(unsigned int h = 0, unsigned int m = 0, const string &s = string("-"));
		Termin(const Termin &t);

		void setText(string s);
		string getText(void);

		friend ostream& operator << (ostream &os, const Termin &t);
};
#endif
