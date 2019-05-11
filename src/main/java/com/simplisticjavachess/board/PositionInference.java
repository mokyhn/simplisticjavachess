/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;

import java.util.Optional;


public class PositionInference
{
     /**
     * @param position the position
     * @param color a color to check for check
     * @return Is player with color in check?
     */
    public static boolean isInCheck(Position position, Color color)
    {
        Optional<Piece> king = position.getKing(color);

        if (king.isPresent()) {
            return PositionInference.attacks(position, king.get().getLocation(), color.opponent()) != null;
        }
        else {
            throw new IllegalStateException();
        }
    }


    /**
     * 
     * @param position - the given position
     * @param location - the location is under attack by the return piece. If
     * null is returned then the location is not under attack.
     * @param attackerColor - color of the attacker
     * @return null if the location is not attacked, and else the piece that attacks the location
     */
    public static Piece attacks(Position position, Location location, Color attackerColor)
    {
        for (Piece attackerPiece : position.getPieces())
        {
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
                        if (rookAttack(position, attackerPiece, location))
                        {
                            return attackerPiece;
                        }
                        break;
                    case BISHOP:
                        if (bishopAttack(position, attackerPiece, location))
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
                        if (rookAttack(position, attackerPiece, location) ||
                            bishopAttack(position, attackerPiece, location))
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

    private static boolean pawnAttack(Piece attacker, Location attackedLocation)
    {
        return (attackedLocation.getY() == attacker.getyPos() + attacker.getColor().getColor())
                && ((attackedLocation.getX() == attacker.getxPos() + 1)
                || (attackedLocation.getX() == attacker.getxPos() - 1));
    }

    
    private static boolean knightAttack(Piece attacker, Location location)
    {
        
        return (
                attacker.getLocation().verticalDistance(location) == 1 &&
                attacker.getLocation().horizontalDistance(location) == 2
                )
               ||
               (
                attacker.getLocation().verticalDistance(location) == 2 &&
                attacker.getLocation().horizontalDistance(location) == 1
                );
    }
         
 
    private static boolean bishopAttack(Position position, Piece attacker, Location attackedLocation)
    {
        Location bishopLocation = attacker.getLocation();

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
    
   private static boolean rookAttack(Position position, Piece attacker, Location attackedLocation)
    {
        Location rookLocation = attacker.getLocation();

        assert(!rookLocation.equals(attackedLocation));

        int lowY; // From y pos
        int highY; // To y pos
        boolean allFree;

        if (rookLocation.onSameFile(attackedLocation))
        {
            allFree = true;

            lowY = Math.min(rookLocation.getY(), attackedLocation.getY());
            highY = Math.max(rookLocation.getY(), attackedLocation.getY());

            for (int i = lowY + 1; i < highY; i++)
            {
                if (!position.freeSquare(rookLocation.getX(), i))
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

        int lowX; // From x pos
        int highX; // To x pos

        if (rookLocation.rankEquals(attackedLocation))
        {
            allFree = true;

            lowX = Math.min(rookLocation.getX(), attackedLocation.getX());
            highX = Math.max(rookLocation.getX(), attackedLocation.getX());

            for (int i = lowX + 1; i < highX; i++) //TODO: Introduce freeInbetween(Location a, Location b) in Position
            {
                if (!position.freeSquare(i, rookLocation.getY()))
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
