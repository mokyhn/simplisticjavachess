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
     * @return the piece that attacks the location
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
        return bishopLocation.diagonalEquals(attackedLocation) && position.freeSquares(bishopLocation, attackedLocation);
    }
    
   private static boolean rookAttack(Position position, Piece attacker, Location attackedLocation)
    {
        Location rookLocation = attacker.getLocation();
        return (rookLocation.fileEquals(attackedLocation) || rookLocation.rankEquals(attackedLocation)) &&
                position.freeSquares(rookLocation, attackedLocation);
    }
    
   private static boolean kingAttack(Piece p, Location location)
   {
        return p.getLocation().distanceTo(location) == 1;
   }
}
