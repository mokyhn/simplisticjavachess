package com.simplisticjavachess.evaluator;

/**
 * @author Morten Kühnrich
 */
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.piece.Color;

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
        
        boolean thereIsAWhiteKing = false;
        boolean thereIsABlackKing = false;

        for (Piece p : b.getPieces())
        {
            switch (p.getPieceType())
            {
                case PAWN:
                    result = PAWNVALUE * p.getColor().getColor() + result;
                    break;
                case ROOK:
                    result = ROOKVALUE * p.getColor().getColor() + result;
                    break;
                case BISHOP:
                    result = BISHOPVALUE * p.getColor().getColor() + result;
                    break;
                case KNIGHT:
                    result = KNIGHTVALUE * p.getColor().getColor() + result;
                    break;
                case QUEEN:
                    result = QUEENVALUE * p.getColor().getColor() + result;
                    break;
                case KING:
                    if (p.getColor() == Color.BLACK)
                    {
                        thereIsABlackKing = true;
                    }
                    if (p.getColor() == Color.WHITE)
                    {
                        thereIsAWhiteKing = true;
                    }
                    break;
                default:
            }
        }

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
