package com.simplisticjavachess.movegenerator;

import com.simplisticjavachess.piece.Color;

public class OpeningBook
{
    private static FixedMoveGenerator fixedMoveGenerator;

    // TODO: Bad idea with static - refactor
    static
    {
        fixedMoveGenerator = new FixedMoveGenerator();

        String startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

        /** When computer plays white *******************************************/
        // Start with e4
        fixedMoveGenerator.add(startPos, "e2e4");

        // Tarrash variant of French
        fixedMoveGenerator = fixedMoveGenerator.addFromMoves(
                startPos,
                "e2e4 e7e6 d2d4 d7d5 b1d2 g8f6 e4e5", Color.WHITE);

        /** When computer plays black *******************************************/
        // Sicilian answer to e4
        fixedMoveGenerator = fixedMoveGenerator.addFromMoves(startPos,
                "e2e4 c7c5 g1f3 b8c6 d2d4 c5d4 f3d4 e7e5 d4b5 d7d6 b1c3 g8f6 c1g5 a7a6 b5a3 b7b5 c3d5 f8e7 g5f6 e7f6 d5f6 d8f6", Color.BLACK);

        // Answer to d4
        fixedMoveGenerator = fixedMoveGenerator.addFromMoves(startPos,
                "d2d4 g8f6 c2c4 g7g6 b1c3 f8g7 e2e4 d7d6 f1d3 b8c6 d4d5 c6d4 d3b1 c7c5", Color.BLACK);


    }



    public static FixedMoveGenerator get()
    {
        return fixedMoveGenerator;
    }
}
