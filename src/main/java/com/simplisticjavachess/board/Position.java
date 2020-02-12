/**
 * @author Morten Kühnrich
 *
 */

package com.simplisticjavachess.board;

import com.simplisticjavachess.game.CastlingState;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Position
{
    private Color inMove;

    private CastlingState castlingState;

    private final Map<Location, Piece> piecesMap;

    private int hashCode;

    // TODO: Get rid of this dummy - no constructor.
    Position()
    {
        castlingState = CastlingState.NOBODY_CAN_CASTLE;
        piecesMap = new HashMap<>();
        hashCode = 0;
        inMove = null;
    }

    public Position(Color inMove)
    {
        this.castlingState = CastlingState.NOBODY_CAN_CASTLE;
        piecesMap = new HashMap<>();
        hashCode = inMove.getChessHashCode();
        this.inMove = inMove;
    }

    private Position(Position position)
    {
        this.castlingState = position.castlingState;
        this.piecesMap = new HashMap<>(position.piecesMap);
        this.inMove = position.inMove;
        this.hashCode = position.hashCode;
    }

    private Position(Color inMove, CastlingState castlingState, Map<Location, Piece> pieces, int hashCode)
    {
        this.inMove = inMove;
        this.castlingState = castlingState;
        this.piecesMap = new HashMap<>(pieces);
        this.hashCode = hashCode;
    }

    public Color getInMove()
    {
        return inMove;
    }

    public Position setInMove(Color inMove) {
        Position newPosition = new Position(this);
        newPosition.inMove = inMove;
        newPosition.hashCode = hashCode ^ this.inMove.getChessHashCode() ^ inMove.getChessHashCode();
        return newPosition;
    }

    public Position setCanCastleShort(boolean flag, Color color)
    {
        CastlingState newCastlingState;

        if (flag)
        {
            newCastlingState = castlingState.setCanCastleShort(color);
        } else {
            newCastlingState = castlingState.setCannotCastleShort(color);
        }

        return new Position(inMove, newCastlingState, this.piecesMap, this.hashCode ^ this.castlingState.getChessHashCode() ^ newCastlingState.getChessHashCode());
    }

    public Position setCanCastleLong(boolean flag, Color color)
    {
        CastlingState newCastlingState;

        if (flag)
        {
            newCastlingState = castlingState.setCanCastleLong(color);
        }
        else
        {
            newCastlingState = castlingState.setCannotCastleLong(color);
        }
        return new Position(inMove, newCastlingState, this.piecesMap, this.hashCode ^ this.castlingState.getChessHashCode() ^ newCastlingState.getChessHashCode());
    }

    public boolean getCanCastleShort(Color color)
    {
        return castlingState.canCastleShort(color);
    }

    public boolean getCanCastleLong(Color color)
    {
        return castlingState.canCastleLong(color);
    }


    /**
     *
     * @param piece to insert
     * @return the resulting position
     */
    Position insert(Piece piece)
    {
        if (piecesMap.containsKey(piece.getLocation()))
        {
            throw new IllegalStateException("Tried to insert piece at a location at an occupied location");
        }
        else
        {
            Position result = new Position(this);
            result.piecesMap.put(piece.getLocation(), piece);
            result.hashCode = result.hashCode ^ piece.getChessHashCode();
            return result;
        }
    }

    public Piece getPiece(Location location)
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
    Position remove(Piece piece)
    {
        if (piecesMap.containsKey(piece.getLocation()))
        {
            Position result = new Position(this);
            result.piecesMap.remove(piece.getLocation());
            result.hashCode = result.hashCode ^ piece.getChessHashCode();
            return result;
        }
        else
        {
             throw new IllegalStateException("Tried to remove a piece which was not there");
        }

    }

    public Position move(Piece piece, Location to)
    {
        if (piecesMap.containsKey(to))
        {
            throw new IllegalStateException();
        }
        else
        {
            Position tmp = remove(piece);
            Piece newPiece = piece.updateLocation(to);
            return tmp.insert(newPiece);
        }
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

    boolean freeSquare(int x, int y)
    {
        return !piecesMap.containsKey(new Location(x, y));
    }

    /**
     * Are all locations free between from and to?
     * @param from location to check from
     * @param to location to check towards
     * @return true if all locations in between are free and false otherwise
     */
    public boolean freeSquares(Location from, Location to)
    {
        for (Location location : from.iterateTO(to))
        {
            if (!freeSquare(location))
            {
                return false;
            }
        }
        return true;
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
        result = result + (inMove == Color.WHITE ? "  White to move\n" : "  Black to move\n");
        result = result + castlingState.toString();
        return result;
    }

    @Override
    public String toString()
    {
        return this.getPositionString();
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (object instanceof Position)
        {
            Position position = (Position) object;
            return  //this.hashCode == position.hashCode && TODO: A bug prevents this from working
                    this.inMove == position.inMove &&
                    this.castlingState.equals(position.castlingState) &&
                    this.piecesMap.equals(position.piecesMap);
        }
        else
        {
            return false;
        }
    }

    //TODO: Add tests that show that hashing lives up to the requirements for chess positions also wrt. definitions
    //needed for threefold repetition checking
    @Override
    public int hashCode() {
        return hashCode;
    }

    public int getChessHashCode()
    {
        return hashCode;
    }

}
