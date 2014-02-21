/* 
 * File:   BitBoard.h
 * Author: Morten Kühnrich
 *
 * Created on February 21, 2014, 11:42 AM
 */

#ifndef BITBOARD_H
#define	BITBOARD_H

#include "piecetype.h"
#include <cstdint>
#include <ostream>

#define getIndexFromColor(COLOR) (COLOR == PieceColor::WHITE ? 1 : 0)
#define getColorFromIndex(INDEX) (INDEX == 1 ? PieceColor::WHITE : PieceColor::BLACK)
#define getSquareNoFromPos(X,Y) (Y * 8 + X)
#define setBitHigh(BIT_NO) (static_cast<uint64_t>(1) << BIT_NO)

class BitBoard {
public:
    BitBoard();
    //BitBoard(const BitBoard& orig);
      //TODO:
      void insertPiece(uint8_t x, uint8_t y, PieceColor color, PieceType type);
      //bool hasPiece(uint8_t x, uint8_t y, PieceColor color, PieceType type);
      // getPiece(int x, int y) 
      //public Piece removePiece(int x, int y) 
       friend std::ostream& operator<<(std::ostream &strm, const BitBoard &bb);  
      
    virtual ~BitBoard();
protected:
     uint64_t bb[NUM_COLORS][NUM_PIECE_TYPES];    

private:

};

#endif	/* BITBOARD_H */

