package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.Location;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import java.util.ArrayList;
import java.util.Iterator;

public class LineMoveGenerator implements IMoveGenerator {
    Vector step;

    public LineMoveGenerator(Vector step)
    {
        this.step = step;
    }

    // TODO: The following can be refined so that not all moves are generated at once
    public Iterator<Move> generateMoves(Board board, Piece piece)
    {
        final ArrayList<Move> moves = new ArrayList<>();
        Location location = piece.getLocation();
        Vector d = new Vector(0,0);
        
        while (location.isValid())
        {
            d = d.add(step);
            
            location = step.translocate(location);
            
            Move newMove;
            newMove = MoveGeneratorUtil.genMove(board, piece, d);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }

            moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent piece
            }
            
        }
        return moves.iterator();
    }      
}
