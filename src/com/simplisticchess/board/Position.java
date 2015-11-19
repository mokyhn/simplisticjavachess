package com.simplisticchess.board;

/**
 * @author Morten Kühnrich
 *
 */
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.PieceType;

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

    public Piece getPiece(final int x, final int y)
    {
        final Piece p = xyPosition[x][y];
        // For testing: areRepresentationsIsomorphic();
        if (p != null)
        {
            assert p.getxPos() == x && p.getyPos() == y;
        }
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
    public Piece removePiece(final int x, final int y)
    {
        int i;
        Boolean flag = true;
        Piece p = xyPosition[x][y];

        assert (p != null) : "removePiece at " + x + ","  + y + "\n" + this.toString();

        assert (p.getxPos() == x) && (p.getyPos() == y);

        for (i = 0; i < numberOfPieces && flag; i++)
        {
            if (pieces[i].getxPos() == x
                    && pieces[i].getyPos() == y)
            {
                pieces[i] = pieces[numberOfPieces - 1];
                numberOfPieces--;
                flag = false;
            }
        }

        xyPosition[x][y] = null;

        getBitBoard().removePiece(x, y);

        return p;
    }

    public void movePiece(int xFrom, int yFrom, int xTo, int yTo)
    {
        final Piece p = xyPosition[xFrom][yFrom];

        assert (xFrom != xTo || yFrom != yTo) : "Cannot move from c to c";
        assert (p != null) : this.toString() + "\n" + "Tried move " + xFrom + "," + yFrom + " to " + xTo + "," + yTo;

        p.setxPos(xTo);
        p.setyPos(yTo);

        xyPosition[xTo][yTo] = p;
        assert p.getxPos() == xyPosition[p.getxPos()][p.getyPos()].getxPos() && p.getyPos() == xyPosition[p.getxPos()][p.getyPos()].getyPos();
        xyPosition[xFrom][yFrom] = null;

        getBitBoard().removePiece(xFrom, yFrom);
        getBitBoard().insertPiece(p);
    }

    public int getNumberOfPieces()
    {
        return numberOfPieces;
    }

    private boolean rookAttack(int x1, int y1, int x2, int y2)
    {
        Boolean allFree = true;
        int lowX, // From x pos
                highX, // To x pos
                lowY, // From y pos 
                highY, // To y pos
                ix, // Iterate x
                iy;    // Iterate y

        if (x1 == x2)
        {
            allFree = true;
            if (y1 < y2)
            {
                lowY = y1;
                highY = y2;
            } else
            {
                lowY = y2;
                highY = y1;
            }
            for (iy = lowY + 1; iy < highY; iy++)
            {
                if (!freeSquare(x1, iy))
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
        if (y1 == y2)
        {
            allFree = true;
            if (x1 < x2)
            {
                lowX = x1;
                highX = x2;
            } else
            {
                lowX = x2;
                highX = x1;
            }
            for (ix = lowX + 1; ix < highX; ix++)
            {
                if (!freeSquare(ix, y1))
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

    private boolean bishopAttack(int x1, int y1, int x2, int y2)
    {
        final int r; // Radius
        int ir;      // Iterator over radii
        int dx;
        int dy;
        boolean allFree = true;
        dx = x2 - x1;
        dy = y2 - y1;

        // First condition that allows one to be threatened by a bishop
        if (Math.abs(dx) == Math.abs(dy))
        {
            r = Math.abs(dx);
            dx = dx / r;
            dy = dy / r;
            for (ir = 1; ir < r; ir++)
            {
                if (!freeSquare(ir * dx + x1, ir * dy + y1))
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

    public boolean attacks(int x, int y, Color inMove)
    {
        Piece p;

        for (int i = 0; i < numberOfPieces; i++)
        {
            p = getPiece(i);

            // Chose one of opposite color
            if (p.getColor() == inMove.flip() && !(p.getxPos() == x && p.getyPos() == y))
            {
                switch (p.getPieceType())
                {
                    case PAWN:
                        if ((y == p.getyPos() + p.getColor().getColor())
                                && ((x == p.getxPos() + 1)
                                || (x == p.getxPos() - 1)))
                        {
                            return true;
                        }
                        break;
                    case ROOK:
                        if (rookAttack(p.getxPos(), p.getyPos(), x, y))
                        {
                            return true;
                        }
                        break;
                    case BISHOP:
                        if (bishopAttack(p.getxPos(), p.getyPos(), x, y))
                        {
                            return true;
                        }
                        break;
                    case KNIGHT:
                        if (((x == p.getxPos() - 2) && (y == p.getyPos() + 1))
                                || ((x == p.getxPos() - 2) && (y == p.getyPos() - 1))
                                || ((x == p.getxPos() - 1) && (y == p.getyPos() - 2))
                                || ((x == p.getxPos() + 1) && (y == p.getyPos() + 2))
                                || ((x == p.getxPos() - 1) && (y == p.getyPos() + 2))
                                || ((x == p.getxPos() + 1) && (y == p.getyPos() - 2))
                                || ((x == p.getxPos() + 2) && (y == p.getyPos() + 1))
                                || ((x == p.getxPos() + 2) && (y == p.getyPos() - 1)))
                        {
                            return true;
                        }
                        break;
                    case QUEEN:
                        if (rookAttack(p.getxPos(), p.getyPos(), x, y)
                                || bishopAttack(p.getxPos(), p.getyPos(), x, y))
                        {
                            return true;
                        }
                        break;
                    case KING:
                        if ((x == p.getxPos() || x == p.getxPos() - 1 || x == p.getxPos() + 1)
                                && (y == p.getyPos() || y == p.getyPos() - 1 || y == p.getyPos() + 1))
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
                p = getPiece(x, y);
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

    /**
     * @param bitBoard the bitBoard to set
     */
    public void setBitBoard(BitBoard bitBoard)
    {
        this.bitBoard = bitBoard;
    }

}
