package com.simplisticjavachess.piece;

import com.simplisticjavachess.position.Location;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PieceMapTest {

    PieceMap pieceMap;

    private static final Location location1 = Location.parse("d4");
    private static final Location location2 = Location.parse("a3");
    private static final Piece piece1 = new Piece(location1, 'K');
    private static final Piece piece2 = new Piece(location2, 'p');

    @Before
    public void before() {
        pieceMap = new PieceMap();
    }


    @Test
    public void insert() {
        assertFalse(pieceMap.insert(piece1).freeSquare(location1));
    }

    @Test(expected = IllegalStateException.class)
    public void doNotInsertTwice() {
        pieceMap.insert(piece1).insert(piece1);
    }

    @Test
    public void get() {
        assertEquals(piece1, pieceMap.insert(piece1).get(location1));
    }

    @Test
    public void getPieces() {
        assertEquals(2, pieceMap.insert(piece1).insert(piece2).getPieces().size());
    }

    @Test
    public void remove() {
        assertEquals(1, pieceMap.insert(piece1).insert(piece2).remove(piece1).getPieces().size());
    }

    @Test
    public void move() {
        PieceMap result = pieceMap.insert(piece2).move(piece2, Location.parse("a4"));
        assertTrue(result.freeSquare(location2));
        assertEquals(piece2.getType(), result.get(Location.parse("a4")).getType());
        assertEquals(piece2.getColor(), result.get(Location.parse("a4")).getColor());
    }

    @Test
    public void values() {
    }

    @Test
    public void getKing() {
        assertFalse(pieceMap.getKing(Color.WHITE).isPresent());
        assertTrue(pieceMap.insert(piece1).getKing(Color.WHITE).isPresent());
    }

    @Test
    public void freeSquare() {
        assertTrue(pieceMap.freeSquare(Location.parse("h2")));
    }

    @Test
    public void testFreeSquare() {
        assertTrue(pieceMap.freeSquare(3,4));
    }

}