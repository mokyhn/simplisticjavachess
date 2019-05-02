package com.simplisticjavachess.game;

import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;

public final class State
{
    public int moveNumber;

    public Move move;
    public Color inMove;

    // Used to check for draw by threefold repetition. 
    public int hash; 

    private boolean blackCanCastleShort;
    private boolean blackCanCastleLong;
    private boolean whiteCanCastleShort;
    private boolean whiteCanCastleLong;

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
    public int halfMovesIndex3PosRepition;

    public State() {}
    
    public State(State state)
    {
        this.moveNumber = state.moveNumber;
        this.move = state.move == null ? null : state.move;
        this.inMove = state.inMove;
        this.blackCanCastleLong = state.blackCanCastleLong;
        this.blackCanCastleShort = state.blackCanCastleShort;
        this.whiteCanCastleLong = state.whiteCanCastleLong;
        this.whiteCanCastleShort = state.whiteCanCastleShort;
        this.halfMoveClock = state.halfMoveClock;
        this.halfMovesIndex3PosRepition = state.halfMovesIndex3PosRepition;
        this.gameResult = state.gameResult;

        this.hash = state.hash;
    }

    public void setCanCastleShort(boolean flag, Color color) 
    {
        if (color == Color.WHITE) 
        {
            whiteCanCastleShort = flag;
        } 
        else
        {
            blackCanCastleShort = flag;
        }
    }

    public void setCanCastleLong(boolean flag, Color color) 
    {
        if (color == Color.WHITE) 
        {
            whiteCanCastleLong = flag;
        } 
        else
        {
            blackCanCastleLong = flag;
        }
    }
    
    public boolean getCanCastleShort(Color color) 
    {
        return color == Color.WHITE ? whiteCanCastleShort : blackCanCastleShort;
    }
    
    public boolean getCanCastleShort()
    {
        return getCanCastleShort(inMove);
    }
    
    public boolean getCanCastleLong(Color color) 
    {
        return color == Color.WHITE ? whiteCanCastleLong : blackCanCastleLong;
    }
   
    public boolean getCanCastleLong()
    {
        return getCanCastleLong(inMove);
    }

    public boolean equals(Object object)
    {
        if (object instanceof State)
        {
            State other = (State) object;
            
            boolean hashMatch;
            hashMatch = this.hash == other.hash;                        
            
            boolean movesMatch;        
            movesMatch = this.move.equals(other.move);
            
            return this.blackCanCastleLong == other.blackCanCastleLong   &&
                   this.blackCanCastleShort == other.blackCanCastleShort &&
                   this.whiteCanCastleLong == other.whiteCanCastleLong &&
                   this.whiteCanCastleShort == other.whiteCanCastleShort && 
                   hashMatch &&
                   this.gameResult == other.gameResult &&
                   this.halfMoveClock == other.halfMoveClock &&
                   this.halfMovesIndex3PosRepition == other.halfMovesIndex3PosRepition &&
                   this.inMove == other.inMove &&
                   movesMatch &&
                   this.moveNumber == other.moveNumber;
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

        String blackCastleShort = blackCanCastleShort ? "X" : " ";
        String blackCastleLong  = blackCanCastleLong  ? "X" : " ";
        String whiteCastleShort = whiteCanCastleShort ? "X" : " ";
        String whiteCastleLong  = whiteCanCastleLong  ? "X" : " ";

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
        result = result + "Index searched from when checking 3 fold repetition: " + halfMovesIndex3PosRepition + "\n";

        return result;
    }

}
