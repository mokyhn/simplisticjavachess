/**
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.evaluation;


import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.piece.Piece;

public class IntegerEvaluator implements Evaluator {
    private static int WHITE_IS_MATED = -20000;
    private static int BLACK_IS_MATED = 20000;

    private static int PAWN_VALUE = 100;
    private static int KNIGHT_VALUE = 320;
    private static int BISHOP_VALUE = 330;
    private static int ROOK_VALUE = 500;
    private static int QUEEN_VALUE = 900;

    public IntegerEvaluator() {
        this.WHITE_IS_MATED = -10000;
        this.BLACK_IS_MATED = 10000;
        this.PAWN_VALUE = 1;
        this.ROOK_VALUE = 5;
        this.BISHOP_VALUE = 3;
        this.KNIGHT_VALUE = 3;
        this.QUEEN_VALUE = 9;
    }

    private IntegerEvaluator(int white_is_mated, int black_is_mated, int pawn_value,
                             int knight_value, int bishop_value, int rook_value, int queen_value) {
        this.WHITE_IS_MATED = white_is_mated;
        this.BLACK_IS_MATED = black_is_mated;
        this.PAWN_VALUE = pawn_value;
        this.KNIGHT_VALUE = knight_value;
        this.BISHOP_VALUE = bishop_value;
        this.ROOK_VALUE = rook_value;
        this.QUEEN_VALUE = queen_value;
    }

    public static IntegerEvaluator variant() {
        return new IntegerEvaluator(-20000, 20000, 100, 320, 330, 500, 900);
    }

    @Override
    public IntegerEvaluation evaluate(Position position) {
        int result = 0;

        for (Piece p : position.getPieces()) {
            int color = p.getColor().getColor();
            switch (p.getType()) {
                case PAWN:
                    result += PAWN_VALUE * color;
                    break;
                case ROOK:
                    result += ROOK_VALUE * color;
                    break;
                case BISHOP:
                    result += BISHOP_VALUE * color;
                    break;
                case KNIGHT:
                    result += KNIGHT_VALUE * color;
                    break;
                case QUEEN:
                    result += QUEEN_VALUE * color;
                    break;
                case KING:
                    /* No counting needed */
                    break;
                default:
            }
        }

        return IntegerEvaluation.of(result);
    }

    @Override
    public Evaluation getNone() {
        return new IntegerEvaluation();
    }

    @Override
    public Evaluation getEqual() {
        return new IntegerEvaluation(0);
    }

    @Override
    public Evaluation getWhiteIsMate() {
        return new IntegerEvaluation(WHITE_IS_MATED);
    }

    @Override
    public Evaluation getBlackIsMate() {
        return new IntegerEvaluation(BLACK_IS_MATED);
    }

}
