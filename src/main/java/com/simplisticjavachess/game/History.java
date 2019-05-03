package com.simplisticjavachess.game;

/**
 *
 * @author Morten Kühnrich
 *
 * This class encapsulates the information needed to check for
 * three-fold-repetition. 
 * 
 * The class also records the history of when the
 * castling possibility was lost for a given player
 * 
 */
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import java.util.Stack;

public class History
{

    private final Stack<State> stateHistory; 

    public History()
    {
        stateHistory = new Stack<>();
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

    public State undo()
    {
        return stateHistory.pop();
    }

    public int size()
    {
        return stateHistory.size();
    }

    public State get(int index)
    {
        return stateHistory.get(index);
    }

    @Override
    public String toString()
    {
        String r = "";
        String prefix = "";

        for (int i = 0; i < stateHistory.size(); i++)
        {
            Move m = stateHistory.get(i).getMove();
            
            if (m != null)
            {
                if (m.getWhoMoves() == Color.WHITE)
                {
                    prefix = (i + 2) / 2 + ".";
                }
                //if (m.whoMoves == Piece.BLACK) prefix = (i+2)/2 + "....";
                r = r + prefix + m.toString() + " ";
                prefix = "";
            }
        }

        return r;

    }

}
