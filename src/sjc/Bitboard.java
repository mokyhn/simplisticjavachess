package sjc;
/*
 * Bitboard structures
 */
public final class Bitboard implements Cloneable {
    public long bb[][];

    /**
     * Total number of colors: black and white, that is 2
     */
    private static final int NUM_COLORS      = 2; 
    
    /**
     * Number of different piece types: pawn, knight, bishop, rook, queen, king,
     * that is 6
     */
    private static final int NUM_PIECE_TYPES = 6;  
    
    
    /**
     * Creates an empty bitboard 
     */
    public Bitboard() {
       bb = new long[NUM_COLORS][NUM_PIECE_TYPES];
    
       for (int t = 0; t < NUM_PIECE_TYPES; t++) {
            bb[0][t] = 0;
            bb[1][t] = 0;
        }
    };

    
    private int colorIndex(int color) {
     if (color == Piece.BLACK) { return 0; }
     return 1;
    }

    private int indexToColor(int index) {
     return index == 0 ? Piece.BLACK : Piece.WHITE;
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
            c = colorIndex(p.color);
            t = p.type;
            if (t != Piece.EMPTY) {
                bb[c][t] =  bb[c][t] | setBitHigh(squareNoFromPos(p.xPos, p.yPos));
            }
        }
    }

    // Returns number in the interval 0..63 from x and y in the interval 0..7
    private static int squareNoFromPos(int x, int y) {
        return y * 8 + x;
    }

    // Set bit with index bitNo to high - i.e. 1
    private static long setBitHigh(int bitNo) {
        return 1L << bitNo;
    }

    public void insertPiece(Piece p) {       
       bb[colorIndex(p.color)][p.type] = bb[colorIndex(p.color)][p.type] | 
                                         setBitHigh(squareNoFromPos(p.xPos, p.yPos));    
    }
    
    public Piece removePiece(int x, int y) {        
        final int UNDF  = 254; 
        int       color = UNDF;
        int       type  = UNDF;
        
        for (int t = 0; t < NUM_PIECE_TYPES; t++) {
            if ((bb[0][t] & setBitHigh(squareNoFromPos(x, y))) != 0) {
                color = Piece.BLACK;
                type  = t;
                break;
            }
            if ((bb[1][t] & setBitHigh(squareNoFromPos(x, y))) != 0) {
                color = Piece.WHITE;
                type  = t;
                break;               
            }            
        }           
        
        assert(color != UNDF && type != UNDF);
        
        bb[colorIndex(color)][type] = bb[colorIndex(color)][type] ^  // Bitwise XOR
                                          setBitHigh(squareNoFromPos(x, y));    
        
        return new Piece(x, y, color, type);
    }
    
    private String bitboard2String(long b) {
        String r = "";
        int x, y;
        
        for (y = 7; y >= 0; y--) {
         for (x = 0; x <= 7; x++) {
             if (((1L << squareNoFromPos(x, y)) & b) == 0) {
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
        final Bitboard b2 = (Bitboard) obj;
        
        if (b2 == null) return false;
        
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
