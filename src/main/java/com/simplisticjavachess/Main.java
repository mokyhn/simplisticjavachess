package com.simplisticjavachess;

/**
 * @author Morten Kühnrich
 */
import com.simplisticjavachess.cli.ChessCLI;

class Main
{
    public static void main(String[] param) throws Exception
    {
        ChessCLI chessCLI = new ChessCLI();
        chessCLI.getCLI().start();
    }
}
