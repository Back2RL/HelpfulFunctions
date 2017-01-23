//
// Created by leonard on 21.12.16.
//

#ifndef VL09_POS_H
#define VL09_POS_H


class Pos {
private:
	int row;
	int col;

public:
	Pos();

	virtual void init();

	virtual ~Pos();

	Pos(int row, int col);

	int getRow() const;

	void setRow(int row);

	int getCol() const;

	virtual void setCol(int col);
};


#endif //VL09_POS_H
