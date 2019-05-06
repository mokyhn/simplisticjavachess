package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.*;
import com.simplisticjavachess.misc.*;
import com.simplisticjavachess.move.*;
import com.simplisticjavachess.piece.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KingMoveGeneratorTest {

	@Test
	public void kingMovesCenter() {
		Board board = Board.createFromLetters("Kd4 w");
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("Kd4");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[d4-c4, d4-e4, d4-d5, d4-d3, d4-c5, d4-c3, d4-e5, d4-e3]", IteratorUtils.toString(moves));
	}


	@Test
	public void kingMovesCorner1() {
		Board board = Board.createFromLetters("Kh8 w");
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("Kh8");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[h8-g8, h8-h7, h8-g7]", IteratorUtils.toString(moves));
	}


	@Test
	public void kingMovesCorner2() {
		Board board = Board.createFromLetters("Kh1 w");
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("Kh1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[h1-g1, h1-h2, h1-g2]", IteratorUtils.toString(moves));
	}


	@Test
	public void kingMovesCorner3() {
		Board board = Board.createFromLetters("Ka1 w");
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("Ka1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[a1-b1, a1-a2, a1-b2]", IteratorUtils.toString(moves));
	}

	@Test
	public void kingMovesCorner4() {
		Board board = Board.createFromLetters("Ka8 w");
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("Ka8");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[a8-b8, a8-a7, a8-b7]", IteratorUtils.toString(moves));
	}

	@Test
	public void kingMovesCenterButPartiallyBlocked() {
		Board board = Board.createFromLetters("Kd4 Pd5 w");
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("Kd4");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[d4-c4, d4-e4, d4-d3, d4-c5, d4-c3, d4-e5, d4-e3]", IteratorUtils.toString(moves));
	}

	@Test
	public void kingCannotMoveOverThreatenedFields() {
		Board board = Board.createFromLetters("Kd4 ra5 ra3 rc1 re1 w");
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("Kd4");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[]", IteratorUtils.toString(moves));
	}


	@Test
	public void kingTakes1() {
		Board board = Board.createFromLetters("kd4 Nd3 Nd5 Ne4 Nc4 b");
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("kd4");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[d4xc4, d4xe4, d4xd5, d4xd3]", IteratorUtils.toString(moves));
	}

	@Test
	public void kingTakes2() {
		Board board = Board.createFromLetters("kd4 Nc5 Nc3 Ne5 Ne3 b");
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("kd4");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[d4xc5, d4xc3, d4xe5, d4xe3]", IteratorUtils.toString(moves));
	}


	@Test
	public void castlingShort() {
		Board board = Board.createFromLetters("Ke1 Rh1 w");
		board.setCanCastleShort(true, Color.WHITE);
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertTrue(IteratorUtils.toString(moves).contains("o-o"));
	}

	@Test
	public void cannotCastleShort() {
		Board board = Board.createFromLetters("Ke1 Rh1 w");
		board.setCanCastleShort(false, Color.WHITE);
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertFalse(IteratorUtils.toString(moves).contains("o-o"));
	}

	@Test
	public void castlingLong() {
		Board board = Board.createFromLetters("Ke1 Ra1 w");
		board.setCanCastleLong(true, Color.WHITE);
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertTrue(IteratorUtils.toString(moves).contains("o-o-o"));
	}

	@Test
	public void cannotCastleLong() {
		Board board = Board.createFromLetters("Ke1 Ra1 w");
		board.setCanCastleLong(false, Color.WHITE);
		System.out.println(board.asASCII());
		Piece piece = Piece.fromPositionCode("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertFalse(IteratorUtils.toString(moves).contains("o-o-o"));
	}

}
