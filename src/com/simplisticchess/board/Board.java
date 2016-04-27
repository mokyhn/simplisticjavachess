/**
 * @author Morten Kühnrich
 */

package com.simplisticchess.board;


import com.simplisticchess.game.History;
import com.simplisticchess.game.State;
import com.simplisticchess.evaluator.Evaluator;
import com.simplisticchess.move.Move;
import com.simplisticchess.move.MoveType;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;

public class Board
{

    private Position position;
    private State state;
    private History history;

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

    public Piece getPiece(int i)
    {
        return position.getPiece(i);
    }

    public Piece getPiece(int x, int y)
    {
        final Piece p = position.getPiece(new Location(x, y));
        return p;
    }
    
    public Piece getPiece(Location location)
    {
        return position.getPiece(location);
    }
    
    public void insertPiece(Piece p)
    {
        position.insertPiece(p);
    }

    public Piece removePiece(Location location)
    {
        return position.removePiece(location);
    }

    public boolean freeSquare(Location location)
    {
        return position.freeSquare(location);
    }
    
    public boolean freeSquare(int x, int y)
    {
        return position.freeSquare(x, y);
    }

    public boolean canCastleShort()
    {
        return state.getCanCastleShort();
    }

    public boolean canCastleLong()
    {
        return state.getCanCastleLong();
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
        return position.attacks(new Location(x, y), state.inMove);
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
        return position.isInCheck(color);        
    }

    //TODO: This is not strong enough. Positions differ by en passent capabilities
    //and also with castling rights...
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

    
 

    // TODO: 2013, Should we check for draw here? 
    // A move might lead to a draw state.
    // The draw flag of the board might be set accordingly.
    public boolean doMove(Move move)
    {
        Piece piece = getPiece(move.getFrom());

        // Put the move m on the stack
        state.move = move;
        history.add(new State(state));
        state.moveNumber++;

        // Used to determine the 50-move rule, three times repition
        if (piece.getPieceType() == PieceType.PAWN)
        {
            state.halfMoveClock = 0;
            state.halfMovesIndex3PosRepition = state.moveNumber;
        } else
        {
            state.halfMoveClock++;
        }

        // Moving the king will disallow castling in the future
        if (piece.getPieceType() == PieceType.KING && move.getFrom().getX() == 4)
        {
            state.setCanCastleLong(false, piece.getColor());
            state.setCanCastleShort(false, piece.getColor());
        }
        
        // Moving a rook can disallow castling in the future
        if (piece.getPieceType() == PieceType.ROOK)
        {
            if (move.getFrom().getX() == 0)
            {
                state.setCanCastleLong(false, piece.getColor());
            }
            else if (move.getFrom().getX() == 7)
            {
                state.setCanCastleShort(false, piece.getColor());
            }
        }

        if (move.aCapture() && move.getCapturedPiece() == PieceType.ROOK)
        {
            if (move.getTo().getX() == 0)
            {
                state.setCanCastleLong(false, piece.getColor());
            }
            else if (move.getTo().getX() == 7)
            {
                state.setCanCastleShort(false, piece.getColor());
            }        
        }
        
        if (move.aSimplePromotion())
        {
            insertPiece(new Piece(move.getTo(), move.getWhoMoves(), move.promotionTo()));
            removePiece(move.getFrom());
        }

        if (move.aCapturePromotion())
        {
            removePiece(move.getTo());
            removePiece(move.getFrom());
            insertPiece(new Piece(move.getTo(), move.getWhoMoves(), move.promotionTo()));
        }

        switch (move.getMoveType())
        {
            case NORMALMOVE:
                position.movePiece(move.getFrom(), move.getTo());
                break;

            case CAPTURE_ENPASSANT:
                position.movePiece(move.getFrom(), move.getTo());
                removePiece(new Location(move.getTo().getX(), move.getFrom().getY()));
                break;

            case CASTLE_SHORT:
                // Move the king
                position.movePiece(move.getFrom(), move.getTo());
                // then the rook
                position.movePiece(new Location(7, move.getFrom().getY()), new Location(5, move.getFrom().getY()));
                break;

            case CASTLE_LONG:
                // Move the king
                position.movePiece(move.getFrom(), move.getTo());
                // then the rook
                position.movePiece(new Location(0, move.getFrom().getY()), new Location(3, move.getFrom().getY()));
                break;

            case CAPTURE:
                removePiece(move.getTo());
                position.movePiece(move.getFrom(), move.getTo());
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

    public void undo()
    {
        state = history.pop();
        Move move = state.move;

        if (move.aSimplePromotion())
        {
            insertPiece(new Piece(move.getFrom(), move.getWhoMoves(), PieceType.PAWN));
            removePiece(move.getTo());
        }

        if (move.aCapturePromotion())
        {
            removePiece(move.getTo());
            insertPiece(new Piece(move.getTo(), move.getWhoMoves().flip(), move.getCapturedPiece()));
            insertPiece(new Piece(move.getFrom(), move.getWhoMoves(), PieceType.PAWN));
        }

        switch (move.getMoveType())
        {
            case NORMALMOVE:
                position.movePiece(move.getTo(), move.getFrom());
                break;

            case CAPTURE_ENPASSANT:
                Color color;
                if (move.getWhoMoves() == Color.WHITE)
                {
                    color = Color.BLACK;
                }
                else                
                {
                    color = Color.WHITE;
                }
                insertPiece(new Piece(new Location(move.getTo().getX(), move.getFrom().getY()), color, PieceType.PAWN));
                position.movePiece(move.getTo(), move.getFrom());
                break;

            case CASTLE_SHORT:
                // Move the king back
                position.movePiece(move.getTo(), move.getFrom());
                // Then the rook
                position.movePiece(new Location(5, move.getFrom().getY()), new Location(7, move.getFrom().getY()));
                break;

            case CASTLE_LONG:
                // Move the king back
                position.movePiece(new Location(move.getTo()), move.getFrom());
                // Then the rook
                position.movePiece(new Location(3, move.getFrom().getY()), new Location(0, move.getFrom().getY()));
                break;

            case CAPTURE:
                position.movePiece(move.getTo(), move.getFrom());
                insertPiece(new Piece(move.getTo(), move.getWhoMoves().flip(), move.getCapturedPiece()));
                break;
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
                    insertPiece(new Piece(new Location(x, y), c));
                    x++;
                    continue;
                } else if (c >= 'B' && c <= 'R')
                {
                    insertPiece(new Piece(new Location(x, y), c));
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
                        state.setCanCastleShort(true, Color.WHITE);
                        break;
                    case 'Q':
                        state.setCanCastleLong(true, Color.WHITE);
                        break;
                    case 'k':
                        state.setCanCastleShort(true, Color.BLACK);
                        break;
                    case 'q':
                        state.setCanCastleLong(true, Color.BLACK);
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
