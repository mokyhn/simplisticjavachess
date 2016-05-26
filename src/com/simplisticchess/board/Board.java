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
import java.util.Collection;

public class Board
{

    private State currentState;
    private Position position;
    private History history;

    
    public Board()
    {
        currentState = new State();
        position = new Position();
        history = new History();
    }

    public Board(String fen)
    {
        this();
        FENUtils.importPosition(this, fen);
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
        return currentState.getCanCastleShort();
    }

    public boolean canCastleLong()
    {
        return currentState.getCanCastleLong();
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
    
    /*
     * true if the side not in move (i.e. opponent) attacks square (x, y)
     * and otherwise false
     */
    public boolean attacks(int x, int y)
    {
        return PositionInference.attacks(position, new Location(x, y), currentState.inMove) != null;
    }

    /**
     *
     * @param color
     * @return Is player with color color in check by opponent?
     */
    public Boolean isInCheck(Color color)
    {
        return PositionInference.isInCheck(position, color);
    }
    
    public Move getLastMove()
    {
        return currentState.move;
    }

    //TODO: This is not strong enough. Positions differ by en passent capabilities
    //and also with castling rights...
    private void checkDrawBy3RepetionsRule()
    {
        State h;
        int k = 0;

        for (int i = currentState.halfMovesIndex3PosRepition; i < history.size(); i++)
        {
            h = history.get(i);
            if (((BitBoard) position.getBitBoard()).equals((BitBoard) h.bbposition))
            {
                k++;
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
                if (move.getCapturedPiece() == PieceType.ROOK)
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

        return wasMoveLegal;
    }

    public void undo()
    {
        Move move = currentState.move;      
        currentState = history.getPreviousState();  

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

}
