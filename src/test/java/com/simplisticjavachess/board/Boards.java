/**
 *
 * @author Morten Kühnrich
 */


package com.simplisticjavachess.board;

public class Boards
{

    public final static Board WHITE_READY_TO_PROMOTE() { return Board.createFromFEN("8/PPPPPPPP/K/8/8/k/pppppppp/8 w"); } 
    public final static Board BLACK_READY_TO_PROMOTE() { return Board.createFromFEN("8/PPPPPPPP/K/8/8/k/pppppppp/8 b"); } 
    
    public final static Board WHITE_READY_TO_CAPTURE_PROMOTE() { return Board.createFromFEN("nnnnnnnn/PPPPPPPP/8/K7/7k/8/pppppppp/NNNNNNNN w"); } 
    public final static Board BLACK_READY_TO_CAPTURE_PROMOTE() { return Board.createFromFEN("nnnnnnnn/PPPPPPPP/8/K7/7k/8/pppppppp/NNNNNNNN b"); }
            
}
