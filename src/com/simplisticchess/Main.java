package com.simplisticchess;

/**
 * @author Morten Kühnrich
 */
import com.simplisticchess.cli.ChessCLI;

class Main
{
    private static final ChessCLI cli = new ChessCLI();

    public static void main(String param[]) throws Exception
    {
        cli.getCLI().start();
    }
}
