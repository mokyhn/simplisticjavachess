package com.simplisticjavachess.game;

import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;

import java.util.Objects;

public final class State
{
	private Move move;
	private Color inMove;

	// Used to check for draw by threefold repetition.
    //public int hash; // TODO

    private final CastlingState castlingState;

    public GameResult gameResult;
    
    /**
     * Number of half moves since the last pawn advance or capture.
     * Used to determine if a draw can be claimed under the fifty-move rule.
    */
    public int halfMoveClock;
    

    /**
     * Number of half moves since last pawn move (including promotions) or
     * capture move When searching for threefold repetitions search from this
     * index.
     */
    public int halfMovesIndex3PosRepetition;

    public State()
    {
        this.castlingState = new CastlingState();
    }

    public State(Move move, Color inMove, CastlingState castlingState, GameResult gameResult,
                 int halfMoveClock, int halfMovesIndex3PosRepetition)
    {
        this.move = move;
        this.inMove = inMove;
        this.castlingState = castlingState;
        this.gameResult = gameResult;
        this.halfMoveClock = halfMoveClock;
        this.halfMovesIndex3PosRepetition = halfMovesIndex3PosRepetition;
    }

    public State(State state)
    {
        this.move = state.move;
        this.inMove = state.inMove;
        this.castlingState = state.castlingState;
        this.halfMoveClock = state.halfMoveClock;
        this.halfMovesIndex3PosRepetition = state.halfMovesIndex3PosRepetition;
        this.gameResult = state.gameResult;
    }

    public State setCanCastleShort(boolean flag, Color color)
    {
        CastlingState newCastlingState;

        if (flag)
        {
            newCastlingState = castlingState.setCanCastleShort(color);
        } else {
            newCastlingState = castlingState.setCannotCastleShort(color);
        }

        return new State(this.move, this.inMove, newCastlingState,  this.gameResult, this.halfMoveClock,
                this.halfMovesIndex3PosRepetition);
    }

    public State setCanCastleLong(boolean flag, Color color)
    {
        CastlingState newCastlingState;

        if (flag)
        {
            newCastlingState = castlingState.setCanCastleLong(color);
        } 
        else
        {
            newCastlingState = castlingState.setCannotCastleLong(color);
        }
        return new State(this.move, this.inMove, newCastlingState,  this.gameResult, this.halfMoveClock,
                this.halfMovesIndex3PosRepetition);
    }
    
    public boolean getCanCastleShort(Color color) 
    {
        return castlingState.canCastleShort(color);
    }
    
    public boolean getCanCastleShort()
    {
        return getCanCastleShort(inMove);
    }
    
    public boolean getCanCastleLong(Color color) 
    {
        return castlingState.canCastleLong(color);
    }
   
    public boolean getCanCastleLong()
    {
        return getCanCastleLong(inMove);
    }

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		if (move == null) {
			throw new IllegalArgumentException();
		}
    	this.move = move;
	}

	public Color getInMove() {
		return inMove;
	}

	public void setInMove(Color inMove) {
		this.inMove = inMove;
	}


	@Override
	public int hashCode()
    {
        return Objects.hash(move, inMove,
                castlingState, gameResult,
                halfMoveClock, halfMovesIndex3PosRepetition);

    }

    @Override
	public boolean equals(Object object)
    {
        if (this == object) {
            return true;
        }

        if (object instanceof State)
        {
            State other = (State) object;
            return this.castlingState.equals(other.castlingState) &&
                   this.gameResult == other.gameResult &&
                   this.halfMoveClock == other.halfMoveClock &&
                   this.halfMovesIndex3PosRepetition == other.halfMovesIndex3PosRepetition &&
                   this.inMove == other.inMove &&
                   Objects.equals(this.move, other.move);
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString()
    {
        String result;

        String blackCastleShort = castlingState.canCastleShort(Color.BLACK) ? "X" : " ";
        String blackCastleLong  = castlingState.canCastleLong(Color.BLACK) ? "X" : " ";
        String whiteCastleShort = castlingState.canCastleShort(Color.WHITE) ? "X" : " ";
        String whiteCastleLong  = castlingState.canCastleLong(Color.WHITE)  ? "X" : " ";

        result = "\n----------------------------State----------------------------\n";
        
        if (gameResult != null) 
        {
            switch (gameResult) 
            {
                case DRAW:
                case DRAW_BY_50_MOVE_RULE:
                case DRAW_BY_REPETITION:
                case STALE_MATE:
                    result = result + "It's a draw!\n";
                break;    
                case MATE:
                    result = result + "Mate!\n";
            }
        }
        result = result + "Black can castle long: [" + blackCastleLong + "],       Black can castle short: [" + blackCastleShort + "]\n";
        result = result + "White can castle long: [" + whiteCastleLong + "],       White can castle short: [" + whiteCastleShort + "]\n";
        result = result + "Number of half moves since last pawn move: " + halfMoveClock + "\n";
        result = result + "Index searched from when checking 3 fold repetition: " + halfMovesIndex3PosRepetition + "\n";

        return result;
    }

}
