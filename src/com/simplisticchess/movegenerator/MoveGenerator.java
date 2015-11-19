package com.simplisticchess.movegenerator;

//TODO: Implementation via an iterator. 
//A numbering of all moves generated. A function on the form
//genmoves(board, moveid) such that only moves with a certain id
//are generated.
// This can be used to optimize our search engine such that not all
// moves have to be generated beforehand. We can simply generated one move
// at a time. (when cutoffs occur in the alpha beta search we will not have
// wasted time for generating moves which where never searched anyway.
// Eliminatate generation of a number of moves when king is in check...
// The king is not allowed to be in check while another piece is moved...

import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.move.MoveType;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.PieceType;
import java.util.ArrayList;




public final class MoveGenerator
{

    public MoveGenerator()
    {
    }

    // Input: Given a board b, and that there is a pawn p
    // Output: The set of moves this pawn can perform.
    public static ArrayList<Move> pawnMoves(Board b, Piece p)
    {

        final Color c = b.inMove();
        final int fx = p.getxPos();
        final int fy = p.getyPos();

        Piece leftPiece;
        Piece rightPiece;
        Piece lastMovePiece;

        final ArrayList<Move> Moves = new ArrayList<Move>();

        // Normal one step forward pawn move
        if (((fy < 6) && (c == Color.WHITE))
                || (fy > 1) && (c == Color.BLACK))
        {
            if (b.freeSquare(fx, fy + c.getColor() * 1))
            {
                Moves.add(new Move(fx, fy, fx, fy + c.getColor() * 1, MoveType.NORMALMOVE, null, c));
            }
        }

        // Normal two step forward pawn move
        if (((fy == 1) && (c == Color.WHITE))
                || ((fy == 6) && (c == Color.BLACK)))
        {
            if (b.freeSquare(fx, fy + c.getColor() * 1) && b.freeSquare(fx, fy + c.getColor() * 2))
            {
                Moves.add(new Move(fx, fy, fx, (fy + c.getColor() * 2), MoveType.NORMALMOVE, null, c));
            }
        }

        // Non capturing PAWN promotion
        if (((fy == 6) && (c == Color.WHITE) && b.freeSquare(fx, 7))
                || ((fy == 1) && (c == Color.BLACK) && b.freeSquare(fx, 0)))
        {
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_QUEEN, null, c));
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_ROOK, null, c));
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_KNIGHT, null, c));
            Moves.add(new Move(fx, fy, fx, fy + c.getColor(), MoveType.PROMOTE_TO_BISHOP, null, c));
        }

        // Normal diagonal capturing to the left
        if ((fx > 0) && (fy != (5 * c.getColor() + 7) / 2))
        {
            leftPiece = b.getPiece(fx - 1, fy + c.getColor());
            if (leftPiece != null && leftPiece.getColor() != c)
            {
                Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), MoveType.CAPTURE, leftPiece.getPieceType(), c));
            }
        }

        // Normal diagonal capturing to the right
        if ((fx < 7) && (fy != (5 * c.getColor() + 7) / 2))
        {
            rightPiece = b.getPiece(fx + 1, fy + c.getColor());
            if (rightPiece != null && rightPiece.getColor() != c)
            {
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE, rightPiece.getPieceType(), c));
            }
        }

        // Promotion via diagonal capturing to the left
        if ((fx > 0) && (fy == (5 * c.getColor() + 7) / 2))
        {
            leftPiece = b.getPiece(fx - 1, fy + c.getColor());
            if (leftPiece != null && leftPiece.getColor() != c)
            {
                Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP, leftPiece.getPieceType(), c));
                Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT, leftPiece.getPieceType(), c));
                Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN, leftPiece.getPieceType(), c));
                Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_ROOK, leftPiece.getPieceType(), c));
            }
        }

        // Promotion via diagonal capturing to the right
        if ((fx < 7) && (fy == (5 * c.getColor() + 7) / 2))
        {
            rightPiece = b.getPiece(fx + 1, fy + c.getColor());
            if (rightPiece != null && rightPiece.getColor() != c)
            {
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP, rightPiece.getPieceType(), c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT, rightPiece.getPieceType(), c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN, rightPiece.getPieceType(), c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_AND_PROMOTE_TO_ROOK, rightPiece.getPieceType(), c));
            }

        }

        // En passant capture
        try
        {
            final Move lastMove = b.getLastMove();
            if (fx > 0)
            {

                lastMovePiece = b.getPiece(lastMove.getToX(), lastMove.getToY());
                // The piece stands to the left
                if (lastMovePiece != null && (lastMove.getToX() == fx - 1) && (lastMove.getToY() == fy)
                        && (lastMovePiece.getPieceType() == PieceType.PAWN)
                        && (Math.abs(lastMove.getFromY() - lastMove.getToY()) == 2))
                {
                    Moves.add(new Move(fx, fy, fx - 1, fy + c.getColor(), MoveType.CAPTURE_ENPASSANT, null, c));
                }
            }

            if (fx < 7)
            {
                lastMovePiece = b.getPiece(lastMove.getToX(), lastMove.getToY());
                // The piece stands to the right
                if (lastMovePiece != null && (lastMove.getToX() == fx + 1) && (lastMove.getToY() == fy)
                        && (lastMovePiece.getPieceType() == PieceType.PAWN)
                        && (Math.abs(lastMove.getFromY() - lastMove.getToY()) == 2))
                {
                    Moves.add(new Move(fx, fy, fx + 1, fy + c.getColor(), MoveType.CAPTURE_ENPASSANT, null, c));

                }
            }

        } catch (java.util.EmptyStackException e)
        {
            // There were no last move. We must be a beginning of game.
            // Hence no en passant moves are possible
        }

        return Moves;

    }

    public static ArrayList<Move> kingMoves(Board b, Piece p)
    {
        final ArrayList<Move> Moves = new ArrayList<Move>();
        final Color c = b.inMove();
        final int fx = p.getxPos();
        final int fy = p.getyPos();

        Piece pTo = null;

        // Castling short
        if (fx == 4
                && b.canCastleShort()
                && b.freeSquare(5, fy)
                && b.freeSquare(6, fy)
                && !b.attacks(5, fy)
                && !b.attacks(6, fy)
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
                && !b.attacks(2, fy)
                && !b.attacks(3, fy)
                && !b.isInCheck(c))
        {
            Moves.add(new Move(fx, fy, fx - 2, fy, MoveType.CASTLE_LONG, null, c));
        }

        // King moves left
        if (fx > 0)
        {
            if (!b.attacks(fx - 1, fy))
            {
                // Left, and not up/down
                if (b.freeSquare(fx - 1, fy))
                {
                    Moves.add(new Move(fx, fy, fx - 1, fy, MoveType.NORMALMOVE, null, c));
                } else
                {
                    pTo = b.getPiece(fx - 1, fy);
                    if (pTo != null && pTo.getColor() != c)
                    {
                        Moves.add(new Move(fx, fy, fx - 1, fy, MoveType.CAPTURE, pTo.getPieceType(), c));
                    }
                }
            }

            // Up
            if (fy < 7 && !b.attacks(fx - 1, fy + 1))
            {
                if (b.freeSquare(fx - 1, fy + 1))
                {
                    Moves.add(new Move(fx, fy, fx - 1, fy + 1, MoveType.NORMALMOVE, null, c));
                } else
                {
                    pTo = b.getPiece(fx - 1, fy + 1);
                    if (pTo != null && pTo.getColor() != c)
                    {
                        Moves.add(new Move(fx, fy, fx - 1, fy + 1, MoveType.CAPTURE, pTo.getPieceType(), c));
                    }
                }
            }

            // Down
            if (fy > 0 && !b.attacks(fx - 1, fy - 1))
            {
                if (b.freeSquare(fx - 1, fy - 1))
                {
                    Moves.add(new Move(fx, fy, fx - 1, fy - 1, MoveType.NORMALMOVE, null, c));
                } else
                {
                    pTo = b.getPiece(fx - 1, fy - 1);
                    if (pTo != null && pTo.getColor() != c)
                    {
                        Moves.add(new Move(fx, fy, fx - 1, fy - 1, MoveType.CAPTURE, pTo.getPieceType(), c));
                    }
                }
            }
        } // End of "Left" section

        // King moves right
        if (fx < 7)
        {
            if (!b.attacks(fx + 1, fy))
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
                        Moves.add(new Move(fx, fy, fx + 1, fy, MoveType.CAPTURE, pTo.getPieceType(), c));
                    }
                }
            }

            // Up and to the right
            if (fy < 7)
            {
                if (!b.attacks(fx + 1, fy + 1))
                {
                    if (b.freeSquare(fx + 1, fy + 1))
                    {
                        Moves.add(new Move(fx, fy, fx + 1, fy + 1, MoveType.NORMALMOVE, null, c));
                    } else
                    {
                        pTo = b.getPiece(fx + 1, fy + 1);
                        if (pTo != null && pTo.getColor() != c)
                        {
                            Moves.add(new Move(fx, fy, fx + 1, fy + 1, MoveType.CAPTURE, pTo.getPieceType(), c));
                        }
                    }
                }
            }

            // Down and to the right
            if (fy > 0)
            {
                if (!b.attacks(fx + 1, fy - 1))
                {
                    if (b.freeSquare(fx + 1, fy - 1))
                    {
                        Moves.add(new Move(fx, fy, fx + 1, fy - 1, MoveType.NORMALMOVE, null, c));
                    } else
                    {
                        pTo = b.getPiece(fx + 1, fy - 1);
                        if (pTo != null && pTo.getColor() != c)
                        {
                            Moves.add(new Move(fx, fy, fx + 1, fy - 1, MoveType.CAPTURE, pTo.getPieceType(), c));
                        }
                    }
                }
            }
        } // End of "right" section

        // King moves straight up
        if (fy < 7 && !b.attacks(fx, fy + 1))
        {
            if (b.freeSquare(fx, fy + 1))
            {
                Moves.add(new Move(fx, fy, fx, fy + 1, MoveType.NORMALMOVE, null, c));
            } else
            {
                pTo = b.getPiece(fx, fy + 1);
                if (pTo != null && pTo.getColor() != c)
                {
                    Moves.add(new Move(fx, fy, fx, fy + 1, MoveType.CAPTURE, pTo.getPieceType(), c));
                }
            }
        }

        // King moves straight down
        if (fy > 0 && !b.attacks(fx, fy - 1))
        {
            if (b.freeSquare(fx, fy - 1))
            {
                Moves.add(new Move(fx, fy, fx, fy - 1, MoveType.NORMALMOVE, null, c));
            } else
            {
                pTo = b.getPiece(fx, fy - 1);
                if (pTo != null && pTo.getColor() != c)
                {
                    Moves.add(new Move(fx, fy, fx, fy - 1, MoveType.CAPTURE, pTo.getPieceType(), c));
                }
            }
        }

        return Moves;
    }

    // Genereate the possible moves for one single piece
    public static ArrayList<Move> generateMoves(Board b, Piece p)
    {
        ArrayList<Move> Moves = new ArrayList<Move>();
        final Color sideToMove = b.inMove();

        if (p.getColor() != sideToMove)
        {
            return Moves;
        }

        switch (p.getPieceType())
        {
            case PAWN:
                return pawnMoves(b, p);
            case KING:
                return kingMoves(b, p);
            case KNIGHT:
                return knightMoves(b, p);
            case BISHOP:
                return bishopMoves(b, p);
            case ROOK:
                return rookMoves(b, p);
            case QUEEN:
                Moves = bishopMoves(b, p);
                Moves.addAll(rookMoves(b, p));
                return Moves;
        }
        return Moves;
    }

    public static ArrayList<Move> knightMoves(Board b, Piece p)
    {
        final ArrayList<Move> Moves = new ArrayList<Move>();

        Move newMove;

        newMove = Move.genMove(b, p, -2, 1);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = Move.genMove(b, p, -2, -1);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = Move.genMove(b, p, -1, 2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = Move.genMove(b, p, 1, 2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = Move.genMove(b, p, -1, -2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = Move.genMove(b, p, 1, -2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = Move.genMove(b, p, 2, 1);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = Move.genMove(b, p, 2, -1);
        if (newMove != null)
        {
            Moves.add(newMove);
        }

        return Moves;
    }

    public static ArrayList<Move> bishopMoves(Board b, Piece p)
    {
        final ArrayList<Move> Moves = new ArrayList<Move>();
        int d; // Displacement

        Move newMove = null;

        //System.out.println("h");
        //System.exit(0);
        // Move up and right
        for (d = 1; ((p.getxPos() + d <= 7) && (p.getyPos() + d <= 7)); d++)
        {
            newMove = Move.genMove(b, p, d, d);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        // Move up and left
        for (d = 1; ((p.getxPos() - d >= 0) && (p.getyPos() + d <= 7)); d++)
        {
            newMove = Move.genMove(b, p, -d, d);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        // Move down and left
        for (d = 1; ((p.getxPos() - d >= 0) && (p.getyPos() - d >= 0)); d++)
        {
            newMove = Move.genMove(b, p, -d, -d);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        // Move down and right
        for (d = 1; ((p.getxPos() + d <= 7) && (p.getyPos() - d >= 0)); d++)
        {
            newMove = Move.genMove(b, p, d, -d);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        return Moves;
    }

    public static ArrayList<Move> rookMoves(Board b, Piece p)
    {
        final ArrayList<Move> Moves = new ArrayList<Move>();
        int d; // Displacement

        Move newMove;

        //System.out.println("h");
        //System.exit(0);
        // Move up
        for (d = 1; (p.getyPos() + d <= 7); d++)
        {
            newMove = Move.genMove(b, p, 0, d);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        // Move down
        for (d = 1; (p.getyPos() - d >= 0); d++)
        {
            newMove = Move.genMove(b, p, 0, -d);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        // Move left
        for (d = 1; (p.getxPos() - d >= 0); d++)
        {
            newMove = Move.genMove(b, p, -d, 0);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        // Move right 
        for (d = 1; (p.getxPos() + d <= 7); d++)
        {
            newMove = Move.genMove(b, p, d, 0);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
        }

        return Moves;
    }

    public static ArrayList<Move> generateAllMoves(Board b)
    {
        final ArrayList<Move> moves = new ArrayList<Move>();
        ArrayList<Move> r = null;
        Piece p;

        if (b.isDraw() || b.isMate())
        {
            return moves; // I.e. no moves to generate...
        }
        for (int i = 0; i < b.getNumberOfPieces(); i++)
        {
            p = b.getPiece(i);
            r = generateMoves(b, p);
            if (r != null && !r.isEmpty())
            {
                moves.addAll(r);
            }
        }

        return moves;
    }
}