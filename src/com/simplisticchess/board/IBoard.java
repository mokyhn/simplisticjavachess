/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.board;

import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.position.Location;

public interface IBoard
{

    boolean doMove(Move m);
    boolean undo();
    
    int getNumberOfPieces();
    Piece getPiece(int i);
    Piece getPiece(Location location);


    
}
