/**
 * @author Morten Kühnrich
 * @year 2010
 * A concise representation of board layouts.
 */
package sjc;

import sjc.Interfaces.IBitBoard;

public class Bitboard implements IBitBoard {
    protected static final int NUM_COLORS      = 2; // Black and white
    protected static final int NUM_PIECE_TYPES = 6; // Pawn, knight, bishop, rook, queen, king,

    protected long bb[][];  
     
    // Create an empty bitboard
    public Bitboard() {
       bb = new long[NUM_COLORS][NUM_PIECE_TYPES];
    
       for (int t = 0; t < NUM_PIECE_TYPES; t++) {
            bb[0][t] = 0;
            bb[1][t] = 0;
       }
    }

    /**
     * Construct a bitboard from a board
     * @param b the input board
     */
    public Bitboard(Board b) {
        this();    // Call constructor
        
        Piece p;
        int   c, t;
 
        for (int i = 0; i < b.getNumberOfPieces(); i++) {
            p = b.getPiece(i);
            c = getIndexFromColor(p.color);
            t = p.type;
            if (t != Piece.EMPTY) {
                bb[c][t] =  bb[c][t] | setBitHigh(getSquareNoFromPos(p.xPos, p.yPos));
            }
        }
    }

    protected final int getIndexFromColor(int color) {
     assert(color == Piece.WHITE || color == Piece.BLACK);
     if (color == Piece.BLACK) { return 0; }
     return 1;
    }

    protected int getColorFromIndex(int index) {
     assert(index == 0 || index == 1);
     return index == 0 ? Piece.BLACK : Piece.WHITE;
    }
    
    private int countOnes(long v) {
     int res = 0; 

     for (int i = 0; i < 64; i++) {
      res += ((1L << i) & v) != 0 ? 1 : 0;
     }
     return res;
    }

    public int getNumberOfPieces() {
       int res = 0;
        for (int t = 0; t < 6; t++) {
         res += countOnes(bb[0][t]) + countOnes(bb[1][t]);
        }
       return res;
    }
    
    // Returns number in the interval 0..63 from x and y in the interval 0..7
    protected final int getSquareNoFromPos(int x, int y) {
        assert(x >= 0 && x <= 7 && y >= 0 && y <= 7);        
        return y * 8 + x;
    }

    // Set bit with index bitNo to high - i.e. 1
    private long setBitHigh(int bitNo) {
        return 1L << bitNo;
    }

    public void insertPiece(Piece p) {       
       bb[getIndexFromColor(p.color)][p.type] = bb[getIndexFromColor(p.color)][p.type] |
                                         setBitHigh(getSquareNoFromPos(p.xPos, p.yPos));
    }
    
    public boolean hasPiece(int x, int y, int color, int type) {
        assert(color == -1 || color == 1);
        assert(type >= 0 && type < 6);
        return (bb[getIndexFromColor(color)][type] &
                setBitHigh(getSquareNoFromPos(x, y)))
                != 0;
    }
    
    //Todo: Is this code correct?
    public Piece getPiece(int x, int y) {
      for (int type = 0; type < NUM_PIECE_TYPES; type++) {
        if ((bb[0][type] & setBitHigh(getSquareNoFromPos(x, y))) != 0) {
         return new Piece(x, y, Piece.BLACK, type);
        }
        if ((bb[1][type] & setBitHigh(getSquareNoFromPos(x, y))) != 0) {
         return new Piece(x, y, Piece.WHITE, type);
        }
      }
      return null;
    }
    
    
    public Piece removePiece(int x, int y) {        
        final int UNDF  = 254; 
        int       color = UNDF;
        int       type  = UNDF;
        
        assert(x>=0 && x <= 7 && y >= 0 && y <= 7);
        
        for (int t = 0; t < NUM_PIECE_TYPES; t++) {
            if ((bb[0][t] & setBitHigh(getSquareNoFromPos(x, y))) != 0) {
                color = Piece.BLACK;
                type  = t;
                break;
            }
            if ((bb[1][t] & setBitHigh(getSquareNoFromPos(x, y))) != 0) {
                color = Piece.WHITE;
                type  = t;
                break;               
            }            
        }           
        
        assert(color != UNDF && type != UNDF);
        
        bb[getIndexFromColor(color)][type] = bb[getIndexFromColor(color)][type] ^  // Bitwise XOR
                                          setBitHigh(getSquareNoFromPos(x, y));
        
        return new Piece(x, y, color, type);
    }
    
    private String bitboard2String(long b) {
        String r = "";
        int x, y;
        
        for (y = 7; y >= 0; y--) {
         for (x = 0; x <= 7; x++) {
             if (((1L << getSquareNoFromPos(x, y)) & b) == 0) {
                 r = r + '.';
             } else r = r + '+';
         }
         r = r + "\n";
        }
        return r;
    }
    
    @Override
    public String toString() {
        String s = "";
        
        for (int t = 0; t < NUM_PIECE_TYPES; t++) {
             s = s + "Black " + Piece.getPieceLetter(Piece.BLACK, t) 
                     + "\n"
                     + bitboard2String(bb[0][t]);

            s = s + "White " + Piece.getPieceLetter(Piece.WHITE, t) 
                     + "\n"
                     + bitboard2String(bb[1][t]);
        }
        
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Bitboard)) return false;       
        final Bitboard b2 = (Bitboard) obj;
        
        for (int t = 0; t < NUM_PIECE_TYPES; t++) {
            if (this.bb[0][t] != b2.bb[0][t] ||
                this.bb[1][t] != b2.bb[1][t]) return false;
        }        
        return true;
    }

    @Override
    public Bitboard clone() {
        int t;

        final Bitboard theClone = new Bitboard();

        for (t = 0; t < NUM_PIECE_TYPES; t++) {
            theClone.bb[0][t] = this.bb[0][t];
            theClone.bb[1][t] = this.bb[1][t];
        }

        return theClone;
      }
}
