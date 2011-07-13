/*
 * Bitboard structures
 */
public class Bitboard {
    public  long bb[][];

    /**
     * Total number of colors: black and white, that is 2
     */
    private static final int NUM_COLORS      = 2; 
    
    /**
     * Number of different piece types: pawn, knight, bishop, rook, queen, king,
     * that is 6
     */
    private static final int NUM_PIECE_TYPES = 6; // 
    
    
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
            if (p.color == Piece.BLACK) c = 0; else c = 1;
            t = p.type;
            if (t != Piece.EMPTY) bb[c][t] =  bb[c][t] | setBitHigh(SquareNoFromPos(p.xPos, p.yPos));
        }
    }

    // Returns number in the interval 0..63 from x and y in the interval 0..7
    private static int SquareNoFromPos(int x, int y) {
        return y * 8 + x;
    }

    // Set bit with index bitNo to high - i.e. 1
    private static long setBitHigh(int bitNo) {
        return 1L << bitNo;
    }

    /* TODO: Implement a function that returns all 6 bitboard for the 6
       individual piece types... */
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
        return r;
    }

    public boolean equals(Bitboard b2) {
        for (int t = 0; t < NUM_PIECE_TYPES; t++) {
            if (this.bb[0][t] != b2.bb[0][t] ||
                this.bb[1][t] != b2.bb[1][t]) return false;
        }
        
        return true;
    }

    @Override
      public Bitboard clone() {
        int t;

        Bitboard theClone = new Bitboard();

        for (t = 0; t < NUM_PIECE_TYPES; t++) {
            theClone.bb[0][t] = this.bb[0][t];
            theClone.bb[1][t] = this.bb[1][t];
        }

        return theClone;
      }

}
