package sjc;

import java.util.Iterator;

public final class Board implements Cloneable {
    private                Position position; // Current position of pieces
    private State          state;             // State wrt. casteling, 
                                              // latest move and movenumber etc.
    private History        history;           // Previus states.

    public Board() {
        position          = new Position();        
        state             = new State();
        history           = new History();
    }
    
    public Board(String fen) {
        this();
        setupFENboard(fen);  
    }


    public int     getNumberOfPieces()       { return position.numberOfPieces(); }
    public int     inMove()                  { return state.inMove;  }
    public void    setBlackToMove()          { state.inMove = Piece.BLACK;}
    public void    setWhiteToMove()          { state.inMove = Piece.WHITE;}
    public void    setDraw()                 { state.drawFlag = true;}
    public void    setMate()                 { state.mateFlag = true;}
    public boolean isDraw()                  { return state.drawFlag;}
    public boolean isMate()                  { return state.mateFlag;}
    
    public Move    getLastMove()             { return history.peek().move; }
    public Piece   getPiece(final int i)     { return position.getPiece(i); }
    public void    insertPiece(Piece p)      { position.insertPiece(p); }
    public Piece   removePiece(int x, int y) { return position.removePiece(x, y); }
    public boolean freeSquare(int x, int y)  { return position.freeSquare(x, y); }
    public boolean canCastleShort() {
     return (state.inMove == Piece.BLACK && state.blackCanCastleShort) ||
            (state.inMove == Piece.WHITE && state.whiteCanCastleShort);
            
    }
    public boolean canCastleLong() {
     return (state.inMove == Piece.BLACK && state.blackCanCastleLong) ||
            (state.inMove == Piece.WHITE && state.whiteCanCastleLong);
            
    }
            
    /**
     * 
     * @param x
     * @param y
     * @return true if the side not in move, in board b attacks square (x, y) and otherwise false
     */
    public boolean attacks(int x, int y)     { return position.attacks( x,  y,  state.inMove); }
   
    /**
     * 
     * @param x
     * @param y
     * @param sideToMove
     * @return true if the side not in move, in board b attacks square (x, y) and otherwise false
     */
    public boolean attacks(int x, int y, int sideToMove) {return position.attacks(x, y, sideToMove);}
    
    public String  getBitboardString()       { return position.bitboard.toString();}
    public Boolean drawBy50MoveRule()        { return state.halfMoveClock >= 50;   }

    /**
     * 
     * @param color
     * @return Is player with color color in check by opponent?
     */
    public Boolean isInCheck(int color) {
        Boolean res = false;
        Piece p;
        int kingx = -1, 
                kingy = -1;
        
        for (int i = 0; i < getNumberOfPieces(); i++) {
         p = getPiece(i);
         if (p.type == Piece.KING && p.color == color) {
          kingx = p.xPos;
          kingy = p.yPos;
          break;
         }
        }
        if (kingx != -1 && position.attacks(kingx, kingy, color)) res = true;
        return res;
    }
    
    public Boolean drawBy3RepetionsRule() {
        State h;
        int k = 0;

        for (int i = state.halfMovesIndex3PosRepition; i < history.size(); i ++) {
            h = history.get(i);
            if (position.bitboard.equals(h.bbposition)) k++;
        }

        return k >= 3;
    }

    // Find a piece at a certain location
    public Piece getPieceXY(int x, int y) {
        final Piece p =  position.getPieceXY(x, y);

        if (p != null) assert p.xPos == x && p.yPos == y;
        return p;
    }

 
    public void performMove(Move m) {
       Piece p;
            
       p = getPieceXY(m.fromX, m.fromY);
       
       state.moveNumber++;

       // Put the move m on the stack
       state.move = m;       
       history.add(state.clone());


       // Used to determine the 50-move rule, three times repition
       if (p.type == Piece.PAWN) {
           state.halfMoveClock              = 0;
           state.halfMovesIndex3PosRepition = state.moveNumber;
       }
       else state.halfMoveClock++;

       // Moving a rook can disallow castling in the future
        if (p.type == Piece.ROOK) {
            if (m.whoMoves == Piece.BLACK) {
              if (m.fromX == 0 && state.blackCanCastleLong)  {
                  state.blackCanCastleLong  = false;
              }
              if (m.fromX == 7 && state.blackCanCastleShort) {
                  state.blackCanCastleShort = false;
              }
            }
            else {
              if (m.fromX == 0 && state.whiteCanCastleLong)  {
                  state.whiteCanCastleLong  = false;
              }
              if (m.fromX == 7 && state.whiteCanCastleShort) {
                  state.whiteCanCastleShort = false;
              }
            }
        }

        // Moving the king will disallow castling in the future
        if (p.type == Piece.KING) {
            if (m.whoMoves == Piece.BLACK) {
                  state.blackCanCastleShort = false;
                  state.blackCanCastleLong  = false;
            }

             if (m.whoMoves == Piece.WHITE) {
                  state.whiteCanCastleShort = false;
                  state.whiteCanCastleLong  = false;
              }           
        }
        
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
                position.movePiece(m.fromX, m.fromY, m.toX, m.toY);
                break;

            case Move.CAPTURE_ENPASSANT:
                position.movePiece(m.fromX, m.fromY, m.toX, m.toY);
                removePiece(m.toX, m.fromY);
                break;

            case Move.CASTLE_SHORT:               
                // Move the king first
                position.movePiece(m.fromX, m.fromY, m.toX, m.toY);
                // Then the rook
                position.movePiece(7, m.fromY, 5, m.fromY);
                break;

            case Move.CASTLE_LONG:
                // Move the king first
                position.movePiece(m.fromX, m.fromY, m.toX, m.toY);
                // Then the rook
                position.movePiece(0, m.fromY, 3, m.fromY);
                break;

            case Move.CAPTURE:
                // Capturing a rook may affect casteling opputunities!
                if (getPieceXY(m.toX, m.toY).type == Piece.ROOK) {
                    if (m.whoMoves == Piece.WHITE && m.toY == 7) {
                      if (m.toX == 0 && state.blackCanCastleLong)  state.blackCanCastleLong  = false;
                      if (m.toX == 7 && state.blackCanCastleShort) state.blackCanCastleShort = false;
                    }
                    if (m.whoMoves == Piece.BLACK && m.toY == 0) {
                      if (m.toX == 0 && state.whiteCanCastleLong)  state.whiteCanCastleLong  = false;
                      if (m.toX == 7 && state.whiteCanCastleShort) state.whiteCanCastleShort = false;
                    }
                }
                // Do the capture
                removePiece(m.toX, m.toY);
                position.movePiece(m.fromX, m.fromY, m.toX, m.toY);
                break;
        }

       
        // Swap the move color
        state.inMove = -state.inMove;


    }

    public boolean retractMove() {
        int color = 0;
        Move        m;

        state.moveNumber--;

        try {
            state = history.pop();
            m     = state.move;

            if (m.aSimplePromotion()) {
                insertPiece(new Piece(m.fromX, m.fromY, m.whoMoves, Piece.PAWN));
                removePiece(m.toX, m.toY);
            }

            if (m.aCapturePromotion()) {
                removePiece(m.toX, m.toY);
                insertPiece(new Piece(m.toX,   m.toY,   -m.whoMoves, m.aCapturedPiece));
                insertPiece(new Piece(m.fromX, m.fromY, m.whoMoves, Piece.PAWN));
                return true;
            }

            switch (m.type) {
                case Move.NORMALMOVE:
                    position.movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    break;

                case Move.CAPTURE_ENPASSANT:
                    if (m.whoMoves == Piece.WHITE) {
                        color = Piece.BLACK;
                    }
                    if (m.whoMoves == Piece.BLACK) {
                        color = Piece.WHITE;
                    }
                    insertPiece(new Piece(m.toX, m.fromY, color, Piece.PAWN));
                    position.movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    break;

                case Move.CASTLE_SHORT:
                  // Move the king back
                    position.movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    // Then the rook
                    position.movePiece(5, m.fromY, 7, m.fromY);
                    break;

                case Move.CASTLE_LONG:
                   // Move the king back
                    position.movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    // Then the rook
                    position.movePiece(3, m.fromY, 0, m.fromY);
                    break;

                case Move.CAPTURE:
                    position.movePiece(m.toX, m.toY, m.fromX, m.fromY);
                    insertPiece(new Piece(m.toX, m.toY, -m.whoMoves, m.aCapturedPiece));
                    break;
            }
            return true;
        } catch (java.util.EmptyStackException e) {
            return false;
        }
    }


   
    
    // Given a position in the FEN - notation.
    // Set up the board
    // TODO: 1) Error handling in case of parse errors.
    //       2) Simplify the code somewhat
    private void setupFENboard(String sfen) {
        int x = 0;
        int y = 7;
        int i;
        int parsingPartNo;
        char c;
        final String fen = Utils.trimWhiteSpace(sfen.trim());
        String num1 = "";
        String num2 = "";

       state.whiteCanCastleShort = false;
       state.whiteCanCastleLong  = false;
       state.blackCanCastleShort = false;
       state.blackCanCastleLong  = false;

       // Parsing part no. 1
       parsingPartNo = 1;

        // Traverse input string
        for (i = 0; i < fen.length(); i++) {
            c = fen.charAt(i);						
            assert x <= 8 && y >= 0 : "Error (Not a correct FEN board)";

            if (parsingPartNo == 1) {
                if (c == ' ') {
                    parsingPartNo = 2;
                    continue;
                }

                if (c >= '1' && c <= '8') { x = x + (int) (c - '0'); }
                else if (c >= 'b' && c <= 'r') {
                    insertPiece(new Piece(x, y, c));
                    x++;
                    continue;
                }
                else if (c >= 'B' && c <= 'R') {
                    insertPiece(new Piece(x, y, c));
                    x++;
                    continue;
                } else if (c == '/') {
                    y--;
                    x = 0;
                    continue;
                }
            }
             
            if (parsingPartNo == 2) {                
                switch (c) {
                    case 'w': state.inMove = Piece.WHITE; break;
                    case 'b': state.inMove = Piece.BLACK; break;
                    case ' ': parsingPartNo = 3; continue;   
                }
            }

            if (parsingPartNo == 3) {
                switch (c) {
                    case 'K': state.whiteCanCastleShort = true; break;
                    case 'Q': state.whiteCanCastleLong  = true; break;
                    case 'k': state.blackCanCastleShort = true; break;
                    case 'q': state.blackCanCastleLong  = true; break;
                    case ' ': parsingPartNo = 4; continue;
                }
            }

            if (parsingPartNo == 4) {             
             if (c == ' ') {
                 parsingPartNo = 5;
                 continue;
             }

              if (c == '-') {
                  continue;
              }

              if (c != ' ') {
               final int xPawn = (int) (c - 'a');
               final int yPawn = (int) (fen.charAt(i+1) - '1');
               assert xPawn >= 0 && xPawn <= 7;
               assert yPawn >= 0 && yPawn <= 7;
               //i++;
               final Piece p = getPieceXY(xPawn, yPawn-state.inMove);
               if (p != null && p.type == Piece.PAWN) {
                   state.move = new Move(xPawn, yPawn+state.inMove, xPawn, yPawn-state.inMove, Move.NORMALMOVE, Piece.EMPTY, state.inMove);                   
                   history.add(state);
               }

               parsingPartNo = 5;
               i = i + 2;
               
              }

              if (fen.charAt(i) == ' ') {
                  parsingPartNo = 5;
                  continue;
              }
            }

            if (parsingPartNo == 5) {
              if (c == ' ') {
                  parsingPartNo = 6;
                  continue;
              }

              if (c != ' ') {
                  num1 = num1 + c;
              }
            }

            if (parsingPartNo == 6) {
              if (c == ' ') { // end of story :)
                  parsingPartNo = 7;
                  continue;
              }

              if (c != ' ') {
                  num2 = num2 + c;
              }  
            }
                    
    }

       state.halfMoveClock = Integer.parseInt(num1);
       state.moveNumber    = 2 * Integer.parseInt(num2) - 2;
       if (state.moveNumber != 0 && state.inMove == Piece.BLACK) state.moveNumber--;

    }
    

  /**
   * Returns the board as ASCII art and game other information
   */
    @Override
    public String toString() {
    String s = position.toString();

    if (inMove() == Piece.WHITE) s = s + "  White to move\n";
    else s = s + "  Black to move\n";

    s = s + state.toString();

    if (!history.isEmpty()) {         
         if (-state.inMove == Piece.WHITE) s = s + "Last move " + (state.moveNumber+1)/2 + "."    +  history.peek().move.toString() + "\n";
         else                              s = s + "Last move " + (state.moveNumber+1)/2 + "...." +  history.peek().move.toString() + "\n";
       }

    s = s + "Immediate evaluation: " + Evaluator.evaluate(this) + "\n";
    
    s = s + "Move history: " + history.toString();

    return s;
    }
  
  
    @Override
    public Board clone() {
        final Board theClone      = new Board();
        theClone.position   = position.clone();
        theClone.state      = this.state.clone();        
        theClone.history    = this.history.clone();

        return theClone;
    }

}