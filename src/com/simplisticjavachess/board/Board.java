/**
 * @author Morten Kühnrich
 */

package com.simplisticjavachess.board;


import com.simplisticjavachess.game.GameResult;
import com.simplisticjavachess.game.History;
import com.simplisticjavachess.game.State;
import com.simplisticjavachess.evaluator.Evaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import java.util.Collection;

public class Board
{

    private State currentState;
    private final Position position;
    private final History history;
    
    public Board()
    {
        currentState = new State();
        position = new Position();
        history = new History();
    }

    public static Board createFromFEN(String fen)
    {
        return BoardParser.parseFEN(fen);        
    }

    public static Board createFromLetters(String str)
    {
        return BoardParser.parseFromLetters(str);
    }
    
    public Board(Board board)
    {
        this.currentState = new State(board.currentState);
        this.position = new Position(board.position);
        this.history = new History(board.history);
    }
    public Color inMove()
    {
        return currentState.inMove;
    }

    public void setBlackToMove()
    {
        currentState.inMove = Color.BLACK;
    }

    public void setWhiteToMove()
    {
        currentState.inMove = Color.WHITE;
    }

    public void setGameResult(GameResult gameResult)
    {
        currentState.gameResult = gameResult;
    }

    public GameResult getGameResult()
    {
        return currentState.gameResult;
    }    
    
    public boolean isDraw()
    {
        if (currentState.gameResult == null)
        {
            return false;
        }
        
        switch (currentState.gameResult) 
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
        return currentState.gameResult == GameResult.MATE;
    }


    public boolean canCastleShort()
    {
        return currentState.getCanCastleShort() && !isInCheck();
    }

    public boolean canCastleLong()
    {
        return currentState.getCanCastleLong() && !isInCheck();
    }

    public void setCanCastleShort(boolean flag, Color color)
    {
        currentState.setCanCastleShort(flag, color);
    }

    public void setCanCastleLong(boolean flag, Color color)
    {
        currentState.setCanCastleLong(flag, color);
    }
    
    public Collection<Piece> getPieces()
    {
        return position.getPieces();
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

    
    public Position getPosition()
    {
        return position;
    }
    
    
    /**
     *
     * @param x - x position
     * @param y - y position
     * @return true, if square is attacked by opponent
     */
    public boolean isAttacked(int x, int y)
    {
        return PositionInference.attacks(position, new Location(x, y), currentState.inMove) != null;
    }

    public boolean isFreeAndUnattacked(Location location)
    {
        return !isAttacked(location) && isFree(location);
    }
    
    /**
     *
     * @param color
     * @return Is player with color color in check?
     */
    public Boolean isInCheck(Color color)
    {
        return PositionInference.isInCheck(position, color);
    }
    
    /**
     * @return Is the player to move in check?
     */
    public Boolean isInCheck()
    {
        return PositionInference.isInCheck(position, currentState.inMove);
    }
    
    public Move getLastMove()
    {
        return currentState.move;
    }

    //TODO: This is not strong enough. Positions differ by en passent capabilities
    //and also with castling rights...
    private void checkDrawBy3RepetionsRule()
    {
        State state;
        int k = 0;

        for (int i = currentState.halfMovesIndex3PosRepition; i < history.size(); i++)
        {
            state = history.get(i);
            if (position.hashCode() ==  state.hash)
            {
                //TODO make three fold repetition check work
                //k++;
            }
        }

        if (k >= 3 && currentState.gameResult == null) 
        {
            currentState.gameResult = GameResult.DRAW_BY_REPETITION;
        }
    }

    private void checkDrawBy50MoveRule()
    {
        if (currentState.halfMoveClock >= 50 && currentState.gameResult == null)
        {
            currentState.gameResult = GameResult.DRAW_BY_50_MOVE_RULE;
        }
    }
 
    public boolean doMove(Move move)
    {        
        Piece piece = position.getPiece(move.getFrom());
 
        currentState.hash = position.hashCode();
        history.add(currentState);
                
        State newState = new State(currentState);
        newState.move = move;
        newState.moveNumber++;

        // Used to determine the 50-move rule, three times repition
        if (piece.getPieceType() == PieceType.PAWN)
        {
            newState.halfMoveClock = 0;
            newState.halfMovesIndex3PosRepition = currentState.moveNumber;
        } else
        {
            newState.halfMoveClock++;
        }

        // Moving the king will disallow castling in the future
        if (piece.getPieceType() == PieceType.KING && move.getFrom().getX() == 4)
        {
            newState.setCanCastleLong(false, piece.getColor());
            newState.setCanCastleShort(false, piece.getColor());
        }
        
        // Moving a rook can disallow castling in the future
        if (piece.getPieceType() == PieceType.ROOK)
        {
            if (move.getFrom().getX() == 0)
            {
                newState.setCanCastleLong(false, piece.getColor());
            }
            else if (move.getFrom().getX() == 7)
            {
                newState.setCanCastleShort(false, piece.getColor());
            }
        }
               
        switch (move.getMoveType())
        {
            case NORMALMOVE:
                position.movePiece(move.getFrom(), move.getTo());
                break;

            case CAPTURE:
                removePiece(move.getTo());
                position.movePiece(move.getFrom(), move.getTo());
                if (move.getCapturedPiece().getPieceType() == PieceType.ROOK)
                {
                    if (move.getTo().getX() == 0)
                    {
                         newState.setCanCastleLong(false, piece.getColor());
                    }
                     else if (move.getTo().getX() == 7)
                    {
                        newState.setCanCastleShort(false, piece.getColor());
                    }
                }
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
            
            case CAPTURE_ENPASSANT:
                position.movePiece(move.getFrom(), move.getTo());
                removePiece(new Location(move.getTo().getX(), move.getFrom().getY()));
                break;
            
            case PROMOTE_TO_BISHOP:  /* Intended fallthrough */
            case PROMOTE_TO_KNIGHT:  /* Intended fallthrough */
            case PROMOTE_TO_ROOK:    /* Intended fallthrough */
            case PROMOTE_TO_QUEEN:   /* Intended fallthrough */
                insertPiece(new Piece(move.getTo(), move.getWhoMoves(), move.promotionTo()));
                removePiece(move.getFrom());
                break;
             
            case CAPTURE_AND_PROMOTE_TO_BISHOP: /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_KNIGHT: /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_ROOK:   /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_QUEEN:  /* Intended fallthrough */
                removePiece(move.getTo());
                removePiece(move.getFrom());
                insertPiece(new Piece(move.getTo(), move.getWhoMoves(), move.promotionTo())); 
        }

        // Swap the move color
        newState.inMove = currentState.inMove.opponent();

        currentState = newState;
        
        boolean wasMoveLegal;

        // The player that did the move is in check
        // his or her move is hence not legal
        if (isInCheck(currentState.inMove.opponent()))
        {
            wasMoveLegal = false;
        }
        else
        {
            checkDrawBy50MoveRule();
            checkDrawBy3RepetionsRule();
            wasMoveLegal = true;
        }


        assert(position.getPiece(move.getFrom()) == null);
        assert(position.getPiece(move.getTo()) != null);
        assert(position.getPiece(move.getTo()).getPieceType() != null);
        return wasMoveLegal;
    }

    public void undo()
    {
        Move move = currentState.move;      
        currentState = history.getPreviousState();  

        switch (move.getMoveType())
        {
            case NORMALMOVE:
                position.movePiece(move.getTo(), move.getFrom());
                break;

            case CAPTURE:
                position.movePiece(move.getTo(), move.getFrom());
                insertPiece(move.getCapturedPiece());
                break;

            case CASTLE_SHORT:
                // Move the king back
                position.movePiece(move.getTo(), move.getFrom());
                // Then the rook
                position.movePiece(new Location(5, move.getFrom().getY()), new Location(7, move.getFrom().getY()));
                break;

            case CASTLE_LONG:
                // Move the king back
                position.movePiece(move.getTo(), move.getFrom());
                // Then the rook
                position.movePiece(new Location(3, move.getFrom().getY()), new Location(0, move.getFrom().getY()));
                break;    
            
            case CAPTURE_ENPASSANT:          
                insertPiece(move.getCapturedPiece());
                position.movePiece(move.getTo(), move.getFrom());
                break;
                
            case PROMOTE_TO_BISHOP: /* Intended fallthrough */
            case PROMOTE_TO_KNIGHT: /* Intended fallthrough */
            case PROMOTE_TO_ROOK:   /* Intended fallthrough */
            case PROMOTE_TO_QUEEN:  /* Intended fallthrough */
                insertPiece(new Piece(move.getFrom(), move.getWhoMoves(), PieceType.PAWN));
                removePiece(move.getTo());
                break;

            case CAPTURE_AND_PROMOTE_TO_BISHOP: /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_KNIGHT: /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_ROOK:   /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_QUEEN:  /* Intended fallthrough */
                removePiece(move.getTo());
                insertPiece(move.getCapturedPiece());
                insertPiece(new Piece(move.getFrom(), move.getWhoMoves(), PieceType.PAWN));
                break;
        }
        
        assert(position.getPiece(move.getFrom()) != null);
        assert(position.getPiece(move.getFrom()).getPieceType() != null);
                
    }


    /**
     * Returns the board as ASCII art and game other information
     * @return An ASCII representation of the board
     */
    public String asASCII()
    {
    
        String s = position.getPositionString();

        if (inMove() == Color.WHITE)
        {
            s = s + "  White to move\n";
        } else
        {
            s = s + "  Black to move\n";
        }

        s = s + currentState.toString();

        if (!history.isEmpty())
        {
            Move lastMove = history.getLastMove();
            
            String lastMoveStr = lastMove == null ? "" : lastMove.toString();
            
            if (currentState.inMove.opponent() == Color.WHITE)
            {
                s = s + "Last move " + (currentState.moveNumber + 1) / 2 + "." + lastMoveStr + "\n";
            } else
            {
                s = s + "Last move " + (currentState.moveNumber + 1) / 2 + "...." + lastMoveStr + "\n";
            }
        }

        s = s + "Immediate evaluation: " + Evaluator.evaluate(this) + "\n";

        s = s + "Move history: " + history.toString();

        return s;
    }
    
    @Override
    public boolean equals(Object object) 
    {
        if (object instanceof Board)
        {
            Board other = (Board) object;
            return this.position.equals(other.position) &&
                   this.currentState.equals(other.currentState);
        }
        else
        {
            return false;
        }
    }

    public boolean isWhiteInMove()
    {
        return currentState.inMove == Color.WHITE;
    } 

    public boolean isAttacked(Location location)
    {
        return PositionInference.attacks(position, location, currentState.inMove) != null;        
    }

    private boolean isFree(Location location)
    {
        return getPiece(location) == null;
    }

}
