package com.simplisticjavachess.game;

import com.simplisticjavachess.piece.Color;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class CastlingState
{
	private final Set<Color> castleShort;
	private final Set<Color> castleLong;

	public static final CastlingState NOBODY_CAN_CASTLE = new CastlingState();

	//private final int hashValue;

	private CastlingState() {
		this.castleShort = EnumSet.noneOf(Color.class);
		this.castleLong = EnumSet.noneOf(Color.class);
	}

	public CastlingState(Set<Color> castleShort, Set<Color> castleLong)
	{
		this.castleShort = EnumSet.copyOf(castleShort);
		this.castleLong = EnumSet.copyOf(castleLong);
	}

	public CastlingState setCanCastleShort(Color... colors)
	{
		CastlingState result = myClone();
		for (Color color : colors)
		{
			result.castleShort.add(color);
		}
		return result;
	}

	public CastlingState setCanCastleLong(Color... colors)
	{
		CastlingState result = myClone();
		for (Color color : colors)
		{
			result.castleLong.add(color);
		}
		return result;
	}

	public CastlingState setCannotCastleShort(Color color)
	{
		CastlingState result = myClone();
		result.castleShort.remove(color);
		return result;
	}

	public CastlingState setCannotCastleLong(Color color)
	{
		CastlingState result = myClone();
		result.castleLong.remove(color);
		return result;
	}

	private CastlingState myClone()
	{
		return new CastlingState(this.castleShort, this.castleLong);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CastlingState)) return false;
		CastlingState that = (CastlingState) o;
		return Objects.equals(castleShort, that.castleShort) &&
				Objects.equals(castleLong, that.castleLong);
	}

	//TODO: Fix this code, it could be buggy.
	public int getChessHashCode() {
		return Objects.hash(castleShort, castleLong);
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
		return castleShort.contains(color);
	}

	public boolean canCastleLong(Color color)
	{
		return castleLong.contains(color);
	}

}
