//
// Created by leonard on 07.12.16.
//

#include "Person.h"

float Person::getZahl() const {
	return zahl;
}

Person::Person() {}

Person::Person(const float zahl, Person *const another) : zahl(zahl), another(another) {

}

Person::~Person() {
	delete (another);
}

