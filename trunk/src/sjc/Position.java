package sjc;

public final class Position implements Cloneable {

    private Piece[]     piecePosition;
    private Piece[][]   xyPosition;
    public  Bitboard    bitboard;
    private int         numberOfPieces;

    public Position() {
        int x, y;
        numberOfPieces = 0;
        piecePosition       = new Piece[32];
        xyPosition     = new Piece[8][8];
        bitboard       = new Bitboard();
        
        for (x = 0; x < 8; x++)
            for (y = 0; y < 8; y++) xyPosition[x][y] = null;

    }

    public Piece getPiece(int i) {
        final Piece p = piecePosition[i];
        assert p != null;

        final Piece ptmp = xyPosition[p.xPos][p.yPos];
        assert ptmp != null : "Unexpected null value with piece " + i + " of type "+ p.toString() + " at (" + p.xPos + "," + p.yPos + ")" + "\n"+
                              this.toString();
        assert ptmp.xPos == p.xPos && ptmp.yPos == p.yPos;
        return p;
    }

    public Piece getPieceXY(final int x, final int y) {
        final Piece p = xyPosition[x][y];
        areRepresentationsIsomorphic();
        if (p != null) assert p.xPos == x && p.yPos == y;
        return p;
    }

    public void insertPiece(final Piece p) {
        assert numberOfPieces < 32 : "PieceList:insertPiece: Inserting to many pieces";
        assert p != null;
        piecePosition[numberOfPieces] = p;
        numberOfPieces++;

        xyPosition[p.xPos][p.yPos] = p;
        
        bitboard.insertPiece(p);
    }



    // Remove a piece from location x, y and return the piece
    public Piece removePiece(final int x, final int y) {
        int i;
        Boolean flag = true;
        Piece   p     = xyPosition[x][y];

        assert (p != null) : "removePiece at " + Move.posToString(x, y) + "\n" + this.toString();

        assert (p.xPos == x) && (p.yPos == y);
        
        for (i = 0; i < numberOfPieces && flag; i++) {
            if (piecePosition[i].xPos == x &&
                piecePosition[i].yPos == y) {
                piecePosition[i] = piecePosition[numberOfPieces-1];
                numberOfPieces--;
                flag = false;
            }
        }

        xyPosition[x][y] = null;
        
        bitboard.removePiece(x, y);
        
        return p;
    } 

    public void movePiece(int xFrom, int yFrom, int xTo, int yTo) {
        final Piece p = xyPosition[xFrom][yFrom];

        assert (xFrom != xTo || yFrom != yTo) : "Cannot move from c to c";
        assert (p != null) : this.toString() + "\n" + "Tried move " + Move.posToString(xFrom, yFrom) + " to " + Move.posToString(xTo, yTo);

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

    //TODO: It is unclear why we should implement an attacks function that
    //mimics the movegenerator.
     public boolean attacks(int x, int y, int inMove) {
        Piece p;

        
        for (int i = 0; i < numberOfPieces; i++) {
            p = getPiece(i);

            // Chose one of opposite color
            if (p.color == -inMove && !(p.xPos == x && p.yPos == y)) {
                switch (p.type) {
                    case Piece.PAWN:
                        if  ((y == p.yPos + p.color) &&
                               ((x == p.xPos + 1) ||
                                (x == p.xPos - 1))) return true;
                        break;
                    case Piece.ROOK:
                        break;
                    case Piece.BISHOP:
                        break;
                    case Piece.KNIGHT:
                        if (((x == p.xPos - 2) && (y == p.yPos + 1)) ||
                            ((x == p.xPos - 2) && (y == p.yPos - 1)) ||
                            ((x == p.xPos - 1) && (y == p.yPos - 2)) ||
                            ((x == p.xPos + 1) && (y == p.yPos + 2)) ||
                            ((x == p.xPos - 1) && (y == p.yPos - 2)) ||
                            ((x == p.xPos + 1) && (y == p.yPos - 2)) ||
                            ((x == p.xPos + 2) && (y == p.yPos + 1)) ||
                            ((x == p.xPos + 2) && (y == p.yPos - 1))) return true;
                        break;
                    case Piece.QUEEN:
                        break;
                    case Piece.KING:
                        if ((x == p.xPos || x == p.xPos - 1 || x == p.xPos + 1) &&
                            (y == p.yPos || y == p.yPos - 1 || y == p.yPos + 1)) return true;
                        break;
                    default: // Lala
                }
            }
        }

        return false; 
    }

     public void areRepresentationsIsomorphic() {
      final int nr1 = numberOfPieces;
      int nr2 = 0;
      int x, y;
      Piece p1, p2;
      for (x=0; x < 8; x++)
          for(y=0; y < 8; y++) {
           if (xyPosition[x][y] != null) nr2++;
          }
      assert nr1 == nr2 : "Listboard = " + nr1 + " and x,y-arrayboard has " + nr2;
      
      // Are the pieces the same?
      for (int i = 0; i < numberOfPieces; i++) {
          p1 = piecePosition[i];
          p2 = xyPosition[p1.xPos][p1.yPos];
          assert p1.equals(p2) : "Had " + p1.toString() + " in list board and " + p2.toString() + " in xyBoard...";
          }
     }
     
      public boolean freeSquare(int x, int y)  {
          areRepresentationsIsomorphic();
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
                  if (p == null) s = s + ".";
                  else s = s + p.toString(); 
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
            p = piecePosition[i].clone();
            theClone.piecePosition[i] = p;
            theClone.xyPosition[p.xPos][p.yPos] = p;
        }

        theClone.bitboard = bitboard.clone();
        
        assert theClone.numberOfPieces() == this.numberOfPieces();
        return theClone;
    }
}
