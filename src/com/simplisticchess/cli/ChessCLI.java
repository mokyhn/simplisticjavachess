package com.simplisticchess.cli;

import com.simplisticchess.cli.cecpcommands.CommandBlack;
import com.simplisticchess.cli.noncecpcommands.CommandPrint;
import com.simplisticchess.cli.cecpcommands.CommandUndo;
import com.simplisticchess.cli.cecpcommands.CommandQuit;
import com.simplisticchess.cli.cecpcommands.CommandNew;
import com.simplisticchess.cli.cecpcommands.CommandGo;
import com.simplisticchess.ChessGame;
import com.simplisticchess.cli.cecpcommands.CommandMove;
import com.simplisticchess.cli.cecpcommands.CommandSd;
import com.simplisticchess.cli.cecpcommands.CommandSetBoard;
import com.simplisticchess.cli.cecpcommands.CommandWhite;
import com.simplisticchess.cli.cecpcommands.CommandXBoard;

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
        cli.registerCommand(new CommandMove(chessGame));
        cli.registerCommand(new CommandNew(chessGame));
        cli.registerCommand(new CommandSetBoard(chessGame));
        cli.registerCommand(new CommandUndo(chessGame));
        cli.registerCommand(new CommandQuit());
        cli.registerCommand(new CommandSd(chessGame));
        cli.registerCommand(new CommandWhite(chessGame));
        cli.registerCommand(new CommandXBoard());
 
        /*
        Customized commands not part of the Chess Engine Communication Protocol
        */
        cli.registerCommand(new CommandPrint(chessGame));
        
    }
    
    public CommandLineInterface getCLI() 
    {
        return cli;
    }
    
}

