//
// Created by leonard on 07.12.16.
//

#ifndef VL08_PERSON_H
#define VL08_PERSON_H

#include <string>

class Person {
private:
	float zahl;
	Person *another;
public:
	float getZahl() const;

	Person();

	Person(const float zahl, Person *const another);

	virtual ~Person();
};


#endif //VL08_PERSON_H
