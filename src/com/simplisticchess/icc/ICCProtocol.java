package com.simplisticchess.icc;

import com.simplisticchess.move.MoveParser;
import com.simplisticchess.move.NoMoveException;
import com.simplisticchess.board.Board;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Color;

/**
 *
 * @author mku
 */
public class ICCProtocol {
    private final static int LINE_TAG    = 0; // identifies line
    private final static int BL1         = 1; // eight fields representing the board position
    private final static int BL2         = 2;
    private final static int BL3         = 3;
    private final static int BL4         = 4;
    private final static int BL5         = 5;
    private final static int BL6         = 6;
    private final static int BL7         = 7;
    private final static int BL8         = 8;
    private final static int WHO_IN_MOVE = 9; // B = Black, W = White
    private final static int DOUBLE_MOVE = 10; // -1 if the previous move was NOT a double pawn push, otherwise the chess  board file  (numbered 0--7 for a--h) in which the double push was made
    private final static int WHITE_O_O   = 11;
    private final static int WHITE_O_O_O = 12;
    private final static int BLACK_O_O   = 13;
    private final static int BLACK_O_O_O = 14;
    private final static int LAST_IRR_MOVE = 15; //the number of moves made since the last irreversible move.  (0 if last move   was irreversible.  If the value is >= 100, the game can be declared a draw   due to the 50 move rule.)
    private final static int GAME_NUMBER   = 16;
    private final static int WHITE_NAME   = 17;
    private final static int BLACK_NAME   = 18;
    private final static int MY_RELATION = 19; /* my relation to this game:
                                                  -3 isolated position, such as for "ref 3" or the "sposition" command
                                                  -2 I am observing game being examined
                                                   2 I am the examiner of this game
                                                  -1 I am playing, it is my opponent's move
                                                   1 I am playing and it is my move
                                                   0 I am observing a game being played */
    private final static int INITIAL_TIME = 20; //initial time  of the match
    private final static int INCREMENT_TIME = 21; // increment In seconds) of the match
    private final static int WHITE_MATERIAL = 22; // White material strength
    private final static int BLACK_MATERIAL = 23; // Black material strength
    private final static int WHITE_TIME_LEFT = 24;//White's remaining time seconds
    private final static int BLACK_TIME_LEFT = 25;//Blacks's remaining time seconds
    private final static int MOVE_NUMBER     = 26; //* the number of the move about to be made (standard chess numbering -- White's and Black's first moves are both 1, etc.)
    private final static int MOVE_STRING     = 27; //verbose coordinate notation for the previous move ("none" if there were none) [note this used to be broken for examined games]
    private final static int TIME_LAST_MOVE = 28; // time taken to make previous move "(min:sec)".
    private final static int SHORT_MOVE_STRING = 29; //* pretty notation for the previous move ("none" if there is none)
    private final static int FLIP_BOARD_BIT   = 30; //* flip field for board orientation: 1 = Black at bottom, 0 = White at bottom.
    // Two more, I do not know what they do...
    
    private String   response;
    private String[] parts;
    private Board    theboard;
    
    
    private String   filterColor;
    
    
    public ICCProtocol() {}
    
    public void setBoard(Board b) {
        theboard = b;
    }
    
    // Get moves for color c
    public void setColor(Color c) {
        if (c == Color.WHITE) { 
            filterColor = "W";
        } else
        {
            filterColor = "B";
        }
    }
    
    public boolean isAMoveResponse() {     
      return parts.length >= 31 &&
             parts[BL1].length() == 8 && 
             parts[BL2].length() == 8 &&
             parts[BL3].length() == 8 &&
             parts[BL4].length() == 8 &&  
             parts[BL5].length() == 8 &&  
             parts[BL6].length() == 8 &&  
             parts[BL7].length() == 8 &&   
             parts[BL8].length() == 8 &&   
             parts[LINE_TAG].startsWith("<") &&
             parts[LINE_TAG].endsWith(">") && parts[WHO_IN_MOVE].equalsIgnoreCase(filterColor);
    }

      
    public void setMoveString(String res) {
      this.response = res;
      parts = response.split("\\s+");    
    }
    
    
    public ICCProtocol(String res) {
      this.response = res;
      parts = response.split("\\s+"); 
      
      /*for (int i = 0; i < parts.length; i++) {
       System.out.printf("%2d: %s\n", i, parts[i]);       
      }*/
      System.out.println("---------------");
      
         System.out.println(isAMoveResponse()); 
         System.out.println(this.toString());
    }

    public String toIccMoveString(Move m) {
     return "";
    }
    
    private String getAlgebraicMoveStr() {
        String s = parts[MOVE_STRING];
        String inMove = parts[WHO_IN_MOVE];      
        
        if (s.equalsIgnoreCase("none")) {
            return null;
        }
        
        if (s.toLowerCase().contains("o-o")   && inMove.equalsIgnoreCase("W")) {
            return "e1g1";
        }
        if (s.toLowerCase().contains("o-o-o") && inMove.equalsIgnoreCase("W")) {
            return "e1c1";
        }
        if (s.toLowerCase().contains("o-o")   && inMove.equalsIgnoreCase("B")) {
            return "e8g8";
        }
        if (s.toLowerCase().contains("o-o-o") && inMove.equalsIgnoreCase("B")) {
            return "e8c8";
        }
        
        s = s.substring(2); // Remove Figurine letter and symbol "/"
        
        
        s = s.replace("-", ""); // Remove symbol "-"
        s = s.replace("=", ""); // Remove symbol "="
        
 
        // Entered: b7-a8=N
        // Nnbqkbnr p---ppp- -------- -------p -------- -------- PPPP-PPP RNBQKBNR B -1 1 1 1 0 0 318 xyzwxyzwq xyzwxyzw -1 2 60 41 31 311 264 5 P/b7-a8=N (0:24) bxa8=N 0 1 0

        return s;         
    }
    
    public Move getMove() {
     MoveParser io = new MoveParser();
     
     if (isAMoveResponse()) {
         try {
             return io.parseMove(theboard, getAlgebraicMoveStr());
         } catch (NoMoveException ex) {
             System.out.println("Move parse error in ICCProtocol.java");
         }
     }
       return null; 
    }
    
    public String toString() {
        //String mku = String.format(, parts);
        
        return  "Game number:          " + parts[GAME_NUMBER]  + "\n" +
                "Name of white player: " + parts[WHITE_NAME]   + "\n" +
                "Name of black player: " + parts[BLACK_NAME]   + "\n" +
                "Initial time:         " + parts[INITIAL_TIME] + "\n" + 
                "Incremental time:     " + parts[INCREMENT_TIME] + "\n";
    }
    
    
        
}
