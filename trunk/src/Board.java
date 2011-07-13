import java.util.Stack;
import java.util.Iterator;

// TODO: Take the many variables below a collect them in a record class State
// with nice clone and toString functions
// Then define a History class containing a stack of States.
// The board class would then contain one variable: an instance of the History
// where the top element (an instance of State) is the current state of the
// chess board/game
public class Board implements Cloneable {
    public static final int NO_SETUP        = 0,
                            NORMAL_SETUP    = 1;

    private                PieceList position = new PieceList();
    private Bitboard       bbposition;

	    
    private Stack<State> history; // A stack of previous performed moves on the board
    private boolean        blackCanCastleShort = true;
    private boolean        blackCanCastleLong  = true;
    private boolean        whiteCanCastleShort = true;
    private boolean        whiteCanCastleLong  = true;
    private int            inMove              = Piece.WHITE;

    private int            moveNumber          = 0;
    private int            halfMoveClock; // Number of halfmoves since the last pawn advance or capture.
                                          // Used to determine if a draw can be claimed under the fifty-move rule.

    Piece                  inCheckByPiece      = null; // Currently not used, but could be used to deal
                                                       // with movegeneration when the king is in check

    private int            halfMovesIndex3PosRepition; // the number of halfmoves since last pawnmove (including promotions) or capture move
                                                       // When searching for threefold repitions search from this index...

    public Board() {
    }
    
    // Constructor, setting up initial position
    public Board(int setup) {
        this();
        moveNumber = 0;        
        inMove     = Piece.WHITE;
        halfMoveClock = 0;
        halfMovesIndex3PosRepition = 0;
        position   = new PieceList();
        history  = new Stack<State>();
        switch (setup) {
            case NO_SETUP:      break;
            case NORMAL_SETUP: setupFENboard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"); 
        }
        
        bbposition = new Bitboard(this);
    }

    public Board(String fen) {
        moveNumber = 0;
        inMove     = Piece.WHITE;
        position   = new PieceList();
        history    = new Stack<State>();
        halfMoveClock = 0;
        halfMovesIndex3PosRepition = 0;

        setupFENboard(fen);
        bbposition = new Bitboard(this);
  
    }

    //TODO: Check whether this code is correct. 	
    @Override
    public Board clone() {
        Board theClone = new Board();

        theClone.whiteCanCastleLong  = whiteCanCastleLong;
        theClone.whiteCanCastleShort = whiteCanCastleShort;
        theClone.blackCanCastleLong  = whiteCanCastleLong;
        theClone.blackCanCastleShort = whiteCanCastleShort;
        theClone.inMove              = inMove;
        theClone.moveNumber          = moveNumber;
        
        theClone.history = new Stack<State>();

        for (int i = 0; i < history.size(); i ++) {
            (theClone.history).push((history.get(i)).clone());
        }

        theClone.position = position.clone();

        if (this.inCheckByPiece == null) {
                 theClone.inCheckByPiece = null;    
        } else { theClone.inCheckByPiece             = this.inCheckByPiece.clone();
        }

        theClone.halfMoveClock              = this.halfMoveClock;
        theClone.halfMovesIndex3PosRepition = this.halfMovesIndex3PosRepition;
        theClone.bbposition                 = this.bbposition.clone();
        return theClone;
    }

    public int     getNumberOfPieces()    { return position.numberOfPieces(); }
    public int     whoIsInMove()          { return inMove;  }
    public void    setBlackToMove()       { inMove = Piece.BLACK;}
    public void    setWhiteToMove()       { inMove = Piece.WHITE;}
    public Move    getLastMove()          { return history.peek().move; }
    public Piece   getPiece(int i)        { return position.getPiece(i); }
    public void    insertPiece(Piece p)   { position.insertPiece(p); }
    public Piece   removePiece(int x, int y) { return position.removePiece(x, y); }
    public boolean freeSquare(int x, int y)  { return position.freeSquare(x, y); }
    // Returns true if the side not in move, in board b attacks square (x, y)
    // and otherwise false
    public boolean attacks(int x, int y)  { return position.attacks( x,  y,  inMove); }
    private void   movePiece(int xFrom, int yFrom, int xTo, int yTo) { position.movePiece(xFrom, yFrom, xTo, yTo); }
    public void    print() {System.out.print(toString());}
    public Boolean drawBy50MoveRule() {return halfMoveClock >= 50;}

    public Boolean drawBy3RepetionsRule() {
        State h;
        int k = 0;

        for (int i = halfMovesIndex3PosRepition; i < history.size(); i ++) {
            h = history.get(i);
            if (bbposition.equals(h.bbposition)) k++;
        }

        return k >= 3;
    }

    // Find a piece at a certain location
    public Piece getPieceXY(int x, int y) {
        Piece p =  position.getPieceXY(x, y);

        if (p != null) assert p.xPos == x && p.yPos == y;
        return p;
    }

 
    public void performMove(Move m) {
       Piece p = getPieceXY(m.fromX, m.fromY);

       moveNumber++;

       // Put the move m on the stack
       history.push(
               new State(m,
                           blackCanCastleShort,
                           blackCanCastleLong,
                           whiteCanCastleShort,
                           whiteCanCastleLong,
                           halfMoveClock,
                           halfMovesIndex3PosRepition,
                           bbposition,
                           inCheckByPiece)
                           );


       // Used to determine the 50-move rule, three times repition
       if (p.type == Piece.PAWN) {
           halfMoveClock              = 0;
           halfMovesIndex3PosRepition = moveNumber;
       }
       else halfMoveClock++;

       // Moving a rook can disallow castling in the future
        if (p.type == Piece.ROOK) {
            if (m.whoMoves == Piece.BLACK) {
              if (m.fromX == 0 && blackCanCastleLong)  {
                  blackCanCastleLong  = false;
              }
              if (m.fromX == 7 && blackCanCastleShort) {
                  blackCanCastleShort = false;
              }
            }
            else {
              if (m.fromX == 0 && whiteCanCastleLong)  {
                  whiteCanCastleLong  = false;
              }
              if (m.fromX == 7 && whiteCanCastleShort) {
                  whiteCanCastleShort = false;
              }
            }
        }

        // Moving the king will disallow castling in the future
        if (p.type == Piece.KING) {
            if (m.whoMoves == Piece.BLACK) {
                  blackCanCastleShort = false;
                  blackCanCastleLong  = false;
            }

             if (m.whoMoves == Piece.WHITE) {
                  whiteCanCastleShort = false;
                  whiteCanCastleLong  = false;
              }
           
        }
        
        if (m.aSimplePromotion()) {
            insertPiece(new Piece(m.toX, m.toY, m.whoMoves, m.promotionTo()));
            removePiece(m.fromX, m.fromY);
        }

        if (m.aCapturePromotion()) {
            removePiece(m.toX,   m.toY);
            removePiece(m.fromX, m.fromY);
            insertPiece(new Piece(m.toX, m.toY, m.whoMoves, m.promotionTo()));
        }

        switch (m.type) {
            case Move.NORMALMOVE:
                movePiece(m.fromX, m.fromY, m.toX, m.toY);
                break;

            case Move.CAPTURE_ENPASSANT:
                movePiece(m.fromX, m.fromY, m.toX, m.toY);
                removePiece(m.toX, m.fromY);
                break;

            case Move.CASTLE_SHORT:
                // Move the king first
                movePiece(m.fromX, m.fromY, m.toX, m.toY);
                // Then the rook
                movePiece(7, m.fromY, 5, m.fromY);
                break;

            case Move.CASTLE_LONG:
                // Move the king first
                movePiece(m.fromX, m.fromY, m.toX, m.toY);
                // Then the rook
                movePiece(0, m.fromY, 3, m.fromY);
                break;

            case Move.CAPTURE:
                removePiece(m.toX, m.toY);
                movePiece(m.fromX, m.fromY, m.toX, m.toY);
                break;
        }

       
        // Swap the move color
        inMove = -inMove;

        bbposition = new Bitboard(this);

    }

    public boolean retractMove() {
        int color = 0;
        State h;
        Move    m;

        moveNumber--;

        try {
            h = history.pop();
            m = h.move;

            blackCanCastleLong  = h.blackCanCastleLong;
            blackCanCastleShort = h.blackCanCastleShort;
            whiteCanCastleLong  = h.whiteCanCastleLong;
            whiteCanCastleShort = h.whiteCanCastleShort;

            halfMoveClock       = h.halfMoveClock;
            halfMovesIndex3PosRepition = h.halfMovesIndex3PosRepition;

            bbposition = h.bbposition;

            inCheckByPiece = h.inCheckByPiece;

            // Swap the move color
            inMove = -inMove;

            if (m.aSimplePromotion()) {
                insertPiece(new Piece(m.fromX, m.fromY, m.whoMoves, Piece.PAWN));
                removePiece(m.toX, m.toY);
            }

            if (m.aCapturePromotion()) {
                removePiece(m.toX, m.toY);
                insertPiece(new Piece(m.toX,   m.toY,   -m.whoMoves, m.aCapturedPiece));
                insertPiece(new Piece(m.fromX, m.fromY, m.whoMoves, Piece.PAWN));
                return true;
            }

            switch (m.type) {
                case Move.NORMALMOVE:
                    movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    break;

                case Move.CAPTURE_ENPASSANT:
                    if (m.whoMoves == Piece.WHITE) {
                        color = Piece.BLACK;
                    }
                    if (m.whoMoves == Piece.BLACK) {
                        color = Piece.WHITE;
                    }
                    insertPiece(new Piece(m.toX, m.fromY, color, Piece.PAWN));
                    movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    break;

                case Move.CASTLE_SHORT:
                  // Move the king back
                    movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    // Then the rook
                    movePiece(5, m.fromY, 7, m.fromY);
                    break;

                case Move.CASTLE_LONG:
                   // Move the king back
                    movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    // Then the rook
                    movePiece(3, m.fromY, 0, m.fromY);
                    break;

                case Move.CAPTURE:
                    movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    insertPiece(new Piece(m.toX, m.toY, -m.whoMoves, m.aCapturedPiece));
                    break;
            }
            return true;
        } catch (java.util.EmptyStackException e) {
            return false;
        }
    }


    public boolean isMoveLegal(Move m) {
        Movegenerator movegen = new Movegenerator();
        Move tmp;
        Iterator theMoves;

        theMoves = movegen.generateAllMoves(this).listIterator();

        // Check if move m is among the possible moves
        while (theMoves.hasNext()) {
            tmp = (Move) theMoves.next();
            if (m.equal(tmp)) {
                return true;
            }
        }
        return false;
    }

    // Given a position in the FEN - notation.
    // Set up the board
    // TODO: 1) Error handling in case of parse errors.
    //       2) Simplify the code somewhat
    private void setupFENboard(String sfen) {
        int x = 0;
        int y = 7;
        int i;
        int parsingPartNo;
        char c;
        String fen = trimWhiteSpace(sfen);
        String num1 = "";
        String num2 = "";

       whiteCanCastleShort = false;
       whiteCanCastleLong  = false;
       blackCanCastleShort = false;
       blackCanCastleLong  = false;

       // Parsing part no. 1
       parsingPartNo = 1;

        // Traverse input string
        for (i = 0; i < fen.length(); i++) {
            c = fen.charAt(i);						
            assert x <= 8 && y >= 0 : "Error (Not a correct FEN board)";

            if (parsingPartNo == 1) {
                if (c == ' ') {
                    parsingPartNo = 2;
                    continue;
                }

                if (c >= '1' && c <= '8') { x = x + (int) (c - '0'); }
                else if (c >= 'b' && c <= 'r') {
                    insertPiece(new Piece(x, y, c));
                    x++;
                    continue;
                }
                else if (c >= 'B' && c <= 'R') {
                    insertPiece(new Piece(x, y, c));
                    x++;
                    continue;
                } else if (c == '/') {
                    y--;
                    x = 0;
                    continue;
                }
            }
             
            if (parsingPartNo == 2) {                
                switch (c) {
                    case 'w': inMove = Piece.WHITE; break;
                    case 'b': inMove = Piece.BLACK; break;
                    case ' ': parsingPartNo = 3; continue;   
                }
            }

            if (parsingPartNo == 3) {
                switch (c) {
                    case 'K': whiteCanCastleShort = true; break;
                    case 'Q': whiteCanCastleLong  = true; break;
                    case 'k': blackCanCastleShort = true; break;
                    case 'q': blackCanCastleLong  = true; break;
                    case ' ': parsingPartNo = 4; continue;
                }
            }

            if (parsingPartNo == 4) {             
             if (c == ' ') {
                 parsingPartNo = 5;
                 continue;
             }

              if (c == '-') {
                  continue;
              }

              if (c != ' ') {
               int xPawn = (int) (c - 'a');
               int yPawn = (int) (fen.charAt(i+1) - '1');
               assert xPawn >= 0 && xPawn <= 7;
               assert yPawn >= 0 && yPawn <= 7;
               //i++;
               Piece p = getPieceXY(xPawn, yPawn-inMove);
               if (p != null && p.type == Piece.PAWN) {
                   Move m = new Move(xPawn, yPawn+inMove, xPawn, yPawn-inMove, Move.NORMALMOVE, Piece.EMPTY, inMove);
                   bbposition = new Bitboard(this);
                   history.push(new State(m, blackCanCastleShort, blackCanCastleLong, whiteCanCastleShort, whiteCanCastleLong,
                           halfMoveClock,
                           halfMovesIndex3PosRepition, bbposition, null
                           ) );
               }

               parsingPartNo = 5;
               i = i + 2;
               
              }

              if (fen.charAt(i) == ' ') {
                  parsingPartNo = 5;
                  continue;
              }
            }

            if (parsingPartNo == 5) {
              if (c == ' ') {
                  parsingPartNo = 6;
                  continue;
              }

              if (c != ' ') {
                  num1 = num1 + c;
              }
            }

            if (parsingPartNo == 6) {
              if (c == ' ') { // end of story :)
                  parsingPartNo = 7;
                  continue;
              }

              if (c != ' ') {
                  num2 = num2 + c;
              }  
            }
                    
    }


       halfMoveClock = Integer.parseInt(num1);

       moveNumber    = 2 * Integer.parseInt(num2) - 2;

       if (moveNumber != 0 && inMove == Piece.BLACK) moveNumber--;

    }
    
  public String trimWhiteSpace(String s) {
      String t     = "";
      char c;
      boolean flag = false;
  
      for (int i = 0; i < s.length(); i++) {
       c = s.charAt(i);

       if (c == ' ' && !flag) {
           flag = true;
           t = t + ' ';
       }

       if (c != ' ') {
           flag = false;
           t = t + c;
       }
      }

      return t;
  }

  /**
   * Returns the board as ASCII art
   */
  @Override
  public String toString() {
        int x, y;
        Piece p;
        String s = "\n _______________\n";

        for (y = 7; y >= 0; y--) {
            for (x = 0; x < 8; x++) {
                 s = s + " "; 
                  p =  getPieceXY(x, y);
                  if (p != null) { s = s + p.toString(); }
                  else s = s + ".";
            }
          s = s + ("     " + (y+1)) + "\n";
        } // end last for-loop
        s = s + " _______________\n";
        s = s + " a b c d e f g h\n";
        if (whoIsInMove() == Piece.WHITE) s = s + "  White to move\n";
        else s = s + "  Black to move";
      return s;
  }
  

   public void printState() {
       String blackCastleShort = " ",
              blackCastleLong  = " ",
              whiteCastleShort = " ",
              whiteCastleLong  = " ";

       if (blackCanCastleShort) blackCastleShort = "X";
       if (blackCanCastleLong)  blackCastleLong  = "X";
       if (whiteCanCastleShort) whiteCastleShort = "X";
       if (whiteCanCastleLong)  whiteCastleLong  = "X";

       System.out.println("\n----------------------------State----------------------------");
       
       System.out.printf("Black can castle long: [%s],       Black can castle short: [%s]\n", blackCastleLong, blackCastleShort);
       System.out.printf("White can castle long: [%s],       White can castle short: [%s]\n", whiteCastleLong, whiteCastleShort);
       if (!history.isEmpty()) {
         if (-inMove == Piece.WHITE) System.out.printf("Last move %d. %s\n",   (moveNumber+1)/2, history.peek().move.toString());
         else                       System.out.printf("Last move %d.... %s\n", (moveNumber+1)/2, history.peek().move.toString());
       }
       System.out.println("Ply Move number " + moveNumber);
       System.out.println("Immediate evaluation: " + Evaluator.evaluate(this));
       System.out.println("Number of half moves since last pawn move: " + halfMoveClock);
       System.out.println("Index searched from when checking 3 fold repetition: " + halfMovesIndex3PosRepition);
   }

  

}