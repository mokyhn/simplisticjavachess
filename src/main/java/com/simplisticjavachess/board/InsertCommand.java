package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Piece;

/**
 *
 * @author Morten Kühnrich
 */
public class InsertCommand implements Command
{
    private final Piece piece;
    
    public InsertCommand(Piece piece)
    {
        this.piece = piece;
    }
    
    public Piece getPiece()
    {
        return piece;
    }
}
