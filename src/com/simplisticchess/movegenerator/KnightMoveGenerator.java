/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.movegenerator;

import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Piece;
import java.util.ArrayList;

public class KnightMoveGenerator
{
    public ArrayList<Move> generateMoves(Board b, Piece p)
    {
        final ArrayList<Move> Moves = new ArrayList<Move>();
        Move newMove;
        newMove = MoveGeneratorUtil.genMove(b, p, -2, 1);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, -2, -1);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, -1, 2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, 1, 2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, -1, -2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, 1, -2);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, 2, 1);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        newMove = MoveGeneratorUtil.genMove(b, p, 2, -1);
        if (newMove != null)
        {
            Moves.add(newMove);
        }
        return Moves;
    }
    
}
