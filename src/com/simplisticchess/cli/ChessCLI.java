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
        /*
        Chess Engine Communication Protocol commands
        */
        cli.registerCommand(new CommandGo(chessGame));
        cli.registerCommand(new CommandNew(chessGame));
        cli.registerCommand(new CommandUndo(chessGame));
        cli.registerCommand(new CommandQuit());
        
        /*
        Customized commands not part of the Chess Engine Communication Protocol
        */
        cli.registerCommand(new CommandPrint(chessGame));
        
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

