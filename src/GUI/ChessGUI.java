
package GUI;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author mshome
 */
public class ChessGUI extends JFrame {
    public ChessGUI() {
        setSize(100,100);
        setTitle("Simplistic Java Chess");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        Font f = loadChessFont();
        JLabel jl1 = new JLabel("bklmnopqrstuvxyzw");
        JLabel jl2 = new JLabel("BKLMNOPQRSTUVXW");
        //1234567890
        jl1.setFont(f);
        jl2.setFont(f);
        add(jl1);
        add(jl2);
        setLayout(new GridLayout(8, 8));
    }
    
    private Font loadChessFont() {
     try {
       Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Alpha.ttf")).deriveFont(30f);
       
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
}
