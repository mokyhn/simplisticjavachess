/**
 *
 * @author Morten Kühnrich
 */

package com.simplisticchess.board;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.position.Location;

public class FENParser
{
    // Given a position in the FEN - notation.
    // Set up the board
    // TODO: This function is not robust enough. It may throw exceptions. sfen = 11 is an example
    
    /**
     * Partial implementation which covers what we need for testing
     */
    public static void setupFENboard(Board board, String sfen)
    {
        int x = 0;
        int y = 7;
        int i;
        int parsingPartNo;
        char c;
        final String fen = trimWhiteSpace(sfen.trim());
        
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

    private static String trimWhiteSpace(final String s)
    {
        String t = "";
        char c;
        boolean flag = false;

        for (int i = 0; i < s.length(); i++)
        {
            c = s.charAt(i);

            if (c == ' ' && !flag)
            {
                flag = true;
                t = t + ' ';
            }

            if (c != ' ')
            {
                flag = false;
                t = t + c;
            }
        }

        return t;
    }
    
}
