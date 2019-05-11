package com.simplisticjavachess.board;

public class MoveResult
{
	private final boolean moveLegal;

	private final Board board;

	public MoveResult(boolean moveLegal, Board board) {
		this.moveLegal = moveLegal;
		this.board = board;
	}
	public boolean isMoveLegal() {
		return moveLegal;
	}

	public Board getBoard() {
		return board;
	}
}
