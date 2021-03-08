/**
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.evaluation;


import com.simplisticjavachess.Immutable;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.movegenerator.MainMoveGenerator;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.position.Position;

import java.util.Iterator;

@Immutable
public final class ComplexEvaluator implements Evaluator {
    private static final int WHITE_IS_MATED = -20000;
    private static final int BLACK_IS_MATED = 20000;

    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 20000;


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
                    result += KING_VALUE * color;
                    break;
                default:
            }
        }


        int numberOfMovesPlayerA = 0;
        Iterator<Move> moves = new MainMoveGenerator().generateMoves(position.setInMove(Color.WHITE));
        while (moves.hasNext()) {
            numberOfMovesPlayerA++;
            moves.next();
        }

        int numberOfMovesPlayerB = 0;
        moves = new MainMoveGenerator().generateMoves(position.setInMove(Color.BLACK));
        while (moves.hasNext()) {
            numberOfMovesPlayerB++;
            moves.next();
        }

        int mobility = numberOfMovesPlayerA - numberOfMovesPlayerB;
        result = result + mobility;

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
