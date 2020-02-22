/**
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.evaluation;


import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.piece.Color;

public class IntegerEvaluator implements Evaluator
{
    public static final int WHITE_IS_MATED = -2147480000;
    public static final int BLACK_IS_MATED = 2147480000;

    private static final int PAWN_VALUE = 1;
    private static final int ROOK_VALUE = 5;
    private static final int BISHOP_VALUE = 3;
    private static final int KNIGHT_VALUE = 3;
    private static final int QUEEN_VALUE = 9;

    @Override
    public IntegerEvaluation evaluate(Board b)
    {
        int result = 0;
        
        boolean thereIsAWhiteKing = false;
        boolean thereIsABlackKing = false;

        for (Piece p : b.getPieces())
        {
            switch (p.getPieceType())
            {
                case PAWN:
                    result = PAWN_VALUE * p.getColor().getColor() + result;
                    break;
                case ROOK:
                    result = ROOK_VALUE * p.getColor().getColor() + result;
                    break;
                case BISHOP:
                    result = BISHOP_VALUE * p.getColor().getColor() + result;
                    break;
                case KNIGHT:
                    result = KNIGHT_VALUE * p.getColor().getColor() + result;
                    break;
                case QUEEN:
                    result = QUEEN_VALUE * p.getColor().getColor() + result;
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

        if (!thereIsABlackKing)
        {
            result = BLACK_IS_MATED;
        }
        if (!thereIsAWhiteKing)
        {
            result = WHITE_IS_MATED;
        }

        return IntegerEvaluation.of(result);
    }

}
