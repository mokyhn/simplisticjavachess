package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.board.Location;
import com.simplisticjavachess.board.Vector;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import java.util.ArrayList;

public class LineMoveGenerator {
    public static ArrayList<Move> generateMoves(Board board, Piece piece, Vector step)
    {
        final ArrayList<Move> Moves = new ArrayList<>();
        
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

            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent piece
            }
            
        }
        
        return Moves;
    }      
}
