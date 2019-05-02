/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.board;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.misc.Strings;

public class BoardParser
{    
    /**
     * Partial implementation which covers what we need for testing
     * @param fen - FEN position string to parse
     * @return A board with the position
     */
    public static Board parseFEN(String fen)
    {
        Board board = new Board();
        
        int x = 0;
        int y = 7;
        int i;
        int parsingPartNo;
        char c;
        fen = Strings.trimWhiteSpace(fen.trim());
        
        // Parsing part no. 1
        parsingPartNo = 1;
      
        // Traverse input string
        for (i = 0; i < fen.length(); i++)
        {
            c = fen.charAt(i);
            assert x <= 8 && y >= 0 : "Error (Not a correct FEN board)";

            if (parsingPartNo == 1)
            {
                if (c == ' ')
                {
                    parsingPartNo = 2;
                    continue;
                }

                if (c >= '1' && c <= '8')
                {
                    x = x + (int) (c - '0');
                } else if (c == '/')
                {
                    y--;
                    x = 0;
                    continue;
                } 
                else 
                {                    
                    board.insertPiece(new Piece(new Location(x, y), c));
                    x++;
                }
            }

            if (parsingPartNo == 2)
            {
                switch (c)
                {
                    case 'w':
                        board.setWhiteToMove();
                        break;
                    case 'b':
                        board.setBlackToMove();
                        break;
                    case ' ':
                        parsingPartNo = 3;
                        continue;
                }
            }

            if (parsingPartNo == 3)
            {
                switch (c)
                {
                    case 'K':                        
                        board.setCanCastleShort(true, Color.WHITE);
                        break;
                    case 'Q':
                        board.setCanCastleLong(true, Color.WHITE);
                        break;
                    case 'k':
                        board.setCanCastleShort(true, Color.BLACK);
                        break;
                    case 'q':
                        board.setCanCastleLong(true, Color.BLACK);
                        break;
                    case ' ':
                        parsingPartNo = 4;                        
                }
            }            
        }
        
        return board;
    }

    public static String exportPosition(Board board) 
    {
        String result = "";
        int countingFreeSpaces = -1;
        
        for (int y = 7; y >= 0; y--)
        {
            for (int x = 0; x < 8; x++)
            {
                Piece piece = board.getPiece(new Location(x, y));
                if (piece == null)
                {
                    if (countingFreeSpaces == -1)
                    {
                        countingFreeSpaces = 1;
                    }
                    else
                    {
                        countingFreeSpaces++;
                    }
                }
                else
                {
                    if (countingFreeSpaces > 0)
                    {
                        result += "" + countingFreeSpaces;
                        countingFreeSpaces = -1;
                    }
                    
                    result += piece.getPieceType().getPieceLetter(piece.getColor());
                }                
            }
            if (countingFreeSpaces > 0)
            {
                result += "" + countingFreeSpaces;
                countingFreeSpaces = -1;
            }
            if (y > 0) 
            {
                result += "/";
            }
        }
        
        result += " " + board.inMove().getColorString();
        return result;
    }

    static Board parseFromLetters(String str)
    {
        String[] strings = str.split(" ");
        Board board = new Board();
        
        for (String s : strings)
        {
            if (s.length() > 1)
            {
                Piece piece = Piece.fromPositionCode(s);
                board.insertPiece(piece);
            }
            else
            {
                if ("w".equals(s))
                {
                    board.setWhiteToMove();
                } 
                
                if ("b".equals(s))
                {
                    board.setBlackToMove();
                }                 
            }
        }
        
        return board;
    }
}
