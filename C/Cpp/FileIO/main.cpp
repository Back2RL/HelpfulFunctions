#include <iostream>
#include "src/FileManager.h"

int main() {
    std::cout << "Hello, World!" << std::endl;
	FileManager::printFileToConsole("test.txt");

    return 0;
}