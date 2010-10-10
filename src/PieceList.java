
import java.util.ArrayList;
import java.util.List;


public class PieceList {

    private List<Piece> position;
    private Piece[][]   xyPosition;

    public PieceList() {
        int x, y;

        position       = new ArrayList<Piece>();
        xyPosition     = new Piece[8][8];
        for (x = 0; x < 8; x++)
            for (y = 0; y < 8; y++) xyPosition[x][y] = null;

    }

    public Piece getPiece(int i) {
        return position.get(i);
    }

    public Piece getPiece(int x, int y) throws NoPieceException {
        Piece p = xyPosition[x][y];
        if (p == null) throw new NoPieceException();
        return p;
    }

    public void insertPiece(Piece p) {
        assert position.size() < 32 : "PieceList:insertPiece: Inserting to many pieces";
        position.add(p);

        xyPosition[p.xPos][p.yPos] = p;
    }

    // Remove a piece from location x, y and return the piece
    public Piece removePiece(int x, int y) {
        Piece   p     = xyPosition[x][y];

        assert (p != null) : "removePiece at " + Move.posToString(x, y);

        position.remove(p);
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
        xyPosition[xFrom][yFrom] = null;
    }

    public int numberOfPieces() {
        return position.size();
    }

     public boolean attacks(int x, int y, int inMove) {
        Piece p;

        for (int i = 0; i < position.size(); i++) {
            p = position.get(i);

            // Chose one of opposite color
            if (p.color == -inMove) {
                switch (p.type) {
                    case Piece.PAWN:
                        if ((p.xPos == x - 1 && p.yPos == p.color + y)
                                || (p.xPos == x + 1 && p.yPos == p.color + y)) {
                            return true;
                        }
                        break;
                    case Piece.ROOK:
                        break;
                    case Piece.BISHOP:
                        break;
                    case Piece.KNIGHT:
                        break;
                    case Piece.QUEEN:
                        break;
                    case Piece.KING:
                        break;
                    default: // Lala
                }
            }
        }

        return false;
    }

      public boolean freeSquare(int x, int y)  {
          return xyPosition[x][y] == null;
    }
      
    public Object clone() {
        int i, x, y;
        Piece p;

        try {
            super.clone();
        } catch (CloneNotSupportedException e) {}

        PieceList theClone   = new PieceList();
        
        for (i = 0; i < position.size(); i++) (theClone.position).add((position.get(i)).clone());

        assert theClone.numberOfPieces() == this.numberOfPieces();


        for (x = 0; x < 8; x++)
            for (y = 0; y < 8; y++) {
                p = xyPosition[x][y];
                if (p == null) theClone.xyPosition[x][y] = null;
                else           theClone.xyPosition[x][y] = (Piece) p.clone();
            }


        return theClone;
    }
}
