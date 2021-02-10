/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.engine;

import com.simplisticjavachess.position.Mover;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.evaluation.Evaluator;
import com.simplisticjavachess.movegenerator.MoveGenerator;

import java.util.logging.Logger;

public class RandomEngine implements Engine
{ // Old experiment
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    @Override
    public SearchResult search(Position b, Mover mover, MoveGenerator moveGenerator, Evaluator evaluator, int depth) {
        if (depth % 2 != 0) {
            LOGGER.warning("Do not use an uneven search depth as one player will be favored.");
        }

        return null;
    }/*
    MoveGenerator moveGenerator = new MoveGenerator();

    Board analyzeBoard;
    Move strongestMove;

    @Override
    public final SearchResult search(Board board, int plyDepth)
    {
        this.analyzeBoard = new Board(board);
        int evaluation = randomSearch();
        return new SearchResult(new MoveSequence(strongestMove), Evaluation.of(evaluation));
    }
    
    private int randomSearch()
    {
        Iterator<Move> movesTmp = new MoveGenerator().generateMoves(analyzeBoard);
        ArrayList<Move> moves = new ArrayList<>();
        
        while (movesTmp.hasNext()) 
        {
            moves.add(movesTmp.next());
        }
        
        int n = moves.size();

        if (n == 0)
        {
            return 0;
        }

        if (analyzeBoard.isDraw() || analyzeBoard.isMate())
        {
            System.out.println("Game over...");
            System.exit(0);
        }

        final Color whoMoves = analyzeBoard.inMove();
        boolean legal;

        boolean retry = true;

        while (retry && n > 0)
        {
            double r = Math.random();
            Move theMove = moves.get((int) Math.ceil((n - 1) * r));
            legal = analyzeBoard.doMove(theMove);

            if (legal)
            {
                retry = false;
                strongestMove = theMove;
                analyzeBoard.undo();
            } 
            else
            {
                analyzeBoard.undo();
                moves.remove(theMove);
                n = moves.size();
                System.out.println("Neglected: " + theMove.toString());
            }
        }

        if (retry)
        {
            if (analyzeBoard.isInCheck(whoMoves))
            {
                analyzeBoard.setGameResult(GameResult.MATE);
                if (whoMoves == Color.WHITE)
                {
                    return Evaluator.WHITE_IS_MATED;
                } else
                {
                    return Evaluator.BLACK_IS_MATED;
                }
            } else
            {
                analyzeBoard.setGameResult(GameResult.STALE_MATE);
                return 0;
            }
        }

        return 0;
    }

    public Move getStrongestMove()
    {
        return strongestMove;
    }
*/
}
