package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;
import java.util.ArrayList;

public class LineMoveGenerator {
    public static ArrayList<Move> generateMoves(Board b, Piece p, int stepX, int stepY)
    {
        final ArrayList<Move> Moves = new ArrayList<Move>();
        
        int x, y;
        
        x = p.getxPos();
        y = p.getyPos();
        
        int dx = 0;
        int dy = 0;
        
        while (x >= 0 && x <= 7 && y >= 0 && y <= 7)
        {
            dx += stepX;
            dy += stepY;
            
            x += stepX;
            y += stepY;

            Move newMove;
            newMove = MoveGeneratorUtil.genMove(b, p, dx, dy);
            if (newMove == null)
            {
                break; // The square was occupied by my own piece
            }

            Moves.add(newMove);
            if (newMove.aCapture())
            {
                break; // The square was occopied by an opponent square
            }
            
        }
        
        return Moves;
    }      
}
