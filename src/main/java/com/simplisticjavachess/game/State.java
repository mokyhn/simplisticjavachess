package com.simplisticjavachess.game;

import com.simplisticjavachess.move.Move;

import java.util.Objects;

public final class State
{
	private final Move move;

	// Used to check for draw by threefold repetition.
    //public int hash; // TODO

    private final GameResult gameResult;

    /**
     * Number of half moves since the last pawn advance or capture.
     * Used to determine if a draw can be claimed under the fifty-move rule.
    */
    private final int halfMoveClock;


    /**
     * Number of half moves since last pawn move (including promotions) or
     * capture move When searching for threefold repetitions search from this
     * index.
     */
    private final int halfMovesIndex3PosRepetition;

    //TODO: Get rid of this constructor
    public State()
    {
        this.move = null;
        this.gameResult = null;
        this.halfMoveClock = 0;
        this.halfMovesIndex3PosRepetition = 0;
    }

    private State(Move move, GameResult gameResult, int halfMoveClock, int halfMovesIndex3PosRepetition)
    {
        this.move = move;
        this.gameResult = gameResult;
        this.halfMoveClock = halfMoveClock;
        this.halfMovesIndex3PosRepetition = halfMovesIndex3PosRepetition;
    }


    public GameResult getGameResult() {
        return gameResult;
    }

    public State setGameResult(GameResult gameResult)
    {
        return new State(this.move, gameResult, this.halfMoveClock, this.halfMovesIndex3PosRepetition);
    }


	public State setMove(Move move) {
        return new State(move, gameResult, this.halfMoveClock, this.halfMovesIndex3PosRepetition);
	}

    public Move getMove() {
        return move;
    }

    public State setHalfMoveClock(int clock)
    {
        return new State(this.move, this.gameResult, clock, this.halfMovesIndex3PosRepetition);
    }

    public int getHalfMoveClock()
    {
        return halfMoveClock;
    }

    public int getHalfMovesIndex3PosRepetition() {
        return halfMovesIndex3PosRepetition;
    }

    public State setHalfMovesIndex3PosRepetition(int index)
    {
        return  new State(this.move, this.gameResult, this.halfMoveClock, index);
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
            return this.gameResult == other.gameResult &&
                   this.halfMoveClock == other.halfMoveClock &&
                   this.halfMovesIndex3PosRepetition == other.halfMovesIndex3PosRepetition &&
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

        result = "\n----------------------------State----------------------------\n";

        if (gameResult != null)
        {
            switch (gameResult)
            {
                case DRAW:
                    result = result + "It's a draw!\n";
                break;
                case MATE:
                    result = result + "Mate!\n";
            }
        }
        result = result + "Number of half moves since last pawn move: " + halfMoveClock + "\n";
        result = result + "Index searched from when checking 3 fold repetition: " + halfMovesIndex3PosRepetition + "\n";

        return result;
    }

}
