package sjc;

public final class Chessio {
    
    
    // Input: the current position, a move string, and the knowledge of
    // who is the next to move: Parse the movestring to a move
    // Output: a move
    public Move parseMove(Board b, String str) throws NoMoveException {
        int    fromX, fromY, toX, toY;
        int    whoToMove = b.inMove();
        char[] s;       
        Move m           = new Move();
        Piece  p, pto;

        if (str == null) throw new NoMoveException();
        if (b   == null) throw new NoMoveException();
        
        if (str.equalsIgnoreCase("o-o")    && b.inMove() == Piece.WHITE) str = "e1g1";
        if (str.equalsIgnoreCase("o-o-o")  && b.inMove() == Piece.WHITE) str = "e1c1";
        if (str.equalsIgnoreCase("o-o")    && b.inMove() == Piece.BLACK) str = "e8g8";
        if (str.equalsIgnoreCase("o-o-o")  && b.inMove() == Piece.BLACK) str = "e8c8";
        
        
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
        if (p == null) throw new NoMoveException("No piece at (" + fromX + ", " + fromY +")");
        if (p.color != whoToMove) { throw new NoMoveException("Trying to move piece of opposite color. In move is " + (whoToMove == Piece.WHITE ? "white" : "not white")); }

        m.fromX          = fromX;
        m.fromY          = fromY;
        m.toX            = toX;
        m.toY            = toY;
        m.aCapturedPiece = Piece.EMPTY;
        m.whoMoves       = whoToMove;

        if (str.length() == 4) {
            // White or black does a short or a long castling
            if (p.type == Piece.KING && fromY == toY && (fromY == 0 || fromY == 7)) {
                if (fromX == 4 && toX == 6) {
                        m.type = Move.CASTLE_SHORT;
                        return m;
                } else if (fromX == 4 && toX == 2) {
                        m.type = Move.CASTLE_LONG;
                        return m;
                }
            }

            // ENPASSENT Move
            if (p.type == Piece.PAWN) {
                if ((fromX != toX) && (b.freeSquare(toX, toY))) {
                    m.type = Move.CAPTURE_ENPASSANT;
                    m.aCapturedPiece = Piece.PAWN;
                    return m;
                }
            }

            // Normal move
            if (b.freeSquare(toX, toY)) {
                m.type = Move.NORMALMOVE;
                return m;
            }

            // A capturing move
            pto = b.getPiece(toX, toY);
            if (pto != null && pto.color == -whoToMove) {
               m.type           = Move.CAPTURE;
               m.aCapturedPiece = pto.type;
               return m;
             }
        }

        // Promotion moves
        if (str.length() == 5  && 
            p.type == Piece.PAWN &&
           ((p.color == Piece.WHITE && fromY == 6) ||
            (p.color == Piece.BLACK && fromY == 1))) {
            
            // Simple promotions
            if (fromX == toX && b.freeSquare(toX, toY)) {
                switch (s[4]) {
                case 'N': m.type = Move.PROMOTE_TO_KNIGHT; break;    
                case 'K': m.type = Move.PROMOTE_TO_KNIGHT; break;
                case 'B': m.type = Move.PROMOTE_TO_BISHOP; break;
                case 'Q': m.type = Move.PROMOTE_TO_QUEEN;  break;
                case 'R': m.type = Move.PROMOTE_TO_ROOK;   break;
             }
             return m;   
            }
            
            // Capture and promote
            if (fromX != toX && 
                !b.freeSquare(toX, toY) &&
                 b.getPiece(toX, toY).color == -p.color) {
                switch (s[4]) {
                    case 'K': m.type = Move.CAPTURE_AND_PROMOTE_TO_KNIGHT; break;
                    case 'B': m.type = Move.CAPTURE_AND_PROMOTE_TO_BISHOP; break;
                    case 'Q': m.type = Move.CAPTURE_AND_PROMOTE_TO_QUEEN;  break;
                    case 'R': m.type = Move.CAPTURE_AND_PROMOTE_TO_ROOK;   break;
                }
                
                m.aCapturedPiece = b.getPiece(toX, toY).type;
                
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
        System.out.println("A Simplistic Chessprogram, under serious development");
        System.out.println("Morten Kuhnrich (for now) 2007");
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
