/**
 *
 * @author Morten Kühnrich
 * @year 2005
 * This class encapsulates the information needed to
 * represent the pieces of a chess game.
 */

package sjc;

public final class Piece implements Cloneable {
    public int xPos,
               yPos,
               color,
               type;

    public static final int   BLACK    = -1,
                              WHITE    =  1,
                              NO_COLOR =  0;

    public static final int   PAWN   = 0,
                              BISHOP = 1,
                              KNIGHT = 2,
                              ROOK   = 3,
                              QUEEN  = 4,
                              KING   = 5,
                              EMPTY  = 6;

    public Piece() { }

    public Piece(int x, int y, int c, int t) {
        assert x >= 0 && x <= 7 && y >= 0 && y <= 7 : "Piece range error in x or y ";
        xPos  = x;
        yPos  = y;
        color = c;
        type  = t;
    }

    public boolean equals(Piece p) {
        return p.color == this.color &&
               p.type  == this.type  &&
               p.xPos  == this.xPos  &&
               p.yPos  == this.yPos;
    }
    
   
    public static String getPieceLetter(int color, int pieceType) {
      String r = "";

        assert(color == WHITE || color == BLACK);
        assert(pieceType == PAWN   || pieceType == ROOK || pieceType == BISHOP ||
               pieceType == KNIGHT || pieceType == KING || pieceType == QUEEN  ||
               pieceType == EMPTY);
      
        if (color == BLACK) {

        switch (pieceType) {
            case PAWN:   r = "p"; break;
            case ROOK:   r = "r"; break;
            case BISHOP: r = "b"; break;
            case KNIGHT: r = "n"; break;
            case KING:   r = "k"; break;
            case QUEEN:  r = "q"; break;
            case EMPTY:  r = "#"; break;
        }
     }

        if (color == WHITE) {
            switch (pieceType) {
            case PAWN:   r = "P"; break;
            case ROOK:   r = "R"; break;
            case BISHOP: r = "B"; break;
            case KNIGHT: r = "N"; break;
            case KING:   r = "K"; break;
            case QUEEN:  r = "Q"; break;
            case EMPTY:  r = "#"; break;
            }
        }
      return r;    
    }
    

    public Piece(int x, int y, char pieceLetter)  {
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

            default: System.out.println("Unexpected error in Piece constructor");
                     System.exit(1);

            }
     }

    @Override
    public  String toString() {
        return getPieceLetter(color, type);
    }

    
    @Override
    public Piece clone() {
        final Piece theClone = new Piece();

        // Initialize theClone.
        theClone.xPos  = this.xPos;
        theClone.yPos  = this.yPos;
        theClone.color = this.color;
        theClone.type  = this.type;

        return theClone;
    }

}

