
package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import sjc.Board;
import sjc.Piece;


public class ChessGUI extends JFrame {
    private static String chessToFigurines[][][] = new String[2][2][7];
    private static final String emptySpaceWhiteFigurine = " ";
    private static final String emptySpaceBlackFigurine = "+";
    private Font chessFont;
    private static int fontSizeInPixels = 0;
    
    private void initFigurines() {
        // Black field, black piece
        chessToFigurines[0][0][Piece.PAWN]   = "O";
        chessToFigurines[0][0][Piece.KNIGHT] = "J";
        chessToFigurines[0][0][Piece.BISHOP] = "N";
        chessToFigurines[0][0][Piece.ROOK]   = "T";
        chessToFigurines[0][0][Piece.QUEEN]  = "W";
        chessToFigurines[0][0][Piece.KING]   = "L";
        
        // Black field, white piece
        chessToFigurines[0][1][Piece.PAWN]   = "P";
        chessToFigurines[0][1][Piece.KNIGHT] = "H";
        chessToFigurines[0][1][Piece.BISHOP] = "B";
        chessToFigurines[0][1][Piece.ROOK]   = "R";
        chessToFigurines[0][1][Piece.QUEEN]  = "Q";
        chessToFigurines[0][1][Piece.KING]   = "K";

        // White field, black piece
        chessToFigurines[1][0][Piece.PAWN]   = "o";
        chessToFigurines[1][0][Piece.KNIGHT] = "j";
        chessToFigurines[1][0][Piece.BISHOP] = "n";
        chessToFigurines[1][0][Piece.ROOK]   = "t";
        chessToFigurines[1][0][Piece.QUEEN]  = "w";
        chessToFigurines[1][0][Piece.KING]   = "l";
        
        // White field, white piece
        chessToFigurines[1][1][Piece.PAWN]   = "p";
        chessToFigurines[1][1][Piece.KNIGHT] = "h";
        chessToFigurines[1][1][Piece.BISHOP] = "b";
        chessToFigurines[1][1][Piece.ROOK]   = "r";
        chessToFigurines[1][1][Piece.QUEEN]  = "q";
        chessToFigurines[1][1][Piece.KING]   = "k";
        
        chessFont = loadChessFont();
        
        fontSizeInPixels = this.getFontMetrics(chessFont).stringWidth(" ");
        
      
   
    }
    
    
    
    public ChessGUI() {
        initFigurines();
        
        
        
        //setSize(fontSizeInPixels*8,fontSizeInPixels*8); NOT needed pack will deal with it
        setTitle("Simplistic Java Chess");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       
        
       /* JLabel jl1 = new JLabel("bklmnopqrstuvxyzw");
        JLabel jl2 = new JLabel("BKLMNOPQRSTUVXW");
        //1234567890
        jl1.setFont(chessFont);
        jl2.setFont(chessFont);
        add(jl1);*/
        //add(jl2);
        LayoutManager lm = new GridLayout(8,8);
        
        setLayout(lm);
        showBoard(new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
        
    }
    
    private Font loadChessFont() {
     try {
       Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Alpha.ttf")).deriveFont(60f);
       
       GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
       ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Alpha.ttf")));
       return customFont;
     } 
     catch (IOException e) {
         System.out.println("Could not load chess font file... " + e.getMessage());
     }
     catch(FontFormatException e)
     {
        System.out.println("Could not understand chess font...");
     }
     return null;
     //yourSwingComponent.setFont(customFont);
    }
    
    
    
    public void showBoard(Board b) {
      int x, y;
      int pieceColor;
      int fieldColor;
      Piece p;
       String figurine;
       
       for (y = 7; y >= 0; y--) { 
        for (x = 0; x < 8; x++) {
      
         p = b.getPiece(x, y);
         fieldColor = (x+y) % 2;

         if (p == null) {
            figurine = fieldColor == 1 ? emptySpaceWhiteFigurine : emptySpaceBlackFigurine ;
         }
         else 
         {
            pieceColor = p.color == 1 ? 1 : 0;
            figurine = chessToFigurines[fieldColor][pieceColor][p.type];
         }
         JLabel l = new JLabel(figurine);
         l.setFont(chessFont);
         l.setName(x +"," + y);
         l.setOpaque(true);
         l.setBackground(Color.lightGray);
         add(l);
         l.addMouseListener(new MouseAdapter()  
            {  
                public void mouseClicked(MouseEvent e)  
                {  
                   // you can open a new frame here as
                   // i have assumed you have declared "frame" as instance variable
                   JLabel jl = (JLabel) e.getSource();
                   //JFrame frame = new JFrame("new frame " + jl.getName());
                   if (jl.getBackground()==Color.lightGray) {
                    //jl.setForeground(Color.green);
                    jl.setBackground(Color.green);
                   } 
                   else jl.setBackground(Color.lightGray);
                   //frame.setVisible(true);

                }  
            }); 
         
       }
      }
      
      this.setResizable(false);
      pack();
      setVisible(true);
     
    }
}
