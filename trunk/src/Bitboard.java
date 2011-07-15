/*
 * Bitboard structures
 */
public class Bitboard implements Cloneable {
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

    
    private int colorIndex(int color) {
     if (color == Piece.BLACK) return 0;
     return 1;
    }

    private int indexToColor(int index) {
     if (index == 0) return Piece.BLACK;
     if (index == 1) return Piece.WHITE;

     return 42; // Unreachable
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

    public void insertPiece(Piece p) {
       long b;
        
       b = bb[colorIndex(p.color)][p.type];       
       b = b | setBitHigh(SquareNoFromPos(p.xPos, p.yPos));       
    }
    
    public Piece removePiece(int x, int y) {
        Piece p = null;
        
        for (int t = 0; t < NUM_PIECE_TYPES; t++) {
            if ((bb[0][t] & setBitHigh(SquareNoFromPos(x, y))) == 0) p = new Piece(x, y, Piece.BLACK, t);
            if ((bb[1][t] & setBitHigh(SquareNoFromPos(x, y))) == 0) p = new Piece(x, y, Piece.WHITE, t);            
        }           
        return p;
    }
    
    //movePiece(int xFrom, int yFrom, int xTo, int yTo)
    
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

        Bitboard theClone = new Bitboard();

        for (t = 0; t < NUM_PIECE_TYPES; t++) {
            theClone.bb[0][t] = this.bb[0][t];
            theClone.bb[1][t] = this.bb[1][t];
        }

        return theClone;
      }

}
