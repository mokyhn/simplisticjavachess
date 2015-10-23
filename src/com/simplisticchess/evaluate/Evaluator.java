package com.simplisticchess.evaluate;

/**
 * @author Morten Kühnrich
 */
import com.simplisticchess.piece.Piece;
import com.simplisticchess.board.Board;
import com.simplisticchess.piece.Color;

public final class Evaluator
{

    public static final int WHITE_IS_MATED = -2147480000;
    public static final int BLACK_IS_MATED = 2147480000;
    public static final int DRAW = 0;

    public static final int PAWNVALUE = 1;
    public static final int ROOKVALUE = 5;
    public static final int BISHOPVALUE = 3;
    public static final int KNIGHTVALUE = 3;
    public static final int QUEENVALUE = 9;

    public static int evaluate(Board b)
    {
        int result = 0;
        Piece p;
        boolean thereIsAWhiteKing = false;
        boolean thereIsABlackKing = false;

        for (int i = 0; i < b.getNumberOfPieces(); i++)
        {
            p = b.getPiece(i);
            switch (p.type)
            {
                case Piece.PAWN:
                    result = PAWNVALUE * p.color.getColor() + result;
                    break;
                case Piece.ROOK:
                    result = ROOKVALUE * p.color.getColor() + result;
                    break;
                case Piece.BISHOP:
                    result = BISHOPVALUE * p.color.getColor() + result;
                    break;
                case Piece.KNIGHT:
                    result = KNIGHTVALUE * p.color.getColor() + result;
                    break;
                case Piece.QUEEN:
                    result = QUEENVALUE * p.color.getColor() + result;
                    break;
                case Piece.KING:
                    if (p.color == Color.BLACK)
                    {
                        thereIsABlackKing = true;
                    }
                    if (p.color == Color.WHITE)
                    {
                        thereIsAWhiteKing = true;
                    }
                    break;
                default:
            }
        }

        assert (!(!thereIsABlackKing && !thereIsAWhiteKing));
        if (thereIsABlackKing == false)
        {
            result = BLACK_IS_MATED;
        }
        if (thereIsAWhiteKing == false)
        {
            result = WHITE_IS_MATED;
        }

        return result;
    }

}
