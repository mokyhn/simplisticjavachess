/*
 * Bitboard structures
 */
public class Bitboard {
    public int bKing, wKing;

    public long bb[][];

    public Bitboard(Board b) {
        Piece p;
        int c;
        int t;

        bb = new long[2][7];

        for (t = 0; t < 7; t++) {
            bb[0][t] = 0;
            bb[1][t] = 0;
        }

        for (int i = 0; i < b.getNumberOfPieces(); i++) {
            p = b.getPiece(i);

            bb[p.color+1][p.type] =  bb[p.color+1][p.type] | setBitHigh(SquareNoFromPos(p.xPos, p.yPos));            
        }
    }

    static int SquareNoFromPos(int x, int y) {
        return y * 8 + x;
    }

    static long setBitHigh(int bitNo) {
        return 1 << bitNo;
    }


}
