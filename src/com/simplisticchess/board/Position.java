/**
 * @author Morten Kühnrich
 *
 */

package com.simplisticchess.board;

import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;
import java.util.ArrayList;

public class Position
{
    private static final int DEFAULT_NUMBER_OF_PIECES_CAPACITY = 32;

    private ArrayList<Piece> pieces;
    private Piece[][] xyPosition;
    
    private BitBoard bitBoard;
            
    public Position()
    {
        init(DEFAULT_NUMBER_OF_PIECES_CAPACITY);   
    }

    public Position(Position position)
    {
        init(position.pieces.size());

        for (Piece p : position.pieces)
        {
            Piece newPiece = new Piece(p);
            this.pieces.add(newPiece);
            this.xyPosition[newPiece.getxPos()][newPiece.getyPos()] = newPiece;
        }
        
        for (PieceType t : PieceType.values())
        {
            this.bitBoard.bb[0][t.getType()] = position.bitBoard.bb[0][t.getType()];
            this.bitBoard.bb[1][t.getType()] = position.bitBoard.bb[1][t.getType()];
        }
    }

    private void init(int n)
    {
        pieces = new ArrayList<Piece>(n);
        xyPosition = new Piece[8][8];
        bitBoard = new BitBoard();
    
    }  
    
    public Piece getPiece(Location location)
    {
        return xyPosition[location.getX()][location.getY()];
    }
    
    public Piece getPiece(int i)
    {
        return pieces.get(i);
    }


    public void insertPiece(Piece p)
    {
        pieces.add(p);
        xyPosition[p.getxPos()][p.getyPos()] = p;
        bitBoard.insertPiece(p);
    }

    // Remove a piece from location and return the piece
    public Piece removePiece(Location location)
    {
        Piece p = xyPosition[location.getX()][location.getY()];
        pieces.remove(p);
        xyPosition[location.getX()][location.getY()] = null;
        bitBoard.removePiece(location);
        return p;
    }
   
    public void movePiece(Location from, Location to)
    {
        Piece p = xyPosition[from.getX()][from.getY()];
          
        p.setLocation(to);
        
        xyPosition[to.getX()][to.getY()] = p;
        xyPosition[from.getX()][from.getY()] = null;

        bitBoard.removePiece(from);
        bitBoard.insertPiece(p);
    }

    public int getNumberOfPieces()
    {
        return pieces.size();
    }

    private boolean rookAttack(Location l1, Location l2)
    {
        Boolean allFree;
        int lowX, // From x pos
                highX, // To x pos
                lowY, // From y pos 
                highY, // To y pos
                ix, // Iterate x
                iy;    // Iterate y

        if (l1.fileEquals(l2))
        {
            allFree = true;
            if (l1.getY() < l2.getY())
            {
                lowY = l1.getY();
                highY = l2.getY();
            } else
            {
                lowY = l2.getY();
                highY = l1.getY();
            }
            for (iy = lowY + 1; iy < highY; iy++)
            {
                if (!freeSquare(l1.getX(), iy))
                {
                    allFree = false;
                    break;
                }
            }
            if (allFree)
            {
                return true;
            }
        }
        if (l1.rankEquals(l2))
        {
            allFree = true;
            if (l1.getX() < l2.getX())
            {
                lowX = l1.getX();
                highX = l2.getX();
            } else
            {
                lowX = l2.getX();
                highX = l1.getX();
            }
            for (ix = lowX + 1; ix < highX; ix++)
            {
                if (!freeSquare(ix, l1.getY()))
                {
                    allFree = false;
                    break;
                }
            }
            if (allFree)
            {
                return true;
            }
        }
        return false;
    }

    private boolean bishopAttack(Location l1, Location l2)
    {
        final int r; // Radius
        int ir;      // Iterator over radii
        int dx;
        int dy;
        boolean allFree = true;
        dx = l2.getX() - l1.getX();
        dy = l2.getY() - l1.getY();

        // First condition that allows one to be threatened by a bishop
        if (Math.abs(dx) == Math.abs(dy))
        {
            r = Math.abs(dx);
            dx = dx / r;
            dy = dy / r;
            for (ir = 1; ir < r; ir++)
            {
                if (!freeSquare(ir * dx + l1.getX(), ir * dy + l1.getY()))
                {
                    allFree = false;
                    break;
                }
            }
            if (allFree)
            {
                return true;
            }
        }

        return false;
    }

    public boolean attacks(Location location, Color inMove)
    {
        for (Piece p : pieces)
        {
            // Chose one of opposite color
            if (p.getColor() == inMove.opponent() && !(p.getxPos() == location.getX() && p.getyPos() == location.getY()))
            {
                switch (p.getPieceType())
                {
                    case PAWN:
                        if ((location.getY() == p.getyPos() + p.getColor().getColor())
                                && ((location.getX() == p.getxPos() + 1)
                                || (location.getX() == p.getxPos() - 1)))
                        {
                            return true;
                        }
                        break;
                    case ROOK:
                        if (rookAttack(p.getLocation(), location))
                        {
                            return true;
                        }
                        break;
                    case BISHOP:
                        if (bishopAttack(p.getLocation(), location))
                        {
                            return true;
                        }
                        break;
                    case KNIGHT:
                        if (((location.getX() == p.getxPos() - 2) && (location.getY() == p.getyPos() + 1))
                                || ((location.getX() == p.getxPos() - 2) && (location.getY() == p.getyPos() - 1))
                                || ((location.getX() == p.getxPos() - 1) && (location.getY() == p.getyPos() - 2))
                                || ((location.getX() == p.getxPos() + 1) && (location.getY() == p.getyPos() + 2))
                                || ((location.getX() == p.getxPos() - 1) && (location.getY() == p.getyPos() + 2))
                                || ((location.getX() == p.getxPos() + 1) && (location.getY() == p.getyPos() - 2))
                                || ((location.getX() == p.getxPos() + 2) && (location.getY() == p.getyPos() + 1))
                                || ((location.getX() == p.getxPos() + 2) && (location.getY() == p.getyPos() - 1)))
                        {
                            return true;
                        }
                        break;
                    case QUEEN:
                        if (rookAttack(p.getLocation(), location)
                                || bishopAttack(p.getLocation(), location))
                        {
                            return true;
                        }
                        break;
                    case KING:
                        if ((location.getX() == p.getxPos() || location.getX() == p.getxPos() - 1 || location.getX() == p.getxPos() + 1)
                                && (location.getY() == p.getyPos() || location.getY() == p.getyPos() - 1 || location.getY() == p.getyPos() + 1))
                        {
                            return true;
                        }
                        break;
                    default:
                }
            }
        }

        return false;
    }

    public boolean freeSquare(Location location)
    {
        return freeSquare(location.getX(), location.getY());
    }
    
    public boolean freeSquare(int x, int y)
    {
        return xyPosition[x][y] == null;
    }

    @Override
    public String toString()
    {
        int x, y;
        Piece p;
        String s = "\n _______________\n";

        for (y = 7; y >= 0; y--)
        {
            for (x = 0; x < 8; x++)
            {
                s = s + " ";
                p = getPiece(new Location(x, y));
                if (p == null)
                {
                    s = s + ".";
                } else
                {
                    s = s + p.toString();
                }
            }
            s = s + ("     " + (y + 1)) + "\n";
        } // end last for-loop
        s = s + " _______________\n";
        s = s + " a b c d e f g h\n";
        return s;
    }

    public BitBoard getBitBoard()
    {
        return bitBoard;
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof Position)
        {
            Position position = (Position) object;
            return bitBoard.equals(position.bitBoard);
        }
        else
        {
            return false;
        }
    }
    
    public boolean isInCheck(Color color)
    {
        for (Piece p : pieces)
        {
            if (p.getPieceType() == PieceType.KING && p.getColor() == color)
            {
                if (attacks(p.getLocation(), color))
                {
                    return true;
                }                
            }
        }     
        return false;
    }
  
}
