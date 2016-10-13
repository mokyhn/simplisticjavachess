package com.simplisticjavachess.cli.cecpcommands;

import com.simplisticjavachess.ChessGame;
import com.simplisticjavachess.cli.Command;

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

    public void execute(String[] arguments) throws Exception
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
