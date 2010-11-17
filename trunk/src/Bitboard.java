/*
 * Bitboard structures
 */
public class Bitboard {
    public  long bb[][];

    public Bitboard() {
       bb = new long[2][6];

    };

    public Bitboard(Board b) {
        Piece p;
        int   c, t;

        bb = new long[2][6];

        for (t = 0; t < 6; t++) {
            bb[0][t] = 0;
            bb[1][t] = 0;
        }

        for (int i = 0; i < b.getNumberOfPieces(); i++) {
            p = b.getPiece(i);

            if (p.color == Piece.BLACK) c = 0;
            else c = 1;

            t = p.type;

            if (t != Piece.EMPTY) bb[c][t] =  bb[c][t] | setBitHigh(SquareNoFromPos(p.xPos, p.yPos));
        }
    }

    static int SquareNoFromPos(int x, int y) {
        return y * 8 + x;
    }

    static long setBitHigh(int bitNo) {
        return 1L << bitNo;
    }

    public String toString(long b) {
        String r = "";
        int x, y;

        for (y = 7; y >= 0; y--) {
         for (x = 0; x <= 7; x++) {
             if (((1L << SquareNoFromPos(x, y)) & b) != 0) {
                 r = r + '+';
             } else r = r + '.';
         }
         r = r + "\n";
        }
/*
        for (int i = 0; i <= 63; i++) {
            if ((i+1) % 8 == 0) r = r + "\n";
            if (((1L << i) & b) != 0) r = r + '+';
                    else r = '.' + r;
        }*/
        return r;
    }

    public boolean equals(Bitboard b2) {
        for (int t = 0; t < 6; t++) {
            if (this.bb[0][t] != b2.bb[0][t] ||
                this.bb[1][t] != b2.bb[1][t]) return false;
        }
        
        return true;
    }

      public Object clone() {
        int t;
          try { super.clone(); } catch (CloneNotSupportedException e) { }

        Bitboard theClone = new Bitboard();

        for (t = 0; t < 6; t++) {
            theClone.bb[0][t] = this.bb[0][t];
            theClone.bb[1][t] = this.bb[1][t];
        }

        return theClone;
      }

}
