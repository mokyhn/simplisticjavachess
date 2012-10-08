/**
 * @author Morten Kühnrich
 * Evaluates a given board position.
 * A positive evaluation means that white has an advantage,
 * and a negative evaluation signifies that black has an advantage.
 * The evaluation 0 is given for positions where black and white has equal play.
 */
package sjc;

public final class Evaluator {
/* TODO: Detailed attack array for each square would be nice. Black material attacking a square
   and white material attacking a square...
   We could use this for evaluating the position and mobility of white/black
   and to (easily) compute the outcome of an exchange serie. */
        public static final int WHITE_IS_MATED = -2147480000; //Integer.MIN_VALUE;//
        public static final int BLACK_IS_MATED =  2147480000; // Integer.MAX_VALUE;//
        public static final int DRAW           =  0;

        public static final int PAWNVALUE      = 1;
        public static final int ROOKVALUE      = 5;
        public static final int BISHOPVALUE    = 3;
        public static final int KNIGHTVALUE    = 3;
        public static final int QUEENVALUE     = 9;
        
	
	public static int evaluate(Board b) {
		int     result = 0;
		Piece   p;
		boolean thereIsAWhiteKing = false;
		boolean thereIsABlackKing = false;
                

		for (int i = 0; i < b.getNumberOfPieces(); i++) {
			p = b.getPiece(i);
			switch (p.type) {
			case Piece.PAWN:   result = PAWNVALUE   * p.color + result; break;
			case Piece.ROOK:   result = ROOKVALUE   * p.color + result; break;
			case Piece.BISHOP: result = BISHOPVALUE * p.color + result; break;
        		case Piece.KNIGHT: result = KNIGHTVALUE * p.color + result; break;
			case Piece.QUEEN:  result = QUEENVALUE  * p.color + result; break;
			case Piece.KING:
                                if (p.color == Piece.BLACK) {thereIsABlackKing = true;}
				if (p.color == Piece.WHITE) {thereIsAWhiteKing = true;}
				break;
                        default:     
			}
		}

		if (thereIsABlackKing == false) { result = BLACK_IS_MATED; }
		if (thereIsAWhiteKing == false) { result = WHITE_IS_MATED; }

		return  result;
	}

}