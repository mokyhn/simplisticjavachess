/**
 * @author Morten Kühnrich
 * Used to represent a position using three different representations:
 *     # a piece list
 *     # a 2 dimensional piece array
 *     # a bitboard
 * All of these are maintained so that the represent the same position at all
 * times. We can use different representations in different parts of the code
 * for our algorithms.
 * */
package com.simplisticchess.board;

import com.simplisticchess.piece.Piece;
import com.simplisticchess.move.Move;

public final class Position extends Bitboard implements Cloneable  {

    private final Piece[]     piecePosition;
    private final Piece[][]   xyPosition;    
    private int         numberOfPieces;

    public Position() {
        super();
        int x, y;
        numberOfPieces = 0;
        piecePosition       = new Piece[32];
        xyPosition     = new Piece[8][8];
        
        for (x = 0; x < 8; x++)
            for (y = 0; y < 8; y++) xyPosition[x][y] = null;

    }

    public Piece getPiece(final int i) {
        final Piece p = piecePosition[i];
        assert p != null;

        final Piece ptmp = xyPosition[p.xPos][p.yPos];
        assert ptmp != null : "Unexpected null value with piece " + i + " of type "+ p.toString() + " at (" + p.xPos + "," + p.yPos + ")" + "\n"+
                              this.toString();
        assert ptmp.xPos == p.xPos && ptmp.yPos == p.yPos;
        return p;
    }

    
    public Piece getPiece(final int x, final int y) {
        final Piece p = xyPosition[x][y];
        // For testing: areRepresentationsIsomorphic();
        if (p != null) assert p.xPos == x && p.yPos == y;
        return p;
    }

    public void insertPiece(final Piece p) {
        assert numberOfPieces < 32 : "PieceList:insertPiece: Inserting to many pieces";
        assert p != null;
        piecePosition[numberOfPieces] = p;
        numberOfPieces++;

        xyPosition[p.xPos][p.yPos] = p;
        
        super.insertPiece(p);
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
        
        super.removePiece(x, y);
        
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
        
        super.removePiece(xFrom, yFrom);
        super.insertPiece(p);
    }

    
    @Override
    public int getNumberOfPieces() {
        return numberOfPieces;
    }
    
    private boolean rookAttack(int x1, int y1, int x2, int y2) {
       Boolean allFree = true;
        int lowX,  // From x pos
          highX, // To x pos
          lowY,  // From y pos 
          highY, // To y pos
          ix,    // Iterate x
          iy;    // Iterate y
         
        if      (x1 == x2) {
            allFree = true;
            if (y1 < y2) { 
                lowY = y1; 
                highY = y2;
            } else {
                lowY = y2;
                highY = y1;
            }
            for (iy = lowY + 1; iy < highY; iy++) {
                if (!freeSquare(x1, iy)) {
                 allFree = false;
                 break;
                }
            }
            if (allFree) return true;
        } 
        if (y1 == y2) {
            allFree = true;
            if (x1 < x2) { 
                lowX  = x1; 
                highX = x2;
            } else {
                lowX  = x2;
                highX = x1;
            }
            for (ix = lowX + 1; ix < highX; ix++) {
                if (!freeSquare(ix, y1)) {
                 allFree = false;
                 break;
                }
            }
            if (allFree) return true;
        }
        return false;
    }
    
    private boolean bishopAttack(int x1, int y1, int x2, int y2) {
      final int r; // Radius
      int ir;      // Iterator over radii
      int dx;
      int dy;
      boolean allFree = true;
      dx = x2-x1;
      dy = y2-y1;
      
      // First condition that allows one to be threatened by a bishop
      if (Math.abs(dx) == Math.abs(dy)) {
        r = Math.abs(dx);
        dx = dx / r;
        dy = dy / r;
        for (ir = 1; ir < r; ir++) {
          if (!freeSquare(ir*dx + x1, ir*dy + y1)) {
           allFree = false;
           break;
         }
        }
        if (allFree) return true;
      }
        
     return false;
    }
    
    public boolean attacks(int x, int y, int inMove) {
      Piece p;
      int lowX,  // From x pos
          highX, // To x pos
          lowY,  // From y pos 
          highY, // To y pos
          ix,    // Iterate x
          iy;    // Iterate y
      boolean allFree;
        
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
                        if (rookAttack(p.xPos, p.yPos, x, y)) return true;
                        break;
                    case Piece.BISHOP:
                        if (bishopAttack(p.xPos, p.yPos, x, y)) return true;
                        break;
                    case Piece.KNIGHT:
                        if (((x == p.xPos - 2) && (y == p.yPos + 1)) ||
                            ((x == p.xPos - 2) && (y == p.yPos - 1)) ||
                            ((x == p.xPos - 1) && (y == p.yPos - 2)) ||
                            ((x == p.xPos + 1) && (y == p.yPos + 2)) ||
                            ((x == p.xPos - 1) && (y == p.yPos + 2)) ||
                            ((x == p.xPos + 1) && (y == p.yPos - 2)) ||
                            ((x == p.xPos + 2) && (y == p.yPos + 1)) ||
                            ((x == p.xPos + 2) && (y == p.yPos - 1))) return true;
                        break;
                    case Piece.QUEEN:
                         if (rookAttack(p.xPos, p.yPos, x, y) || 
                             bishopAttack(p.xPos, p.yPos, x, y)) return true;
                        break;
                    case Piece.KING:
                        if ((x == p.xPos || x == p.xPos - 1 || x == p.xPos + 1) &&
                            (y == p.yPos || y == p.yPos - 1 || y == p.yPos + 1)) return true;
                        break;
                    default: 
                }
            }
        }

        return false; 
    }

    // FOR TESTING: areRepresentationsIsomorphic();
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
                  p =  getPiece(x, y);
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

        for (int t = 0; t < NUM_PIECE_TYPES; t++) {
            theClone.bb[0][t] = this.bb[0][t];
            theClone.bb[1][t] = this.bb[1][t];
        }
        
        assert theClone.getNumberOfPieces() == this.getNumberOfPieces();
        return theClone;
    }
}
