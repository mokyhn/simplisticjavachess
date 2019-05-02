package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Piece;

/**
 *
 * @author Morten Kühnrich
 */
public class RemoveCommand implements Command
{
    private final Piece piece;
    
    public RemoveCommand(Piece piece)
    {
        this.piece = piece;
    }
    
    public Piece getPiece()
    {
        return piece;
    }
}
