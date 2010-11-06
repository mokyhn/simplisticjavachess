//TODO: Implement inCheck predicate here or BETTER
//an inCapture predicate??

// A numbering of all moves generated. A function on the form
//genmoves(board, moveid) such that only moves with a certain id
//are generated.
// This can be used to optimize our search engine such that not all
// moves have to be generated beforehand. We can simply generated one move
// at a time. (when cutoffs occur in the alpha beta search we will not have
// wasted time for generating moves which where never searched anyway.

// Eliminatate generation of a number of moves when king is in check...

// Add knight moves as the next thing.
// Only allow promotion to a knight...


// The king is not allowed to be in check while another piece is moved...

import java.util.ArrayList;

class Movegenerator {

    public Movegenerator() {
    }

    // Input: Given a board b, and that there is a pawn p
    // Output: The set of moves this pawn can perform.
    public static ArrayList<Move> pawnMoves(Board b, Piece p)  {

        int c = b.whoIsInMove();
        int x = p.xPos;
        int y = p.yPos;

        Piece leftPiece;
        Piece rightPiece;
        Piece lastMovePiece;

        ArrayList<Move> Moves = new ArrayList<Move>();


        // Normal one step forward pawn move
        if (((y < 6) && (c == Piece.WHITE))
          || (y > 1) && (c == Piece.BLACK)) {
            if (b.freeSquare(x, y + c * 1)) Moves.add(new Move(x, y, x, y + c * 1, Move.NORMALMOVE, Piece.EMPTY, c));
        }

        // Normal two step forward pawn move
        if (((y == 1) && (c == Piece.WHITE))
         || ((y == 6) && (c == Piece.BLACK))) {
          if (b.freeSquare(x, y + c * 1) && b.freeSquare(x, y + c * 2)) Moves.add(new Move(x, y, x, (y + c * 2), Move.NORMALMOVE, Piece.EMPTY, c));
        }

        // Non capturing PAWN promotion
        if (((y == 6) && (c == Piece.WHITE) && b.freeSquare(x, 7))
         || ((y == 1) && (c == Piece.BLACK) && b.freeSquare(x, 0))) {
            Moves.add(new Move(x, y, x, y + c, Move.PROMOTE_TO_QUEEN,  Move.NORMALMOVE, c));
            Moves.add(new Move(x, y, x, y + c, Move.PROMOTE_TO_ROOK,   Move.NORMALMOVE, c));
            Moves.add(new Move(x, y, x, y + c, Move.PROMOTE_TO_KNIGHT, Move.NORMALMOVE, c));
            Moves.add(new Move(x, y, x, y + c, Move.PROMOTE_TO_BISHOP, Move.NORMALMOVE, c));
        }

        // Normal diagonal capturing to the left
        if ((x > 0) && (y != (5 * c + 7) / 2)) {
         leftPiece = b.getPieceXY(x - 1, y + c);
         if (leftPiece != null && leftPiece.color != c) Moves.add(new Move(x, y, x - 1, y + c, Move.CAPTURE, leftPiece.type, c));   
        }

        // Normal diagonal capturing to the right
        if ((x < 7) && (y != (5 * c + 7) / 2)) {
            rightPiece = b.getPieceXY(x + 1, y + c);
            if (rightPiece != null && rightPiece.color != c) Moves.add(new Move(x, y, x + 1, y + c, Move.CAPTURE, rightPiece.type, c));
        }

        // Promotion via diagonal capturing to the left
        if ((x > 0) && (y == (5 * c + 7) / 2)) {
            leftPiece = b.getPieceXY(x - 1, y + c);
            if (leftPiece != null && leftPiece.color != c) {
                Moves.add(new Move(x, y, x - 1, y + c, Move.CAPTURE_AND_PROMOTE_TO_BISHOP, leftPiece.type, c));
                Moves.add(new Move(x, y, x - 1, y + c, Move.CAPTURE_AND_PROMOTE_TO_KNIGHT, leftPiece.type, c));
                Moves.add(new Move(x, y, x - 1, y + c, Move.CAPTURE_AND_PROMOTE_TO_QUEEN,  leftPiece.type, c));
                Moves.add(new Move(x, y, x - 1, y + c, Move.CAPTURE_AND_PROMOTE_TO_ROOK,   leftPiece.type, c));
            }
            }

        // Promotion via diagonal capturing to the right
        if ((x < 7) && (y == (5 * c + 7) / 2)) {
            rightPiece = b.getPieceXY(x + 1, y + c);
            if (rightPiece != null && rightPiece.color != c) {
                Moves.add(new Move(x, y, x + 1, y + c, Move.CAPTURE_AND_PROMOTE_TO_BISHOP, rightPiece.type, c));
                Moves.add(new Move(x, y, x + 1, y + c, Move.CAPTURE_AND_PROMOTE_TO_KNIGHT, rightPiece.type, c));
                Moves.add(new Move(x, y, x + 1, y + c, Move.CAPTURE_AND_PROMOTE_TO_QUEEN,  rightPiece.type, c));
                Moves.add(new Move(x, y, x + 1, y + c, Move.CAPTURE_AND_PROMOTE_TO_ROOK,   rightPiece.type, c));
            }

        }

        // En passant capture
        try {
            Move lastMove = b.getLastMove();
            if (x > 0) {

                lastMovePiece = b.getPieceXY(lastMove.toX, lastMove.toY);
                // The piece stands to the left
                if (lastMovePiece != null && (lastMove.toX == x - 1) && (lastMove.toY == y)
                        && (lastMovePiece.type == Piece.PAWN)
                        && (Math.abs(lastMove.fromY - lastMove.toY) == 2)) {
                    Moves.add(new Move(x, y, x - 1, y + c, Move.CAPTURE_ENPASSANT, Piece.EMPTY, c));
                }
            }

            if (x < 7) {
                lastMovePiece = b.getPieceXY(lastMove.toX, lastMove.toY);
                // The piece stands to the right
                if (lastMovePiece != null && (lastMove.toX == x + 1) && (lastMove.toY == y)
                        && (lastMovePiece.type == Piece.PAWN)
                        && (Math.abs(lastMove.fromY - lastMove.toY) == 2)) {
                    Moves.add(new Move(x, y, x + 1, y + c, Move.CAPTURE_ENPASSANT, Piece.EMPTY, c));

                }
            }

        } catch (java.util.EmptyStackException e) {
            // There were no last move. We must be a beginning of game.
            // Hence no en passant moves are possible
        }

      return Moves;

    }

    public static ArrayList<Move> kingMoves(Board b, Piece p)  {
        ArrayList<Move> Moves = new ArrayList<Move>();
        int c = b.whoIsInMove();
        int x = p.xPos;
        int y = p.yPos;

        Piece pTo = null;

        // King moves left
        if (x > 0) {
            if (!b.attacks(x - 1, y)) {
                // Left, and not up/down
                if (b.freeSquare(x - 1, y)) Moves.add(new Move(x, y, x - 1, y, Move.NORMALMOVE, 0, c));
                else {
                    pTo = b.getPieceXY(x - 1, y);
                    if (pTo != null && pTo.color != c) Moves.add(new Move(x, y, x - 1, y, Move.CAPTURE, pTo.type, c));
                }
            }
            
            // Up
            if (y < 7 && !b.attacks(x - 1, y + 1)) 
                if (b.freeSquare(x - 1, y + 1)) Moves.add(new Move(x, y, x - 1, y + 1, Move.NORMALMOVE, 0, c));
                else {
                  pTo = b.getPieceXY(x - 1, y + 1);
                  if (pTo != null && pTo.color != c) Moves.add(new Move(x, y, x - 1, y + 1, Move.CAPTURE, pTo.type, c));
                }
            

            // Down
            if (y > 0 && !b.attacks(x - 1, y - 1)) {
                if (b.freeSquare(x - 1, y - 1)) Moves.add(new Move(x, y, x - 1, y - 1, Move.NORMALMOVE, 0, c));
                else {
                    pTo = b.getPieceXY(x - 1, y - 1);
                    if (pTo != null && pTo.color != c) Moves.add(new Move(x, y, x - 1, y - 1, Move.CAPTURE, pTo.type, c));
                }
            }
        } // End of "Left" section


        // King moves right
        if (x < 7) {
                if (!b.attacks(x + 1, y)) {
                // To side
                if (b.freeSquare(x + 1, y)) Moves.add(new Move(x, y, x + 1, y, Move.NORMALMOVE, 0, c));
                else {
                    pTo = b.getPieceXY(x + 1, y);
                    if (pTo != null && pTo.color != c) Moves.add(new Move(x, y, x + 1, y, Move.CAPTURE, pTo.type, c));
                }
            }

            // Up and to the right
            if (y < 7) {
                if (!b.attacks(x + 1, y + 1)) {
                if (b.freeSquare(x + 1, y + 1)) Moves.add(new Move(x, y, x + 1, y + 1, Move.NORMALMOVE, 0, c));
                else {
                    pTo = b.getPieceXY(x + 1, y + 1);
                    if (pTo != null && pTo.color != c) Moves.add(new Move(x, y, x + 1, y + 1, Move.CAPTURE, pTo.type, c));
                  }
               }
            }

            // Down and to the right
            if (y > 0) {
               if (!b.attacks(x + 1, y - 1)) {
                if (b.freeSquare(x + 1, y - 1)) Moves.add(new Move(x, y, x + 1, y - 1, Move.NORMALMOVE, 0, c));
                else {
                    pTo = b.getPieceXY(x + 1, y - 1);
                    if (pTo != null && pTo.color != c) Moves.add(new Move(x, y, x + 1, y - 1, Move.CAPTURE, pTo.type, c));
                }
              }
            }
        } // End of "right" section

        // King moves straight up
        if (y < 7 && !b.attacks(x, y + 1)) {
            if (b.freeSquare(x, y + 1)) Moves.add(new Move(x, y, x, y + 1, Move.NORMALMOVE, 0, c));
            else {
                pTo = b.getPieceXY(x, y + 1);
                if (pTo != null && pTo.color != c) Moves.add(new Move(x, y, x, y + 1, Move.CAPTURE, pTo.type, c));
            }
        }
        

        // King moves straight down
        if (y > 0 && !b.attacks(x, y - 1)) {
            if (b.freeSquare(x, y - 1)) Moves.add(new Move(x, y, x, y - 1, Move.NORMALMOVE, 0, c));
            else {
                pTo = b.getPieceXY(x, y - 1);
                if (pTo != null && pTo.color != c) Moves.add(new Move(x, y, x, y - 1, Move.CAPTURE, pTo.type, c)); 
            }
        }
        

      return Moves;
    }

    // Genereate the possible moves for one single piece
    public static ArrayList<Move> generateMoves(Board b, Piece p)  {
        ArrayList<Move> Moves = new ArrayList<Move>();
        int sideToMove        = b.whoIsInMove();
        Piece king            = null;

        if (p.color != sideToMove) return Moves;

        // Find the king
        for (int i = 0; i < b.getNumberOfPieces(); i++) {
            king = b.getPiece(i);
            if (king.type == Piece.KING && king.color == sideToMove) break;
        }

        

        switch (p.type) {
            // WHITE pawn moves
            case Piece.PAWN:
                //if (!b.attacks(king.xPos, king.yPos))
                    return pawnMoves(b, p); // TODO: Not correct in general...
                //break;
            case Piece.KING:
                return kingMoves(b, p);
        }

        return Moves;
    }

    public static ArrayList<Move> generateAllMoves(Board b) {
        ArrayList<Move> Moves = new ArrayList<Move>();
        ArrayList<Move> r = null;
        Piece p;


        for (int i = 0; i < b.getNumberOfPieces(); i++) {
            p = b.getPiece(i);
            r = generateMoves(b, p);
            if (r != null && !r.isEmpty()) Moves.addAll(r);
        }

        return Moves;
    }

    // Used to testing purposes
    // ArrayList<Move>?
    public static void  printMoves(ArrayList<Move> m) {
        Move myMove;

        for (int i = 0; i < m.size(); i++) {
            myMove = m.get(i);
            System.out.println(myMove.toString());
        }



    }
}