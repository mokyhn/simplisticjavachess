/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;

public class ZobristHashing 
{    
    public static long setBoard(Board board)
    {
        long hash = 0L;
        
        for (Piece p : board.getPieces()) 
        {
            
            int index = p.getLocation().hashCode() + 64 * (p.getColor().getColor() + 1);
            
            long castling = 
                    board.canCastleShort() && (board.inMove() == Color.BLACK) ? ZobristRandomSource.BLACK_CAN_CASTLE_SHORT :
                    board.canCastleLong()  && (board.inMove() == Color.BLACK) ? ZobristRandomSource.BLACK_CAN_CASTLE_LONG : 
                    board.canCastleShort() && (board.inMove() == Color.WHITE) ? ZobristRandomSource.WHITE_CAN_CASTLE_SHORT :
                    board.canCastleLong()  && (board.inMove() == Color.WHITE) ? ZobristRandomSource.WHITE_CAN_CASTLE_LONG : 
                    0;
            hash = hash ^ ZobristRandomSource.NUMBERS[index] ^ castling;
        }
        
        return hash;
    }
    
}
