package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.position.Location;
import com.simplisticjavachess.position.Position;
import com.simplisticjavachess.position.Vector;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import java.util.ArrayList;
import java.util.Iterator;

public class LineMoveGenerator {
    private final Vector step;

    public LineMoveGenerator(Vector step)
    {
        this.step = step;
    }

    public Iterator<Move> generateMoves(Position position, Piece piece)
    {
        final ArrayList<Move> moves = new ArrayList<>();
        Location location = piece.getLocation();
        Vector delta = new Vector(0,0);

        while (location.isValid())
        {
            delta = delta.add(step);
            location = step.translocate(location);
            
            Move newMove = MoveGeneratorUtil.genMove(position, piece, delta);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }
            moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occupied by an opponent piece
            }
            
        }
        return moves.iterator();
    }      
}
