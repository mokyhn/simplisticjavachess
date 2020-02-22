package com.simplisticjavachess.movegenerator;

public class OpeningMoves
{
    private static OpeningMoveGenerator openingMoveGenerator;

    static
    {
        openingMoveGenerator = new OpeningMoveGenerator();

        // When computer plays white
        openingMoveGenerator.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "e2e4");  // Start with e4


        // When computer plays black
        openingMoveGenerator.add("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1", "c7c5"); // Sicilian answer to e4
    }



    public static OpeningMoveGenerator get()
    {
        return openingMoveGenerator;
    }
}
