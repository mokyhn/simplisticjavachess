package com.simplisticjavachess.board;

/**
 * The result of a move. Either the move is legal or not. Furthermore the resulting board is a part of the move result.
 */
public class MoveResult
{
	//TODO: Use moveResultType instead of boolean moveLegal
	private final Board board;
	private final boolean moveLegal;



	public MoveResult(boolean moveLegal, Board board) {
		this.moveLegal = moveLegal;
		this.board = board;
	}

	/**
	 * The legality of the move
	 * @return true (legal) or false (illegal)
	 */
	public boolean isMoveLegal() {
		return moveLegal;
	}

	/**
	 * The resulting board
	 * @return A board with the move performed
	 */
	public Board getBoard() {
		return board;
	}
}
