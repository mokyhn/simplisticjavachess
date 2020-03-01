package com.simplisticjavachess.game;

import com.simplisticjavachess.move.Move;

import java.util.Objects;

public final class State
{
	private final Move move;

	// Used to check for draw by threefold repetition.
    //public int hash; // TODO

    /**
     * Number of half moves since the last pawn advance or capture move.
     * Used to determine if a draw can be claimed under the fifty-move rule.
    */
    private final int halfMoveClock;


    //TODO: Get rid of this constructor
    public State()
    {
        this.move = null;
        this.halfMoveClock = 0;
    }

    private State(Move move, int halfMoveClock)
    {
        this.move = move;
        this.halfMoveClock = halfMoveClock;
    }

	public State setMove(Move move) {
        return new State(move, this.halfMoveClock);
	}

    public Move getMove() {
        return move;
    }

    public State setHalfMoveClock(int clock)
    {
        return new State(this.move, clock);
    }

    public int getHalfMoveClock()
    {
        return halfMoveClock;
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
            return this.halfMoveClock == other.halfMoveClock &&
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
        result = result + "Number of half moves since last capture or pawn move: " + halfMoveClock + "\n";
        return result;
    }

}
