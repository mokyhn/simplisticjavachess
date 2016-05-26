/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.board;

import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.PieceType;
import static com.simplisticchess.piece.PieceType.*;
import com.simplisticchess.position.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBoardGenerator
{
    Random random;
    
    public RandomBoardGenerator(long seed)
    {
        random = new Random(seed);
    }
    
    public Board getBoard()
    {
        Board board = new Board();
        
        insertPieces(board, Color.WHITE);
        insertPieces(board, Color.BLACK);
        
        
        if (random.nextBoolean()) 
        {
            board.setBlackToMove();
        }
        else
        {
            board.setWhiteToMove();
        }
        
        if (random.nextBoolean())
        {
            insertKing(board, Color.BLACK);
            insertKing(board, Color.WHITE);
        }
        else
        {
            insertKing(board, Color.WHITE);
            insertKing(board, Color.BLACK);
        }        
        
        return board;
    }
    
    private void insertPieces(Board board, Color color) 
    {
        int numberOfPawns    = random.nextInt(9);
        int numberOfOfficers = random.nextInt(9);
        
        List<Location> freeSquares;
        for (int i = 0; i < numberOfPawns; i++)
        {
             freeSquares = getFreeSquaresPawns(board);
             int n = freeSquares.size();
             int select = random.nextInt(n);
             board.insertPiece(new Piece(freeSquares.get(select), color, PieceType.PAWN));
        }
        
        for (int i = 0; i < numberOfOfficers; i++)
        {
             freeSquares = getFreeSquares(board);
             int n = freeSquares.size();
             int select = random.nextInt(n);
             int selectPiece = random.nextInt(4);
             PieceType pieceType = null;
             switch (selectPiece) 
             {
                 case 0: 
                     pieceType = BISHOP;
                     break;
                 case 1:
                     pieceType = KNIGHT;
                     break;
                 case 2:
                     pieceType = PieceType.ROOK;
                     break;
                 case 3:
                     pieceType = PieceType.QUEEN;
                     break;
             }
             board.insertPiece(new Piece(freeSquares.get(select), color, pieceType));        
        }
        
    }
    
    private List<Location> getFreeSquares(Board board)
    {
        List<Location> result = new ArrayList<Location>();
        
        for (int x = 0; x < 8; x++) 
        {
            for (int y = 0; y < 8; y++) 
            {
                Location location = new Location(x, y);
                if (board.freeSquare(location)) 
                {
                    result.add(location);
                }
            }
        }
        
        return result;
    }
    
    
    private List<Location> getFreeSquaresPawns(Board board)
    {
        List<Location> result = new ArrayList<Location>();
        
        for (int x = 0; x < 8; x++) 
        {
            for (int y = 1; y < 7; y++) 
            {
                Location location = new Location(x, y);
                if (board.freeSquare(location)) 
                {
                    result.add(location);
                }
            }
        }
        
        return result;
    }
    
    private void insertKing(Board board, Color color)
    {
        List<Location> locations = getFreeNonAttackedSquares(board, color);
        int select = random.nextInt(locations.size());
        board.insertPiece(new Piece(locations.get(select), color, KING));
        
    }
    
    
    private List<Location> getFreeNonAttackedSquares(Board board, Color color)
    {
        List<Location> test = getFreeSquares(board);
        List<Location> result = new ArrayList<Location>();
        
        for (Location location : test)
        {
            if (PositionInference.attacks(board.getPosition(), location, color) == null) 
            {
                result.add(location);
            }
        }
        
        return result;
    }
    
    public static void main(String args[])
    {
        RandomBoardGenerator randomBoardGenerator = new RandomBoardGenerator(37);
        
        int n = 5000;
        
        System.out.println("public static final String[] FENPositions = new String[] {");
        for (int i = 0; i < n; i++)
        {
            Board board = randomBoardGenerator.getBoard();
            System.out.print("\"" + FENUtils.exportPosition(board) + "\"");
            if (i < n-1) 
            {
                System.out.print(",");
            }
            System.out.println();
        }
        System.out.println("}");
        
    }
    
}
