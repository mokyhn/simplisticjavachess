package com.simplisticjavachess.cli;

import com.simplisticjavachess.cli.cecpcommands.CommandBlack;
import com.simplisticjavachess.cli.noncecpcommands.CommandPrint;
import com.simplisticjavachess.cli.cecpcommands.CommandUndo;
import com.simplisticjavachess.cli.cecpcommands.CommandQuit;
import com.simplisticjavachess.cli.cecpcommands.CommandNew;
import com.simplisticjavachess.cli.cecpcommands.CommandGo;
import com.simplisticjavachess.ChessGame;
import com.simplisticjavachess.cli.cecpcommands.CommandMove;
import com.simplisticjavachess.cli.cecpcommands.CommandSd;
import com.simplisticjavachess.cli.cecpcommands.CommandSetBoard;
import com.simplisticjavachess.cli.cecpcommands.CommandWhite;
import com.simplisticjavachess.cli.cecpcommands.CommandXBoard;

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

