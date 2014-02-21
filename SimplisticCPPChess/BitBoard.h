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

#define getIndexFromColor(COLOR) (COLOR == PieceColor::WHITE ? 1 : 0)
#define getColorFromIndex(INDEX) (INDEX == 1 ? PieceColor::WHITE : PieceColor::BLACK)
#define getSquareNoFromPos(X,Y) (Y * 8 + X)
#define setBitHigh(BIT_NO) (static_cast<uint64_t>(1) << BIT_NO)

class BitBoard {
public:
    BitBoard();
    //BitBoard(const BitBoard& orig);
          //DONE:
      //inline uint8_t getIndexFromColor(PieceColor color);
      //inline PieceColor getColorFromIndex(uint8_t index);
      //inline uint8_t getSquareNoFromPos(uint8_t x, uint8_t y);
      //inline int64_t setBitHigh(uint8_t bitNo); 
      
      //TODO:
      //void insertPiece(uint8_t x, uint8_t y, PieceColor color, PieceType type);
      //bool hasPiece(uint8_t x, uint8_t y, PieceColor color, PieceType type);
      // getPiece(int x, int y) 
      //public Piece removePiece(int x, int y) 
    virtual ~BitBoard();
protected:
     uint64_t bb[NUM_COLORS][NUM_PIECE_TYPES];    

private:

};

#endif	/* BITBOARD_H */

