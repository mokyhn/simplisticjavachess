/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.move;

import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.board.Locations;

public class Moves
{
    public final static Move WHITE_SHORT_CASTLING() { return new Move(Locations.E1, Locations.G1, MoveType.CASTLE_SHORT, null, Color.WHITE); }
    public final static Move WHITE_LONG_CASTLING()  { return new Move(Locations.E1, Locations.C1, MoveType.CASTLE_LONG,  null, Color.WHITE); }
    
    public final static Move BLACK_SHORT_CASTLING() { return new Move(Locations.E8, Locations.G8, MoveType.CASTLE_SHORT, null, Color.BLACK); }
    public final static Move BLACK_LONG_CASTLING()  { return new Move(Locations.E8, Locations.C8, MoveType.CASTLE_LONG,  null, Color.BLACK); }
}
