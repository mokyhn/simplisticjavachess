/* 
 * File:   BitBoard.cpp
 * Author: Morten Kühnrich
 * 
 * Created on February 21, 2014, 11:42 AM
 */

#include "BitBoard.h"

BitBoard::BitBoard() {
     for (int t = 0; t < NUM_PIECE_TYPES; t++) {
            bb[0][t] = 0;
            bb[1][t] = 0;
     }
}


void BitBoard::insertPiece(uint8_t x, uint8_t y, PieceColor color, PieceType type) {
  bb[getIndexFromColor(color)][type] = 
          bb[getIndexFromColor(color)][type] |
          setBitHigh(getSquareNoFromPos(x, y));
}

std::ostream& operator<<(std::ostream &strm, const BitBoard &bb) { 
 return strm << "Test" << std::endl;
}

BitBoard::~BitBoard() {
}

