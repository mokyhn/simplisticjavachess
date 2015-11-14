package com.simplisticchess.cli;

import com.simplisticchess.cli.cecpcommands.CommandBlack;
import com.simplisticchess.cli.noncecpcommands.CommandPrint;
import com.simplisticchess.cli.cecpcommands.CommandUndo;
import com.simplisticchess.cli.cecpcommands.CommandQuit;
import com.simplisticchess.cli.cecpcommands.CommandNew;
import com.simplisticchess.cli.cecpcommands.CommandGo;
import com.simplisticchess.ChessGame;
import com.simplisticchess.cli.cecpcommands.CommandWhite;

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
        cli.registerCommand(new CommandBlack(chessGame));
        cli.registerCommand(new CommandGo(chessGame));
        cli.registerCommand(new CommandNew(chessGame));
        cli.registerCommand(new CommandUndo(chessGame));
        cli.registerCommand(new CommandQuit());
        cli.registerCommand(new CommandWhite(chessGame));
        
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

