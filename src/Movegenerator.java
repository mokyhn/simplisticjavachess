//TODO: Implement inCheck predicate here or BETTER
//an inCapture predicate??

import java.util.ArrayList;

class Movegenerator {

	public Movegenerator() {
	}


	// Input: Given a board b, and that there is a pawn p
	// Output: The set of moves this pawn can perform.
	public ArrayList<Move> pawnMoves(Board b, Piece p)
			throws NoMoveException {

		int c = b.whoIsInMove();
                int x = p.xPos;
                int y = p.yPos;

		Piece leftPiece;
		Piece rightPiece;
		Piece lastMovePiece;

		ArrayList<Move> Moves = new ArrayList<Move>();

		// Normal one step forward pawn move
		if (((y < 6) && (c == Piece.WHITE)) || (y > 1) && (c == Piece.BLACK))
			if (b.freeSquare(x, y + c * 1)) {
				Moves.add(new Move(x, y, x, y + c * 1, Move.NORMALMOVE,
						Piece.EMPTY, c));
			}

		// Normal two step forward pawn move
		if (((y == 1) && (c == Piece.WHITE))
				|| ((y == 6) && (c == Piece.BLACK)))
			if (b.freeSquare(x, y + c * 1) && b.freeSquare(x, y + c * 2)) {
				Moves.add(new Move(x, y, x, (y + c * 2), Move.NORMALMOVE,
						Piece.EMPTY, c));
			}

		// Non capturing PAWN promotion
		if (((y == 6) && (c == Piece.WHITE) && b.freeSquare(x, 7))
				|| ((y == 1) && (c == Piece.BLACK) && b.freeSquare(x, 0))) {
			Moves.add(new Move(x, y, x, y + c, Move.PROMOTE_TO_QUEEN,
					Move.NORMALMOVE, c));
			Moves.add(new Move(x, y, x, y + c, Move.PROMOTE_TO_ROOK,
					Move.NORMALMOVE, c));
			Moves.add(new Move(x, y, x, y + c, Move.PROMOTE_TO_KNIGHT,
					Move.NORMALMOVE, c));
			Moves.add(new Move(x, y, x, y + c, Move.PROMOTE_TO_BISHOP,
					Move.NORMALMOVE, c));
		}

		// Normal diagonal capturing to the left
		if ((x > 0) && (y != (5 * c + 7) / 2))
			try {
				leftPiece = b.getPiece(x - 1, y + c);
				if (leftPiece.color != c)
					Moves.add(new Move(x, y, x - 1, y + c, Move.CAPTURE,
							leftPiece.type, c));
			} catch (NoPieceException e) {
			}

		// Normal diagonal capturing to the right
		if ((x < 7) && (y != (5 * c + 7) / 2)) {
			try {
				rightPiece = b.getPiece(x + 1, y + c);
				if (rightPiece.color != c) {
					Moves.add(new Move(x, y, x + 1, y + c, Move.CAPTURE,
							rightPiece.type, c));
					}
			} catch (NoPieceException e) {
			}
		}

		// Promotion via diagonal capturing to the left
		if ((x > 0) && (y == (5 * c + 7) / 2)) {
			try {
				leftPiece = b.getPiece(x - 1, y + c);
				if (leftPiece.color != c)
					Moves.add(new Move(x, y, x - 1, y + c,
							Move.CAPTURE_AND_PROMOTE_TO_BISHOP, leftPiece.type,
							c));
				Moves.add(new Move(x, y, x - 1, y + c,
						Move.CAPTURE_AND_PROMOTE_TO_KNIGHT, leftPiece.type, c));
				Moves.add(new Move(x, y, x - 1, y + c,
						Move.CAPTURE_AND_PROMOTE_TO_QUEEN, leftPiece.type, c));
				Moves.add(new Move(x, y, x - 1, y + c,
						Move.CAPTURE_AND_PROMOTE_TO_ROOK, leftPiece.type, c));
			} catch (NoPieceException e) {
			}
		}

		// Promotion via diagonal capturing to the right
		if ((x < 7) && (y == (5 * c + 7) / 2)) {
			try {
				rightPiece = b.getPiece(x + 1, y + c);
				if (rightPiece.color != c) {
					Moves.add(new Move(x, y, x + 1, y + c,Move.CAPTURE_AND_PROMOTE_TO_BISHOP,
							rightPiece.type, c));}
				Moves.add(new Move(x, y, x + 1, y + c,
								Move.CAPTURE_AND_PROMOTE_TO_KNIGHT,
								rightPiece.type, c));
				Moves.add(new Move(x, y, x + 1, y + c,
						Move.CAPTURE_AND_PROMOTE_TO_QUEEN, rightPiece.type, c));
				Moves.add(new Move(x, y, x + 1, y + c,
						Move.CAPTURE_AND_PROMOTE_TO_ROOK, rightPiece.type, c));

			} catch (NoPieceException e) {
			}
		}

		// En passant capture
		try {
			Move lastMove = b.getLastMove();
			if (x > 0) {
				try {
					lastMovePiece = b.getPiece(lastMove.toX, lastMove.toY);
					// The piece stands to the left
					if ((lastMove.toX == x - 1) && (lastMove.toY == y)
							&& (lastMovePiece.type == Piece.PAWN)
							&& (Math.abs(lastMove.fromY - lastMove.toY) == 2)) {
						Moves.add(new Move(x, y, x - 1, y + c,
								Move.CAPTURE_ENPASSANT, Piece.EMPTY, c));
					}
				} catch (NoPieceException e) {
				}
			}

			if (x < 7) {
				try {
					lastMovePiece = b.getPiece(lastMove.toX, lastMove.toY);
					// The piece stands to the right
					if ((lastMove.toX == x + 1) && (lastMove.toY == y)
							&& (lastMovePiece.type == Piece.PAWN)
							&& (Math.abs(lastMove.fromY - lastMove.toY) == 2)) {
						Moves.add(new Move(x, y, x + 1, y + c,
								Move.CAPTURE_ENPASSANT, Piece.EMPTY, c));

					}
				} catch (NoPieceException e) {
				}
			}

		} catch (java.util.EmptyStackException e) {
			// There were no last move. We must be a beginning of game.
			// Hence no en passant moves are possible
		}

		if (Moves.isEmpty()) {
			throw new NoMoveException();
		} else { return Moves; }

	}

	public ArrayList<Move> kingMoves(Board b, Piece p) throws NoMoveException {
		ArrayList<Move> Moves = new ArrayList<Move>();
		int             c     = b.whoIsInMove();

                int x = p.xPos;
                int y = p.yPos;
                
		// King moves left
		if (x > 0) {
			// To side
			if (b.freeSquare(x - 1, y) && !b.attacks(x - 1, y)) { Moves.add(new Move(x, y, x - 1, y, Move.NORMALMOVE, 0, c)); }
			else {
                            try { if (b.getPiece(x - 1, y).color != c)
				   Moves.add(new Move(x, y, x - 1, y, Move.CAPTURE, b.getPiece(x - 1, y).type, c));
				}
                            catch (NoPieceException e)
                            {}
                        };

			// Up
			if (y < 7)
				if (b.freeSquare(x - 1, y + 1) && !b.attacks(x - 1, y + 1)) {
					Moves.add(new Move(x, y, x - 1, y + 1, Move.NORMALMOVE, 0,
							c)); }
				else {
					try {
						if (b.getPiece(x - 1, y + 1).color != c) {
							Moves.add(new Move(x, y, x - 1, y + 1,
									Move.CAPTURE,
									b.getPiece(x - 1, y + 1).type, c)); }
					} catch (NoPieceException e) {
					}
				}

			// Down
			if (y > 0)
				if (b.freeSquare(x - 1, y - 1) && !b.attacks(x - 1, y - 1)) {
					Moves.add(new Move(x, y, x - 1, y - 1, Move.NORMALMOVE, 0,
							c)); }
				else {
					try {
						if (b.getPiece(x - 1, y - 1).color != c) {
							Moves.add(new Move(x, y, x - 1, y - 1,
									Move.CAPTURE,
									b.getPiece(x - 1, y - 1).type, c)); }
					} catch (NoPieceException e) {}
				}
			}
		

		// King moves right
		if (x < 7) {
			// To side
			if (b.freeSquare(x + 1, y) && !b.attacks(x + 1, y)) {
				Moves.add(new Move(x, y, x + 1, y, Move.NORMALMOVE, 0, c)); }
			else {
				try {
					if (b.getPiece(x + 1, y).color != c) {
						Moves.add(new Move(x, y, x + 1, y, Move.CAPTURE, b
								.getPiece(x + 1, y).type, c)); }
				} catch (NoPieceException e) {
				}
			}
		

		// Up and to the right
		if (y < 7)
			if (b.freeSquare(x + 1, y + 1) && !b.attacks(x + 1, y + 1)) {
				Moves.add(new Move(x, y, x + 1, y + 1, Move.NORMALMOVE, 0, c)); } 
			else
			{
				try {
					if (b.getPiece(x + 1, y + 1).color != c)
						{Moves.add(new Move(x, y, x + 1, y + 1, Move.CAPTURE, b
								.getPiece(x + 1, y + 1).type, c)); }
				} catch (NoPieceException e) {
				}
			}

		// Down and to the right
		if (y > 0)
			if (b.freeSquare(x + 1, y - 1) && !b.attacks(x + 1, y - 1)) Moves.add(new Move(x, y, x + 1, y - 1, Move.NORMALMOVE, 0, c));
			else
			try {
			 if (b.getPiece(x + 1, y - 1).color != c) Moves.add(new Move(x, y, x + 1, y - 1, Move.CAPTURE, b.getPiece(x + 1, y - 1).type, c));
			}
                        catch (NoPieceException e)
                        { };
    }

		// King moves straight up
		if (y < 7)
			if (b.freeSquare(x, y + 1) && !b.attacks(x, y + 1))
				Moves.add(new Move(x, y, x, y + 1, Move.NORMALMOVE, 0, c));
			else
				try {
					if (b.getPiece(x, y + 1).color != c)
						Moves.add(new Move(x, y, x, y + 1, Move.CAPTURE, b
								.getPiece(x, y + 1).type, c));
				} catch (NoPieceException e) {
				}
		;

		// King moves straight down
		if (y > 0)
			if (b.freeSquare(x, y - 1) && !b.attacks(x, y - 1))
				Moves.add(new Move(x, y, x, y - 1, Move.NORMALMOVE, 0, c));
			else
				try {
					if (b.getPiece(x, y - 1).color != c)
						Moves.add(new Move(x, y, x, y - 1, Move.CAPTURE, b
								.getPiece(x, y - 1).type, c));
				} catch (NoPieceException e) {
				}
		;

		if (Moves.isEmpty()) {
			throw new NoMoveException();
		} else
			return Moves;
	}

	// Genereate the possible moves for one single piece
	public ArrayList<Move> generateMoves(Board b, Piece p)
			throws NoMoveException {
		int sideToMove = b.whoIsInMove();

		if (p.color != sideToMove) throw new NoMoveException();

		switch (p.type) {
		// WHITE pawn moves
		case Piece.PAWN:
			return pawnMoves(b, p);
		case Piece.KING:
			return kingMoves(b, p);
		}

		throw new NoMoveException();

	}

	public ArrayList<Move> generateAllMoves(Board b) {
		ArrayList<Move> Moves = new ArrayList<Move>();
		Piece[] position;
		Piece p;
		position = b.getPosition();
		for (int i = 0; i < b.getNumberOfPieces(); i++) {
			p = position[i];
			try {
				Moves.addAll(generateMoves(b, p));
			} catch (NoMoveException e) {
			}
		}

		return Moves;
	}

	// Used to testing purposes
        // ArrayList<Move>?
	public static void printMoves(ArrayList<Move> m) {
		Move myMove;

                for (int i = 0; i < m.size(); i++) {
                	myMove = m.get(i);
			System.out.println(myMove.getMoveStr());
		}


		
	}
}
