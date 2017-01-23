//
// Created by leonard on 21.12.16.
//

#include <iostream>
#include "Pos.h"

int Pos::getRow() const {
	return row;
}

void Pos::setRow(int row) {
	Pos::row = row;
}

int Pos::getCol() const {
	return col;
}

void Pos::setCol(int col) {
	Pos::col = col;
}

Pos::Pos() {
	setRow(0);
	setCol(0);
}

Pos::Pos(int row, int col) : row(row), col(col) {}

Pos::~Pos() {
	std::cout << "Pos destructed" << std::endl;
}

void Pos::init() {
std::cout << "Pos init" << std::endl;
}
