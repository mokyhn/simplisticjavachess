import java.util.Stack;
import java.util.Iterator;

public class Board implements Cloneable {
        private              PieceList position = new PieceList(); 

	public static final int NO_SETUP        = 0,
                            NORMAL_SETUP    = 1;
	

    // A stack of previous performed moves on the board
    private Stack<Move> moveStack;

    private boolean blackCanCastleShort = true;
    private boolean blackCanCastleLong  = true;
    private boolean whiteCanCastleShort = true;
    private boolean whiteCanCastleLong  = true;

    private int     inMove              = Piece.WHITE;

	// Constructor, setting up initial position
    public Board(int setup) {
        inMove    = Piece.WHITE;
        position  = new PieceList();
        moveStack = new Stack<Move>();
        switch (setup) {
            case NO_SETUP:      break;
            case NORMAL_SETUP: setupFENboard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"); 
        }
    }

    public Board(String fen) {
        inMove    = Piece.WHITE;
        position  = new PieceList();
        moveStack = new Stack<Move>();
        setupFENboard(fen);
    }

	
    public int     getNumberOfPieces()    { return position.numberOfPieces(); }
    public int     whoIsInMove()          { return inMove;  }
    public void    setBlackToMove()       { inMove = Piece.BLACK;}
    public void    setWhiteToMove()       { inMove = Piece.BLACK;}
    public Move    getLastMove()          { return moveStack.peek(); }
    public Piece   getPiece(int i)        { return position.getPiece(i); }
    public void    insertPiece(Piece p)   { position.insertPiece(p); }
    public Piece   removePiece(int x, int y) { return position.removePiece(x, y); }
    public boolean freeSquare(int x, int y)  { return position.freeSquare(x, y); }
    // Returns true if the side not in move, in board b attacks square (x, y)
    // and otherwise false
    public boolean attacks(int x, int y)  { return position.attacks( x,  y,  inMove); }
    private void   movePiece(int xFrom, int yFrom, int xTo, int yTo) { position.movePiece(xFrom, yFrom, xTo, yTo); }
    public void    print() {Chessio.printBoard(this);}
    // Given a position in the FEN - notation.
    // Set the board up correctly
    private void setupFENboard(String fen) {
        int x = 0;
        int y = 7;
        int i;
        int parsingPartNo = 1;
        char c;
        // Traverse input string
        for (i = 0; i < fen.length(); i++) {
            c = fen.charAt(i);
            assert x <= 8 && y >= 0 : "Error (Not a correct FEN board)";

            if (y == 0 && x == 8) { parsingPartNo = 2; }

            if (parsingPartNo == 1) {
                if (c >= '1' && c <= '8') { x = x + (int) (c - '0'); }
                else if (c >= 'b' && c <= 'r') {
                    insertPiece(new Piece(x, y, c));                     
                    x++;
                }
                else if (c >= 'B' && c <= 'R') {
                    insertPiece(new Piece(x, y, c)); 
                    x++;
                } else if (c == '/') {
                    y--;
                    x = 0;
                } else if (c == ' ') {
                    break; } // No more pieces to setup...
                else {} // Error;
            }

            // Parsing part no 2
            if (parsingPartNo == 2) {
                c = fen.charAt(i);
                switch (c) {
                    case 'w': inMove = Piece.WHITE; break;
                    case 'b': inMove = Piece.BLACK; break;
                    case ' ': break;
                    case 'K': whiteCanCastleShort = true; break;
                    case 'Q': whiteCanCastleLong  = true; break;
                    case 'k': blackCanCastleShort = true; break;
                    case 'q': blackCanCastleLong  = true; break;
                    default: {} // Ignore rest.
                }
            }
        }
    }

    public Object clone() {
        try { super.clone(); } catch (CloneNotSupportedException e) { }

        Board theClone = new Board(NO_SETUP);
       
        theClone.whiteCanCastleLong  = whiteCanCastleLong;
        theClone.whiteCanCastleShort = whiteCanCastleShort;
        theClone.blackCanCastleLong  = whiteCanCastleLong;
        theClone.blackCanCastleShort = whiteCanCastleShort;
        theClone.inMove              = inMove;
        
        theClone.moveStack = new Stack<Move>();
        theClone.moveStack.addAll(moveStack);

        theClone.position = (PieceList) position.clone();
        return theClone;
    }


    // Find a piece at a certain location
    public Piece getPieceXY(int x, int y) {
        Piece p =  position.getPieceXY(x, y);

        if (p != null) assert p.xPos == x && p.yPos == y;
        return p;
    }

 
    public void performMove(Move m) {
     
       // Moving a rook can disallow castling in the future
        if (m.type == Piece.ROOK) {
            if (m.whoMoves == Piece.BLACK) {
              if (m.fromX == 0 && blackCanCastleLong)  {
                  blackCanCastleLong  = false;
                  m.event = Move.BREAK_LONG_CASTLING;
              }
              if (m.fromX == 7 && blackCanCastleShort) {
                  blackCanCastleShort = false;
                  m.event = Move.BREAK_SHORT_CASTLING;
              }
            }
            else {
              if (m.fromX == 0 && whiteCanCastleLong)  {
                  whiteCanCastleLong  = false;
                  m.event = Move.BREAK_LONG_CASTLING;
              }
              if (m.fromX == 7 && whiteCanCastleShort) {
                  whiteCanCastleShort = false;
                  m.event = Move.BREAK_SHORT_CASTLING;
              }
            }
        }

        // Moving the king will disallow castling in the future
        if (m.type == Piece.KING) {
            if (m.whoMoves == Piece.BLACK) {
                if  (blackCanCastleLong && blackCanCastleShort) {
                  blackCanCastleShort = false;
                  blackCanCastleLong  = false;
                  m.event = Move.BREAK_LONG_AND_SHORT_CASTLING;
                } else
                if (!blackCanCastleLong && blackCanCastleShort) {
                    blackCanCastleShort = false;
                    m.event = Move.BREAK_SHORT_CASTLING;
                } else
                if (blackCanCastleLong && !blackCanCastleShort) {
                  blackCanCastleLong = false;
                  m.event = Move.BREAK_LONG_CASTLING;
                }
        }
      
        // Add code for white...

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

        // Put the move m on the stack
        moveStack.push(m);

        // Swap the move color
        inMove = -inMove;

    }

    public boolean retractMove() {
        int color = 0;

        // Test if the castle flags should be set.
        // and reverse the state of these flags...

        try {
            Move m = moveStack.pop();

            // Swap the move color
            inMove = -inMove;

            if (m.event == m.BREAK_LONG_AND_SHORT_CASTLING) {
              if (m.whoMoves == Piece.BLACK) {
                  blackCanCastleLong  = true;
                  blackCanCastleShort = true;
                }
              else {
                  whiteCanCastleLong  = true;
                  whiteCanCastleShort = true;
                }
            }

            if (m.event == m.BREAK_LONG_CASTLING) {
              if (m.whoMoves == Piece.BLACK) blackCanCastleLong  = true;
              else whiteCanCastleLong  = true;
            }


            if (m.event == m.BREAK_SHORT_CASTLING) {
              if (m.whoMoves == Piece.BLACK) blackCanCastleShort = true;
              else whiteCanCastleShort = true;
            }



            if (m.aSimplePromotion()) {
                insertPiece(new Piece(m.fromX, m.fromY, m.whoMoves, Piece.PAWN));
                removePiece(m.toX, m.toY);
            }

            if (m.aCapturePromotion()) {
                removePiece(m.toX, m.toY);
                insertPiece(new Piece(m.toX,   m.toY,   -m.whoMoves, m.aCapturedPiece));
                insertPiece(new Piece(m.fromX, m.fromY, -m.whoMoves, Piece.PAWN));
                
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

   
}