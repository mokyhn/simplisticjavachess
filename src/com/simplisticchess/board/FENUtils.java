/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.board;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.position.Location;
import com.simplisticjavachess.misc.Strings;

public class FENUtils
{
    // Given a position in the FEN - notation.
    // Set up the board
    // TODO: This function is not robust enough. It may throw exceptions. sfen = 11 is an example
    
    /**
     * Partial implementation which covers what we need for testing
     */
    public static void importPosition(Board board, String sfen)
    {
        int x = 0;
        int y = 7;
        int i;
        int parsingPartNo;
        char c;
        final String fen = Strings.trimWhiteSpace(sfen.trim());
        
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
                } else if (c >= 'b' && c <= 'r')
                {
                    board.insertPiece(new Piece(new Location(x, y), c));
                    x++;
                    continue;
                } else if (c >= 'B' && c <= 'R')
                {
                    board.insertPiece(new Piece(new Location(x, y), c));
                    x++;
                    continue;
                } else if (c == '/')
                {
                    y--;
                    x = 0;
                    continue;
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
}
