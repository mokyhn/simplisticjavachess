public class Position {

    private Piece[]     position;
    private Piece[][]   xyPosition;
    public  Bitboard    bitboard;
    private int         numberOfPieces;

    public Position() {
        int x, y;
        numberOfPieces = 0;
        position       = new Piece[32];
        xyPosition     = new Piece[8][8];
        bitboard       = new Bitboard();
        
        for (x = 0; x < 8; x++)
            for (y = 0; y < 8; y++) xyPosition[x][y] = null;

    }

    public Piece getPiece(int i) {
        Piece p = position[i];
        assert p != null;

        Piece ptmp = xyPosition[p.xPos][p.yPos];
        assert ptmp != null : i + ", " + p.toString();
        assert ptmp.xPos == p.xPos && ptmp.yPos == p.yPos;
        return p;
    }

    public Piece getPieceXY(int x, int y) {
        Piece p = xyPosition[x][y];

        return p;
    }

    public void insertPiece(Piece p) {
        assert numberOfPieces < 32 : "PieceList:insertPiece: Inserting to many pieces";
        position[numberOfPieces] = p;
        numberOfPieces++;

        xyPosition[p.xPos][p.yPos] = p;
        
        bitboard.insertPiece(p);
    }



    // Remove a piece from location x, y and return the piece
    public Piece removePiece(int x, int y) {
        int i;
        Boolean flag = true;
        Piece   p     = xyPosition[x][y];

        assert (p != null) : "removePiece at " + Move.posToString(x, y) + "\n" + this.toString();

        for (i = 0; i < numberOfPieces && flag; i++) {
            if (position[i].xPos == x &&
                position[i].yPos == y) {
                position[i] = position[numberOfPieces-1];
                numberOfPieces--;
                flag = false;
            }
        }

        xyPosition[x][y] = null;
        
        bitboard.removePiece(x, y);
        
        return p;
    } 

    public void movePiece(int xFrom, int yFrom, int xTo, int yTo) {
        Piece p = xyPosition[xFrom][yFrom];

        assert (xFrom != xTo || yFrom != yTo) : "Cannot move from c to c";
        assert (p != null) : "Move " + Move.posToString(xFrom, yFrom) + " to " + Move.posToString(xTo, yTo);

        p.xPos = xTo;
        p.yPos = yTo;

        xyPosition[xTo][yTo]     = p;
        assert p.xPos == xyPosition[p.xPos][p.yPos].xPos && p.yPos == xyPosition[p.xPos][p.yPos].yPos;
        xyPosition[xFrom][yFrom] = null;
        
        bitboard.removePiece(xFrom, yFrom);
        bitboard.insertPiece(p);
    }

    public int numberOfPieces() {
        return numberOfPieces;
    }

     public boolean attacks(int x, int y, int inMove) {
        Piece p;

        for (int i = 0; i < numberOfPieces; i++) {
            p = position[i];

            // Chose one of opposite color
            if (p.color == -inMove && !(p.xPos == x && p.yPos == y)) {
                switch (p.type) {
                    case Piece.PAWN:
                        if ((x == p.xPos + 1 && y == p.yPos + p.color)
                         || (x == p.xPos - 1 && y == p.yPos + p.color)) {
                            return true;
                        }
                    case Piece.ROOK:
                        break;
                    case Piece.BISHOP:
                        break;
                    case Piece.KNIGHT:
                        break;
                    case Piece.QUEEN:
                        break;
                    case Piece.KING:
                        if ((x == p.xPos || x == p.xPos - 1 || x == p.xPos + 1) &&
                            (y == p.yPos || y == p.yPos - 1 || y == p.yPos + 1)) return true;
                        
                    default: // Lala
                }
            }
        }

        return false;
    }

      public boolean freeSquare(int x, int y)  {
          return xyPosition[x][y] == null;
    }

      
    @Override
      public String toString() {
        int x, y;
        Piece p;
        String s = "\n _______________\n";

        for (y = 7; y >= 0; y--) {
            for (x = 0; x < 8; x++) {
                 s = s + " "; 
                  p =  getPieceXY(x, y);
                  if (p != null) { s = s + p.toString(); }
                  else s = s + ".";
            }
          s = s + ("     " + (y+1)) + "\n";
        } // end last for-loop
        s = s + " _______________\n";
        s = s + " a b c d e f g h\n";
      return s;
    }

    @Override
    public Position clone() {
        int i, x, y;
        Piece p;

        Position theClone   = new Position();

        theClone.numberOfPieces = numberOfPieces;

        for (x = 0; x < 8; x++)
          for (y = 0; y < 8; y++) theClone.xyPosition[x][y] = null;

        for (i = 0; i < numberOfPieces; i++) {
            p = position[i].clone();
            theClone.position[i] = p;
            theClone.xyPosition[p.xPos][p.yPos] = p;
        }

        theClone.bitboard = bitboard.clone();
        
        assert theClone.numberOfPieces() == this.numberOfPieces();
        return theClone;
    }
}