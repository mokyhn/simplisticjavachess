package sjc;

public final class State implements Cloneable{
    public Move     move;
    public int      inMove;
    
    public int      moveNumber          = 0;
    
    public Bitboard bbposition; // Used wrt. check for draw by threefold repetition. Could be used in a hash table for search evaluations.
	
    public boolean  blackCanCastleShort;
    public boolean  blackCanCastleLong;
    public boolean  whiteCanCastleShort;
    public boolean  whiteCanCastleLong;

    public boolean  drawFlag;
    public boolean  mateFlag;
    
    public int      halfMoveClock; // Number of halfmoves since the last pawn advance or capture.
                                 // Used to determine if a draw can be claimed under the fifty-move rule.


    /**
     * Number of halfmoves since last pawnmove (including promotions) or capture move
     * When searching for threefold repitions search from this index...
     */
    public int     halfMovesIndex3PosRepition;

    public Piece   inCheckByPiece;

    public State() {
        this.move                       = new Move();
        this.moveNumber                 = 0;        
        this.inMove                     = Piece.NO_COLOR;
        this.halfMoveClock              = 0;
        this.halfMovesIndex3PosRepition = 0;
        this.inCheckByPiece             = null;
        this.drawFlag                   = false;
        this.mateFlag                   = false;
    };

    @Override
    public String toString() {
       String s = "";
        
       String blackCastleShort = " ",
              blackCastleLong  = " ",
              whiteCastleShort = " ",
              whiteCastleLong  = " ";

       if (blackCanCastleShort) blackCastleShort = "X";
       if (blackCanCastleLong)  blackCastleLong  = "X";
       if (whiteCanCastleShort) whiteCastleShort = "X";
       if (whiteCanCastleLong)  whiteCastleLong  = "X";

       s =     "\n----------------------------State----------------------------\n";
       if (drawFlag) s = s + "It's a draw!\n";
       if (mateFlag) s = s + "Mate!\n";
       s = s + "Black can castle long: [" + blackCastleLong + "],       Black can castle short: [" + blackCastleShort + "]\n";
       s = s + "White can castle long: [" + whiteCastleLong + "],       White can castle short: [" + whiteCastleShort + "]\n";
       s = s + "Number of half moves since last pawn move: " + halfMoveClock + "\n";
       s = s + "Index searched from when checking 3 fold repetition: " + halfMovesIndex3PosRepition + "\n";
       s = s + "Ply Move number " + moveNumber + "\n";


       return s;
    }
    
    @Override
    public State clone() {
        final State theClone = new State();

        theClone.move                       = this.move.clone();
        theClone.inMove                     = this.inMove;
        theClone.moveNumber                 = this.moveNumber;
        theClone.blackCanCastleLong         = this.blackCanCastleLong;
        theClone.blackCanCastleShort        = this.blackCanCastleShort;
        theClone.whiteCanCastleLong         = this.whiteCanCastleLong;
        theClone.whiteCanCastleShort        = this.whiteCanCastleShort;
        theClone.halfMoveClock              = this.halfMoveClock;
        theClone.halfMovesIndex3PosRepition = this.halfMovesIndex3PosRepition;
        theClone.drawFlag                   = this.drawFlag;
        theClone.mateFlag                   = this.mateFlag;
        
        if (this.bbposition == null) {
         theClone.bbposition = null;
        } else {        
         theClone.bbposition                 = this.bbposition.clone();
        } 
        
        if (inCheckByPiece == null) theClone.inCheckByPiece = null;
        else                        theClone.inCheckByPiece = inCheckByPiece.clone();
               
        return theClone;
    }

}
