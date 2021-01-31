package com.simplisticjavachess.cli.cecpcommands;

import com.simplisticjavachess.game.ChessGame;
import com.simplisticjavachess.cli.Command;

import java.util.NoSuchElementException;

/**
 *
 * @author Morten Kühnrich
 */

@ChessEngineCommunicationProtocol
public class CommandUndo implements Command
{

    private final ChessGame chessGame;
    
    public CommandUndo(ChessGame chessGame) 
    {
        this.chessGame = chessGame;
    }
    
    @Override
    public boolean isApplicable(String str)
    {
        return str.matches("undo");
    }

    @Override
    public void execute(String[] arguments)
    {
        try {
            chessGame.undo();
        } catch (NoSuchElementException e) {
            System.out.println("No possible to undo in this position");
        }
    }

    @Override
    public String helpCommand()
    {
        return "undo";
    }

    @Override
    public String helpExplanation()
    {
        return "Undo the last move";
    }
    
}
