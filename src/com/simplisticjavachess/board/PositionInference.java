/**
 *
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Color;
import com.simplisticjavachess.piece.Piece;
import com.simplisticjavachess.piece.PieceType;
import com.simplisticjavachess.position.Location;


public class PositionInference
{
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

    public static Piece attacks(Position position, Location location, Color inMove)
    {
        for (Piece p : position.getPieces())
        {
            // Chose one of opposite color
            if (p.getColor() == inMove.opponent() && !(p.getLocation().equals(location)))
            {
                switch (p.getPieceType())
                {
                    case PAWN:
                        if ((location.getY() == p.getyPos() + p.getColor().getColor())
                                && ((location.getX() == p.getxPos() + 1)
                                || (location.getX() == p.getxPos() - 1)))
                        {
                            return p;
                        }
                        break;
                    case ROOK:
                        if (rookAttack(position, p.getLocation(), location))
                        {
                            return p;
                        }
                        break;
                    case BISHOP:
                        if (bishopAttack(position, p.getLocation(), location))
                        {
                            return p;
                        }
                        break;
                    case KNIGHT:
                        if (((location.getX() == p.getxPos() - 2) && (location.getY() == p.getyPos() + 1))
                                || ((location.getX() == p.getxPos() - 2) && (location.getY() == p.getyPos() - 1))
                                || ((location.getX() == p.getxPos() - 1) && (location.getY() == p.getyPos() - 2))
                                || ((location.getX() == p.getxPos() + 1) && (location.getY() == p.getyPos() + 2))
                                || ((location.getX() == p.getxPos() - 1) && (location.getY() == p.getyPos() + 2))
                                || ((location.getX() == p.getxPos() + 1) && (location.getY() == p.getyPos() - 2))
                                || ((location.getX() == p.getxPos() + 2) && (location.getY() == p.getyPos() + 1))
                                || ((location.getX() == p.getxPos() + 2) && (location.getY() == p.getyPos() - 1)))
                        {
                            return p;
                        }
                        break;
                    case QUEEN:
                        if (rookAttack(position, p.getLocation(), location)
                                || bishopAttack(position, p.getLocation(), location))
                        {
                            return p;
                        }
                        break;
                    case KING:
                        if ((location.getX() == p.getxPos() || location.getX() == p.getxPos() - 1 || location.getX() == p.getxPos() + 1)
                                && (location.getY() == p.getyPos() || location.getY() == p.getyPos() - 1 || location.getY() == p.getyPos() + 1))
                        {
                            return p;
                        }
                        break;
                    default:
                }
            }
        }

        return null;
    }

 
    public static boolean bishopAttack(Position position, Location l1, Location l2)
    {
        boolean allFree = true;

        int dx = l2.getX() - l1.getX();
        int dy = l2.getY() - l1.getY();
        // First condition that allows one to be threatened by a bishop
        if (Math.abs(dx) == Math.abs(dy))
        {
            int radius = Math.abs(dx);
            dx = dx / radius;
            dy = dy / radius;
            for (int ir = 1; ir < radius; ir++) // Radii
            {
                if (!position.freeSquare(ir * dx + l1.getX(), ir * dy + l1.getY()))
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
    
   public static boolean rookAttack(Position position, Location l1, Location l2)
    {
        Boolean allFree;
        int lowX, // From x pos
                highX, // To x pos
                lowY, // From y pos 
                highY, // To y pos
                ix, // Iterate x
                iy;    // Iterate y

        if (l1.fileEquals(l2))
        {
            allFree = true;
            if (l1.getY() < l2.getY())
            {
                lowY = l1.getY();
                highY = l2.getY();
            } else
            {
                lowY = l2.getY();
                highY = l1.getY();
            }
            for (iy = lowY + 1; iy < highY; iy++)
            {
                if (!position.freeSquare(l1.getX(), iy))
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
        if (l1.rankEquals(l2))
        {
            allFree = true;
            if (l1.getX() < l2.getX())
            {
                lowX = l1.getX();
                highX = l2.getX();
            } else
            {
                lowX = l2.getX();
                highX = l1.getX();
            }
            for (ix = lowX + 1; ix < highX; ix++)
            {
                if (!position.freeSquare(ix, l1.getY()))
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
    
}
