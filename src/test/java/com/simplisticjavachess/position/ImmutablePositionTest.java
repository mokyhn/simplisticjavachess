package com.simplisticjavachess.position;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Morten Kühnrich
 */
public class ImmutablePositionTest {

    Position position;
    Piece piece;

    @Before
    public void before() {
        position = PositionIO.algebraic("w");
        piece = new Piece(new Location(7, 7), Color.WHITE, PieceType.KING);
    }

    @Test
    public void testAreRepresentationsIsomorphic() {
        Assert.assertTrue(areRepresentationsIsomorphic(PositionIO.algebraic("w")));

        position.insert(new Piece(new Location(2, 3), Color.BLACK, PieceType.KING));

        Assert.assertTrue(areRepresentationsIsomorphic(position));

        position.insert(new Piece(new Location(0, 0), Color.WHITE, PieceType.KING));
        Assert.assertTrue(areRepresentationsIsomorphic(position));

        position.insert(new Piece(new Location(7, 7), Color.BLACK, PieceType.PAWN));
        Assert.assertTrue(areRepresentationsIsomorphic(position));
    }

    private boolean areRepresentationsIsomorphic(Position position) {
        int n = 0;

        int x, y;
        for (x = 0; x < 8; x++) {
            for (y = 0; y < 8; y++) {
                if (position.getPiece(new Location(x, y)) != null) {
                    n++;
                }
            }
        }

        if (n != position.getPieces().size()) {
            return false;
        }

        for (Piece p1 : position.getPieces()) {
            Piece p2 = position.getPiece(p1.getLocation());
            if (!p1.equals(p2)) {
                return false;
            }
        }

        return true;
    }


    @Test
    public void testInsert() {
        Position tmp = position.insert(piece);
        Assert.assertEquals(piece, tmp.getPiece(new Location(7, 7)));
    }

    @Test(expected = IllegalStateException.class)
    public void testDoubleInsert() {
        Position tmp = position.insert(piece);
        tmp.insert(piece);
    }

    @Test
    public void testRemove() {
        Position tmp;
        tmp = position.insert(piece);
        tmp = tmp.remove(piece);
        Assert.assertNull(tmp.getPiece(piece.getLocation()));
    }

    @Test(expected = IllegalStateException.class)
    public void testDoubleOfPieceNotPresent() {
        position.remove(piece);
        position.remove(piece);
    }

    @Test
    public void testFreeSquareEmptySquare() {
        Assert.assertTrue(position.freeSquare(new Location(4, 5)));
    }

    @Test
    public void testFreeSquareNonEmptySquare() {
        position = position.insert(piece);
        Assert.assertFalse(position.freeSquare(piece.getLocation()));
    }

    @Test
    public void testFreeSquares() {
        Position position = PositionIO.algebraic("Kd4 Rh4 w");
        Assert.assertTrue(position.freeSquares(Location.parse("d4"), Location.parse("h4")));
    }

    @Test
    public void testFreeSquares2() {
        Position position = PositionIO.FEN("r3r1Qk/6pp/8/6N1/8/1B6/1PPP4/2K5 b");
        Assert.assertTrue(position.freeSquares(Location.parse("g8"), Location.parse("e8")));
    }
}
