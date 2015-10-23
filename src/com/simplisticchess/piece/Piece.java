package com.simplisticchess.piece;

import static com.simplisticchess.piece.PieceType.*;

/**
 *
 * @author Morten Kühnrich
 */

public final class Piece {
    public int xPos,
               yPos;
    
    public PieceType type;

    public Color color;

    public Piece(int x, int y, Color c, PieceType t) {
        assert x >= 0 && x <= 7 && y >= 0 && y <= 7 : "Piece range error in x or y ";
        this.xPos  = x;
        this.yPos  = y;
        this.color = c;
        this.type  = t;
    }

    public Piece(Piece piece) 
    {
        this(piece.xPos, piece.yPos, piece.color, piece.type);
    }
    
    public boolean equals(Piece p) {
        if (p == null) {
            return false;
        }
        
        return p.color == this.color &&
               p.type  == this.type  &&
               p.xPos  == this.xPos  &&
               p.yPos  == this.yPos;
    }
    
   
    public static String getPieceLetter(Color color, PieceType pieceType) {
      String r = "";

      
        if (color == Color.BLACK) {

        switch (pieceType) {
            case PAWN:   r = "p"; break;
            case ROOK:   r = "r"; break;
            case BISHOP: r = "b"; break;
            case KNIGHT: r = "n"; break;
            case KING:   r = "k"; break;
            case QUEEN:  r = "q"; break;
        }
     }

        if (color == Color.WHITE) {
            switch (pieceType) {
            case PAWN:   r = "P"; break;
            case ROOK:   r = "R"; break;
            case BISHOP: r = "B"; break;
            case KNIGHT: r = "N"; break;
            case KING:   r = "K"; break;
            case QUEEN:  r = "Q"; break;
            }
        }
      return r;    
    }
    

    public Piece(int x, int y, char pieceLetter)  {
        assert x >= 0 && x <= 7 && y >= 0 && y <= 7 : "Piece range error in x or y ";

        xPos = x;
        yPos = y;

        switch (pieceLetter) {
            case 'p': type  = PAWN;
                      color = Color.BLACK;
                      break;
            case 'r': type  = ROOK;
                      color = Color.BLACK;
                      break;
            case 'n': type  = KNIGHT;
                      color = Color.BLACK;
                      break;
            case 'b': type  = BISHOP;
                      color = Color.BLACK;
                      break;
            case 'k': type  = KING;
                      color = Color.BLACK;
                      break;
            case 'q': type  = QUEEN;
                      color = Color.BLACK;
                      break;
            case 'P': type  = PAWN;
                      color = Color.WHITE;
                      break;
            case 'R': type  = ROOK;
                      color = Color.WHITE;
                      break;
            case 'N': type  = KNIGHT;
                      color = Color.WHITE;
                      break;
            case 'B': type  = BISHOP;
                      color = Color.WHITE;
                      break;
            case 'K': type  = KING;
                      color = Color.WHITE;
                      break;
            case 'Q': type  = QUEEN;
                      color = Color.WHITE;
                      break;

            default: System.out.println("Unexpected error in Piece constructor");
                     System.exit(1);

            }
     }

    @Override
    public  String toString() {
        return getPieceLetter(color, type);
    }

}

