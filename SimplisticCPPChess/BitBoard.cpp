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
    int x, y;
    
    strm << "\n _______________\n";

 for (y = 7; y >= 0; y--) {
            for (x = 0; x < 8; x++) {
                 strm << " "; 
                  //p =  getPiece(x, y);
                  //if (p == null) s = s + ".";
                  //else s = s + p.toString(); 
            }
          strm << "     " << y+1 << "\n";
        } // end last for-loop
 
        strm << " _______________\n";
        strm << " a b c d e f g h\n";
     
    
    return strm;
}

BitBoard::~BitBoard() {
}

