

package com.simplisticjavachess.board;


import com.simplisticjavachess.engine.MoveSequence;
import com.simplisticjavachess.game.GameResult;
import com.simplisticjavachess.game.State;
import com.simplisticjavachess.evaluation.IntegerEvaluator;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.simplisticjavachess.game.GameResult.DRAW;
import static com.simplisticjavachess.piece.PieceType.KING;
import static com.simplisticjavachess.piece.PieceType.PAWN;
import static com.simplisticjavachess.piece.PieceType.ROOK;

/**
 * @author Morten Kühnrich
 */
//TODO: Add further tests of this class
public class Board
{

    private final State state;
    private final Position position;

    //TODO: Make this constructor less fill-in-dummy-garbage-data as done with State() which assumes values for state.
    public Board(Color inMove)
    {
        state = new State();
        position = new Position(inMove);
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
        return position.inMove();
    }

    public Board setBlackToMove()
    {
        return new Board(state, position.setInMove(Color.BLACK));
    }

    public Board setWhiteToMove()
    {
        return new Board(state, position.setInMove(Color.WHITE));
    }

    public GameResult getGameResult()
    {
        return state.getGameResult();
    }    
    
    public boolean isDraw()
    {
          return state.getGameResult() == DRAW;
    }

    public boolean isMate()
    {
        return GameResult.MATE.equals(state.getGameResult());
    }


    public boolean canCastleShort()
    {
        return position.getCanCastleShort(inMove());
    }

    public boolean canCastleLong()
    {
        return position.getCanCastleLong(inMove());
    }

    public Board setCanCastleShort(boolean flag, Color color)
    {
        return new Board(state, position.setCanCastleShort(flag, color));
    }

    public Board setCanCastleLong(boolean flag, Color color)
    {
        return new Board(state, position.setCanCastleLong(flag, color));
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

    public Board insert(List<Piece> pieces)
    {
        Board board = this;

        for (Piece piece : pieces)
        {
            board = board.insert(piece);
        }

        return board;
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

    public boolean isAttacked(int x, int y)
    {
        return position.isAttacked(x, y);
    }

    public Boolean isInCheck(Color color)
    {
        return position.isInCheck(color);
    }

    public Boolean isInCheck()
    {
        return position.isInCheck();
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

    public MoveResult doMove(String moves)
    {
        MoveSequence moveSequence = MoveSequence.parse(this, moves);
        return this.doMove(moveSequence);
    }

    private MoveResult doMove(MoveSequence moveSequence)
    {
        MoveResult moveResult = null;
        Iterator<Move> moves = moveSequence.iterator();

        Board theboard = this;

        while (moves.hasNext())
        {
            moveResult = theboard.doMove(moves.next());
            theboard = moveResult.getBoard();
        }

        return moveResult;
    }

    public MoveResult doMove(Move move)
    {
        Piece piece = position.getPiece(move.getFrom());

        State newState = state;
        newState = newState.setMove(move);

        // Used to determine the 50-move rule, three times repetition
        if (PAWN.equals(piece.getPieceType()))
        {
            newState = newState.setHalfMoveClock(0);
            //newState.halfMovesIndex3PosRepetition = state.moveNumber;
        } else
        {
            newState = newState.setHalfMoveClock(newState.getHalfMoveClock()+1);
        }

        Position newPosition = this.position;

        // Moving the king will disallow castling in the future
        if (KING.equals(piece.getPieceType()))
        {
            newPosition = newPosition.setCanCastleLong(false, move.getWhoMoves());
            newPosition = newPosition.setCanCastleShort(false, move.getWhoMoves());
        }

        // Moving a rook can disallow castling in the future
        if (ROOK.equals(piece.getPieceType()))
        {
            if (move.getFrom().getX() == 0)
            {
                newPosition = newPosition.setCanCastleLong(false, piece.getColor());
            }
            else if (move.getFrom().getX() == 7)
            {
                newPosition = newPosition.setCanCastleShort(false, piece.getColor());
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
                if (ROOK.equals(move.getCapturedPiece().getPieceType()))
                {
                    if (move.getTo().getX() == 0)
                    {
                        newPosition = newPosition.setCanCastleLong(false, move.getCapturedPiece().getColor());
                    }
                     else if (move.getTo().getX() == 7)
                    {
                        newPosition = newPosition.setCanCastleShort(false, move.getCapturedPiece().getColor());
                    }
                }
                break;

            case CASTLE_SHORT:
                if (!position.getCanCastleShort(inMove()))
                {
                    return new MoveResult(false, this);
                }
                // Move the king
                newPosition = newPosition.move(position.getPiece(move.getFrom()), move.getTo());
                newPosition = newPosition.move(position.getPiece(new Location(7, move.getFrom().getY())),
                        new Location(5, move.getFrom().getY()));
                break;

            case CASTLE_LONG:
                if (!position.getCanCastleLong(inMove()))
                {
                    return new MoveResult(false, this);
                }
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
        newPosition = newPosition.setInMove(position.inMove().opponent());

        boolean wasMoveLegal;

        // The player that did the move is in check
        // his or her move is hence not legal
        if (newPosition.isInCheck(position.inMove()))
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
        StringBuilder sb = new StringBuilder();
        sb.append(position.getPositionString());
        sb.append(state.toString());
        sb.append("Immediate evaluation: " + new IntegerEvaluator().evaluate(this) + "\n");
        sb.append("FEN: " + BoardParser.exportPosition(this));
        return sb.toString();
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
        return Color.WHITE.equals(position.inMove());
    }

    public boolean isAttacked(Location location)
    {
        return position.isAttacked(location);
    }

    public Vector getMoveDirection()
    {
        return Vector.getMoveDirection(inMove());
    }

}
