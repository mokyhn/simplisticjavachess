package com.simplisticjavachess.move;

import com.simplisticjavachess.move.MoveType;
import com.simplisticjavachess.piece.PieceType;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Morten Kühnrich
 */
public class MoveTypeTest
{
    @Test
    public void testIsCapture()
    {
        assertTrue(MoveType.CAPTURE.isCapture());
        assertTrue(MoveType.CAPTURE_ENPASSANT.isCapture());
        
        assertTrue(MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP.isCapture());
        assertTrue(MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT.isCapture());
        assertTrue(MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN.isCapture());
        assertTrue(MoveType.CAPTURE_AND_PROMOTE_TO_ROOK.isCapture());
        
        assertFalse(MoveType.CALL_FOR_DRAW.isCapture());
        assertFalse(MoveType.CASTLE_LONG.isCapture());
        assertFalse(MoveType.NORMALMOVE.isCapture());
    }
    
    @Test
    public void testIsCapturePromotion()
    {          
        assertTrue(MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP.isCapturePromotion());
        assertTrue(MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT.isCapturePromotion());
        assertTrue(MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN.isCapturePromotion());
        assertTrue(MoveType.CAPTURE_AND_PROMOTE_TO_ROOK.isCapturePromotion());
        
        assertFalse(MoveType.CALL_FOR_DRAW.isCapturePromotion());
        assertFalse(MoveType.CASTLE_LONG.isCapturePromotion());
        assertFalse(MoveType.NORMALMOVE.isCapturePromotion());
    }

    
    @Test
    public void testGetPromotionPiece() 
    {
        assertEquals(PieceType.KNIGHT, MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT.getPromotionPiece());
        assertEquals(PieceType.ROOK, MoveType.CAPTURE_AND_PROMOTE_TO_ROOK.getPromotionPiece());
        assert(MoveType.CALL_FOR_DRAW.getPromotionPiece() == null);
    }
    
}
