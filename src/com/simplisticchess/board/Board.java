/**
 * @author Morten Kühnrich
 */

package com.simplisticchess.board;


import com.simplisticchess.GameResult;
import com.simplisticchess.game.History;
import com.simplisticchess.game.State;
import com.simplisticchess.evaluator.Evaluator;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.Piece;
import com.simplisticchess.piece.PieceType;
import com.simplisticchess.position.Location;

public class Board
{

    int moveNumber;

    private State state;
    private Position position;
    private History history;

    public Board()
    {
        state = new State();
        position = new Position();
        history = new History();
    }

    public Board(String fen)
    {
        this();
        FENParser.setupFENboard(this, fen);
    }

    public Board(Board board)
    {
        this.moveNumber = board.moveNumber;
        this.state = new State(board.state);
        this.position = new Position(board.position);
        this.history = new History(board.history);
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

    public void setGameResult(GameResult gameResult)
    {
        state.gameResult = gameResult;
    }

    public GameResult getGameResult()
    {
        return state.gameResult;
    }    
    
    public boolean isDraw()
    {
        if (state.gameResult == null)
        {
            return false;
        }
        
        switch (state.gameResult) 
        {
            case DRAW:
            case DRAW_BY_50_MOVE_RULE:
            case DRAW_BY_REPETITION:
            case STALE_MATE:
                return true;
            default:
                return false;
        }
    }

    public boolean isMate()
    {
        return state.gameResult == GameResult.MATE;
    }


    public boolean canCastleShort()
    {
        return state.getCanCastleShort();
    }

    public boolean canCastleLong()
    {
        return state.getCanCastleLong();
    }

    public void setCanCastleShort(boolean flag, Color color)
    {
        state.setCanCastleShort(flag, color);
    }

    public void setCanCastleLong(boolean flag, Color color)
    {
        state.setCanCastleLong(flag, color);
    }
    
    public int getNumberOfPieces()
    {
        return position.getNumberOfPieces();
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

    
    /*
     * true if the side not in move attacks square (x, y)
     * and otherwise false
     */
    public boolean attacks(int x, int y)
    {
        return position.attacks(new Location(x, y), state.inMove);
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
    
    public Move getLastMove()
    {
        return history.peek().move;
    }

    //TODO: This is not strong enough. Positions differ by en passent capabilities
    //and also with castling rights...
    private void checkDrawBy3RepetionsRule()
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

        if (k >= 3 && state.gameResult == null) 
        {
            state.gameResult = GameResult.DRAW_BY_REPETITION;
        }
    }

    private void checkDrawBy50MoveRule()
    {
        if (state.halfMoveClock >= 50 && state.gameResult == null)
        {
            state.gameResult = GameResult.DRAW_BY_50_MOVE_RULE;
        }
    }
 
    public boolean doMove(Move move)
    {
        Piece piece = getPiece(move.getFrom());

        state.move = move;
        history.add(new State(state));
        moveNumber++;

        // Used to determine the 50-move rule, three times repition
        if (piece.getPieceType() == PieceType.PAWN)
        {
            state.halfMoveClock = 0;
            state.halfMovesIndex3PosRepition = moveNumber;
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
        state.inMove = state.inMove.opponent();

        boolean legalityOfMove = true;

        // The player that did the move is in check
        // his or her move is hence not legal
        if (isInCheck(state.inMove.opponent()))
        {
            legalityOfMove = false;
            this.undo();
        }
        else
        {
            checkDrawBy50MoveRule();
            checkDrawBy3RepetionsRule();            
        }

        return legalityOfMove;
    }

    public void undo()
    {
        state = history.pop();
        moveNumber--;
        Move move = state.move;

        if (move.aSimplePromotion())
        {
            insertPiece(new Piece(move.getFrom(), move.getWhoMoves(), PieceType.PAWN));
            removePiece(move.getTo());
        }

        if (move.aCapturePromotion())
        {
            removePiece(move.getTo());
            insertPiece(new Piece(move.getTo(), move.getWhoMoves().opponent(), move.getCapturedPiece()));
            insertPiece(new Piece(move.getFrom(), move.getWhoMoves(), PieceType.PAWN));
        }

        switch (move.getMoveType())
        {
            case NORMALMOVE:
                position.movePiece(move.getTo(), move.getFrom());
                break;

            case CAPTURE_ENPASSANT:          
                insertPiece(new Piece(new Location(move.getTo().getX(), move.getFrom().getY()), move.getWhoMoves().opponent(), PieceType.PAWN));
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
                insertPiece(new Piece(move.getTo(), move.getWhoMoves().opponent(), move.getCapturedPiece()));
                break;
        }
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
            if (state.inMove.opponent() == Color.WHITE)
            {
                s = s + "Last move " + (moveNumber + 1) / 2 + "." + history.peek().move.toString() + "\n";
            } else
            {
                s = s + "Last move " + (moveNumber + 1) / 2 + "...." + history.peek().move.toString() + "\n";
            }
        }

        s = s + "Immediate evaluation: " + Evaluator.evaluate(this) + "\n";

        s = s + "Move history: " + history.toString();

        return s;
    }

}
