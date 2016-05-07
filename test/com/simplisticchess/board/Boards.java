/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticchess.board;

public class Boards
{
    public final static Board WHITE_IN_MOVE_CASTLING() { return new Board("r3k2r/8/8/8/8/8/8/R3K2R w KQkq"); }
    public final static Board WHITE_IN_MOVE_CANNOT_CASTLE() { return new Board("r3k2r/8/8/8/8/8/8/R3K2R w kq"); }
    public final static Board AFTER_WHITE_SHORT_CASTLING() {return new Board("r3k2r/8/8/8/8/8/8/R4RK1 b kq"); }
    public final static Board AFTER_WHITE_LONG_CASTLING() {return new Board("r3k2r/8/8/8/8/8/8/2KR3R b kq"); }

    
    public final static Board BLACK_IN_MOVE_CASTLING() { return new Board("r3k2r/8/8/8/8/8/8/R3K2R b KQkq"); }
    public final static Board BLACK_IN_MOVE_CANNOT_CASTLE() { return new Board("r3k2r/8/8/8/8/8/8/R3K2R b KQ"); }
    public final static Board AFTER_BLACK_SHORT_CASTLING() {return new Board("r4rk1/8/8/8/8/8/8/R3K2R w kq"); }
    public final static Board AFTER_BLACK_LONG_CASTLING() {return new Board("2kr3r/8/8/8/8/8/8/R3K2R w kq"); }
        
}
