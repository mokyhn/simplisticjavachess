package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.position.*;
import com.simplisticjavachess.misc.*;
import com.simplisticjavachess.move.*;
import com.simplisticjavachess.piece.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class KnightMoveGeneratorTest {

	@Test
	public void knightMoves() {
		Position board = PositionIO.algebraic("Nd4 w");
		Piece piece = Piece.parse("Nd4");
		Iterator<Move> moves = new KnightMoveGenerator().generateMoves(board, piece);
		assertEquals("[d4-b5, d4-b3, d4-c6, d4-e6, d4-c2, d4-e2, d4-f5, d4-f3]", IteratorUtils.toString(moves));
	}

	@Test
	public void knightMovesNearEdge() {
		Position board = PositionIO.algebraic("Ng2 w");
		Piece piece = Piece.parse("Ng2");
		Iterator<Move> moves = new KnightMoveGenerator().generateMoves(board, piece);
		assertEquals("[g2-e3, g2-e1, g2-f4, g2-h4]", IteratorUtils.toString(moves));
	}

	@Test
	public void knightTakes() {
		Position board = PositionIO.algebraic("Nd4 pb5 pb3 pc6 pe6 pc2 pe2 pf5 pf3 w");
		Piece piece = Piece.parse("Nd4");
		Iterator<Move> moves = new KnightMoveGenerator().generateMoves(board, piece);
		assertEquals("[d4xb5, d4xb3, d4xc6, d4xe6, d4xc2, d4xe2, d4xf5, d4xf3]", IteratorUtils.toString(moves));
	}


}