

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

    private final State state;
    private final Position position;

    public Board()
    {
        state = new State();
        position = new Position();
    }

    private Board(State newState, Position newPosition)
    {
        this.state = newState;
        this.position = newPosition;
    }

    public static Board createFromFEN(String fen)
    {
        return BoardParser.parseFEN(fen);        
    }

    public static Board createFromLetters(String str)
    {
        return BoardParser.parseFromLetters(str);
    }

    public Color inMove()
    {
        return state.getInMove();
    }

    public Board setBlackToMove()
    {
        return new Board(state.setInMove(Color.BLACK), position);
    }

    public Board setWhiteToMove()
    {
        return new Board(state.setInMove(Color.WHITE), position);
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

    public Board setCanCastleShort(boolean flag, Color color)
    {
        return new Board(state.setCanCastleShort(flag, color), position);
    }

    public Board setCanCastleLong(boolean flag, Color color)
    {
        return new Board(state.setCanCastleLong(flag, color), position);
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
    
    public Board insert(Piece p)
    {
        return new Board(state, position.insert(p));
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


    //TODO: Get rid of these methods that simply delegate functionality on to other objects.
    //The risk is that they mess up state / new state when called from the inside on THIS particular class.
    /**
     *
     * @param x - x position
     * @param y - y position
     * @return true, if square is attacked by opponent
     */
    public boolean isAttacked(int x, int y)
    {
        return PositionInference.attacks(position, new Location(x, y), state.getInMove().opponent()) != null;
    }


    //TODO: Get rid of these methods that simply delegate functionality on to other objects.
    //The risk is that they mess up state / new state when called from the inside on THIS particular class.
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

 /*
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
*/
//    private void checkDrawBy50MoveRule()
//    {
//        if (state.getHalfMoveClock() >= 50 && state.getGameResult() == null)
//        {
//            state = state.setGameResult(GameResult.DRAW_BY_50_MOVE_RULE);
//        }
//    }
 
    public MoveResult doMove(Move move)
    {
        Board board;
        Position newPosition = this.position;
        Piece piece = position.getPiece(move.getFrom());

        State newState = state;
        newState = newState.setMove(move);

        // Used to determine the 50-move rule, three times repetition
        if (piece.getPieceType() == PieceType.PAWN)
        {
            newState = newState.setHalfMoveClock(0);
            //newState.halfMovesIndex3PosRepetition = state.moveNumber;
        } else
        {
            newState = newState.setHalfMoveClock(newState.getHalfMoveClock()+1);
        }

        // Moving the king will disallow castling in the future
        if (piece.getPieceType() == PieceType.KING)
        {
            newState = newState.setCanCastleLong(false, move.getWhoMoves());
            newState = newState.setCanCastleShort(false, move.getWhoMoves());
        }
        
        // Moving a rook can disallow castling in the future
        if (piece.getPieceType() == PieceType.ROOK)
        {
            if (move.getFrom().getX() == 0)
            {
                newState = newState.setCanCastleLong(false, piece.getColor());
            }
            else if (move.getFrom().getX() == 7)
            {
                newState = newState.setCanCastleShort(false, piece.getColor());
            }
        }
               
        switch (move.getMoveType())
        {
            case NORMALMOVE:
                newPosition = newPosition.move(piece, move.getTo());
                break;

            case CAPTURE:
                newPosition = newPosition.remove(move.getCapturedPiece());
                newPosition = newPosition.move(piece, move.getTo());
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
                newPosition = newPosition.move(position.getPiece(move.getFrom()), move.getTo());
                newPosition = newPosition.move(position.getPiece(new Location(7, move.getFrom().getY())),
                        new Location(5, move.getFrom().getY()));
                break;

            case CASTLE_LONG:
                newPosition = newPosition.move(position.getPiece(move.getFrom()), move.getTo());
                newPosition = newPosition.move(position.getPiece(new Location(0, move.getFrom().getY())),
                        new Location(3, move.getFrom().getY()));
                break;
            
            case CAPTURE_ENPASSANT:
                newPosition = newPosition.move(piece, move.getTo());
                newPosition = newPosition.remove(move.getCapturedPiece());
                break;
            
            case PROMOTE_TO_BISHOP:  /* Intended fallthrough */
            case PROMOTE_TO_KNIGHT:  /* Intended fallthrough */
            case PROMOTE_TO_ROOK:    /* Intended fallthrough */
            case PROMOTE_TO_QUEEN:   /* Intended fallthrough */
                newPosition = newPosition.insert(new Piece(move.getTo(), move.getWhoMoves(), move.promotionTo()));
                newPosition = newPosition.remove(piece);
                break;
             
            case CAPTURE_AND_PROMOTE_TO_BISHOP: /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_KNIGHT: /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_ROOK:   /* Intended fallthrough */
            case CAPTURE_AND_PROMOTE_TO_QUEEN:  /* Intended fallthrough */
                newPosition = newPosition.remove(position.getPiece(move.getTo()));
                newPosition = newPosition.remove(position.getPiece(move.getFrom()));
                newPosition = newPosition.insert(new Piece(move.getTo(), move.getWhoMoves(), move.promotionTo()));
        }

        // Swap the move color
        newState = newState.setInMove(state.getInMove().opponent());

        boolean wasMoveLegal;

        // The player that did the move is in check
        // his or her move is hence not legal
        if (PositionInference.isInCheck(newPosition, state.getInMove()))
        {
            wasMoveLegal = false;
        }
        else
        {
            //checkDrawBy50MoveRule();
            //checkDrawBy3RepetionsRule();
            wasMoveLegal = true;
        }
        
        return new MoveResult(wasMoveLegal, new Board(newState, newPosition));
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

    //TODO: Get rid of these methods that simply delegate functionality on to other objects.
    //The risk is that they mess up state / new state when called from the inside on THIS particular class.
    boolean isWhiteInMove()
    {
        return state.getInMove() == Color.WHITE;
    }

    //TODO: Get rid of these methods that simply delegate functionality on to other objects.
    //The risk is that they mess up state / new state when called from the inside on THIS particular class.
    public boolean isAttacked(Location location)
    {
        return PositionInference.attacks(position, location, state.getInMove().opponent()) != null;
    }

    public Vector getMoveDirection()
    {
        return Vector.getMoveDirection(inMove());
    }

}
