package sjc;

//TODO: Implementation via an iterator. 
//A numbering of all moves generated. A function on the form
//genmoves(board, moveid) such that only moves with a certain id
//are generated.
// This can be used to optimize our search engine such that not all
// moves have to be generated beforehand. We can simply generated one move
// at a time. (when cutoffs occur in the alpha beta search we will not have
// wasted time for generating moves which where never searched anyway.

// Eliminatate generation of a number of moves when king is in check...
// The king is not allowed to be in check while another piece is moved...

import java.util.ArrayList;

public final class Movegenerator {

    public Movegenerator() {
    }
    
    // Input: Given a board b, and that there is a pawn p
    // Output: The set of moves this pawn can perform.
    public static ArrayList<Move> pawnMoves(Board b, Piece p)  {

        final int c = b.inMove();
        final int fx = p.xPos;
        final int fy = p.yPos;

        Piece leftPiece;
        Piece rightPiece;
        Piece lastMovePiece;

        final ArrayList<Move> Moves = new ArrayList<Move>();


        // Normal one step forward pawn move
        if (((fy < 6) && (c == Piece.WHITE))
          || (fy > 1) && (c == Piece.BLACK)) {
            if (b.freeSquare(fx, fy + c * 1)) Moves.add(new Move(fx, fy, fx, fy + c * 1, Move.NORMALMOVE, Piece.EMPTY, c));
        }

        // Normal two step forward pawn move
        if (((fy == 1) && (c == Piece.WHITE))
         || ((fy == 6) && (c == Piece.BLACK))) {
          if (b.freeSquare(fx, fy + c * 1) && b.freeSquare(fx, fy + c * 2)) Moves.add(new Move(fx, fy, fx, (fy + c * 2), Move.NORMALMOVE, Piece.EMPTY, c));
        }

        // Non capturing PAWN promotion
        if (((fy == 6) && (c == Piece.WHITE) && b.freeSquare(fx, 7))
         || ((fy == 1) && (c == Piece.BLACK) && b.freeSquare(fx, 0))) {
            Moves.add(new Move(fx, fy, fx, fy + c, Move.PROMOTE_TO_QUEEN,  Piece.EMPTY, c));  
            Moves.add(new Move(fx, fy, fx, fy + c, Move.PROMOTE_TO_ROOK,   Piece.EMPTY, c)); 
            Moves.add(new Move(fx, fy, fx, fy + c, Move.PROMOTE_TO_KNIGHT, Piece.EMPTY, c));
            Moves.add(new Move(fx, fy, fx, fy + c, Move.PROMOTE_TO_BISHOP, Piece.EMPTY, c)); 
        }

        // Normal diagonal capturing to the left
        if ((fx > 0) && (fy != (5 * c + 7) / 2)) {
         leftPiece = b.getPieceXY(fx - 1, fy + c);
         if (leftPiece != null && leftPiece.color != c) Moves.add(new Move(fx, fy, fx - 1, fy + c, Move.CAPTURE, leftPiece.type, c));   
        }

        // Normal diagonal capturing to the right
        if ((fx < 7) && (fy != (5 * c + 7) / 2)) {
            rightPiece = b.getPieceXY(fx + 1, fy + c);
            if (rightPiece != null && rightPiece.color != c) Moves.add(new Move(fx, fy, fx + 1, fy + c, Move.CAPTURE, rightPiece.type, c));
        }

        // Promotion via diagonal capturing to the left
        if ((fx > 0) && (fy == (5 * c + 7) / 2)) {
            leftPiece = b.getPieceXY(fx - 1, fy + c);
            if (leftPiece != null && leftPiece.color != c) {
                Moves.add(new Move(fx, fy, fx - 1, fy + c, Move.CAPTURE_AND_PROMOTE_TO_BISHOP, leftPiece.type, c));
                Moves.add(new Move(fx, fy, fx - 1, fy + c, Move.CAPTURE_AND_PROMOTE_TO_KNIGHT, leftPiece.type, c));
                Moves.add(new Move(fx, fy, fx - 1, fy + c, Move.CAPTURE_AND_PROMOTE_TO_QUEEN,  leftPiece.type, c));
                Moves.add(new Move(fx, fy, fx - 1, fy + c, Move.CAPTURE_AND_PROMOTE_TO_ROOK,   leftPiece.type, c));
            }
            }

        // Promotion via diagonal capturing to the right
        if ((fx < 7) && (fy == (5 * c + 7) / 2)) {
            rightPiece = b.getPieceXY(fx + 1, fy + c);
            if (rightPiece != null && rightPiece.color != c) {
                Moves.add(new Move(fx, fy, fx + 1, fy + c, Move.CAPTURE_AND_PROMOTE_TO_BISHOP, rightPiece.type, c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c, Move.CAPTURE_AND_PROMOTE_TO_KNIGHT, rightPiece.type, c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c, Move.CAPTURE_AND_PROMOTE_TO_QUEEN,  rightPiece.type, c));
                Moves.add(new Move(fx, fy, fx + 1, fy + c, Move.CAPTURE_AND_PROMOTE_TO_ROOK,   rightPiece.type, c));
            }

        }

        // En passant capture
        try {
            final Move lastMove = b.getLastMove();
            if (fx > 0) {

                lastMovePiece = b.getPieceXY(lastMove.toX, lastMove.toY);
                // The piece stands to the left
                if (lastMovePiece != null && (lastMove.toX == fx - 1) && (lastMove.toY == fy)
                        && (lastMovePiece.type == Piece.PAWN)
                        && (Math.abs(lastMove.fromY - lastMove.toY) == 2)) {
                    Moves.add(new Move(fx, fy, fx - 1, fy + c, Move.CAPTURE_ENPASSANT, Piece.EMPTY, c));
                }
            }

            if (fx < 7) {
                lastMovePiece = b.getPieceXY(lastMove.toX, lastMove.toY);
                // The piece stands to the right
                if (lastMovePiece != null && (lastMove.toX == fx + 1) && (lastMove.toY == fy)
                        && (lastMovePiece.type == Piece.PAWN)
                        && (Math.abs(lastMove.fromY - lastMove.toY) == 2)) {
                    Moves.add(new Move(fx, fy, fx + 1, fy + c, Move.CAPTURE_ENPASSANT, Piece.EMPTY, c));

                }
            }

        } catch (java.util.EmptyStackException e) {
            // There were no last move. We must be a beginning of game.
            // Hence no en passant moves are possible
        }

      return Moves;

    }

    public static ArrayList<Move> kingMoves(Board b, Piece p)  {
        final ArrayList<Move> Moves = new ArrayList<Move>();
        final int c = b.inMove();
        final int fx = p.xPos;
        final int fy = p.yPos;

        Piece pTo = null;

        // Castling short
        if (fx == 4 && 
            b.canCastleShort()   && 
            b.freeSquare(5, fy) && 
            b.freeSquare(6, fy) &&
            !b.attacks(5, fy) &&
            !b.attacks(6, fy)) 
        {
            assert (b.getPieceXY(7, fy) != null);
            assert (b.getPieceXY(7, fy).type == Piece.ROOK) : "Expected rook, found wirdo piece: " + b.getPieceXY(7, fy).toString() + "fx, fy=" + fx + ", " + fy + "c = " + c;
            assert (b.getPieceXY(7, fy).color == c);
            Moves.add(new Move(fx, fy, fx+2, fy, Move.CASTLE_SHORT, Piece.EMPTY, c));
        }

        // Castling long
        if (fx == 4 && 
            b.canCastleLong()   && 
            b.freeSquare(3, fy) && 
            b.freeSquare(2, fy) &&
            b.freeSquare(1, fy) &&
            !b.attacks(2, fy) &&
            !b.attacks(3, fy))
        {
         Moves.add(new Move(fx, fy, fx-2, fy, Move.CASTLE_LONG, Piece.EMPTY, c));
        }
        
        
        // King moves left
        if (fx > 0) {
            if (!b.attacks(fx - 1, fy)) {
                // Left, and not up/down
                if (b.freeSquare(fx - 1, fy)) Moves.add(new Move(fx, fy, fx - 1, fy, Move.NORMALMOVE, Piece.EMPTY, c));
                else {
                    pTo = b.getPieceXY(fx - 1, fy);
                    if (pTo != null && pTo.color != c) Moves.add(new Move(fx, fy, fx - 1, fy, Move.CAPTURE, pTo.type, c));
                }
            }
            
            // Up
            if (fy < 7 && !b.attacks(fx - 1, fy + 1)) 
                if (b.freeSquare(fx - 1, fy + 1)) Moves.add(new Move(fx, fy, fx - 1, fy + 1, Move.NORMALMOVE, Piece.EMPTY, c));
                else {
                  pTo = b.getPieceXY(fx - 1, fy + 1);
                  if (pTo != null && pTo.color != c) Moves.add(new Move(fx, fy, fx - 1, fy + 1, Move.CAPTURE, pTo.type, c));
                }
            

            // Down
            if (fy > 0 && !b.attacks(fx - 1, fy - 1)) {
                if (b.freeSquare(fx - 1, fy - 1)) Moves.add(new Move(fx, fy, fx - 1, fy - 1, Move.NORMALMOVE, Piece.EMPTY, c));
                else {
                    pTo = b.getPieceXY(fx - 1, fy - 1);
                    if (pTo != null && pTo.color != c) Moves.add(new Move(fx, fy, fx - 1, fy - 1, Move.CAPTURE, pTo.type, c));
                }
            }
        } // End of "Left" section


        // King moves right
        if (fx < 7) {
                if (!b.attacks(fx + 1, fy)) {
                // To side
                if (b.freeSquare(fx + 1, fy)) Moves.add(new Move(fx, fy, fx + 1, fy, Move.NORMALMOVE, Piece.EMPTY, c));
                else {
                    pTo = b.getPieceXY(fx + 1, fy);
                    if (pTo != null && pTo.color != c) Moves.add(new Move(fx, fy, fx + 1, fy, Move.CAPTURE, pTo.type, c));
                }
            }

            // Up and to the right
            if (fy < 7) {
                if (!b.attacks(fx + 1, fy + 1)) {
                if (b.freeSquare(fx + 1, fy + 1)) Moves.add(new Move(fx, fy, fx + 1, fy + 1, Move.NORMALMOVE, Piece.EMPTY, c));
                else {
                    pTo = b.getPieceXY(fx + 1, fy + 1);
                    if (pTo != null && pTo.color != c) Moves.add(new Move(fx, fy, fx + 1, fy + 1, Move.CAPTURE, pTo.type, c));
                  }
               }
            }

            // Down and to the right
            if (fy > 0) {
               if (!b.attacks(fx + 1, fy - 1)) {
                if (b.freeSquare(fx + 1, fy - 1)) Moves.add(new Move(fx, fy, fx + 1, fy - 1, Move.NORMALMOVE, Piece.EMPTY, c));
                else {
                    pTo = b.getPieceXY(fx + 1, fy - 1);
                    if (pTo != null && pTo.color != c) Moves.add(new Move(fx, fy, fx + 1, fy - 1, Move.CAPTURE, pTo.type, c));
                }
              }
            }
        } // End of "right" section

        // King moves straight up
        if (fy < 7 && !b.attacks(fx, fy + 1)) {
            if (b.freeSquare(fx, fy + 1)) Moves.add(new Move(fx, fy, fx, fy + 1, Move.NORMALMOVE, Piece.EMPTY, c));
            else {
                pTo = b.getPieceXY(fx, fy + 1);
                if (pTo != null && pTo.color != c) Moves.add(new Move(fx, fy, fx, fy + 1, Move.CAPTURE, pTo.type, c));
            }
        }
        

        // King moves straight down
        if (fy > 0 && !b.attacks(fx, fy - 1)) {
            if (b.freeSquare(fx, fy - 1)) Moves.add(new Move(fx, fy, fx, fy - 1, Move.NORMALMOVE, Piece.EMPTY, c));
            else {
                pTo = b.getPieceXY(fx, fy - 1);
                if (pTo != null && pTo.color != c) Moves.add(new Move(fx, fy, fx, fy - 1, Move.CAPTURE, pTo.type, c)); 
            }
        }
        

      return Moves;
    }

    // Genereate the possible moves for one single piece
    public static ArrayList<Move> generateMoves(Board b, Piece p)  {
        ArrayList<Move> Moves = new ArrayList<Move>();
        final int sideToMove        = b.inMove();

        if (p.color != sideToMove) return Moves;
        
        switch (p.type) {            
            case Piece.PAWN:
                    return pawnMoves(b, p);
            case Piece.KING:
                return kingMoves(b, p);
            case Piece.KNIGHT:
                return knightMoves(b, p);
            case Piece.BISHOP:
                return bishopMoves(b, p);
            case Piece.ROOK:
                return rookMoves(b, p);
            case Piece.QUEEN:
                Moves = bishopMoves(b, p);
                Moves.addAll(rookMoves(b, p));
                return Moves;
        }
        return Moves;
    }

    
    public static ArrayList<Move> knightMoves(Board b, Piece p)  {
        final ArrayList<Move> Moves = new ArrayList<Move>();

        Move newMove;
        
        newMove = Move.genMove(b, p, -2,  1); if (newMove != null) Moves.add(newMove);
        newMove = Move.genMove(b, p, -2, -1); if (newMove != null) Moves.add(newMove);
        newMove = Move.genMove(b, p, -1,  2); if (newMove != null) Moves.add(newMove);        
        newMove = Move.genMove(b, p,  1,  2); if (newMove != null) Moves.add(newMove);                
        newMove = Move.genMove(b, p, -1, -2); if (newMove != null) Moves.add(newMove);
        newMove = Move.genMove(b, p,  1, -2); if (newMove != null) Moves.add(newMove);
        newMove = Move.genMove(b, p,  2,  1); if (newMove != null) Moves.add(newMove);
        newMove = Move.genMove(b, p,  2, -1); if (newMove != null) Moves.add(newMove);
        
        return Moves;
    }
    
    
    public static ArrayList<Move> bishopMoves(Board b, Piece p) {
        final ArrayList<Move> Moves = new ArrayList<Move>();
        int d; // Displacement
        
        Move newMove = null;
        
        //System.out.println("h");
        //System.exit(0);
        
        // Move up and right
        for (d = 1; ((p.xPos + d <= 7) && (p.yPos + d <= 7)); d++) {
         newMove = Move.genMove(b, p, d, d);
         if (newMove == null) break; // The square was occupied by my own piece
         Moves.add(newMove);
         if (newMove.aCapture()) break; // The square was occopied by an opponent square
        }

        // Move up and left
        for (d = 1; ((p.xPos - d >= 0) && (p.yPos + d <= 7)); d++) {
         newMove = Move.genMove(b, p, -d, d);
         if (newMove == null) break; // The square was occupied by my own piece
         Moves.add(newMove);
         if (newMove.aCapture()) break; // The square was occopied by an opponent square
        }
        
        // Move down and left
        for (d = 1; ((p.xPos - d >= 0) && (p.yPos - d >= 0)); d++) {
         newMove = Move.genMove(b, p, -d, -d);
         if (newMove == null) break; // The square was occupied by my own piece
         Moves.add(newMove);
         if (newMove.aCapture()) break; // The square was occopied by an opponent square
        }

        // Move down and right
        for (d = 1; ((p.xPos + d <= 7) && (p.yPos - d >= 0)); d++) {
         newMove = Move.genMove(b, p, d, -d);
         if (newMove == null) break; // The square was occupied by my own piece
         Moves.add(newMove);
         if (newMove.aCapture()) break; // The square was occopied by an opponent square
        }

        
        return Moves;
    }

    
    public static ArrayList<Move> rookMoves(Board b, Piece p) {
        final ArrayList<Move> Moves = new ArrayList<Move>();
        int d; // Displacement
        
        Move newMove = null;
        
        //System.out.println("h");
        //System.exit(0);
        
        // Move up
        for (d = 1; (p.yPos + d <= 7); d++) {
         newMove = Move.genMove(b, p, 0, d);
         if (newMove == null) break; // The square was occupied by my own piece
         Moves.add(newMove);
         if (newMove.aCapture()) break; // The square was occopied by an opponent square
        }

        // Move down
        for (d = 1; (p.yPos - d >= 0); d++) {
         newMove = Move.genMove(b, p, 0, -d);
         if (newMove == null) break; // The square was occupied by my own piece
         Moves.add(newMove);
         if (newMove.aCapture()) break; // The square was occopied by an opponent square
        }
        
        // Move left
        for (d = 1; (p.xPos - d >= 0); d++) {
         newMove = Move.genMove(b, p, -d, 0);
         if (newMove == null) break; // The square was occupied by my own piece
         Moves.add(newMove);
         if (newMove.aCapture()) break; // The square was occopied by an opponent square
        }

        // Move right 
        for (d = 1; (p.xPos + d <= 7); d++) {
         newMove = Move.genMove(b, p, d, 0);
         if (newMove == null) break; // The square was occupied by my own piece
         Moves.add(newMove);
         if (newMove.aCapture()) break; // The square was occopied by an opponent square
        }

        
        return Moves;
    }
     
    public static ArrayList<Move> generateAllMoves(Board b) {
        final ArrayList<Move> moves = new ArrayList<Move>();
        ArrayList<Move> r = null;
        Piece p;

        if (b.isDraw() || b.isMate()) return moves; // I.e. no moves to generate...

        for (int i = 0; i < b.getNumberOfPieces(); i++) {
            p = b.getPiece(i);
            r = generateMoves(b, p);
            if (r != null && !r.isEmpty()) moves.addAll(r);
        }

        return moves;
    }
}
 
