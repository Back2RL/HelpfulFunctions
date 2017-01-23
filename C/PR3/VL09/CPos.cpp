//
// Created by leonard on 21.12.16.
//

#include <iostream>
#include "CPos.h"

CPos::CPos() {}

CPos::CPos(int row, int col) : Pos(row, col) {}

CPos::CPos(int color) : color(color) {}

CPos::CPos(int row, int col, int color) : Pos(row, col), color(color) {}

CPos::~CPos() {
	std::cout << "CPos destructed" << std::endl;
}

void CPos::init() {
	Pos::init();
	std::cout << "CPos init" << std::endl;
}

