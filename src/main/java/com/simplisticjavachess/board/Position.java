/**
 * @author Morten Kühnrich
 *
 */

package com.simplisticjavachess.board;

import com.simplisticjavachess.piece.Piece;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public class Position
{
    private final Map<Location, Piece> piecesMap;
    private final Stack<Command> undoStack;
    
    public Position()
    {
        piecesMap = new HashMap<>();
        undoStack = new Stack<>();
    }

    public Position(Position position)
    {
        this.piecesMap = new HashMap<>(position.piecesMap);
        this.undoStack = (Stack<Command>) position.undoStack.clone();
    }
 
    public void doCommand(Command command)
    {
        undoStack.push(command);
        doCommandAux(command);
    }
    
    private void doCommandAux(Command command)
    {
        if (command instanceof InsertCommand)
        {
            insertPiece(((InsertCommand) command).getPiece());
        }
        else
        if (command instanceof RemoveCommand)
        {
            Piece piece = ((RemoveCommand) command).getPiece();
            removePiece(piece);
        }
        else
        if (command instanceof MoveCommand)
        {
            movePiece(((MoveCommand) command).getPiece(), ((MoveCommand) command).getNewLocation());
        }
        else
        if (command instanceof ComposedCommand)
        {
            ((ComposedCommand) command).getCommands().forEach(this::doCommandAux);
        }
        else
        {
            throw new IllegalStateException();
        }
        
    }
    
    public void undo()
    {
        Command command = undoStack.pop();    
        undoAux(command);
    }
    
    
    private void undoAux(Command command)
    {
        if (command instanceof InsertCommand)
        {
            removePiece(((InsertCommand) command).getPiece());
        }
        else
        if (command instanceof RemoveCommand)
        {
            insertPiece(((RemoveCommand) command).getPiece());
        }
        else
        if (command instanceof MoveCommand)
        {
            Piece piece = getPiece(((MoveCommand) command).getNewLocation());
            Location oldLocation = ((MoveCommand) command).getPiece().getLocation();
            movePiece(piece, oldLocation);
        }
        else
        if (command instanceof ComposedCommand)
        {
            List<Command> commands = ((ComposedCommand) command).getCommands();
            
            Collections.reverse(commands);
            
            commands.forEach(this::undoAux);
        }
        else
        {
            throw new IllegalStateException();
        }
    }
  
    
    private void insertPiece(Piece piece)
    {
        if (piecesMap.containsKey(piece.getLocation()))
        {
            throw new IllegalStateException("Tried to insert piece at a location at an occupied location");
        }
        else
        {
            piecesMap.put(piece.getLocation(), piece);
        }
    }
       
    public Piece getPiece(Location location)
    {
        return piecesMap.get(location);
    }
  
    public Collection<Piece> getPieces() 
    {
        return piecesMap.values();
    }
        
    /**
     * Remove a piece
     * @param piece - to remove
     * @return the removed piece
     */
    private void removePiece(Piece piece)
    {    
        if (piece == null)
        {
             throw new IllegalStateException("Tried to remove a piece which was not there");
        }
        if (piecesMap.containsKey(piece.getLocation()))
        {
            piecesMap.remove(piece.getLocation());
        }
        else
        {
             throw new IllegalStateException("Tried to remove a piece which was not there");
        }

    }
   
    private void movePiece(Piece piece, Location to)
    {
        if (piecesMap.containsKey(to))
        {
            throw new IllegalStateException();
        }
        else
        {
            removePiece(piece);
            Piece newPiece = piece.updateLocation(to);
            insertPiece(newPiece);
        }
    }

    public boolean freeSquare(Location location)
    {
        return freeSquare(location.getX(), location.getY());
    }
    
    public boolean freeSquare(int x, int y)
    {
        return !piecesMap.containsKey(new Location(x, y));
    }

    public String getPositionString()
    {
        int x, y;
        Piece p;
        String s = "\n _______________\n";

        for (y = 7; y >= 0; y--)
        {
            for (x = 0; x < 8; x++)
            {
                s = s + " ";
                p = getPiece(new Location(x, y));
                if (p == null)
                {
                    s = s + ".";
                } else
                {
                    s = s + p.toString();
                }
            }
            s = s + ("     " + (y + 1)) + "\n";
        } // end last for-loop
        s = s + " _______________\n";
        s = s + " a b c d e f g h\n";
        return s;
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof Position)
        {
            Position position = (Position) object;
            return this.piecesMap.equals(position.piecesMap);
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(piecesMap.values());
    }

}
