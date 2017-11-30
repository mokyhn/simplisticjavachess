/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import java.util.ArrayList;
import java.util.Iterator;

public class KingMoveGenerator
{
    private static ArrayList<Move> generateMoves(Board b, Piece p)
    {
        final ArrayList<Move> Moves = new ArrayList<Move>();
        final Color c = b.inMove();
        final int fx = p.getxPos();
        final int fy = p.getyPos();


        // Castling short
        if (fx == 4
                && b.canCastleShort()
                && b.freeSquare(5, fy)
                && b.freeSquare(6, fy)
                && !b.isAttacked(5, fy)
                && !b.isAttacked(6, fy)
                && !b.isInCheck(c))
        {
            assert (b.getPiece(7, fy) != null);
            assert (b.getPiece(7, fy).getPieceType() == PieceType.ROOK) : "Expected rook, found wirdo piece: " + b.getPiece(7, fy).toString() + "fx, fy=" + fx + ", " + fy + "c = " + c;
            assert (b.getPiece(7, fy).getColor() == c);
            Moves.add(new Move(fx, fy, fx + 2, fy, MoveType.CASTLE_SHORT, null, c));
        }

        // Castling long
        if (fx == 4
                && b.canCastleLong()
                && b.freeSquare(3, fy)
                && b.freeSquare(2, fy)
                && b.freeSquare(1, fy)
                && !b.isAttacked(2, fy)
                && !b.isAttacked(3, fy)
                && !b.isInCheck(c))
        {
            Moves.add(new Move(fx, fy, fx - 2, fy, MoveType.CASTLE_LONG, null, c));
        }
        
        Piece pTo;

        // King moves left
        if (fx > 0)
        {
            if (!b.isAttacked(fx - 1, fy))
            {
                // Left, and not up/down
                if (b.freeSquare(fx - 1, fy))
                {
                    Moves.add(new Move(fx, fy, fx - 1, fy, MoveType.NORMALMOVE, null, c));
                } 
                else
                {
                    pTo = b.getPiece(fx - 1, fy);
                    if (pTo != null && pTo.getColor() != c)
                    {
                        Moves.add(new Move(fx, fy, fx - 1, fy, MoveType.CAPTURE, pTo, c));
                    }
                }
            }

            // Up
            if (fy < 7 && !b.isAttacked(fx - 1, fy + 1))
            {
                if (b.freeSquare(fx - 1, fy + 1))
                {
                    Moves.add(new Move(fx, fy, fx - 1, fy + 1, MoveType.NORMALMOVE, null, c));
                } else
                {
                    pTo = b.getPiece(fx - 1, fy + 1);
                    if (pTo != null && pTo.getColor() != c)
                    {
                        Moves.add(new Move(fx, fy, fx - 1, fy + 1, MoveType.CAPTURE, pTo, c));
                    }
                }
            }

            // Down
            if (fy > 0 && !b.isAttacked(fx - 1, fy - 1))
            {
                if (b.freeSquare(fx - 1, fy - 1))
                {
                    Moves.add(new Move(fx, fy, fx - 1, fy - 1, MoveType.NORMALMOVE, null, c));
                } else
                {
                    pTo = b.getPiece(fx - 1, fy - 1);
                    if (pTo != null && pTo.getColor() != c)
                    {
                        Moves.add(new Move(fx, fy, fx - 1, fy - 1, MoveType.CAPTURE, pTo, c));
                    }
                }
            }
        } // End of "Left" section

        // King moves right
        if (fx < 7)
        {
            if (!b.isAttacked(fx + 1, fy))
            {
                // To side
                if (b.freeSquare(fx + 1, fy))
                {
                    Moves.add(new Move(fx, fy, fx + 1, fy, MoveType.NORMALMOVE, null, c));
                } else
                {
                    pTo = b.getPiece(fx + 1, fy);
                    if (pTo != null && pTo.getColor() != c)
                    {
                        Moves.add(new Move(fx, fy, fx + 1, fy, MoveType.CAPTURE, pTo, c));
                    }
                }
            }

            // Up and to the right
            if (fy < 7)
            {
                if (!b.isAttacked(fx + 1, fy + 1))
                {
                    if (b.freeSquare(fx + 1, fy + 1))
                    {
                        Moves.add(new Move(fx, fy, fx + 1, fy + 1, MoveType.NORMALMOVE, null, c));
                    } else
                    {
                        pTo = b.getPiece(fx + 1, fy + 1);
                        if (pTo != null && pTo.getColor() != c)
                        {
                            Moves.add(new Move(fx, fy, fx + 1, fy + 1, MoveType.CAPTURE, pTo, c));
                        }
                    }
                }
            }

            // Down and to the right
            if (fy > 0)
            {
                if (!b.isAttacked(fx + 1, fy - 1))
                {
                    if (b.freeSquare(fx + 1, fy - 1))
                    {
                        Moves.add(new Move(fx, fy, fx + 1, fy - 1, MoveType.NORMALMOVE, null, c));
                    } else
                    {
                        pTo = b.getPiece(fx + 1, fy - 1);
                        if (pTo != null && pTo.getColor() != c)
                        {
                            Moves.add(new Move(fx, fy, fx + 1, fy - 1, MoveType.CAPTURE, pTo, c));
                        }
                    }
                }
            }
        } // End of "right" section

        // King moves straight up
        if (fy < 7 && !b.isAttacked(fx, fy + 1))
        {
            if (b.freeSquare(fx, fy + 1))
            {
                Moves.add(new Move(fx, fy, fx, fy + 1, MoveType.NORMALMOVE, null, c));
            } else
            {
                pTo = b.getPiece(fx, fy + 1);
                if (pTo != null && pTo.getColor() != c)
                {
                    Moves.add(new Move(fx, fy, fx, fy + 1, MoveType.CAPTURE, pTo, c));
                }
            }
        }

        // King moves straight down
        if (fy > 0 && !b.isAttacked(fx, fy - 1))
        {
            if (b.freeSquare(fx, fy - 1))
            {
                Moves.add(new Move(fx, fy, fx, fy - 1, MoveType.NORMALMOVE, null, c));
            } else
            {
                pTo = b.getPiece(fx, fy - 1);
                if (pTo != null && pTo.getColor() != c)
                {
                    Moves.add(new Move(fx, fy, fx, fy - 1, MoveType.CAPTURE, pTo, c));
                }
            }
        }

        return Moves;
    }    

    // TODO: The following can be refined so that not all moves are generated at once
    public static Iterator<Move> getIterator(final Board b, final Piece p)
    {
        return new Iterator<Move>()
        {
            Iterator<Move> generated = null;
            
            public boolean hasNext()
            {
                if (generated == null) 
                {
                    generated = generateMoves(b, p).iterator();
                }
                return generated.hasNext();
            }

            public Move next()
            {
                if (hasNext()) 
                {
                    return generated.next();
                }
                else
                {
                    return null;
                }
            }

            public void remove()
            {                
            }
        };
    }

}
