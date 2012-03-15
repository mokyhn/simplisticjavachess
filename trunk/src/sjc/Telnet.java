/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sjc;

  
import com.sun.xml.internal.ws.api.pipe.Engine;
import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author mku
 */
public class Telnet {
    private String ca(String s, int i) {
     return Character.toString(s.charAt(i));
    }
    
   
    private String parseMove(String s) {
        String result;
        String tmp;
        
        Pattern p = Pattern.compile("(\\p{Upper}/\\w\\d[-|x]\\w\\d[\\w|=]*)"); // ]?[] *[o-o]?[o-o-o]?
        
        Matcher m = p.matcher(s);
        
        if (s.toLowerCase().contains("o-o-o")) return "o-o-o";
        if (s.toLowerCase().contains("o-o")) return "o-o";
        
        if (m.find()) {
            result = s.substring(m.start(), m.end());
               result = result.substring(2);
                tmp = ca(result,0) + ca(result, 1) + ca(result, 3) + ca(result, 4);
                if (result.contains("=")) tmp = tmp + result.charAt(6);
                result = tmp;
            }
        else result = "";
       
        return result;

    }
    
    public void test() throws Exception
    {
        //String command;
        Search  engine       = new Search();
        Board   theBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Chessio io = new Chessio();
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
        
        ICCProtocol icc = new ICCProtocol(response);
        System.exit(0);
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
           moveStr = parseMove(s);
         
           if (moveStr.length() > 3  && playingFlag) {
           System.out.println("Parsed move " + moveStr);
           try {
            m = io.parseMove(theBoard, moveStr);
               if (m != null) {
               theBoard.performMove(m);
               engine.dosearch(theBoard, 5, Search.ALPHABETA);
               System.out.println("Found " + engine.getStrongestMove().toString());
               dout.writeBytes(engine.getStrongestMove().toString() + "\n");
               theBoard.performMove(engine.getStrongestMove());
               System.out.println(theBoard.toString());
               }
           } catch (NoMoveException e) {;}
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


