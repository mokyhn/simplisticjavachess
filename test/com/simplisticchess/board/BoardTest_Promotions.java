package com.simplisticchess.board;


import com.simplisticchess.move.Move;
import com.simplisticchess.move.MoveType;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Morten Kühnrich
 */
public class BoardTest_Promotions
{
    @Test
    public void testWhiteSimplePromotions()
    {
        Board board = Boards.WHITE_READY_TO_PROMOTE();
        
        for (int i = 0; i < 4; i++) 
        {
            MoveType moveType = null;
            PieceType pieceType = null;
            switch (i) 
            {
                case 0: 
                    moveType = MoveType.PROMOTE_TO_BISHOP;
                    pieceType = PieceType.BISHOP;
                    break;
                case 1:
                    moveType = MoveType.PROMOTE_TO_KNIGHT;
                    pieceType = PieceType.KNIGHT;
                    break;
                case 2:
                    moveType = MoveType.PROMOTE_TO_QUEEN;
                    pieceType = PieceType.QUEEN;
                    break;
                case 3:
                    moveType = MoveType.PROMOTE_TO_ROOK;
                    pieceType = PieceType.ROOK;
                    break;
            }

            for (int x = 0; x < 8; x++)
            {
                Location from = new Location(x, 6);
                Location to = new Location(x, 7);
                Move move = new Move(from, to, moveType, null, Color.WHITE);
                System.out.println("Doing move " + move.toString());
                board.doMove(move);
                assertTrue(board.freeSquare(from));
                assertTrue(board.getPiece(to).getPieceType().equals(pieceType));
                assertTrue(board.getPiece(to).getColor() == Color.WHITE);
                board.undo();
                assertTrue(board.equals(Boards.WHITE_READY_TO_PROMOTE()));

            }
        }
    }
    
    @Test
    public void testBlackSimplePromotions()
    {
        Board board = Boards.BLACK_READY_TO_PROMOTE();
        
        for (int i = 0; i < 4; i++) 
        {
            MoveType moveType = null;
            PieceType pieceType = null;
            switch (i) 
            {
                case 0: 
                    moveType = MoveType.PROMOTE_TO_BISHOP;
                    pieceType = PieceType.BISHOP;
                    break;
                case 1:
                    moveType = MoveType.PROMOTE_TO_KNIGHT;
                    pieceType = PieceType.KNIGHT;
                    break;
                case 2:
                    moveType = MoveType.PROMOTE_TO_QUEEN;
                    pieceType = PieceType.QUEEN;
                    break;
                case 3:
                    moveType = MoveType.PROMOTE_TO_ROOK;
                    pieceType = PieceType.ROOK;
                    break;
            }

            for (int x = 0; x < 8; x++)
            {
                Location from = new Location(x, 1);
                Location to = new Location(x, 0);
                Move move = new Move(from, to, moveType, null, Color.BLACK);
                System.out.println("Doing move " + move.toString());
                board.doMove(move);
                assertTrue(board.freeSquare(from));
                assertTrue(board.getPiece(to).getPieceType().equals(pieceType));
                assertTrue(board.getPiece(to).getColor() == Color.BLACK);
                board.undo();
                assertTrue(board.equals(Boards.BLACK_READY_TO_PROMOTE()));

            }
        }
    }
    
}   