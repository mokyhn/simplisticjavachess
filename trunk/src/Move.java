public class Move {
	public int fromX,
                   fromY,
                   toX,
                   toY,
                   type, // The move type
		   aPiece, // Used for storing pieces that are taken
			// by the piece which moves
		   whoMoves;

	// The different move types
	final static int NORMALMOVE = 0,

			// Normal capture
			CAPTURE = 1,

			// The enpassant capture move
			CAPTURE_ENPASSANT = 2,

			// Capture and promotion at the same time
			CAPTURE_AND_PROMOTE_TO_BISHOP = 3,
			CAPTURE_AND_PROMOTE_TO_KNIGHT = 4, 
                        CAPTURE_AND_PROMOTE_TO_ROOK   = 5,
			CAPTURE_AND_PROMOTE_TO_QUEEN  = 6,

			// Simple promotions
			PROMOTE_TO_BISHOP             = 7,
                        PROMOTE_TO_KNIGHT             = 8,
                        PROMOTE_TO_ROOK               = 9,
			PROMOTE_TO_QUEEN              = 10,

			// Casteling
			CASTLE_SHORT                  = 11,
                        CASTLE_LONG                   = 12;
        /*
        public Move(Piece p, int dX, int dY, int aPiece) {
            
        }*/

	public Move(int fX, int fY, int tX, int tY, int t, int p, int who) {
            assert fX >= 0 && fX <= 7 &&
                   fY >= 0 && fY <= 7 &&
                   tX >= 0 && tX <= 7 &&
                   tY >= 0 && tY <= 7 &&
                   (!(fX == tX && fY == tY)) : "(fX, fY, tX, tY) = " + "(" + fX + ", " +  fY + ", " + tX + "," + tY + ")" ;

                fromX = fX;
		fromY = fY;
		toX = tX;
		toY = tY;
		type = t;
		aPiece = p;
		whoMoves = who;
	}

	public Move() {
	}

	// TODO: Should maby also include type and apiece?
	public boolean equal(Move m) {
		return ((fromX == m.fromX) && (toX == m.toX) && (fromY == m.fromY)
				&& (toY == m.toY) && (type == m.type));
	}

	public boolean aSimplePromotion() {
		return (type >= PROMOTE_TO_BISHOP) && (type <= PROMOTE_TO_QUEEN);
	}

	public boolean aCapturePromotion() {
		return (type >= CAPTURE_AND_PROMOTE_TO_BISHOP)
				&& (type <= CAPTURE_AND_PROMOTE_TO_QUEEN);
	}

	// TODO: Provide better error handling
	public int promotionTo() {
		switch (type) {
		case CAPTURE_AND_PROMOTE_TO_BISHOP: 	return Piece.BISHOP;
		case CAPTURE_AND_PROMOTE_TO_KNIGHT:	return Piece.KNIGHT;
		case CAPTURE_AND_PROMOTE_TO_ROOK:	return Piece.ROOK;
                case CAPTURE_AND_PROMOTE_TO_QUEEN:      return Piece.QUEEN;
		case PROMOTE_TO_BISHOP: 		return Piece.BISHOP;
		case PROMOTE_TO_KNIGHT:			return Piece.KNIGHT;
		case PROMOTE_TO_ROOK:			return Piece.ROOK;
		case PROMOTE_TO_QUEEN:			return Piece.QUEEN;
		default: System.out.println("Wrong promotion code");
		return 0;
		}
	}

	private static String numToChar(int pos) {
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
		return "ERR: numToChar: input was " + pos;
	}

	private static String numToNumChar(int pos) {
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
		return "ERR: numToNumChar: input was " + pos;
	}

	public static String posToString(int x, int y) { return numToChar(x) + numToNumChar(y); }

        public static String pieceNumberToChar(int num) {
          String letter = "";
            switch (num) {
			case Piece.BISHOP: letter = "B"; break;
			case Piece.KNIGHT: letter = "K"; break;
			case Piece.ROOK:   letter = "R"; break;
			case Piece.QUEEN:  letter = "Q"; break;
			}
          return letter;
        }

	public String getMoveStr() {
		if (type == NORMALMOVE) { return posToString(fromX, fromY) + "-" + posToString(toX, toY); }

		// Normal capture moves
		if (type == CAPTURE_ENPASSANT || type == CAPTURE) { return posToString(fromX, fromY) + "x" + posToString(toX, toY); }

		// mate
		if (type == CAPTURE && aPiece == Piece.KING) { return "mate"; }

		// Promotions
		if (aSimplePromotion()) {
		      return posToString(fromX, fromY) + "-" + posToString(toX, toY) + "="+pieceNumberToChar(promotionTo());
		  }

		if (aCapturePromotion()) {
                     return posToString(fromX, fromY) + "x" + posToString(toX, toY) +  "="+pieceNumberToChar(promotionTo());
                }

		if (type == CASTLE_SHORT) {	return "o-o"; }
		if (type == CASTLE_LONG)  {	return "o-o-o"; }

		return "ERR: getMoveStr";
	}

}
