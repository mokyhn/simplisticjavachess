/* 
 * File:   main.cpp
 * Author: mshome
 *
 * Created on 20. februar 2014, 08:47
 */

#include <cstdlib>
#include <iostream>

//#define NDEBUG 

#include <assert.h>

#include "piecetype.h"
#include "BitBoard.h"


using namespace std;

/*
 * 
 */
int main(int argc, char** argv) {
    cout << "Hello world!" << endl;
    assert(PieceColor::WHITE == 1);
    assert(PieceColor::BLACK == -1);
    assert(getIndexFromColor(PieceColor::WHITE) == 1);
    assert(getIndexFromColor(PieceColor::BLACK) == 0);
    assert(getColorFromIndex(0) == PieceColor::BLACK);
    assert(getColorFromIndex(1) == PieceColor::WHITE);
    assert(getSquareNoFromPos(0,0)==0);
    assert(getSquareNoFromPos(7,7)==63); 
    assert(setBitHigh(0) == 1);
    assert(setBitHigh(1) == 2);
    assert(setBitHigh(2) == 4);
    assert(setBitHigh(3) == 8);
    assert(setBitHigh(4) == 16);
    assert( sizeof(setBitHigh(17)) == 8); // 8 bytes 
    
    BitBoard b1 = BitBoard();
    
    b1.insertPiece(0, 0, PieceColor::BLACK, PieceType::ROOK);
    
    cout << b1;
    
    return 0;
}

