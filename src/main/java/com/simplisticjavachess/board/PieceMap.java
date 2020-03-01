package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class PieceMap
{
    private final Map<Location, Piece> piecesMap;

    private final int piecesHash;

    PieceMap()
    {
        piecesMap = new HashMap<>();
        piecesHash = 0;
    }

    public PieceMap(Collection<Piece> pieces)
    {
        this.piecesMap = new HashMap<>();
        int h = 0;
        for (Piece piece : pieces)
        {
            piecesMap.put(piece.getLocation(), piece);
            h ^= piece.getChessHashCode();
        }
        this.piecesHash = h;
    }

    private PieceMap(Map<Location, Piece> piecesMap, int piecesHash)
    {
        this.piecesMap = piecesMap;
        this.piecesHash = piecesHash;
    }

    PieceMap insert(Piece piece)
    {
        if (piecesMap.containsKey(piece.getLocation()))
        {
            throw new IllegalStateException("Tried to insert piece at a location at an occupied location");
        }
        else
        {
            Map<Location, Piece> newPiecesMap = new HashMap<>(this.piecesMap);
            newPiecesMap.put(piece.getLocation(), piece);
            int newChessHashCode = this.piecesHash ^ piece.getChessHashCode();
            return new PieceMap(newPiecesMap, newChessHashCode);
        }
    }

    public Piece getPiece(Location location)
    {
        return piecesMap.get(location);
    }

    public Piece get(Location location)
    {
        return piecesMap.get(location);
    }

    Collection<Piece> getPieces()
    {
        return piecesMap.values();
    }

    /**
     * Remove a piece
     * @param piece - to remove
     * @return the resulting position
     */
    PieceMap remove(Piece piece)
    {
        if (piecesMap.containsKey(piece.getLocation()))
        {
            Map<Location, Piece> newPiecesMap = new HashMap<>(this.piecesMap);
            newPiecesMap.remove(piece.getLocation());
            int newChessHashCode = this.piecesHash ^ piece.getChessHashCode();
            return new PieceMap(newPiecesMap, newChessHashCode);
        }
        else
        {
            throw new IllegalStateException("Tried to remove a piece which was not there");
        }

    }

    public PieceMap move(Piece piece, Location to)
    {
        if (piecesMap.containsKey(to))
        {
            throw new IllegalStateException();
        }
        else
        {
            PieceMap tmp = remove(piece);
            Piece newPiece = piece.updateLocation(to);
            return tmp.insert(newPiece);
        }
    }


    public Collection<Piece> values()
    {
        return piecesMap.values();
    }

    public Optional<Piece> getKing(Color color)
    {
        return piecesMap.values().
                stream().filter(piece ->
                PieceType.KING.equals(piece.getPieceType()) &&
                        color.equals(piece.getColor())).
                findAny();
    }


    boolean freeSquare(Location location)
    {
        return freeSquare(location.getX(), location.getY());
    }

    public boolean freeSquare(int x, int y)
    {
        return !piecesMap.containsKey(new Location(x, y));
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (object == null || getClass() != object.getClass())
        {
            return false;
        }

        PieceMap pieceMap = (PieceMap) object;
        return piecesHash == pieceMap.piecesHash &&
                Objects.equals(piecesMap, pieceMap.piecesMap);
    }

    @Override
    public int hashCode() {
        return getChessHashCode();
    }

    /**
     * @return Ascii representation of the position
     */
    String getPositionString()
    {
        String result = "\n _______________\n";

        for (int y = 7; y >= 0; y--)
        {
            for (int x = 0; x < 8; x++)
            {
                result = result + " ";
                Piece piece = getPiece(new Location(x, y));
                result = piece == null ? result + "." : result + piece.toString();
            }
            result = result + ("     " + (y + 1)) + "\n";
        }
        result = result + " _______________\n";
        result = result + " a b c d e f g h\n";
        return result;
    }

    public String toString()
    {
        return getPositionString();
    }

    public int getChessHashCode() {
        return piecesHash;
    }


}
