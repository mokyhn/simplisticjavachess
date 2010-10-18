		// TODO: CAPTURE AND PROMOTION DOES NOT WORK!!

public class Chessio {

    // Constuctor
    public Chessio() {
    }

    // Print a given board in ASCII to standard output
    public static void printBoard(Board theBoard) {
        int x, y;
        Piece p;

        System.out.println("");

        for (y = 7; y >= 0; y--) {
            for (x = 0; x < 8; x++) {
                 System.out.print(" ");
                  p =  theBoard.getPiece(x, y);
                  if (p != null) { System.out.print(p.toString()); }
                  else System.out.print('.');
            }
          System.out.println();
        } // end last for-loop
        if (theBoard.whoIsInMove() == Piece.WHITE) System.out.println("White to move");
        else System.out.println("Black to move");
    }

    // Input: the current position, a move string, and the knowledge of
    // who is the next to move: Parse the movestring to a move
    // Output: a move
    public Move parse_move(Board b, String str) throws NoMoveException {
        char[] s = str.toCharArray();
        int fromX, fromY, toX, toY;
        int whoToMove = b.whoIsInMove();
        Piece p, pto;
        Move m = new Move();

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

        if (fromX < 0 || fromX > 7 || toX < 0 || toX > 7) {
            System.out.println("Not a correct move string.");
            throw new NoMoveException();
        }

        p = b.getPiece(fromX, fromY);
        if (p == null) throw new NoMoveException();

        if (p.color != whoToMove) { throw new NoMoveException(); }



        m.fromX          = fromX;
        m.fromY          = fromY;
        m.toX            = toX;
        m.toY            = toY;
        m.aCapturedPiece = 0;
        m.whoMoves       = whoToMove;

        if (str.length() == 4) {
            // Normal move
            if (b.freeSquare(toX, toY)) {
                    m.type = Move.NORMALMOVE;
                    return m;
            }

            // A capturing move
            
            pto = b.getPiece(toX, toY);
            if (pto == null) throw new NoMoveException();
            if (pto.color == -whoToMove) {
               m.type           = Move.CAPTURE;
               m.aCapturedPiece = pto.type;
               return m;
             }
             


             // ENPASSENT Move
            // Are we dealing with a pawn move?
            if (p.type == Piece.PAWN) {
                if ((fromX != toX) && (b.freeSquare(toX, toY))) {
                    m.type = Move.CAPTURE_ENPASSANT;
                    m.aCapturedPiece = Piece.PAWN;
                    return m;
                }
            }

            // White or black does a short or a long castling
            if (fromY == toY && (fromY == 0 || fromY == 7)) {
                    if (fromX == 4 && toX == 6) {
                            m.type = Move.CASTLE_SHORT;
                            return m;
                    } else if (fromX == 4 && toX == 2) {
                            m.type = Move.CASTLE_LONG;
                            return m;
                    }
            }





        }




        // Promotion moves
        if (str.length() == 5) {
            if (fromX == toX) {
                switch (s[4]) {
                case 'K': m.type = Move.PROMOTE_TO_KNIGHT;
                          break;
                case 'B': m.type = Move.PROMOTE_TO_BISHOP;
                          break;
                case 'Q': m.type = Move.PROMOTE_TO_QUEEN;
                          break;
                case 'R': m.type = Move.PROMOTE_TO_ROOK;
                          break;
             }
         }
         else
         {
                switch (s[4]) {
                case 'K': m.type = Move.CAPTURE_AND_PROMOTE_TO_KNIGHT;
                          break;
                case 'B': m.type = Move.CAPTURE_AND_PROMOTE_TO_BISHOP;
                          break;
                case 'Q': m.type = Move.CAPTURE_AND_PROMOTE_TO_QUEEN;
                          break;
                case 'R': m.type = Move.CAPTURE_AND_PROMOTE_TO_ROOK;
                          break;
                }
         }
        }


        // Pawn promotions
        p = b.getPiece(fromX, fromY);
        if (p == null) throw new NoMoveException();
        if ((b.getPiece(fromX, fromY).type == Piece.PAWN
           && b.getPiece(fromX, fromY).color == Piece.WHITE && fromY == 6)
           || (b.getPiece(fromX, fromY).type == Piece.PAWN
            && b.getPiece(fromX, fromY).color == Piece.BLACK && fromY == 1))
                if (b.freeSquare(toX, toY)) {
                        return m;
                }

            


            throw new NoMoveException();
    }

    public final static String numToChar(int pos) {
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

    public final static String numToNumChar(int pos) {
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
        System.out.println("\n----------------------------------------------------");
        System.out.println("Action                           Key stroke");
        System.out.println("Quit                             quit, bye, exit, q ");
        System.out.println("Entering a move: d2d4 or promotion d7d8Q   ");
        System.out.println("----------------------------------------------------");
    }

}
