package com.simplisticchess.cli;

import com.simplisticchess.ChessGame;

/**
 *
 * @author Morten Kühnrich
 */
public class ChessCLI
{
    private final CommandLineInterface cli = new CommandLineInterface();
    private final ChessGame chessGame = new ChessGame();
    
    public ChessCLI()
    {
        cli.registerCommand(new CommandQuit());
        cli.registerCommand(new CommandNew(chessGame));
    }
    
    public CommandLineInterface getCLI() 
    {
        return cli;
    }
    
    public static void main(String param[])
    {
        ChessCLI cli = new ChessCLI();
        cli.getCLI().start();
    }
}

