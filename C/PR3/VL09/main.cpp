#include <iostream>
#include "CPos.h"

#define deleteSafe(ptr) delete ptr; ptr = nullptr;
#define deleteArraySafe(ptr) delete[] ptr; ptr = nullptr;

class ptr;

int main() {
	std::cout << "Hello, World!" << std::endl;
	CPos pos;
	pos.Pos::init();
	pos.init();

	CPos *posPtr{new CPos};
	deleteSafe(posPtr);
	printf("%p\n", posPtr);
	printf("Here\n");

	return 0;
}