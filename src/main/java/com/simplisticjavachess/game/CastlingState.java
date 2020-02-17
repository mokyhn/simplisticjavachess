package com.simplisticjavachess.game;

import com.simplisticjavachess.piece.Color;

public class CastlingState
{

	private boolean whiteShort;
	private boolean whiteLong;
	private boolean blackShort;
	private boolean blackLong;

	public static final CastlingState NOBODY_CAN_CASTLE = new CastlingState(false, false, false, false);

	private final static int WHITE_SHORT = -365235027;
	private final static int WHITE_LONG  =  1561152162;
	private final static int BLACK_SHORT = -1485870876;
	private final static int BLACK_LONG  =  145385139;

	private int chessHashCode;


	public CastlingState(boolean whiteShort, boolean whiteLong, boolean blackShort, boolean blackLong)
	{
		this.whiteShort = whiteShort;
		this.whiteLong = whiteLong;
		this.blackShort = blackShort;
		this.blackLong = blackLong;
		this.chessHashCode = (whiteShort ? WHITE_SHORT : 0) ^ (whiteLong ? WHITE_LONG : 0) ^
				             (blackShort ? BLACK_SHORT : 0) ^ (blackLong ? BLACK_LONG : 0) ;
	}

	public CastlingState setCanCastleShort(Color color, boolean state)
	{
		switch (color)
		{
			case BLACK:
				return new CastlingState(whiteShort, whiteLong, state, blackLong);
			case WHITE:
				return new CastlingState(state, whiteLong, blackShort, blackLong);
			default:
				throw new IllegalStateException("Unreachable statement");
		}
	}

	public CastlingState setCanCastleLong(Color color, boolean state)
	{
		switch (color)
		{
			case BLACK:
				return new CastlingState(whiteShort, whiteLong, blackShort, state);
			case WHITE:
				return new CastlingState(whiteShort, state, blackShort, blackLong);
			default:
				throw new IllegalStateException("Unreachable statement");
		}
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
		{
			return true;
		}
		if (other instanceof CastlingState)
		{
			CastlingState that = (CastlingState) other;
			return  this.whiteShort == that.whiteShort &&
					this.whiteLong  == that.whiteLong  &&
					this.blackShort == that.blackShort &&
					this.blackLong  == that.blackLong;
		} else
		{
			return false;
		}
	}

	public int getChessHashCode() {
		return chessHashCode;
	}

	@Override
	public int hashCode()
	{
		return getChessHashCode();
	}

	@Override
	public String toString()
	{
		String result = "";
		String blackCastleShort = canCastleShort(Color.BLACK) ? "X" : " ";
		String blackCastleLong  = canCastleLong(Color.BLACK) ? "X" : " ";
		String whiteCastleShort = canCastleShort(Color.WHITE) ? "X" : " ";
		String whiteCastleLong  = canCastleLong(Color.WHITE)  ? "X" : " ";
		result = result + "Black can castle long: [" + blackCastleLong + "],       Black can castle short: [" + blackCastleShort + "]\n";
		result = result + "White can castle long: [" + whiteCastleLong + "],       White can castle short: [" + whiteCastleShort + "]\n";
		return result;
	}

	public boolean canCastleShort(Color color)
	{
		return Color.WHITE.equals(color) ? whiteShort : blackShort;
	}

	public boolean canCastleLong(Color color)
	{
		return Color.WHITE.equals(color) ? whiteLong : blackLong;
	}

}
