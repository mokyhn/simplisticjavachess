
public class History {
    public Move move;
   
    public Bitboard bbposition; // For future use wrt. draw by threefold repetition and a hash table for search evaluations.
	
    public boolean blackCanCastleShort;
    public boolean blackCanCastleLong;
    public boolean whiteCanCastleShort;
    public boolean whiteCanCastleLong;

    public int halfMoveClock; // Number of halfmoves since the last pawn advance or capture.
                              // Used to determine if a draw can be claimed under the fifty-move rule.


    public int halfMovesIndex3PosRepition;

    public Piece inCheckByPiece;

    public History() {};

    public History (Move m, boolean bs, boolean bl, boolean ws, boolean wl, int hc, int h3, Bitboard b, Piece ic) {
        move = m;

        blackCanCastleShort = bs;
        blackCanCastleLong  = bl;
        whiteCanCastleShort = ws;
        whiteCanCastleLong  = wl;

        halfMoveClock       = hc;

        halfMovesIndex3PosRepition = h3;

        bbposition          = b;

        inCheckByPiece      =  ic;

    }


    public History clone() {
        History theClone = new History();

        theClone.move                       = this.move.clone();
        theClone.blackCanCastleLong         = this.blackCanCastleShort;
        theClone.blackCanCastleShort        = this.blackCanCastleShort;
        theClone.whiteCanCastleLong         = this.whiteCanCastleLong;
        theClone.whiteCanCastleShort        = this.whiteCanCastleShort;
        theClone.halfMoveClock              = this.halfMoveClock;
        theClone.halfMovesIndex3PosRepition = this.halfMovesIndex3PosRepition;
        theClone.bbposition                 = this.bbposition.clone();
        
        if (inCheckByPiece == null) theClone.inCheckByPiece = null;
        else                        theClone.inCheckByPiece = (Piece) inCheckByPiece.clone();
               
        return theClone;
    }

}
