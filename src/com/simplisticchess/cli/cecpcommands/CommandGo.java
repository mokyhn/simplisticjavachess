package com.simplisticchess.cli.cecpcommands;

import com.simplisticchess.ChessGame;
import com.simplisticchess.cli.Command;

/**
 *
 * @author Morten Kühnrich
 */
@ChessEngineCommunicationProtocol
public class CommandGo implements Command
{

    private final ChessGame chessGame;

    public CommandGo(ChessGame chessGame)
    {
        this.chessGame = chessGame;
    }

    public boolean isApplicable(String str)
    {
        return str.matches("go");
    }

    public void execute(String[] arguments)
    {
        chessGame.go();
    }


    public String helpCommand()
    {
        return "go";
    }

    public String helpExplanation()
    {
        return "Engine plays the color that is on move";
    }

}
