package com.simplisticjavachess.cli.cecpcommands;

import com.simplisticjavachess.game.ChessGame;
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

    @Override
    public boolean isApplicable(String str)
    {
        return str.matches("go");
    }

    @Override
    public void execute(String[] arguments) throws Exception
    {
        chessGame.go();
    }


    @Override
    public String helpCommand()
    {
        return "go";
    }

    @Override
    public String helpExplanation()
    {
        return "Engine plays the color that is on move";
    }

}
