package com.simplisticjavachess.board;

/**
 * @author Morten Kühnrich
 */
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.position.Location;

public class BitBoard
{

    protected static final int NUM_COLORS = 2; // Black and white

    protected long bb[][];

    public BitBoard()
    {
        bb = new long[NUM_COLORS][PieceType.values().length];
    }

    public BitBoard(Board b)
    {
        this();

        for (Piece p : b.getPieces())
        {
            int c = getIndexFromColor(p.getColor());
            PieceType t = p.getPieceType();
            if (t != null)
            {
                bb[c][t.getType()] = bb[c][t.getType()] | setBitHigh(getSquareNoFromPos(p.getLocation()));
            }
        }
    }

    public BitBoard(BitBoard bitBoard)
    {
        this();
        
        for (int t = 0; t < PieceType.values().length; t++)
        {
            this.bb[0][t] = bitBoard.bb[0][t];
            this.bb[1][t] = bitBoard.bb[1][t];
        }
    }

    protected final int getIndexFromColor(Color color)
    {
        return color == Color.BLACK ? 0 : 1;
    }

    protected Color getColorFromIndex(int index)
    {
        assert (index == 0 || index == 1);
        return index == 0 ? Color.BLACK : Color.WHITE;
    }

    private int countOnes(long v)
    {
        return Long.bitCount(v);
    }

    public int getNumberOfPieces()
    {
        int res = 0;
        for (int t = 0; t < 6; t++)
        {
            res += countOnes(bb[0][t]) + countOnes(bb[1][t]);
        }
        return res;
    }

    // Returns number in the interval 0..63 from x and y in the interval 0..7
    protected final int getSquareNoFromPos(Location location)
    {
        return location.getY() * 8 + location.getX();
    }

    // Set bit with index bitNo to high - i.e. 1
    private long setBitHigh(int bitNo)
    {
        return 1L << bitNo;
    }

    public void insertPiece(Piece p)
    {
        bb[getIndexFromColor(p.getColor())][p.getPieceType().getType()] = bb[getIndexFromColor(p.getColor())][p.getPieceType().getType()]
                | setBitHigh(getSquareNoFromPos(p.getLocation()));
    }

    public boolean hasPiece(Location location, Color color, PieceType type)
    {
        return (bb[getIndexFromColor(color)][type.getType()]
                & setBitHigh(getSquareNoFromPos(location)))
                != 0;
    }

    public Piece removePiece(Location location)
    {
        Color color = null;
        PieceType type = null;

        for (PieceType t : PieceType.values())
        {
            if ((bb[0][t.getType()] & setBitHigh(getSquareNoFromPos(location))) != 0)
            {
                color = Color.BLACK;
                type = t;
                break;
            }
            if ((bb[1][t.getType()] & setBitHigh(getSquareNoFromPos(location))) != 0)
            {
                color = Color.WHITE;
                type = t;
                break;
            }
        }
        
        bb[getIndexFromColor(color)][type.getType()] = bb[getIndexFromColor(color)][type.getType()] ^ // Bitwise XOR
                setBitHigh(getSquareNoFromPos(location));

        return new Piece(location, color, type);
    }

    private String bitboard2String(long b)
    {
        String r = "";
        int x, y;

        for (y = 7; y >= 0; y--)
        {
            for (x = 0; x <= 7; x++)
            {
                if (((1L << getSquareNoFromPos(new Location(x, y))) & b) == 0)
                {
                    r = r + '.';
                } else
                {
                    r = r + '+';
                }
            }
            r = r + "\n";
        }
        return r;
    }

    @Override
    public String toString()
    {
        String s = "";

        for (PieceType pieceType : PieceType.values())
        {
            s = s + "Black " + pieceType.getPieceLetter(Color.BLACK)
                    + "\n"
                    + bitboard2String(bb[0][pieceType.getType()]);

            s = s + "White " + pieceType.getPieceLetter(Color.WHITE)
                    + "\n"
                    + bitboard2String(bb[1][pieceType.getType()]);
        }

        return s;
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof BitBoard)
        {
            BitBoard bitboard = (BitBoard) object;

            for (PieceType t : PieceType.values())
            {
                if (this.bb[0][t.getType()] != bitboard.bb[0][t.getType()]
                        || this.bb[1][t.getType()] != bitboard.bb[1][t.getType()])
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

}

