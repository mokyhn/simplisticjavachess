package com.simplisticchess;

import com.simplisticchess.piece.Piece;
import com.simplisticchess.board.BitBoard;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Color;

public final class State
{

    public Move move;
    public Color inMove;

    public int moveNumber = 0;

    public BitBoard bbposition; // Used wrt. check for draw by threefold repetition. Could be used in a hash table for search evaluations.

    public boolean blackCanCastleShort;
    public boolean blackCanCastleLong;
    public boolean whiteCanCastleShort;
    public boolean whiteCanCastleLong;

    public boolean drawFlag;
    public boolean mateFlag;

    public int halfMoveClock; // Number of halfmoves since the last pawn advance or capture.
    // Used to determine if a draw can be claimed under the fifty-move rule.

    /**
     * Number of half moves since last pawn move (including promotions) or
     * capture move When searching for threefold repetitions search from this
     * index...
     */
    public int halfMovesIndex3PosRepition;

    public Piece inCheckByPiece;

    public State()
    {
        this.move = new Move();
        this.moveNumber = 0;
        this.halfMoveClock = 0;
        this.halfMovesIndex3PosRepition = 0;
        this.inCheckByPiece = null;
        this.drawFlag = false;
        this.mateFlag = false;
    }

    public State(State state)
    {
        move = new Move(state.move);
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

        if (inCheckByPiece != null)
        {
            inCheckByPiece = new Piece(inCheckByPiece);
        }
    }

    @Override
    public String toString()
    {
        String s = "";

        String blackCastleShort = " ",
                blackCastleLong = " ",
                whiteCastleShort = " ",
                whiteCastleLong = " ";

        if (blackCanCastleShort)
        {
            blackCastleShort = "X";
        }
        if (blackCanCastleLong)
        {
            blackCastleLong = "X";
        }
        if (whiteCanCastleShort)
        {
            whiteCastleShort = "X";
        }
        if (whiteCanCastleLong)
        {
            whiteCastleLong = "X";
        }

        s = "\n----------------------------State----------------------------\n";
        if (drawFlag)
        {
            s = s + "It's a draw!\n";
        }
        if (mateFlag)
        {
            s = s + "Mate!\n";
        }
        s = s + "Black can castle long: [" + blackCastleLong + "],       Black can castle short: [" + blackCastleShort + "]\n";
        s = s + "White can castle long: [" + whiteCastleLong + "],       White can castle short: [" + whiteCastleShort + "]\n";
        s = s + "Number of half moves since last pawn move: " + halfMoveClock + "\n";
        s = s + "Index searched from when checking 3 fold repetition: " + halfMovesIndex3PosRepition + "\n";
        s = s + "Ply Move number " + moveNumber + "\n";

        return s;
    }

}
