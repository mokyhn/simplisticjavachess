
public class History {
    public Move move;
   
    public Bitboard bb; // For future use wrt. draw by threefold repetition and a hash table for search evaluations.
	
    public boolean blackCanCastleShort;
    public boolean blackCanCastleLong;
    public boolean whiteCanCastleShort;
    public boolean whiteCanCastleLong;

    public int halfMoveClock; // Number of halfmoves since the last pawn advance or capture.
                              // Used to determine if a draw can be claimed under the fifty-move rule.


    public History (Move m, boolean bs, boolean bl, boolean ws, boolean wl, int hc) {
        move = (Move) m.clone();

        blackCanCastleShort = bs;
        blackCanCastleLong  = bl;
        whiteCanCastleShort = ws;
        whiteCanCastleLong  = wl;

        halfMoveClock       = hc;

    }


    public Object clone() {
        try { super.clone(); } catch (CloneNotSupportedException e) { }

        History theClone = (History) new History(
                this.move,
                this.blackCanCastleShort,
                this.blackCanCastleLong,
                this.whiteCanCastleShort,
                this.whiteCanCastleLong,
                this.halfMoveClock);
        return theClone;
    }

}
