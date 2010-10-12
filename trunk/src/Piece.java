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

    public  String toString() {
      String r = "";

        if (color == BLACK) {

        switch (type) {
            case PAWN:   r = "P"; break;
            case ROOK:   r = "R"; break;
            case BISHOP: r = "B"; break;
            case KNIGHT: r = "N"; break;
            case KING:   r = "K"; break;
            case QUEEN:  r = "Q"; break;
        }
     }

        if (color == WHITE) {
            switch (type) {
            case PAWN:   r = "p"; break;
            case ROOK:   r = "r"; break;
            case BISHOP: r = "b"; break;
            case KNIGHT: r = "n"; break;
            case KING:   r = "k"; break;
            case QUEEN:  r = "q"; break;
            }
        }
      return r;
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


    public void print() {
        System.out.print(toString());
    }
}
