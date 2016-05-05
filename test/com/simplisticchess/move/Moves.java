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
}
