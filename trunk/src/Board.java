import java.util.Stack;
import java.util.Iterator;

public class Board implements Cloneable {
        static final private int MAXPIECES = 32;
	private Piece[] myBoard = new Piece[MAXPIECES];
        private int numberOfPieces = 0;

	public static final int NO_SETUP     = 0,
                                NORMAL_SETUP = 1;
	

	private Stack<Move> moveStack; // A stack of previous performed moves on
				       // the board

	private Errorhandler error = new Errorhandler();

	private boolean blackCastling = false; // has black made a castling?
	private boolean whiteCastling = false; // has white made a castling?
	private boolean blackCanCastleShort = true;
	private boolean blackCanCastleLong = true;
	private boolean whiteCanCastleShort = true;
	private boolean whiteCanCastleLong = true;
	private int inMove = Piece.WHITE;

	// Constructor, setting up initial position
	public Board(int setup) {
		numberOfPieces = 0;
		inMove = Piece.WHITE;
		switch (setup) {
		case NO_SETUP: 	moveStack = new Stack<Move>(); break;
		case NORMAL_SETUP:
			moveStack = new Stack<Move>();
			setupFENboard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
			break;
		}
	}

	public Board(String fen) {
		moveStack = new Stack<Move>();
		numberOfPieces = 0;
		inMove = Piece.WHITE;
		setupFENboard(fen);
        }

	// Returns the actual board position
	public Piece[] getPosition()      { return myBoard; }
	public int getNumberOfPieces()    { return numberOfPieces; }
	public int whoIsInMove()          { return inMove;  }
        public void setBlackToMove()      { inMove = Piece.BLACK;}
        public void setWhiteToMove()      { inMove = Piece.BLACK;}
	public Move getLastMove()         { return moveStack.peek(); }
	// Encode the color and type into one number
	int piece(int color, int type)    { return color + type; }


	// Given a position in the FEN - notation.
	// Set the board up correctly
	// TODO: Support the entire FEN standard?
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

			if (y == 0 && x == 8) {	parsingPartNo = 2; }

			if (parsingPartNo == 1) {
				if (c >= '1' && c <= '8') { x = x + (int) (c - '0'); }
                                else if (c >= 'b' && c <= 'r') {
					try {
						insertPiece(new Piece(x, y, c));
					} 
                                        catch (NoPieceException e) { }
					x++;
				}
                                else if (c >= 'B' && c <= 'R') {
					try {
						insertPiece(new Piece(x, y, c));
					}
                                        catch (NoPieceException e)
                                        { }
					x++;
				} else if (c == '/') {
					y--;
					x = 0;
				} else if (c == ' ') {
					break;
				} // No more pieces to setup...
				else {
				} // Error;
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
				default: {
				} // Ignore rest.
				}
			}
		}
	}

	public Object clone() {
		try { 
			super.clone();
		} catch (CloneNotSupportedException e) {}
		
		Board theClone = new Board(NO_SETUP);

		// Initialize theClone.
		theClone.numberOfPieces = numberOfPieces;

		theClone.whiteCastling = whiteCastling;
		theClone.blackCastling = blackCastling;

		theClone.whiteCanCastleLong = whiteCanCastleLong;
		theClone.whiteCanCastleShort = whiteCanCastleShort;
		theClone.blackCanCastleLong = whiteCanCastleLong;
		theClone.blackCanCastleShort = whiteCanCastleShort;
		theClone.inMove = inMove;

		theClone.myBoard = new Piece[MAXPIECES];
		for (int i = 0; i < numberOfPieces; i++) {
			theClone.myBoard[i] = (Piece) myBoard[i].clone();
		}

		theClone.moveStack = new Stack<Move>();
		theClone.moveStack.addAll(moveStack);
		
		return theClone;
	}

	// Find a piece at a certain location
	public Piece getPiece(int x, int y) throws NoPieceException {
		Piece p;
		for (int i = 0; i < numberOfPieces; i++) {
			p = myBoard[i];
			if (p.xPos == x && p.yPos == y) return p;
		}
		throw new NoPieceException();
	}

	public int getColor(int x, int y) throws NoPieceException {
		Piece p;
		for (int i = 0; i < numberOfPieces; i++) {
			p = myBoard[i];
			if (p.xPos == x && p.yPos == y) { return p.color; }
		}
		throw new NoPieceException();
	}

	// Insert a piece p
	public void insertPiece(Piece p) {
		assert numberOfPieces < MAXPIECES : "board:insertPiece: Inserting to many pieces";
		myBoard[numberOfPieces] = p;
		numberOfPieces++;
	}

	// Remove a piece from location x, y
	// and return that piece
	public Piece removePiece(int x, int y) {
		Piece p = myBoard[0];
		int i = 0;

		while (i < numberOfPieces) {
			if (p.xPos == x && p.yPos == y) {
				myBoard[i] = myBoard[numberOfPieces - 1];
				numberOfPieces--;
				return p;
			}
			i++;
			p = myBoard[i];
		}
		error.fatalError(Errorhandler.NOSUCHPIECE, "board:removePiece at "
				+ Move.posToString(x, y) + " failed ");
		return p;
	}

        // TODO: Refactor this into piece class
        // Should be vector based, such that the call for a given piece p is
        // p.Move(int dx, int dy)
	private void movePiece(int xFrom, int yFrom, int xTo, int yTo) {
                /* Old slow code
                Piece p = removePiece(xFrom, yFrom);
		p.xPos = xTo;
		p.yPos = yTo;
		insertPiece(p);
                */

                //Faster code
                    Piece p;
                    try {
                      p = getPiece(xFrom, yFrom);
                      p.xPos = xTo;
                      p.yPos = yTo;
                    } catch (NoPieceException ex) { }
                  
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
				if (m.whoMoves == Piece.WHITE) {color = Piece.BLACK;}
				if (m.whoMoves == Piece.BLACK) {color = Piece.WHITE;}
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


	public boolean freeSquare(int x, int y) {
		Piece p;
		for (int i = 0; i < numberOfPieces; i++) {
			p = (Piece) myBoard[i];
			if (p.xPos == x && p.yPos == y)	{ return false;}
		}
		return true;
	}


	public boolean isMoveLegal(Move m) {
		Movegenerator movegen = new Movegenerator();
		Move tmp;
		Iterator theMoves;

		theMoves = movegen.generateAllMoves(this).listIterator();

		// Check if move m is among the possible moves
		while (theMoves.hasNext()) {
			tmp = (Move) theMoves.next();
			if (m.equal(tmp)) {return true;}
		}
		return false;
	}

        // Returns true if the side not in move, in board b attacks square (x, y)
	// and otherwise false
	public boolean attacks(int x, int y) {
		Piece p;

		for (int i = 0; i < numberOfPieces; i++) {
			// Lookup a piece
			p = myBoard[i];

			// Chose one of opposite color
			if (p.color == -inMove)
				switch (p.type) {
				case Piece.PAWN:
					if ((p.xPos == x - 1 && p.yPos == p.color + y)
							|| (p.xPos == x + 1 && p.yPos == p.color + y))
						return true;
					break;
				case Piece.ROOK:
					break;
				case Piece.BISHOP:
					break;
				case Piece.KNIGHT:
					break;
				case Piece.QUEEN:
					break;
				case Piece.KING:
					break;
				default: // Lala
				}
		}

		return false;
	}


}

// private int hashValue = 0;
// Input: a piece p
// Output: a number which is a perfect hash of position, color and type.
// private static int hash(piece p) {
// return (2*p.xPos+11*p.yPos) * (p.color+p.type);
// }

// public int getHash() { return hashValue; }
