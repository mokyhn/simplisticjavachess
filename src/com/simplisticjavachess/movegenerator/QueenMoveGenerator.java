/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Piece;

import java.util.Iterator;


public class QueenMoveGenerator
{  
    public static Iterator<Move> getIterator(final Board b, final Piece p)
    {
        return IteratorUtils.compose(
                BishopMoveGenerator.getIterator(b, p), 
                RookMoveGenerator.iterator(b, p)
        );
    }
   
}
