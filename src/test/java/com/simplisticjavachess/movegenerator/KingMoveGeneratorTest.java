package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.board.BoardParser;
import com.simplisticjavachess.board.Position;
import com.simplisticjavachess.misc.IteratorUtils;
import com.simplisticjavachess.move.Move;
import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KingMoveGeneratorTest {

	@Test
	public void kingMovesCenter() {
		Position board = BoardParser.algebraic("Kd4 w");
		Piece piece = Piece.parse("Kd4");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[d4-c4, d4-e4, d4-d5, d4-d3, d4-c5, d4-c3, d4-e5, d4-e3]", IteratorUtils.toString(moves));
	}


	@Test
	public void kingMovesCorner1() {
		Position board = BoardParser.algebraic("Kh8 w");
		Piece piece = Piece.parse("Kh8");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[h8-g8, h8-h7, h8-g7]", IteratorUtils.toString(moves));
	}


	@Test
	public void kingMovesCorner2() {
		Position board = BoardParser.algebraic("Kh1 w");
		Piece piece = Piece.parse("Kh1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[h1-g1, h1-h2, h1-g2]", IteratorUtils.toString(moves));
	}


	@Test
	public void kingMovesCorner3() {
		Position board = BoardParser.algebraic("Ka1 w");
		Piece piece = Piece.parse("Ka1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[a1-b1, a1-a2, a1-b2]", IteratorUtils.toString(moves));
	}

	@Test
	public void kingMovesCorner4() {
		Position board = BoardParser.algebraic("Ka8 w");
		Piece piece = Piece.parse("Ka8");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[a8-b8, a8-a7, a8-b7]", IteratorUtils.toString(moves));
	}

	@Test
	public void kingMovesCenterButPartiallyBlocked() {
		Position board = BoardParser.algebraic("Kd4 Pd5 w");
		Piece piece = Piece.parse("Kd4");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[d4-c4, d4-e4, d4-d3, d4-c5, d4-c3, d4-e5, d4-e3]", IteratorUtils.toString(moves));
	}

	@Test
	public void kingCannotMoveOverThreatenedFields() {
		Position board = BoardParser.algebraic("Kd4 ra5 ra3 rc1 re1 w");
		Piece piece = Piece.parse("Kd4");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[]", IteratorUtils.toString(moves));
	}


	@Test
	public void kingTakes1() {
		Position board = BoardParser.algebraic("kd4 Nd3 Nd5 Ne4 Nc4 b");
		Piece piece = Piece.parse("kd4");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[d4xc4, d4xe4, d4xd5, d4xd3]", IteratorUtils.toString(moves));
	}

	@Test
	public void kingTakes2() {
		Position board = BoardParser.algebraic("kd4 Nc5 Nc3 Ne5 Ne3 b");
		Piece piece = Piece.parse("kd4");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertEquals("[d4xc5, d4xc3, d4xe5, d4xe3]", IteratorUtils.toString(moves));
	}


	@Test
	public void castleShort() {
		Position board = BoardParser.algebraic("Ke1 Rh1 w");
		board = board.setCanCastleShort(true, Color.WHITE);
		Piece piece = Piece.parse("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertTrue(IteratorUtils.toString(moves).contains("o-o"));
	}

	@Test
	public void cannotCastleShortOverThreat1() {
		Position board = BoardParser.algebraic("Ke1 Rh1 bd3 w");
		board = board.setCanCastleShort(true, Color.WHITE);
		Piece piece = Piece.parse("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertFalse(IteratorUtils.toString(moves).contains("o-o"));
	}

	@Test
	public void cannotCastleShortOverThreat2() {
		Position board = BoardParser.algebraic("Ke1 Rh1 bd4 w");
		board = board.setCanCastleShort(true, Color.WHITE);
		Piece piece = Piece.parse("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertFalse(IteratorUtils.toString(moves).contains("o-o"));
	}

	@Test
	public void cannotCastleShortOutOfCheck() {
		Position board = BoardParser.algebraic("Ke1 Rh1 re4 w");
		board = board.setCanCastleShort(true, Color.WHITE);
		Piece piece = Piece.parse("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertFalse(IteratorUtils.toString(moves).contains("o-o"));
	}



	@Test
	public void cannotCastleShort() {
		Position board = BoardParser.algebraic("Ke1 Rh1 w");
		board = board.setCanCastleShort(false, Color.WHITE);
		Piece piece = Piece.parse("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertFalse(IteratorUtils.toString(moves).contains("o-o"));
	}

	@Test
	public void castlingLong() {
		Position board = BoardParser.algebraic("Ke1 Ra1 w");
		board = board.setCanCastleLong(true, Color.WHITE);
		Piece piece = Piece.parse("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertTrue(IteratorUtils.toString(moves).contains("o-o-o"));
	}

	@Test
	public void cannotCastleLongOverThreat1() {
		Position board = BoardParser.algebraic("Ke1 Ra1 rc8 w");
		board = board.setCanCastleLong(true, Color.WHITE);
		Piece piece = Piece.parse("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertFalse(IteratorUtils.toString(moves).contains("o-o-o"));
	}

	@Test
	public void cannotCastleLongOverThreat2() {
		Position board = BoardParser.algebraic("Ke1 Ra1 rd8 w");
		board = board.setCanCastleLong(true, Color.WHITE);
		Piece piece = Piece.parse("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertFalse(IteratorUtils.toString(moves).contains("o-o-o"));
	}

	@Test
	public void cannotCastleLongOutOfCheck() {
		Position board = BoardParser.algebraic("Ke1 Ra1 re8 w");
		board = board.setCanCastleLong(true, Color.WHITE);
		Piece piece = Piece.parse("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertFalse(IteratorUtils.toString(moves).contains("o-o-o"));
	}


	@Test
	public void cannotCastleLong() {
		Position board = BoardParser.algebraic("Ke1 Ra1 w");
		board = board.setCanCastleLong(false, Color.WHITE);
		Piece piece = Piece.parse("Ke1");
		Iterator<Move> moves = new KingMoveGenerator().generateMoves(board, piece);
		assertFalse(IteratorUtils.toString(moves).contains("o-o-o"));
	}

}
