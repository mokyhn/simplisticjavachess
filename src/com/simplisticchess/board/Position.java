package com.simplisticchess.board;

/**
 * @author Morten Kühnrich
 *
 */
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;

public final class Position
{

    private final Piece[] pieces;
    private final Piece[][] xyPosition;
    private int numberOfPieces;
    
    private BitBoard bitBoard;
            
    public Position()
    {
        super();
        numberOfPieces = 0;
        pieces = new Piece[32];
        xyPosition = new Piece[8][8];
        bitBoard = new BitBoard();
    }

    public Position(Position position)
    {
        this();
        Piece p;
        this.numberOfPieces = position.numberOfPieces;

        for (int i = 0; i < numberOfPieces; i++)
        {
            p = new Piece(position.pieces[i]);
            this.pieces[i] = p;
            this.xyPosition[p.getxPos()][p.getyPos()] = p;
        }

        for (PieceType t : PieceType.values())
        {
            this.bitBoard.bb[0][t.getType()] = position.bitBoard.bb[0][t.getType()];
            this.bitBoard.bb[1][t.getType()] = position.bitBoard.bb[1][t.getType()];
        }
    }

    public Piece getPiece(Location location)
    {
        final Piece p = xyPosition[location.getX()][location.getY()];
        return p;
    }
    
    public Piece getPiece(final int i)
    {
        final Piece p = pieces[i];
        assert p != null;

        final Piece ptmp = xyPosition[p.getxPos()][p.getyPos()];
        assert ptmp != null : "Unexpected null value with piece " + i + " of type " + p.toString() + " at (" + p.getxPos() + "," + p.getyPos() + ")" + "\n"
                + this.toString();
        assert ptmp.getxPos() == p.getxPos() && ptmp.getyPos() == p.getyPos();
        return p;
    }


    public void insertPiece(final Piece p)
    {
        assert numberOfPieces < 32 : "PieceList:insertPiece: Inserting to many pieces";
        assert p != null;
        pieces[numberOfPieces] = p;
        numberOfPieces++;

        xyPosition[p.getxPos()][p.getyPos()] = p;

        getBitBoard().insertPiece(p);
    }

    // Remove a piece from location x, y and return the piece
    public Piece removePiece(Location location)
    {
        int i;
        Boolean flag = true;
        Piece p = xyPosition[location.getX()][location.getY()];

        assert (p != null) : "removePiece at " + location.getX() + ","  + location.getY() + "\n" + this.toString();

        assert (p.getxPos() == location.getX()) && (p.getyPos() == location.getY());

        for (i = 0; i < numberOfPieces && flag; i++)
        {
            if (pieces[i].getxPos() == location.getX()
                    && pieces[i].getyPos() == location.getY())
            {
                pieces[i] = pieces[numberOfPieces - 1];
                numberOfPieces--;
                flag = false;
            }
        }

        xyPosition[location.getX()][location.getY()] = null;

        getBitBoard().removePiece(location);

        return p;
    }
   
    public void movePiece(Location from, Location to)
    {
        final Piece p = xyPosition[from.getX()][from.getY()];

        assert (from.getX() != to.getX() || from.getY() != to.getY()) : "Cannot move from c to c";
        assert (p != null) : this.toString() + "\n" + "Tried move " + from.getX() + "," + from.getY() + " to " + to.getX() + "," + to.getY();

        p.setxPos(to.getX());
        p.setyPos(to.getY());

        xyPosition[to.getX()][to.getY()] = p;
        assert p.getxPos() == xyPosition[p.getxPos()][p.getyPos()].getxPos() && p.getyPos() == xyPosition[p.getxPos()][p.getyPos()].getyPos();
        xyPosition[from.getX()][from.getY()] = null;

        getBitBoard().removePiece(from);
        getBitBoard().insertPiece(p);
    }

    public int getNumberOfPieces()
    {
        return numberOfPieces;
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
        Piece p;

        for (int i = 0; i < numberOfPieces; i++)
        {
            p = getPiece(i);

            // Chose one of opposite color
            if (p.getColor() == inMove.flip() && !(p.getxPos() == location.getX() && p.getyPos() == location.getY()))
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

    /**
     * @return the bitBoard
     */
    public BitBoard getBitBoard()
    {
        return bitBoard;
    }
  
}
