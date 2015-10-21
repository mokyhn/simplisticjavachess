
package com.simplisticchess.GUI;

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
import com.simplisticchess.Board;
import com.simplisticchess.Piece;


public class ChessGUI extends JFrame {
    
    
    
    public ChessGUI() {
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
        BoardPanel bp = new BoardPanel();
        add(bp.setBoard(new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")));
        pack();
        this.setVisible(true);
        this.setResizable(false);
    }
    
   
}
