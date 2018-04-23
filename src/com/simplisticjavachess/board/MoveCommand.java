package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Piece;

/**
 *
 * @author Morten Kühnrich
 */
public class MoveCommand implements Command
{
    private final Piece piece;
    private final Location newLocation;
    
    public MoveCommand(Piece piece, Location newLocation)
    {
        this.piece = piece;
        this.newLocation = newLocation;
    }
    
    public Piece getPiece()
    {
        return piece;
    }
    
    public Location getNewLocation()
    {
        return newLocation;
    }
}
