#include <iostream>
#include "Person.h"

#define print(args) std::cout << args << std::endl

using namespace std;

int main() {
	std::cout << "Hello, World!" << std::endl;
	print("Hello, World!");

	Person *test = new Person{1.0, nullptr};
	Person test2;

	cout << test->getZahl() << endl;
	cout << test2.getZahl() << endl;

	delete test;

	print("Fertig");
	printf("Fertig\n");
	return 0;
}