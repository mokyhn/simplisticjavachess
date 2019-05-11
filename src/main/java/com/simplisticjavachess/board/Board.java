

package com.simplisticjavachess.board;


import com.simplisticjavachess.game.GameResult;
import com.simplisticjavachess.game.State;
import com.simplisticjavachess.evaluator.Evaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Morten Kühnrich
 */
public class Board
{

    private State state;
    private Position position;

    public Board()
    {
        state = new State();
        position = new Position();
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
        this.state = new State(board.state);
        this.position = board.position;
    }

    public Color inMove()
    {
        return state.getInMove();
    }

    public void setBlackToMove()
    {
        state = state.setInMove(Color.BLACK);
    }

    public void setWhiteToMove()
    {
        state = state.setInMove(Color.WHITE);
    }

    public void setGameResult(GameResult gameResult)
    {
        state = state.setGameResult(gameResult);
    }

    public GameResult getGameResult()
    {
        return state.getGameResult();
    }    
    
    public boolean isDraw()
    {
        if (state.getGameResult() == null)
        {
            return false;
        }
        
        switch (state.getGameResult())
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
        return state.getGameResult() == GameResult.MATE;
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
        state = state.setCanCastleShort(flag, color);
    }

    public void setCanCastleLong(boolean flag, Color color)
    {
        state = state.setCanCastleLong(flag, color);
    }
    
    public Collection<Piece> getPieces()
    {
        return position.getPieces();
    }
    
    public Piece getPiece(int x, int y)
    {
        return position.getPiece(new Location(x, y));
    }
    
    public Piece getPiece(Location location)
    {
        return position.getPiece(location);
    }
    
    public void insert(Piece p)
    {
        position = position.insert(p);
    }

    public void remove(Piece p)
    {
        position = position.remove(p);
    }

    private void move(Piece piece, Location to)
    {
        position = position.move(piece, to);
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
        return PositionInference.attacks(position, new Location(x, y), state.getInMove()) != null;
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
    
    public Move getLastMove()
    {
        return state.getMove();
    }

    //TODO: This is not strong enough. Positions differ by en passent capabilities
    //and also with castling rights...
    private void checkDrawBy3RepetionsRule()
    {
        State state;
        int k = 0;

//        for (int i = state.halfMovesIndex3PosRepetition; i < history.size(); i++)
//        {
//            state = history.get(i);
//            if (position.hashCode() ==  state.hash)
//            {
//                //TODO make three fold repetition check work
//                //k++;
//            }
//        }

        if (k >= 3 && this.state.getGameResult() == null)
        {
            this.state = this.state.setGameResult(GameResult.DRAW_BY_REPETITION);
        }
    }

    private void checkDrawBy50MoveRule()
    {
        if (state.getHalfMoveClock() >= 50 && state.getGameResult() == null)
        {
            state = state.setGameResult(GameResult.DRAW_BY_50_MOVE_RULE);
        }
    }
 
    public boolean doMove(Move move)
    {        
        Piece piece = position.getPiece(move.getFrom());
 

        State newState = new State(state);
        newState = newState.setMove(move);

        // Used to determine the 50-move rule, three times repetition
        if (piece.getPieceType() == PieceType.PAWN)
        {
            newState = newState.setHalfMoveClock(0);
            //newState.halfMovesIndex3PosRepetition = state.moveNumber;
        } else
        {
            newState.setHalfMoveClock(newState.getHalfMoveClock()+1);
        }

        // Moving the king will disallow castling in the future
        if (piece.getPieceType() == PieceType.KING)
        {
            newState.setCanCastleLong(false, move.getWhoMoves());
            newState.setCanCastleShort(false, move.getWhoMoves());
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
                move(piece, move.getTo());
                break;

            case CAPTURE:
                remove(move.getCapturedPiece());
                move(piece, move.getTo());
                if (move.getCapturedPiece().getPieceType() == PieceType.ROOK)
                {
                    if (move.getTo().getX() == 0)
                    {
                         newState.setCanCastleLong(false, move.getCapturedPiece().getColor());
                    }
                     else if (move.getTo().getX() == 7)
                    {
                        newState.setCanCastleShort(false, move.getCapturedPiece().getColor());
                    }
                }
                break;

            case CASTLE_SHORT:
                // Move the king
                move(position.getPiece(move.getFrom()), move.getTo());
                move(position.getPiece(new Location(7, move.getFrom().getY())),
                        new Location(5, move.getFrom().getY()));
                break;

            case CASTLE_LONG:
                move(position.getPiece(move.getFrom()), move.getTo());
                move(position.getPiece(new Location(0, move.getFrom().getY())),
                        new Location(3, move.getFrom().getY()));
                break;
            
            case CAPTURE_ENPASSANT:
                move(piece, move.getTo());
                remove(move.getCapturedPiece());
                break;
            
            case PROMOTE_TO_BISHOP:  /* Intended fallthrough */
            case PROMOTE_TO_KNIGHT:  /* Intended fallthrough */
            case PROMOTE_TO_ROOK:    /* Intended fallthrough */
            case PROMOTE_TO_QUEEN:   /* Intended fallthrough */
                insert(new Piece(move.getTo(), move.getWhoMoves(), move.promotionTo()));
                remove(piece);
                break;
             
            case CAPTURE_AND_PROMOTE_TO_BISHOP: /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_KNIGHT: /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_ROOK:   /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_QUEEN:  /* Intended fallthrough */
                remove(position.getPiece(move.getTo()));
                remove(position.getPiece(move.getFrom()));
                insert(new Piece(move.getTo(), move.getWhoMoves(), move.promotionTo()));
        }

        // Swap the move color
        newState = newState.setInMove(state.getInMove().opponent());

        state = newState;
        
        boolean wasMoveLegal;

        // The player that did the move is in check
        // his or her move is hence not legal
        if (isInCheck(state.getInMove().opponent()))
        {
            wasMoveLegal = false;
        }
        else
        {
            checkDrawBy50MoveRule();
            checkDrawBy3RepetionsRule();
            wasMoveLegal = true;
        }
        
        return wasMoveLegal;
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
        s = s + state.toString();
        s = s + "Immediate evaluation: " + new Evaluator().evaluate(this) + "\n";
        s = s + "FEN: " + BoardParser.exportPosition(this);
        return s;
    }
    
    @Override
    public boolean equals(Object object) 
    {
        if (object instanceof Board)
        {
            Board other = (Board) object;
            return this.position.equals(other.position) &&
                   this.state.equals(other.state);
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(position.hashCode(), state.hashCode());
    }

    boolean isWhiteInMove()
    {
        return state.getInMove() == Color.WHITE;
    } 

    public boolean isAttacked(Location location)
    {
        return PositionInference.attacks(position, location, state.getInMove()) != null;
    }

    public Vector getMoveDirection()
    {
        return Vector.getMoveDirection(inMove());
    }

}
