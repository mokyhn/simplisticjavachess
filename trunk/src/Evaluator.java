// Detailed attack array for each square would be nice. Black material attacking a square
// and white material attacking a square...
// We could use this for evaluating the position and mobility of white/black
// and to (easily) compute the outcome of an exchange serie.


public class Evaluator {

	public Evaluator() {
	}

	public static int evaluate(Board b) {
		int sum = 0;

		Piece p;
		boolean thereIsAWhiteKing = false;
		boolean thereIsABlackKing = false;

		int side = b.whoIsInMove();

		for (int i = 0; i < b.getNumberOfPieces(); i++) {
			p = b.getPiece(i);
			switch (p.type) {
			case Piece.PAWN:   sum = 1*p.color + sum; break;
			case Piece.ROOK:   sum = 5*p.color + sum; break;
			case Piece.BISHOP: sum = 3*p.color + sum; break;
        		case Piece.KNIGHT: sum = 3*p.color + sum; break;
			case Piece.QUEEN:  sum = 9*p.color + sum; break;
			case Piece.KING:
                                if (p.color == Piece.BLACK) {thereIsABlackKing = true;}
				if (p.color == Piece.WHITE) {thereIsAWhiteKing = true;}
				break;
			}
		}

		if (thereIsABlackKing == false) { sum = 1000;  }
		if (thereIsAWhiteKing == false) { sum = -1000; }

		return side * sum;
	}

}
