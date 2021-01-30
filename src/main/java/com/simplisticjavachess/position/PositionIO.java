package com.simplisticjavachess.position;

import com.simplisticjavachess.game.CastlingState;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.misc.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.simplisticjavachess.piece.Color.BLACK;
import static com.simplisticjavachess.piece.Color.WHITE;

/**
 * @author Morten Kühnrich
 *
 * Import and export functionality for chess positions.
 */
public class PositionIO
{    
    /**
     * @param fen - FEN position string to parse
     * @return A board with the position
     */
    public static Position FEN(String fen)
    {
        fen = Strings.trimWhiteSpace(fen);

        // The FEN sections are divided by space
        String[] sections = fen.split(" ");

        ///////////////// PART 1 /////////////////
        // Parse pieces
        String piecesString = sections[0];
        List<Piece> pieces = parseFENPieces(piecesString);

        ///////////////// PART 2 /////////////////
        // Parse who to move
        String inMoveString = sections[1];
        Color inMove = parseFENWhoToMove(inMoveString);

        if (sections.length == 2)
        {
            return new Position(inMove, CastlingState.NOBODY_CAN_CASTLE, pieces, Optional.empty(), new HalfMoveClock(), new HalfMoveClock());
        }

        ///////////////// PART 3 /////////////////
        // Parse castling options
        String castlingString = sections[2];
        CastlingState castlingState = parseFENCastling(castlingString);

        ///////////////// PART 4 /////////////////
        // Parse en passant field
        String enpassant = sections[3];
        Optional<Move> move = parseEnpassant(enpassant);

        ///////////////// PART 5 /////////////////
        // Parse fifty move draw rule clock
        // Half moves since last capture or pawn advance
        String fiftymoveDrawClock = sections[4];
        HalfMoveClock fiftyMoveDrawClock = HalfMoveClock.fromString(fiftymoveDrawClock);

        ///////////////// PART 6 /////////////////
        // Parse current move number
        // Half moves in total of the game
        String moveClock = sections[5];
        HalfMoveClock gameClock = HalfMoveClock.fromString(moveClock);

        Position position = new Position(inMove, castlingState, pieces, move, fiftyMoveDrawClock, gameClock);

        validate(position);
        return position;
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

    private static CastlingState parseFENCastling(String castlingString) {
        CastlingState castlingState = CastlingState.NOBODY_CAN_CASTLE;

        for (char c : castlingString.toCharArray())
        {
            switch (c)
            {
                case '-':
                    castlingState = CastlingState.NOBODY_CAN_CASTLE;
                case 'K':
                    castlingState = castlingState.setCanCastleShort(WHITE, true);
                    break;
                case 'Q':
                    castlingState = castlingState.setCanCastleLong(WHITE, true);
                    break;
                case 'k':
                    castlingState = castlingState.setCanCastleShort(BLACK, true);
                    break;
                case 'q':
                    castlingState = castlingState.setCanCastleLong(BLACK, true);
                    break;
            }
        }
        return castlingState;
    }

    private static Color parseFENWhoToMove(String inMoveString) {
        inMoveString = inMoveString.toLowerCase();
        if ("w".equals(inMoveString))
        {
            return WHITE;
        } else if ("b".equals(inMoveString))
        {
            return BLACK;
        } else
        {
            throw new IllegalArgumentException("Who-to-move-color not specified correctly, use either w or b");
        }
    }

    private static Optional<Move> parseEnpassant(String enpassant)
    {
        if ("-".equals(enpassant))
        {
            return Optional.empty();
        }
        else
        {
            Location enpassantfield = Location.parse(enpassant);
            if (enpassantfield.y == 2)
            {
                Location from = new Location(enpassantfield.getX(), enpassantfield.getY()-1);
                Location to = new Location(enpassantfield.getX(), enpassantfield.getY()+1);
                return Optional.of(new Move(from, to, MoveType.NORMALMOVE, null, WHITE));
            }
            else
            {
                Location from = new Location(enpassantfield.getX(), enpassantfield.getY()+1);
                Location to = new Location(enpassantfield.getX(), enpassantfield.getY()-1);
                return Optional.of(new Move(from, to, MoveType.NORMALMOVE, null, BLACK));
            }
        }
    }

    public static String exportPosition(Position position)
    {
        String result = "";
        int countingFreeSpaces = -1;

        for (int y = 7; y >= 0; y--)
        {
            for (int x = 0; x < 8; x++)
            {
                Piece piece = position.getPiece(new Location(x, y));
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

        result += " " + position.inMove().getColorString();
        return result;
    }

    /**
     * Big letters means white
     * Small letters means black
     * @param str
     * @return
     */
    public static Position algebraic(String str)
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
                    inMove = WHITE;
                } 
                
                if ("b".equals(s))
                {
                    inMove = BLACK;
                }                 
            }
        }

        if (inMove == null)
        {
            throw new IllegalArgumentException("You must supply a color for who is to move, w or b");
        }

        Position position = new Position(inMove, CastlingState.NOBODY_CAN_CASTLE, pieces, Optional.empty(), new HalfMoveClock(), new HalfMoveClock());

        return position;
    }


    private static void validate(Position position)
    {
        int numberOfWhiteKings = 0;
        int numberOfBlackKings = 0;

        for (Piece p : position.getPieces())
        {
            if (p.getPieceType().isKing() && p.getColor().isWhite())
            {
                numberOfWhiteKings++;
            }
            if (p.getPieceType().isKing() && p.getColor().isBlack())
            {
                numberOfBlackKings++;
            }
        }
        if (numberOfBlackKings != 1 || numberOfWhiteKings != 1)
        {
            throw new IllegalStateException("There has to be exactly one black and white king.");
        }
    }

}
