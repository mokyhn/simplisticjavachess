public class PieceList {

    private Piece[] position;
    private Piece[][]   xyPosition;
    private int numberOfPieces;

    public PieceList() {
        int x, y;
        numberOfPieces = 0;
        position       = new Piece[32];
        xyPosition     = new Piece[8][8];
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
        String res = "";
        for (int i = 0; i < numberOfPieces; i++)
            res = res + " " + "(" + Chessio.numToChar(position[i].xPos) + ", " + (position[i].yPos + 1) + ", " + position[i].toString() + ")";
        return res;
    }


    @Override
    public PieceList clone() {
        int i, x, y;
        Piece p;

        PieceList theClone   = new PieceList();

        theClone.numberOfPieces = numberOfPieces;

        for (x = 0; x < 8; x++)
          for (y = 0; y < 8; y++) theClone.xyPosition[x][y] = null;

        for (i = 0; i < numberOfPieces; i++) {
            p = position[i].clone();
            theClone.position[i] = p;
            theClone.xyPosition[p.xPos][p.yPos] = p;
        }

        assert theClone.numberOfPieces() == this.numberOfPieces();
        return theClone;
    }
}
