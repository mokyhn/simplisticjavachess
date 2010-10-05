public class Chessio {

	// Constuctor
	public Chessio() {
	}

	// Print a given board in ASCII to standard output
	public void printBoard(Board theBoard) {
		int x;
		int y;
		Piece p;

		System.out.println("");

		for (y = 7; y >= 0; y--) {
			for (x = 0; x < 8; x++) {
			     System.out.print(" ");
                              try {
					p = theBoard.getPiece(x, y);

					if (p.color == Piece.BLACK) {
						switch (p.type) {
						case Piece.PAWN:   System.out.print('P'); break;
						case Piece.ROOK:   System.out.print('R'); break;
						case Piece.BISHOP: System.out.print('B'); break;
						case Piece.KNIGHT: System.out.print('N'); break;
						case Piece.KING:   System.out.print('K'); break;
						case Piece.QUEEN:  System.out.print('Q'); break;
						}
					}

					if (p.color == Piece.WHITE) {
						switch (p.type) {
						case Piece.PAWN:   System.out.print('p'); break;
						case Piece.ROOK:   System.out.print('r'); break;
						case Piece.BISHOP: System.out.print('b'); break;
						case Piece.KNIGHT: System.out.print('n'); break;
						case Piece.KING:   System.out.print('k'); break;
						case Piece.QUEEN:  System.out.print('q'); break;
						}
					}
				} catch (NoPieceException e) {
					System.out.print('.');
				}
			}
			System.out.println("");
		} // end last for-loop

		if (theBoard.whoIsInMove() == Piece.WHITE) {
			System.out.println("White to move"); }
		else
			{ System.out.println("Black to move"); }

	}

	// Input: the current position, a move string, and the knowledge of
	// who is the next to move: Parse the movestring to a move

	// Output: a move
	public Move parse_move(Board b, String str) throws NoMoveException {
		int fromX;
		int fromY;
		int toX;
		int toY;
		char[] s = str.toCharArray();

		int whoToMove = b.whoIsInMove();

		Move m = new Move();

		if (s.length < 4) { throw new NoMoveException();}

		/* make sure the string looks like a move */
		if (s[0] < 'a' || s[0] > 'h' || s[1] < '0' || s[1] > '9' || s[2] < 'a'
				|| s[2] > 'h' || s[3] < '0' || s[3] > '9')
			{ throw new NoMoveException(); }

		fromX = (int) (s[0] - 'a');
		fromY = (int) ((s[1] - '0') - 1);
		toX = (int) (s[2] - 'a');
		toY = (int) ((s[3] - '0') - 1);

		// TODO: CAPTURE AND PROMOTION DOES NOT WORK!!

		if (str.length() > 4) {
			if (fromX == toX) {
				switch (s[4]) {
				case 'K':
					m.type = Move.PROMOTE_TO_KNIGHT;
					break;
				case 'B':
					m.type = Move.PROMOTE_TO_BISHOP;
					break;
				case 'Q':
					m.type = Move.PROMOTE_TO_QUEEN;
					break;
				case 'R':
					m.type = Move.PROMOTE_TO_ROOK;
					break;
				} 
			  }
			else {
				switch (s[4]) {
				case 'K':
					m.type = Move.CAPTURE_AND_PROMOTE_TO_KNIGHT;
					break;
				case 'B':
					m.type = Move.CAPTURE_AND_PROMOTE_TO_BISHOP;
					break;
				case 'Q':
					m.type = Move.CAPTURE_AND_PROMOTE_TO_QUEEN;
					break;
				case 'R':
					m.type = Move.CAPTURE_AND_PROMOTE_TO_ROOK;
					break;
				}
			}
		}

		m.fromX = fromX;
		m.fromY = fromY;
		m.toX = toX;
		m.toY = toY;
		m.aPiece = 0;
		m.whoMoves = whoToMove;

		try {
			if (b.getColor(fromX, fromY) != whoToMove) { throw new NoMoveException(); }
		} catch (NoPieceException e) {
			throw new NoMoveException();
		}

		// Pawn promotions
		try {
			if ((b.getPiece(fromX, fromY).type == Piece.PAWN
					&& b.getPiece(fromX, fromY).color == Piece.WHITE && fromY == 6)
					|| (b.getPiece(fromX, fromY).type == Piece.PAWN
							&& b.getPiece(fromX, fromY).color == Piece.BLACK && fromY == 1))
				if (b.freeSquare(toX, toY)) {
					return m;
				}
		} catch (NoPieceException e) {
			throw new NoMoveException();
		}

		// ENPASSENT Move
		try {
			Piece p = b.getPiece(fromX, fromY);
			// Are we dealing with a pawn move?
			if (p.type == Piece.PAWN)
				if ((fromX != toX) && (b.freeSquare(toX, toY))) {
					m.type = Move.CAPTURE_ENPASSANT;
					m.aPiece = Piece.PAWN;
					return m;
				}
		} catch (NoPieceException e) {
			throw new NoMoveException();
		}

		// Normal move
		if (b.freeSquare(toX, toY)) {
			m.type = Move.NORMALMOVE;
			return m;
		}

		// A capturing move
		try {
			if (b.getColor(toX, toY) == -whoToMove) {
				m.type = Move.CAPTURE;
				m.aPiece = b.getPiece(toX, toY).type;
				return m;
			}

		} catch (NoPieceException e) {
			throw new NoMoveException();
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

		throw new NoMoveException();
	}
}
