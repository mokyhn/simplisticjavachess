package com.simplisticchess.board;

/**
 * @author Morten Kühnrich
 */
import com.simplisticchess.game.History;
import com.simplisticchess.game.State;
import com.simplisticchess.evaluator.Evaluator;
import com.simplisticchess.move.Move;
import com.simplisticchess.move.MoveType;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;

public final class Board
{

    private Position position; // Current position of pieces
    private State state;             // State wrt. casteling, 
    // latest move and movenumber etc.
    private History history;           // Previus states.

    public Board()
    {
        position = new Position();
        state = new State();
        history = new History();
    }

    public Board(String fen)
    {
        this();
        setupFENboard(fen);
    }

    public Board(Board board)
    {
        this.position = new Position(board.position);
        this.state = new State(board.state);
        this.history = new History(board.history);
    }

    public int getNumberOfPieces()
    {
        return position.getNumberOfPieces();
    }

    public Color inMove()
    {
        return state.inMove;
    }

    public void setBlackToMove()
    {
        state.inMove = Color.BLACK;
    }

    public void setWhiteToMove()
    {
        state.inMove = Color.WHITE;
    }

    public void setDraw()
    {
        state.drawFlag = true;
    }

    public void setMate()
    {
        state.mateFlag = true;
    }

    public boolean isDraw()
    {
        return state.drawFlag;
    }

    public boolean isMate()
    {
        return state.mateFlag;
    }

    public Move getLastMove()
    {
        return history.peek().move;
    }

    public Piece getPiece(final int i)
    {
        return position.getPiece(i);
    }

    public void insertPiece(Piece p)
    {
        position.insertPiece(p);
    }

    public Piece removePiece(Location location)
    {
        return removePiece(location.getX(), location.getY());
    }
    
    public Piece removePiece(int x, int y)
    {
        return position.removePiece(x, y);
    }

    public boolean freeSquare(Location location)
    {
        return freeSquare(location.getX(), location.getY());
    }
    
    public boolean freeSquare(int x, int y)
    {
        return position.freeSquare(x, y);
    }

    public boolean canCastleShort()
    {
        return (state.inMove == Color.BLACK && state.blackCanCastleShort)
                || (state.inMove == Color.WHITE && state.whiteCanCastleShort);

    }

    public boolean canCastleLong()
    {
        return (state.inMove == Color.BLACK && state.blackCanCastleLong)
                || (state.inMove == Color.WHITE && state.whiteCanCastleLong);

    }

    /**
     *
     * @param x
     * @param y
     * @return true if the side not in move, in board b attacks square (x, y)
     * and otherwise false
     */
    public boolean attacks(int x, int y)
    {
        return position.attacks(x, y, state.inMove);
    }

    /**
     *
     * @param x
     * @param y
     * @param sideToMove
     * @return true if the side not in move, in board b attacks square (x, y)
     * and otherwise false
     */
    public boolean attacks(int x, int y, Color sideToMove)
    {
        return position.attacks(x, y, sideToMove);
    }

    public String getBitboardString()
    {
        return ((BitBoard) position.getBitBoard()).toString();
    }

    public Boolean drawBy50MoveRule()
    {
        return state.halfMoveClock >= 50;
    }

    /**
     *
     * @param color
     * @return Is player with color color in check by opponent?
     */
    public Boolean isInCheck(Color color)
    {
        Boolean res = false;
        Piece p;
        int kingx = -1,
                kingy = -1;

        for (int i = 0; i < getNumberOfPieces(); i++)
        {
            p = getPiece(i);
            if (p.getPieceType() == PieceType.KING && p.getColor() == color)
            {
                kingx = p.getxPos();
                kingy = p.getyPos();
                break;
            }
        }
        if (kingx != -1 && position.attacks(kingx, kingy, color))
        {
            res = true;
        }
        return res;
    }

    public Boolean drawBy3RepetionsRule()
    {
        State h;
        int k = 0;

        for (int i = state.halfMovesIndex3PosRepition; i < history.size(); i++)
        {
            h = history.get(i);
            if (((BitBoard) position.getBitBoard()).equals((BitBoard) h.bbposition))
            {
                k++;
            }
        }

        return k >= 3;
    }

    public Piece getPiece(Location location) 
    {
        return getPiece(location.getX(), location.getY());
    }
    
    // Find a piece at a certain location
    public Piece getPiece(int x, int y)
    {
        final Piece p = position.getPiece(x, y);

        if (p != null)
        {
            assert p.getxPos() == x && p.getyPos() == y;
        }
        return p;
    }

    // TODO: 2013, Should we check for draw here? 
    // A move might lead to a draw state.
    // The draw flag of the board might be set accordingly.
    public boolean performMove(Move m)
    {
        Piece p;

        p = getPiece(m.getFromX(), m.getFromY());

        state.moveNumber++;

        // Put the move m on the stack
        state.move = m;
        history.add(new State(state));

        // Used to determine the 50-move rule, three times repition
        if (p.getPieceType() == PieceType.PAWN)
        {
            state.halfMoveClock = 0;
            state.halfMovesIndex3PosRepition = state.moveNumber;
        } else
        {
            state.halfMoveClock++;
        }

        // Moving a rook can disallow castling in the future
        if (p.getPieceType() == PieceType.ROOK)
        {
            if (m.getWhoMoves() == Color.BLACK)
            {
                if (m.getFromX() == 0 && state.blackCanCastleLong)
                {
                    state.blackCanCastleLong = false;
                }
                if (m.getFromX() == 7 && state.blackCanCastleShort)
                {
                    state.blackCanCastleShort = false;
                }
            } else
            {
                if (m.getFromX() == 0 && state.whiteCanCastleLong)
                {
                    state.whiteCanCastleLong = false;
                }
                if (m.getFromX() == 7 && state.whiteCanCastleShort)
                {
                    state.whiteCanCastleShort = false;
                }
            }
        }

        // Moving the king will disallow castling in the future
        if (p.getPieceType() == PieceType.KING)
        {
            if (m.getWhoMoves() == Color.BLACK)
            {
                state.blackCanCastleShort = false;
                state.blackCanCastleLong = false;
            }

            if (m.getWhoMoves() == Color.WHITE)
            {
                state.whiteCanCastleShort = false;
                state.whiteCanCastleLong = false;
            }
        }

        if (m.aSimplePromotion())
        {
            insertPiece(new Piece(m.getToX(), m.getToY(), m.getWhoMoves(), m.promotionTo()));
            removePiece(m.getFromX(), m.getFromY());
        }

        if (m.aCapturePromotion())
        {
            if (getPiece(m.getToX(), m.getToY()).getPieceType() == PieceType.ROOK)
            {
                if (m.getWhoMoves() == Color.WHITE && m.getToY() == 7)
                {
                    if (m.getToX() == 0 && state.blackCanCastleLong)
                    {
                        state.blackCanCastleLong = false;
                    }
                    if (m.getToX() == 7 && state.blackCanCastleShort)
                    {
                        state.blackCanCastleShort = false;
                    }
                }
                if (m.getWhoMoves() == Color.BLACK && m.getToY() == 0)
                {
                    if (m.getToX() == 0 && state.whiteCanCastleLong)
                    {
                        state.whiteCanCastleLong = false;
                    }
                    if (m.getToX() == 7 && state.whiteCanCastleShort)
                    {
                        state.whiteCanCastleShort = false;
                    }
                }
            }

            removePiece(m.getToX(), m.getToY());
            removePiece(m.getFromX(), m.getFromY());
            insertPiece(new Piece(m.getToX(), m.getToY(), m.getWhoMoves(), m.promotionTo()));
        }

        switch (m.getMoveType())
        {
            case NORMALMOVE:
                position.movePiece(m.getFromX(), m.getFromY(), m.getToX(), m.getToY());
                break;

            case CAPTURE_ENPASSANT:
                position.movePiece(m.getFromX(), m.getFromY(), m.getToX(), m.getToY());
                removePiece(m.getToX(), m.getFromY());
                break;

            case CASTLE_SHORT:
                // Move the king first
                position.movePiece(m.getFromX(), m.getFromY(), m.getToX(), m.getToY());
                // Then the rook
                position.movePiece(7, m.getFromY(), 5, m.getFromY());
                break;

            case CASTLE_LONG:
                // Move the king first
                position.movePiece(m.getFromX(), m.getFromY(), m.getToX(), m.getToY());
                // Then the rook
                position.movePiece(0, m.getFromY(), 3, m.getFromY());
                break;

            case CAPTURE:
                // Capturing a rook may affect casteling opputunities!
                if (getPiece(m.getToX(), m.getToY()).getPieceType() == PieceType.ROOK)
                {
                    if (m.getWhoMoves() == Color.WHITE && m.getToY() == 7)
                    {
                        if (m.getToX() == 0 && state.blackCanCastleLong)
                        {
                            state.blackCanCastleLong = false;
                        }
                        if (m.getToX() == 7 && state.blackCanCastleShort)
                        {
                            state.blackCanCastleShort = false;
                        }
                    }
                    if (m.getWhoMoves() == Color.BLACK && m.getToY() == 0)
                    {
                        if (m.getToX() == 0 && state.whiteCanCastleLong)
                        {
                            state.whiteCanCastleLong = false;
                        }
                        if (m.getToX() == 7 && state.whiteCanCastleShort)
                        {
                            state.whiteCanCastleShort = false;
                        }
                    }
                }
                // Do the capture
                removePiece(m.getToX(), m.getToY());
                position.movePiece(m.getFromX(), m.getFromY(), m.getToX(), m.getToY());
                break;
        }

        // Swap the move color
        state.inMove = state.inMove.flip();

        boolean legalityOfMove = true;

        // The player that did the move is in check
        // his or her move is hence not legal
        if (isInCheck(state.inMove.flip()))
        {
            legalityOfMove = false;
            this.undo();
        }

        return legalityOfMove;
    }

    public boolean undo()
    {
        Color color = null;
        Move m;

        state.moveNumber--;

        try
        {
            state = history.pop();
            m = state.move;

            if (m.aSimplePromotion())
            {
                insertPiece(new Piece(m.getFromX(), m.getFromY(), m.getWhoMoves(), PieceType.PAWN));
                removePiece(m.getToX(), m.getToY());
            }

            if (m.aCapturePromotion())
            {
                removePiece(m.getToX(), m.getToY());
                insertPiece(new Piece(m.getToX(), m.getToY(), m.getWhoMoves().flip(), m.getCapturedPiece()));
                insertPiece(new Piece(m.getFromX(), m.getFromY(), m.getWhoMoves(), PieceType.PAWN));
                return true;
            }

            switch (m.getMoveType())
            {
                case NORMALMOVE:
                    position.movePiece(m.getToX(), m.getToY(), m.getFromX(), m.getFromY());
                    break;

                case CAPTURE_ENPASSANT:
                    if (m.getWhoMoves() == Color.WHITE)
                    {
                        color = Color.BLACK;
                    }
                    if (m.getWhoMoves() == Color.BLACK)
                    {
                        color = Color.WHITE;
                    }
                    insertPiece(new Piece(m.getToX(), m.getFromY(), color, PieceType.PAWN));
                    position.movePiece(m.getToX(), m.getToY(), m.getFromX(), m.getFromY());
                    break;

                case CASTLE_SHORT:
                    // Move the king back
                    position.movePiece(m.getToX(), m.getToY(), m.getFromX(), m.getFromY());
                    // Then the rook
                    position.movePiece(5, m.getFromY(), 7, m.getFromY());
                    break;

                case CASTLE_LONG:
                    // Move the king back
                    position.movePiece(m.getToX(), m.getToY(), m.getFromX(), m.getFromY());
                    // Then the rook
                    position.movePiece(3, m.getFromY(), 0, m.getFromY());
                    break;

                case CAPTURE:
                    position.movePiece(m.getToX(), m.getToY(), m.getFromX(), m.getFromY());
                    insertPiece(new Piece(m.getToX(), m.getToY(), m.getWhoMoves().flip(), m.getCapturedPiece()));
                    break;
            }
            return true;
        } catch (java.util.EmptyStackException e)
        {
            return false;
        }
    }

    // Given a position in the FEN - notation.
    // Set up the board
    // TODO: This function is not robust enough. It may throw exceptions. sfen = 11 is an example
    private void setupFENboard(String sfen)
    {
        int x = 0;
        int y = 7;
        int i;
        int parsingPartNo;
        char c;
        final String fen = trimWhiteSpace(sfen.trim());
        String num1 = "";
        String num2 = "";

        state.whiteCanCastleShort = false;
        state.whiteCanCastleLong = false;
        state.blackCanCastleShort = false;
        state.blackCanCastleLong = false;

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
                    insertPiece(new Piece(x, y, c));
                    x++;
                    continue;
                } else if (c >= 'B' && c <= 'R')
                {
                    insertPiece(new Piece(x, y, c));
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
                        state.inMove = Color.WHITE;
                        break;
                    case 'b':
                        state.inMove = Color.BLACK;
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
                        state.whiteCanCastleShort = true;
                        break;
                    case 'Q':
                        state.whiteCanCastleLong = true;
                        break;
                    case 'k':
                        state.blackCanCastleShort = true;
                        break;
                    case 'q':
                        state.blackCanCastleLong = true;
                        break;
                    case ' ':
                        parsingPartNo = 4;
                        continue;
                }
            }

            if (parsingPartNo == 4)
            {
                if (c == ' ')
                {
                    parsingPartNo = 5;
                    continue;
                }

                if (c == '-')
                {
                    continue;
                }

                if (c != ' ')
                {
                    final int xPawn = (int) (c - 'a');
                    final int yPawn = (int) (fen.charAt(i + 1) - '1');
                    assert xPawn >= 0 && xPawn <= 7;
                    assert yPawn >= 0 && yPawn <= 7;
                    final Piece p = getPiece(xPawn, yPawn - state.inMove.getColor());
                    if (p != null && p.getPieceType() == PieceType.PAWN)
                    {
                        state.move = new Move(xPawn, yPawn + state.inMove.getColor(), 
                                              xPawn, yPawn - state.inMove.getColor(), 
                                MoveType.NORMALMOVE, null, state.inMove);
                        history.add(state);
                    }

                    parsingPartNo = 5;
                    i = i + 2;

                }

                if (fen.charAt(i) == ' ')
                {
                    parsingPartNo = 5;
                    continue;
                }
            }

            if (parsingPartNo == 5)
            {
                if (c == ' ')
                {
                    parsingPartNo = 6;
                    continue;
                }

                if (c != ' ')
                {
                    num1 = num1 + c;
                }
            }

            if (parsingPartNo == 6)
            {
                if (c == ' ')
                { // end of story :)
                    parsingPartNo = 7;
                    continue;
                }

                if (c != ' ')
                {
                    num2 = num2 + c;
                }
            }

        }

        state.halfMoveClock = Integer.parseInt(num1);
        state.moveNumber = 2 * Integer.parseInt(num2) - 2;
        if (state.moveNumber != 0 && state.inMove == Color.BLACK)
        {
            state.moveNumber--;
        }

    }

    private String trimWhiteSpace(final String s)
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

    /**
     * Returns the board as ASCII art and game other information
     * @return An ASCII representation of the board
     */
    public String getASCIIBoard()
    {
    
        String s = position.toString();

        if (inMove() == Color.WHITE)
        {
            s = s + "  White to move\n";
        } else
        {
            s = s + "  Black to move\n";
        }

        s = s + state.toString();

        if (!history.isEmpty())
        {
            if (state.inMove.flip() == Color.WHITE)
            {
                s = s + "Last move " + (state.moveNumber + 1) / 2 + "." + history.peek().move.toString() + "\n";
            } else
            {
                s = s + "Last move " + (state.moveNumber + 1) / 2 + "...." + history.peek().move.toString() + "\n";
            }
        }

        s = s + "Immediate evaluation: " + Evaluator.evaluate(this) + "\n";

        s = s + "Move history: " + history.toString();

        return s;
    }

}
