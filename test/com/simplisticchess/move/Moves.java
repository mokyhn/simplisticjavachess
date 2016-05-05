/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticchess.move;

import com.simplisticchess.piece.Color;
import com.simplisticchess.position.Locations;

public class Moves
{
    public final static Move WHITE_SHORT_CASTLING() { return new Move(Locations.E1, Locations.G1, MoveType.CASTLE_SHORT, null, Color.WHITE); }
    public final static Move WHITE_LONG_CASTLING()  { return new Move(Locations.E1, Locations.C1, MoveType.CASTLE_LONG,  null, Color.WHITE); }
    
    public final static Move BLACK_SHORT_CASTLING() { return new Move(Locations.E8, Locations.G8, MoveType.CASTLE_SHORT, null, Color.BLACK); }
    public final static Move BLACK_LONG_CASTLING()  { return new Move(Locations.E8, Locations.C8, MoveType.CASTLE_LONG,  null, Color.BLACK); }
}
