class Piece implements Cloneable {
    public int xPos,
               yPos,
               color,
               type;

    public static final int   BLACK    = -1,
                              WHITE    =  1,
                              NO_COLOR =  0;

    public static final int   EMPTY  = 0,
                              PAWN   = 1,
                              BISHOP = 2,
                              KNIGHT = 3,
                              ROOK   = 4,
                              QUEEN  = 5,
                              KING   = 6;

    public Piece clone() {
        try { super.clone(); }
        catch (CloneNotSupportedException e) {}

        Piece theClone = (Piece) new Piece();

        // Initialize theClone.
        theClone.xPos  = xPos;
        theClone.yPos  = yPos;
        theClone.color = color;
        theClone.type  = type;

        return theClone;
    }

    public Piece() { }

    public Piece(int x, int y, int c, int t) {
        assert x >= 0 && x <= 7 && y >= 0 && y <= 7 : "Piece range error in x or y ";
        xPos  = x;
        yPos  = y;
        color = c;
        type  = t;
    }

    public Piece(int x, int y, char pieceLetter) throws NoPieceException {
        assert x >= 0 && x <= 7 && y >= 0 && y <= 7 : "Piece range error in x or y ";

        xPos = x;
        yPos = y;

        switch (pieceLetter) {
            case 'p': type  = PAWN;
                      color = BLACK;
                      break;
            case 'r': type  = ROOK;
                      color = BLACK;
                      break;
            case 'n': type  = KNIGHT;
                      color = BLACK;
                      break;
            case 'b': type  = BISHOP;
                      color = BLACK;
                      break;
            case 'k': type  = KING;
                      color = BLACK;
                      break;
            case 'q': type  = QUEEN;
                      color = BLACK;
                      break;
            case 'P': type  = PAWN;
                      color = WHITE;
                      break;
            case 'R': type  = ROOK;
                      color = WHITE;
                      break;
            case 'N': type  = KNIGHT;
                      color = WHITE;
                      break;
            case 'B': type  = BISHOP;
                      color = WHITE;
                      break;
            case 'K': type  = KING;
                      color = WHITE;
                      break;
            case 'Q': type  = QUEEN;
                      color = WHITE;
                      break;

            default: throw new NoPieceException();
            }
     }
}
