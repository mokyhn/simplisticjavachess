/* 
 * File:   main.cpp
 * Author: mshome
 *
 * Created on 20. februar 2014, 08:47
 */

#include <cstdlib>
#include <iostream>

#include "piecetype.h"
#include "bitboard.h"

using namespace std;

/*
 * 
 */
int main(int argc, char** argv) {
    cout << "Hello world!" << endl;
    cout << (getIndexFromColor(PieceColor::WHITE) == 1);
    return 0;
}

