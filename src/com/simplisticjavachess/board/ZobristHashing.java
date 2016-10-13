/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;

public class ZobristHashing 
{
    private long hash;
    
    public void setBoard(Board board)
    {
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
    }
    
    
    public long getHash()
    {
        return hash;
    }
}
