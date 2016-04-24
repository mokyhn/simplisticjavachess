package com.simplisticchess.game;

import com.simplisticchess.board.BitBoard;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Color;

public final class State
{

    public Move move;
    public Color inMove;

    public int moveNumber;
    

    // Used wrt. check for draw by threefold repetition. 
    // Could also be used in a hash table for search evaluations.
    public BitBoard bbposition; 

    public boolean blackCanCastleShort;
    public boolean blackCanCastleLong;
    public boolean whiteCanCastleShort;
    public boolean whiteCanCastleLong;

    public boolean drawFlag;
    public boolean mateFlag;

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
        move = state.move == null ? null : new Move(state.move);
        inMove = state.inMove;
        moveNumber = state.moveNumber;
        blackCanCastleLong = state.blackCanCastleLong;
        blackCanCastleShort = state.blackCanCastleShort;
        whiteCanCastleLong = state.whiteCanCastleLong;
        whiteCanCastleShort = state.whiteCanCastleShort;
        halfMoveClock = state.halfMoveClock;
        halfMovesIndex3PosRepition = state.halfMovesIndex3PosRepition;
        drawFlag = state.drawFlag;
        mateFlag = state.mateFlag;

        if (this.bbposition != null)
        {
            bbposition = new BitBoard(state.bbposition);
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
        if (drawFlag)
        {
            result = result + "It's a draw!\n";
        }
        if (mateFlag)
        {
            result = result + "Mate!\n";
        }
        result = result + "Black can castle long: [" + blackCastleLong + "],       Black can castle short: [" + blackCastleShort + "]\n";
        result = result + "White can castle long: [" + whiteCastleLong + "],       White can castle short: [" + whiteCastleShort + "]\n";
        result = result + "Number of half moves since last pawn move: " + halfMoveClock + "\n";
        result = result + "Index searched from when checking 3 fold repetition: " + halfMovesIndex3PosRepition + "\n";
        result = result + "Ply Move number " + moveNumber + "\n";

        return result;
    }

}
