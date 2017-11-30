/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;


public class PositionInference
{
     /**
     * @param position the position
     * @param color a color
     * @return Is player with color color in check?
     */
    public static boolean isInCheck(Position position, Color color)
    {
        for (Piece p : position.getPieces())
        {
            if (p.getPieceType() == PieceType.KING && p.getColor() == color)
            {
                if (PositionInference.attacks(position, p.getLocation(), color) != null)
                {
                    return true;
                }
                }
            }
        return false;
    }

    // TODO: Consider this code
//    public static boolean isInCheck(Position position, Color color)
//    {
//        for (Piece p : position.getPieces())
//        {
//            if (p.getPieceType() == PieceType.KING)
//            {
//                if (p.getColor() == color)
//                {
//                    return  PositionInference.attacks(position, p.getLocation(), color) != null;
//                }
//                else
//                {
//                    return false;
//                }
//            }
//            
//        }
//        return false;
//    }
     
     
    /**
     * 
     * @param position - the given position
     * @param location - the location is under attack by the return piece. If
     * null is returned then the location is not under attack.
     * @param inMove - color of the player to move
     * @return null if the location is not attacked, and else the piece that attacks the location
     */
    public static Piece attacks(Position position, Location location, Color inMove)
    {
        Color attackerColor = inMove.opponent();
        
        for (Piece attackerPiece : position.getPieces())
        {
            // Chose one of opposite color
            if (attackerPiece.getColor() == attackerColor && !(attackerPiece.getLocation().equals(location)))
            {
                switch (attackerPiece.getPieceType())
                {
                    case PAWN:
                        if (pawnAttack(attackerPiece, location))
                        {
                            return attackerPiece;
                        }
                        break;
                    case ROOK:
                        if (rookAttack(position, attackerPiece.getLocation(), location))
                        {
                            return attackerPiece;
                        }
                        break;
                    case BISHOP:
                        if (bishopAttack(position, attackerPiece.getLocation(), location))
                        {
                            return attackerPiece;
                        }
                        break;
                    case KNIGHT:
                        if (knightAttack(attackerPiece, location))
                        {
                            return attackerPiece;
                        }
                        break;
                    case QUEEN:
                        if (rookAttack(position, attackerPiece.getLocation(), location) ||
                            bishopAttack(position, attackerPiece.getLocation(), location))
                        {
                            return attackerPiece;
                        }
                        break;
                    case KING:
                        if (kingAttack(attackerPiece, location))
                        {
                            return attackerPiece;
                        }
                        break;
                    default:
                }
            }
        }

        return null;
    }

    private static boolean pawnAttack(Piece pawn, Location attackedLocation)
    {
        return (attackedLocation.getY() == pawn.getyPos() + pawn.getColor().getColor())
                && ((attackedLocation.getX() == pawn.getxPos() + 1)
                || (attackedLocation.getX() == pawn.getxPos() - 1));
    }

    
    private static boolean knightAttack(Piece p, Location location)
    {
        
        return (
                p.getLocation().verticalDistance(location) == 1 &&
                p.getLocation().horizontalDistance(location) == 2
                )
               ||
               (
                p.getLocation().verticalDistance(location) == 2 &&
                p.getLocation().horizontalDistance(location) == 1
                );
    }
         
 
    private static boolean bishopAttack(Position position, Location bishopLocation, Location attackedLocation)
    {
        boolean allFree = true;

        int dx = attackedLocation.getX() - bishopLocation.getX();
        int dy = attackedLocation.getY() - bishopLocation.getY();
        // First condition that allows one to be threatened by a bishop
        if (Math.abs(dx) == Math.abs(dy))
        {
            int radius = Math.abs(dx);
            dx = dx / radius;
            dy = dy / radius;
            for (int ir = 1; ir < radius; ir++) // Radii
            {
                if (!position.freeSquare(ir * dx + bishopLocation.getX(), ir * dy + bishopLocation.getY()))
                {
                    allFree = false;
                    break;
                }
            }
            if (allFree)
            {
                return true;
            }
        }

        return false;
    }
    
   private static boolean rookAttack(Position position, Location rookLocation, Location attackedLocation)
    {
        Boolean allFree;
        int lowX, // From x pos
                highX, // To x pos
                lowY, // From y pos 
                highY, // To y pos
                ix, // Iterate x
                iy;    // Iterate y

        if (rookLocation.fileEquals(attackedLocation))
        {
            allFree = true;
            if (rookLocation.getY() < attackedLocation.getY())
            {
                lowY = rookLocation.getY();
                highY = attackedLocation.getY();
            } else
            {
                lowY = attackedLocation.getY();
                highY = rookLocation.getY();
            }
            for (iy = lowY + 1; iy < highY; iy++)
            {
                if (!position.freeSquare(rookLocation.getX(), iy))
                {
                    allFree = false;
                    break;
                }
            }
            if (allFree)
            {
                return true;
            }
        }
        if (rookLocation.rankEquals(attackedLocation))
        {
            allFree = true;
            if (rookLocation.getX() < attackedLocation.getX())
            {
                lowX = rookLocation.getX();
                highX = attackedLocation.getX();
            } else
            {
                lowX = attackedLocation.getX();
                highX = rookLocation.getX();
            }
            for (ix = lowX + 1; ix < highX; ix++)
            {
                if (!position.freeSquare(ix, rookLocation.getY()))
                {
                    allFree = false;
                    break;
                }
            }
            if (allFree)
            {
                return true;
            }
        }
        return false;
    }
    
   private static boolean kingAttack(Piece p, Location location)
   {
        return p.getLocation().distanceTo(location) == 1;
   }
}
