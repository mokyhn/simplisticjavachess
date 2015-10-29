package com.simplisticchess;

import com.simplisticchess.piece.Piece;
import com.simplisticchess.move.NoMoveException;
import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.move.MoveType;
import com.simplisticchess.piece.Color;
import com.simplisticchess.piece.PieceType;

public final class Chessio {
    
    
    // Input: the current position, a move string, and the knowledge of
    // who is the next to move: Parse the movestring to a move
    // Output: a move
    public Move parseMove(Board b, String str) throws NoMoveException {
        int    fromX, fromY, toX, toY;
        Color    whoToMove = b.inMove();
        char[] s;       
        Move m           = new Move();
        Piece  p, pto;

        if (str == null) {
            throw new NoMoveException();
        }
        if (b   == null) {
            throw new NoMoveException();
        }
        
        if (str.equalsIgnoreCase("o-o")    && b.inMove() == Color.WHITE) 
        {
            str = "e1g1";
        }
        if (str.equalsIgnoreCase("o-o-o")  && b.inMove() == Color.WHITE) 
        {
            str = "e1c1";
        }
        if (str.equalsIgnoreCase("o-o")    && b.inMove() == Color.BLACK) 
        {
            str = "e8g8";
        }
        if (str.equalsIgnoreCase("o-o-o")  && b.inMove() == Color.BLACK) 
        {
            str = "e8c8";
        }
        
        
        s = str.toCharArray();        
        
        if (s.length == 0) {
         return null;
        }        
        if (s.length < 4) {
            System.out.println("Movestring to short, not a valid move.");
            throw new NoMoveException();
        }

        if (s.length > 5) {
            System.out.println("Movestring to long, not a valid move.");
            throw new NoMoveException();
        }

        
        fromX = (int)  (s[0] - 'a');
        fromY = (int) ((s[1] - '0') - 1);
        toX   = (int)  (s[2] - 'a');
        toY   = (int) ((s[3] - '0') - 1);

        if (fromX < 0 || fromX > 7 || 
              toX < 0 || toX > 7 ||
            fromY < 0 || fromY > 7 ||
              toY < 0 || toY > 7) {
            System.out.println("Wrong coordinates in move string.");
            throw new NoMoveException();
        }

        p = b.getPiece(fromX, fromY);
        if (p == null) {
            throw new NoMoveException("No piece at (" + fromX + ", " + fromY +")");
        }
        if (p.color != whoToMove) { throw new NoMoveException("Trying to move piece of opposite color. In move is " + (whoToMove == Color.WHITE ? "white" : "not white")); }

        m.fromX          = fromX;
        m.fromY          = fromY;
        m.toX            = toX;
        m.toY            = toY;
        m.capturedPiece = null;
        m.whoMoves       = whoToMove;

        if (str.length() == 4) {
            // White or black does a short or a long castling
            if (p.pieceType == PieceType.KING && fromY == toY && (fromY == 0 || fromY == 7)) {
                if (fromX == 4 && toX == 6) {
                        m.moveType = MoveType.CASTLE_SHORT;
                        return m;
                } else if (fromX == 4 && toX == 2) {
                        m.moveType = MoveType.CASTLE_LONG;
                        return m;
                }
            }

            // ENPASSENT Move
            if (p.pieceType == PieceType.PAWN) {
                if ((fromX != toX) && (b.freeSquare(toX, toY))) {
                    m.moveType = MoveType.CAPTURE_ENPASSANT;
                    m.capturedPiece = PieceType.PAWN;
                    return m;
                }
            }

            // Normal move
            if (b.freeSquare(toX, toY)) {
                m.moveType = MoveType.NORMALMOVE;
                return m;
            }

            // A capturing move
            pto = b.getPiece(toX, toY);
            if (pto != null && pto.color == whoToMove.flip()) {
               m.moveType           = MoveType.CAPTURE;
               m.capturedPiece = pto.pieceType;
               return m;
             }
        }

        // Promotion moves
        if (str.length() == 5  && 
            p.pieceType == PieceType.PAWN &&
           ((p.color == Color.WHITE && fromY == 6) ||
            (p.color == Color.BLACK && fromY == 1))) {
            
            // Simple promotions
            if (fromX == toX && b.freeSquare(toX, toY)) {
                switch (s[4]) {
                case 'N': m.moveType = MoveType.PROMOTE_TO_KNIGHT; break;    
                case 'K': m.moveType = MoveType.PROMOTE_TO_KNIGHT; break;
                case 'B': m.moveType = MoveType.PROMOTE_TO_BISHOP; break;
                case 'Q': m.moveType = MoveType.PROMOTE_TO_QUEEN;  break;
                case 'R': m.moveType = MoveType.PROMOTE_TO_ROOK;   break;
             }
             return m;   
            }
            
            // Capture and promote
            if (fromX != toX && 
                !b.freeSquare(toX, toY) &&
                 b.getPiece(toX, toY).color == p.color.flip()) {
                switch (s[4]) {
                    case 'K': m.moveType = MoveType.CAPTURE_AND_PROMOTE_TO_KNIGHT; break;
                    case 'B': m.moveType = MoveType.CAPTURE_AND_PROMOTE_TO_BISHOP; break;
                    case 'Q': m.moveType = MoveType.CAPTURE_AND_PROMOTE_TO_QUEEN;  break;
                    case 'R': m.moveType = MoveType.CAPTURE_AND_PROMOTE_TO_ROOK;   break;
                }
                
                m.capturedPiece = b.getPiece(toX, toY).pieceType;
                
               return m; 
             }       
        }

        throw new NoMoveException();
    }

    public static String numToChar(int pos) {
            switch (pos) {
            case 0: return "a";
            case 1:	return "b";
            case 2:	return "c";
            case 3:	return "d";
            case 4:	return "e";
            case 5:	return "f";
            case 6:	return "g";
            case 7:	return "h";
            }
            System.out.println( "ERR: numToChar: input was " + pos);
            System.exit(1);
            return "";
    }

    public static String numToNumChar(int pos) {
            switch (pos) {
            case 0: return "1";
            case 1:	return "2";
            case 2:	return "3";
            case 3:	return "4";
            case 4:	return "5";
            case 5:	return "6";
            case 6:	return "7";
            case 7:	return "8";
            }
            System.out.println( "ERR: numToNumChar: input was " + pos);
            System.exit(1);
            return "";
    }



    public static void printWelcomeText() {
        System.out.println("----------------------------------------------------");
        System.out.println("A Simplistic Chessprogram, under development");
        System.out.println("Morten Kuhnrich");
        System.out.println("Type help if you need help");
        System.out.println("----------------------------------------------------");


    }

    public static void printHelpText() {
        System.out.println();
        System.out.println("+-------------------------------------------------- -+");
        System.out.println("| Action                           Key stroke        |");
        System.out.println("+----------------------------------------------------+");
        System.out.println("|setboard <FEN>                   Setup position FEN |");
        System.out.println("|Quit                             quit, bye, exit, q |");
        System.out.println("|Entering a move: d2d4 or promotion d7d8Q            |");
        System.out.println("+----------------------------------------------------+");
    }

}
