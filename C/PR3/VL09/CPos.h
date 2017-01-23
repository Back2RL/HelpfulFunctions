//
// Created by leonard on 21.12.16.
//

#ifndef VL09_CPOS_H
#define VL09_CPOS_H


#include "Pos.h"

class CPos : public Pos{
private:
	int color;
public:
	CPos();

	CPos(int row, int col, int color);

	CPos(int row, int col);

	CPos(int color);

	virtual ~CPos();

	void init() override;
};


#endif //VL09_CPOS_H
