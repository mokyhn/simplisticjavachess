package com.simplisticchess.game;

/**
 *
 * @author Morten Kühnrich
 *
 * This class encapsulates the information needed to check for
 * three-fold-repetition. The class also records the history of when the
 * castling possibility was lost for a given player
 */
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Color;
import java.util.Stack;

public final class History
{

    private final Stack<State> stateHistory; // A stack of previous game states

    public History()
    {
        stateHistory = new Stack<State>();
    }

    public History(History history)
    {
        this();
        for (int i = 0; i < history.size(); i++)
        {
            this.stateHistory.push(new State(history.get(i)));
        }
    }

    public void add(State s)
    {
        stateHistory.push(s);
    }

    public State pop()
    {
        return stateHistory.pop();
    }

    public State peek()
    {
        return stateHistory.peek();
    }

    public int size()
    {
        return stateHistory.size();
    }

    public State get(int index)
    {
        return stateHistory.get(index);
    }

    public boolean isEmpty()
    {
        return stateHistory.isEmpty();
    }

    @Override
    public String toString()
    {
        String r = "";
        String prefix = "";
        Move m;

        for (int i = 0; i < stateHistory.size(); i++)
        {
            m = stateHistory.get(i).move;
            if (m.whoMoves == Color.WHITE)
            {
                prefix = (i + 2) / 2 + ".";
            }
            //if (m.whoMoves == Piece.BLACK) prefix = (i+2)/2 + "....";
            r = r + prefix + m.toString() + " ";
            prefix = "";
        }

        return r;

    }

}
