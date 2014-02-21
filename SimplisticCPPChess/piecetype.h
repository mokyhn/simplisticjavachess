/* 
 * File:   piecetype.h
 * Author: Morten Kühnrich
 *
 * Created on 20. februar 2014, 08:59
 */

#ifndef PIECETYPE_H
#define	PIECETYPE_H

#include <cstdint>

#define NUM_COLORS 2 

enum PieceColor : int8_t
{
        BLACK = -1,
        WHITE = 1,
        NO_COLOR = 0
};

#define NUM_PIECE_TYPES 6

enum PieceType : uint8_t 
{
        PAWN = 0,
        BISHOP,
        KNIGHT,
        ROOK,
        QUEEN,
        KING,
        EMPTY
};


#endif	/* PIECETYPE_H */

