import java.util.Stack;
import java.util.Iterator;
import java.util.List;

public class Board implements Cloneable {
        private              PieceList position = new PieceList(); 

	public static final int NO_SETUP        = 0,
                                NORMAL_SETUP    = 1;
	

	private Stack<Move> moveStack; // A stack of previous performed moves on
				       // the board

	private Errorhandler error          = new Errorhandler();

	private boolean blackCastling       = false; // has black made a castling?
	private boolean whiteCastling       = false; // has white made a castling?
	private boolean blackCanCastleShort = true;
	private boolean blackCanCastleLong  = true;
	private boolean whiteCanCastleShort = true;
	private boolean whiteCanCastleLong  = true;
	private int     inMove              = Piece.WHITE;

	// Constructor, setting up initial position
	public Board(int setup) {
		// 2 numberOfPieces = 0;
		inMove         = Piece.WHITE;
                position = new PieceList();
		switch (setup) {
		case NO_SETUP: 	moveStack = new Stack<Move>(); break;
		case NORMAL_SETUP: moveStack = new Stack<Move>();
			           setupFENboard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
			           break;
		}
	}

	public Board(String fen) {
		moveStack      = new Stack<Move>();
		// 2numberOfPieces = 0;
                position = new PieceList();
                inMove = Piece.WHITE;
		setupFENboard(fen);
        }

	
	public int getNumberOfPieces()    { return position.numberOfPieces(); }
	public int whoIsInMove()          { return inMove;  }
        public void setBlackToMove()      { inMove = Piece.BLACK;}
        public void setWhiteToMove()      { inMove = Piece.BLACK;}
	public Move getLastMove()         { return moveStack.peek(); }
	// Encode the color and type into one number
	int piece(int color, int type)    { return color + type; }


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
                    try { insertPiece(new Piece(x, y, c)); }
                    catch (NoPieceException e) { }
                    x++;
                }
                else if (c >= 'B' && c <= 'R') {
                    try { insertPiece(new Piece(x, y, c)); }
                    catch (NoPieceException e) { }
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

        theClone.whiteCastling       = whiteCastling;
        theClone.blackCastling       = blackCastling;
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

    public Piece getPiece(int i) {
        return position.getPiece(i);
    }

	// Find a piece at a certain location
    public Piece getPiece(int x, int y) throws NoPieceException {
        return position.getPiece(x, y);
    }

      // Insert a piece p
    public void insertPiece(Piece p) {
        position.insertPiece(p);
    }

    // Remove a piece from location x, y and return the piece
    public Piece removePiece(int x, int y) {
        return position.removePiece(x, y);
    }

        // TODO: Refactor this into piece class
        // Should be vector based, such that the call for a given piece p is
        // p.Move(int dx, int dy)
    private void movePiece(int xFrom, int yFrom, int xTo, int yTo) {
       position.movePiece(xFrom, yFrom, xTo, yTo);
    }

    public void performMove(Move m) {
        // Put the move m on the stack
        moveStack.push(m);

        // Swap the move color
        inMove = -inMove;

        if (m.aSimplePromotion()) {
            insertPiece(new Piece(m.toX, m.toY, m.whoMoves, m.promotionTo()));
            removePiece(m.fromX, m.fromY);
        }

        if (m.aCapturePromotion()) {
            removePiece(m.toX, m.toY);
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
                if (m.whoMoves == Piece.WHITE) {
                    whiteCastling = true;
                    whiteCanCastleShort = false;
                }
                if (m.whoMoves == Piece.BLACK) {
                    blackCastling = true;
                    blackCanCastleShort = false;
                }
                // Move the king first
                movePiece(m.fromX, m.fromY, m.toX, m.toY);
                // Then the rook
                movePiece(7, m.fromY, 5, m.fromY);
                break;

            case Move.CASTLE_LONG:
                if (m.whoMoves == Piece.WHITE) {
                    whiteCastling = true;
                    whiteCanCastleLong = false;
                }
                if (m.whoMoves == Piece.BLACK) {
                    blackCastling = true;
                    blackCanCastleLong = false;
                }
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
    }

    public boolean retractMove() {
        int color = 0;

        try {
            Move m = moveStack.pop();

            // Swap the move color
            inMove = -inMove;

            if (m.aSimplePromotion()) {
                insertPiece(new Piece(m.fromX, m.fromY, m.whoMoves, Piece.PAWN));
                removePiece(m.toX, m.toY);
            }

            if (m.aCapturePromotion()) {
                insertPiece(new Piece(m.fromX, m.fromY, -m.whoMoves, Piece.PAWN));
                insertPiece(new Piece(m.toX, m.toY, -m.whoMoves, m.aPiece));
                removePiece(m.toX, m.toY);
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
                    if (m.whoMoves == Piece.WHITE) {
                        whiteCastling = false;
                        whiteCanCastleShort = true;
                    }
                    if (m.whoMoves == Piece.BLACK) {
                        blackCastling = false;
                        blackCanCastleShort = true;
                    }
                    // Move the king back
                    movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    // Then the rook
                    movePiece(5, m.fromY, 7, m.fromY);
                    break;

                case Move.CASTLE_LONG:
                    if (m.whoMoves == Piece.WHITE) {
                        whiteCastling = false;
                        whiteCanCastleLong = true;
                    }
                    if (m.whoMoves == Piece.BLACK) {
                        blackCastling = false;
                        blackCanCastleLong = true;
                    }
                    // Move the king back
                    movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    // Then the rook
                    movePiece(3, m.fromY, 0, m.fromY);
                    break;

                case Move.CAPTURE:
                    movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    insertPiece(new Piece(m.toX, m.toY, -m.whoMoves, m.aPiece));
                    break;
            }
            return true;
        } catch (java.util.EmptyStackException e) {
            return false;
        }
    }

    public boolean freeSquare(int x, int y)  {
       return position.freeSquare(x, y);
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

    // Returns true if the side not in move, in board b attacks square (x, y)
    // and otherwise false
    public boolean attacks(int x, int y) {
      return position.attacks( x,  y,  inMove);
    }
}



// private int hashValue = 0;
// Input: a piece p
// Output: a number which is a perfect hash of position, color and type.
// private static int hash(piece p) {
// return (2*p.xPos+11*p.yPos) * (p.color+p.type);
// }

// public int getHash() { return hashValue; }
