package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.misc.Strings;

/**
 * @author Morten Kühnrich
 *
 * Forsyth–Edwards Notation (FEN) support
 */
public class BoardParser
{    
    /**
     * Partial implementation which covers what we need for testing
     * @param fen - FEN position string to parse
     * @return A board with the position
     */
    public static Board parseFEN(String fen)
    {
        fen = Strings.trimWhiteSpace(fen);

        // The FEN sections are divided by space
        String[] sections = fen.split(" ");

        Board board = new Board();

        // Parse pieces
        String piecesString = sections[0];
        board = parseFENPieces(board, piecesString);

        // Parse who to move
        String inMoveString = sections[1];
        board = parseFENWhoToMove(board, inMoveString);

        if (sections.length == 2)
        {
            return board;
        }

        // Parse castling options
        String castlingString = sections[2];
        board = parseFENCastling(board, castlingString);

        return board;
    }

    private static Board parseFENPieces(Board board, String piecesString) {
        int x = 0;
        int y = 7;
        for (char c : piecesString.toCharArray())
        {
            assert x <= 8 && y >= 0 : "Error (Not a correct FEN board)";
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
                board = board.insert(new Piece(new Location(x, y), c));
                x++;
            }
        }
        return board;
    }

    private static Board parseFENCastling(Board board, String castlingString) {
        for (char c : castlingString.toCharArray())
        {
            switch (c)
            {
                case 'K':
                    board = board.setCanCastleShort(true, Color.WHITE);
                    break;
                case 'Q':
                    board = board.setCanCastleLong(true, Color.WHITE);
                    break;
                case 'k':
                    board = board.setCanCastleShort(true, Color.BLACK);
                    break;
                case 'q':
                    board = board.setCanCastleLong(true, Color.BLACK);
                    break;
            }
        }
        return board;
    }

    private static Board parseFENWhoToMove(Board board, String inMoveString) {
        inMoveString = inMoveString.toLowerCase();
        if ("w".equals(inMoveString))
        {
            board = board.setWhiteToMove();
        } else if ("b".equals(inMoveString))
        {
            board = board.setBlackToMove();
        } else
        {
            throw new IllegalArgumentException("Who-to-move-color not specified correctly, use either w or b");
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
                Piece piece = Piece.parse(s);
                board = board.insert(piece);
            }
            else
            {
                if ("w".equals(s))
                {
                    board = board.setWhiteToMove();
                } 
                
                if ("b".equals(s))
                {
                    board = board.setBlackToMove();
                }                 
            }
        }
        
        return board;
    }
}
