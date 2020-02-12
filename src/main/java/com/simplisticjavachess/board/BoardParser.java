package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.misc.Strings;

import java.util.ArrayList;
import java.util.List;

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

        // Parse pieces
        String piecesString = sections[0];
        List<Piece> pieces = parseFENPieces(piecesString);

        // Parse who to move
        String inMoveString = sections[1];
        Color inMove = parseFENWhoToMove(inMoveString);

        Board board = new Board(inMove);
        board = board.insert(pieces);

        if (sections.length == 2)
        {
            return board;
        }

        // Parse castling options
        String castlingString = sections[2];
        board = parseFENCastling(board, castlingString);

        return board;
    }

    private static List<Piece> parseFENPieces(String piecesString) {
        List<Piece> pieces = new ArrayList<>();

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
                pieces.add(new Piece(new Location(x, y), c));
                x++;
            }
        }
        return pieces;
    }

    private static Board parseFENCastling(Board board, String castlingString) {
        for (char c : castlingString.toCharArray())
        {
            switch (c)
            {
                case '-':
                    board = board.setCanCastleLong(false, Color.WHITE);
                    board = board.setCanCastleShort(false, Color.WHITE);
                    board = board.setCanCastleLong(false, Color.BLACK);
                    board = board.setCanCastleShort(false, Color.BLACK);
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

    private static Color parseFENWhoToMove(String inMoveString) {
        inMoveString = inMoveString.toLowerCase();
        if ("w".equals(inMoveString))
        {
            return Color.WHITE;
        } else if ("b".equals(inMoveString))
        {
            return Color.BLACK;
        } else
        {
            throw new IllegalArgumentException("Who-to-move-color not specified correctly, use either w or b");
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

    static Board parseFromLetters(String str)
    {
        String[] strings = str.split(" ");

        List<Piece> pieces = new ArrayList<>();
        Color inMove = null;

        for (String s : strings)
        {
            if (s.length() > 1)
            {
                Piece piece = Piece.parse(s);
                pieces.add(piece);
            }
            else
            {
                if ("w".equals(s))
                {
                    inMove = Color.WHITE;
                } 
                
                if ("b".equals(s))
                {
                    inMove = Color.BLACK;
                }                 
            }
        }

        if (inMove == null)
        {
            throw new IllegalArgumentException("You must supply a color for who is to move, w or b");
        }

        Board board = new Board(inMove);
        board = board.insert(pieces);

        return board;
    }
}
