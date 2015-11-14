package com.simplisticchess.icc;

/**
 *
 * @author Morten Kühnrich
 */
  
import com.simplisticchess.board.Board;
import com.simplisticchess.search.AbstractSearch;
import com.simplisticchess.search.AlphaBetaSearch;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Color;
import java.net.*;
import java.io.*;


public class Telnet {
    private String ca(String s, int i) {
     return Character.toString(s.charAt(i));
    }
    
    
    public void test() throws Exception
    {
        //String command;
        AbstractSearch  engine       = new AlphaBetaSearch();
        Board   theBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        ICCProtocol  icc = new ICCProtocol();
        Move m;
        Boolean playingFlag = false;
        String s,moveStr;
        
        /*System.out.println(parseMove("P/e2-e4"));
        System.out.println(parseMove("P/e2xe4"));
        System.out.println(parseMove("P/e2xe4=Q"));
        System.out.println(parseMove("o-o"));
        System.out.println(parseMove("o-o-o"));*/
        String response = "<12> rn-qkbnr ppp--ppp ---p---- ----p--- --P-P-b- P----N-- -P-P-PPP RNBQKB-R B -1 1 1 1 1 0 241 GuestRWGM GuestDBDL -1 5 0 39 39 243 287 4 o-o (0:22) a3 0 1 0";
        
        /*String[] newtest = parseResponse(response);
        for (int j = 0; j < newtest.length; j++) System.out.println(newtest[j]);
        System.exit(0);
        System.out.println(parseMove(response));*/
        
        icc.setBoard(theBoard);
          //if (22==11+11) return;
        
        
        Socket           soc  = new Socket("69.36.243.188", 23);             //Create object of Socket , freechess.org
        DataInputStream  din  = new DataInputStream(soc.getInputStream());   //Input Stream, read from socket                
        DataOutputStream dout = new DataOutputStream(soc.getOutputStream()); //Output Stream, write to socket 
        
        // Object of Buffered Reader to read command from terminal
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        dout.writeChars("sjcmku\n");  // Username
        dout.writeChars("\n");        // Passwd. empty
        
        dout.writeChars("set silence 1\n"); // Quiert when playing
        dout.writeChars("set style 12\n");  // Computer friendly output from server
        
        dout.writeChars("seek u 2 12 b\n");   // Seek rated 2 12 black
        
        while ((s = din.readLine()) != null) {
         if (s.startsWith("Creating") )
                 {playingFlag = true; 
                  continue;}
            System.out.println(s); //gets the response of server        //.readLine()
          
            icc.setBoard(theBoard);
            icc.setMoveString(s);
            icc.setColor(Color.WHITE); // Get White moves
            m = icc.getMove();
            if (m != null) {
                System.out.println("Found move " + m.toString());
         }
               if (m != null && m.whoMoves == Color.WHITE) {
               theBoard.performMove(m);
               engine.setPlyDepth(3);
               engine.setBoard(theBoard);
               engine.search();               
               System.out.println("Found " + engine.getStrongestMove().toString());
               dout.writeBytes(engine.getStrongestMove().toString() + "\n");
               theBoard.performMove(engine.getStrongestMove());
               System.out.println(theBoard.getASCIIBoard());
               }
            
         }
        
        //System.out.println(din.readUTF()); //gets the response of server        //.readLine()

        
        //command = br.readLine();//reads the command 
        //System.out.println("Welcome to Telnet Client");
        //System.out.println("< Telnet Prompt >");
        //dout.writeUTF(command);//sends command to server

        din.close();  //close input stream      
        dout.close(); //close output stream      
        soc.close();  //close port  
        //br.close();  //close buffered Reader    
    }
}


