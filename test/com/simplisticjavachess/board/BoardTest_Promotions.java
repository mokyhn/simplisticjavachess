package com.simplisticjavachess.board;


import com.simplisticjavachess.board.Board;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.position.Location;
import com.simplisticjavachess.position.Locations;
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
    
    @Test
    public void testWhiteCapturePromotions()
    {
        Board board = Boards.WHITE_READY_TO_CAPTURE_PROMOTE();
        
        for (int i = 0; i < 4; i++) 
        {
            MoveType moveType = null;
            PieceType pieceType = null;
            switch (i) 
            {
                case 0: 
                    moveType = MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP;
                    pieceType = PieceType.BISHOP;
                    break;
                case 1:
                    moveType = MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT;
                    pieceType = PieceType.KNIGHT;
                    break;
                case 2:
                    moveType = MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN;
                    pieceType = PieceType.QUEEN;
                    break;
                case 3:
                    moveType = MoveType.CAPTURE_AND_PROMOTE_TO_ROOK;
                    pieceType = PieceType.ROOK;
                    break;
            }
          
            Move[] moveList = 
                    new Move[] 
                    {
                        new Move(Locations.A7, Locations.B8, moveType, new Piece(Locations.B8, Color.BLACK, PieceType.KNIGHT), Color.WHITE),
                        new Move(Locations.B7, Locations.A8, moveType, new Piece(Locations.A8, Color.BLACK, PieceType.KNIGHT), Color.WHITE),
                        new Move(Locations.B7, Locations.C8, moveType, new Piece(Locations.C8, Color.BLACK, PieceType.KNIGHT), Color.WHITE),
                        new Move(Locations.C7, Locations.B8, moveType, new Piece(Locations.B8, Color.BLACK, PieceType.KNIGHT), Color.WHITE),
                        new Move(Locations.C7, Locations.D8, moveType, new Piece(Locations.D8, Color.BLACK, PieceType.KNIGHT), Color.WHITE),
                        new Move(Locations.D7, Locations.C8, moveType, new Piece(Locations.C8, Color.BLACK, PieceType.KNIGHT), Color.WHITE), 
                        new Move(Locations.D7, Locations.E8, moveType, new Piece(Locations.E8, Color.BLACK, PieceType.KNIGHT), Color.WHITE),
                        new Move(Locations.E7, Locations.D8, moveType, new Piece(Locations.D8, Color.BLACK, PieceType.KNIGHT), Color.WHITE),
                        new Move(Locations.E7, Locations.F8, moveType, new Piece(Locations.F8, Color.BLACK, PieceType.KNIGHT), Color.WHITE),
                        new Move(Locations.F7, Locations.E8, moveType, new Piece(Locations.E8, Color.BLACK, PieceType.KNIGHT), Color.WHITE), 
                        new Move(Locations.F7, Locations.G8, moveType, new Piece(Locations.G8, Color.BLACK, PieceType.KNIGHT), Color.WHITE),
                        new Move(Locations.G7, Locations.F8, moveType, new Piece(Locations.F8, Color.BLACK, PieceType.KNIGHT), Color.WHITE),
                        new Move(Locations.G7, Locations.H8, moveType, new Piece(Locations.H8, Color.BLACK, PieceType.KNIGHT), Color.WHITE),
                        new Move(Locations.H7, Locations.G8, moveType, new Piece(Locations.G8, Color.BLACK, PieceType.KNIGHT), Color.WHITE)
                    };
            
            for (Move move : moveList) { 
                System.out.println("Doing move " + move.toString());
                board.doMove(move);
                assertTrue(board.freeSquare(move.getFrom()));
                assertTrue(board.getPiece(move.getTo()).getPieceType().equals(pieceType));
                assertTrue(board.getPiece(move.getTo()).getColor() == Color.WHITE);
                board.undo();
                assertTrue(board.equals(Boards.WHITE_READY_TO_CAPTURE_PROMOTE()));

            }
        }
    }    

    @Test
    public void testBlackCapturePromotions()
    {
        Board board = Boards.BLACK_READY_TO_CAPTURE_PROMOTE();
        
        for (int i = 0; i < 4; i++) 
        {
            MoveType moveType = null;
            PieceType pieceType = null;
            switch (i) 
            {
                case 0: 
                    moveType = MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP;
                    pieceType = PieceType.BISHOP;
                    break;
                case 1:
                    moveType = MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT;
                    pieceType = PieceType.KNIGHT;
                    break;
                case 2:
                    moveType = MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN;
                    pieceType = PieceType.QUEEN;
                    break;
                case 3:
                    moveType = MoveType.CAPTURE_AND_PROMOTE_TO_ROOK;
                    pieceType = PieceType.ROOK;
                    break;
            }
          
            Move[] moveList = 
                    new Move[] 
                    {
                        new Move(Locations.A2, Locations.B1, moveType, new Piece(Locations.B1, Color.WHITE, PieceType.KNIGHT), Color.BLACK),
                        new Move(Locations.B2, Locations.A1, moveType, new Piece(Locations.A1, Color.WHITE, PieceType.KNIGHT), Color.BLACK),
                        new Move(Locations.B2, Locations.C1, moveType, new Piece(Locations.C1, Color.WHITE, PieceType.KNIGHT), Color.BLACK),
                        new Move(Locations.C2, Locations.B1, moveType, new Piece(Locations.B1, Color.WHITE, PieceType.KNIGHT), Color.BLACK),
                        new Move(Locations.C2, Locations.D1, moveType, new Piece(Locations.D1, Color.WHITE, PieceType.KNIGHT), Color.BLACK),
                        new Move(Locations.D2, Locations.C1, moveType, new Piece(Locations.C1, Color.WHITE, PieceType.KNIGHT), Color.BLACK), 
                        new Move(Locations.D2, Locations.E1, moveType, new Piece(Locations.E1, Color.WHITE, PieceType.KNIGHT), Color.BLACK),
                        new Move(Locations.E2, Locations.D1, moveType, new Piece(Locations.D1, Color.WHITE, PieceType.KNIGHT), Color.BLACK),
                        new Move(Locations.E2, Locations.F1, moveType, new Piece(Locations.F1, Color.WHITE, PieceType.KNIGHT), Color.BLACK),
                        new Move(Locations.F2, Locations.E1, moveType, new Piece(Locations.E1, Color.WHITE, PieceType.KNIGHT), Color.BLACK), 
                        new Move(Locations.F2, Locations.G1, moveType, new Piece(Locations.G1, Color.WHITE, PieceType.KNIGHT), Color.BLACK),
                        new Move(Locations.G2, Locations.F1, moveType, new Piece(Locations.F1, Color.WHITE, PieceType.KNIGHT), Color.BLACK),
                        new Move(Locations.G2, Locations.H1, moveType, new Piece(Locations.H1, Color.WHITE, PieceType.KNIGHT), Color.BLACK),
                        new Move(Locations.H2, Locations.G1, moveType, new Piece(Locations.G1, Color.WHITE, PieceType.KNIGHT), Color.BLACK)
                    };
            
            for (Move move : moveList) { 
                System.out.println("Doing move " + move.toString());
                board.doMove(move);
                assertTrue(board.freeSquare(move.getFrom()));
                assertTrue(board.getPiece(move.getTo()).getPieceType().equals(pieceType));
                assertTrue(board.getPiece(move.getTo()).getColor() == Color.BLACK);
                board.undo();
                assertTrue(board.equals(Boards.BLACK_READY_TO_CAPTURE_PROMOTE()));

            }
        }
    }    
}   