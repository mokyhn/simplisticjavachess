package com.simplisticjavachess;

/**
 * @author Morten Kühnrich
 */
import com.simplisticjavachess.cli.ChessCLI;

class Main
{
    private static final ChessCLI cli = new ChessCLI();

    public static void main(String param[]) throws Exception
    {
        cli.getCLI().start();
    }
}
