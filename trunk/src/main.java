//-enableassertions

/*
 * TODO: Testing via a given position, search depth, search method and comparision
 * against an expected evaluation and expected move.
 * TODO: Draw by insufficient material
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

class main {
    public static void checkForDraw(Board b) {      
        if (b.drawBy3RepetionsRule()) {
            System.out.println("Draw by threefold repetition...");
            System.exit(0);
        }

        if (b.drawBy50MoveRule()) {
            System.out.println("Draw by 50 moves rule...");
            System.exit(0);
        }

    }

    private static boolean testSearch(Board b, int method, int plyDepth, int expectedEvaluation, ArrayList<Move> expectedMoves) throws NoMoveException {
       Search  engine       = new Search();
       int searchResult     = engine.dosearch(b, plyDepth, method);
       Move strongestMove;
       
       strongestMove = engine.getStrongestMove();

       if (!expectedMoves.isEmpty())
       System.out.println("Expected moves:       " + expectedMoves.toString() + ", actual " + engine.moveAndStatistics());
       else 
       System.out.println("Expected moves:       none" + ", actual " + strongestMove);
       
       System.out.println("Expected evaluation: " + expectedEvaluation      + ", actual evaluation: "  + searchResult);
       System.out.println();
       
       
       if (expectedEvaluation == searchResult && 
           strongestMove == null && expectedMoves.isEmpty()) return true;
       
       Iterator<Move> it = expectedMoves.iterator();
       
       while (it.hasNext()) {
        if (it.next().equal(strongestMove)) return  expectedEvaluation == searchResult;
       }
       
        return false;
    
     }    
    
    
    public static boolean testSearch(String fen, String moveSequence, int method, int plyDepth, int expectedEvaluation, String expectedMoveStrs) throws NoMoveException {
        String[] moveStrings          = moveSequence.split(" ");
        Board b                       = new Board(fen);
        Chessio cio                   = new Chessio();
        String[] expectedMoveStrings  = expectedMoveStrs.split(" ");
        ArrayList<Move> expectedMoves = new ArrayList<Move>();
        Move m = null;
        int i;
        
        for (i = 0; i < expectedMoveStrings.length; i++) {
         if (expectedMoveStrings[i] != null) {
            m = cio.parse_move(b, expectedMoveStrings[i]);
            if (m != null) expectedMoves.add(m);
         }
        }

        for(i = 0; i < moveStrings.length; i++) {
         if (moveStrings[i] != null)
          m = cio.parse_move(b, moveStrings[i]);
          if (m != null)
          b.performMove(m);
        }
        
       
        return testSearch(b, method, plyDepth, expectedEvaluation, expectedMoves);
    }
    
    
    public static void test() throws NoMoveException {     

    
       //System.out.println("End game tactics : pawn breakthrough");
       //assert(testSearch("7k/ppp5/8/PPP5/8/8/8/7K w - - 0 1", Search.ALPHABETA, 9, 9-3, "b5b6"));
       
       
  
       

    }
    
    
    public static void main(String param[]) throws java.io.IOException, NoMoveException {

        int     plyDepth      = 5;
        Chessio io            = new Chessio();
        Search  engine1       = new Search();
        Search  engine2       = new Search();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str            = null;
        int simSteps          = 0;
        int searchMethod      = Search.MINMAX;
        int searchResult;
        Move    m;

        int x, y;

        
        // Do a simple setup with pawns.
        Board interfaceBoard = new Board("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq - 0 1");
  
        // A simple rook setup
        //Board interfaceBoard = new Board("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w - 0 1");


        io.printWelcomeText();

        while (true) {
            System.out.print("\n> "); 
            str = reader.readLine();

            if (str.matches("go")) {
                searchResult = engine1.dosearch(interfaceBoard, plyDepth, searchMethod);
                System.out.println(engine1.moveAndStatistics());
                interfaceBoard.performMove(engine1.getStrongestMove());
                checkForDraw(interfaceBoard);
                System.out.println(interfaceBoard.toString());
            } else if (str.matches("undo")) { interfaceBoard.retractMove(); }
              else if (str.matches("allmoves")) {
                Movegenerator mg = new Movegenerator();
                ArrayList<Move> mlist = mg.generateAllMoves(interfaceBoard);
                Move myMove;

                for (int i = 0; i < mlist.size(); i++) {
                    myMove = mlist.get(i);
                    System.out.println(myMove.toString());
                }
                
            } else if (str.matches("black")) { interfaceBoard.setBlackToMove(); }
              else if (str.matches("white")) { interfaceBoard.setWhiteToMove(); }
              else if (str.matches("branching")) {System.out.println(engine1.findBranchingFactor(interfaceBoard, 4));}
              else if (str.startsWith("sim ")) {
                  simSteps = Integer.parseInt(str.substring(4));
                  engine1 = new Search();
                  engine2 = new Search();
                  
                  System.out.println(interfaceBoard.toString());

                  for (int i = 0; i < simSteps; i++) {
                      engine1.dosearch(interfaceBoard, plyDepth, Search.ALPHABETA);
                      System.out.println(engine1.moveAndStatistics());
                      interfaceBoard.performMove(engine1.getStrongestMove());
                      System.out.println(interfaceBoard.toString());
                      checkForDraw(interfaceBoard);
                      engine2.dosearch(interfaceBoard, plyDepth, Search.RANDOM);
                      System.out.println(engine2.moveAndStatistics());
                      interfaceBoard.performMove(engine2.getStrongestMove());
                      System.out.println(interfaceBoard.toString());
                      checkForDraw(interfaceBoard);
                  }
              
              }
              else if (str.matches("attacks")) {
               for (x = 0; x < 8; x++)
                   for (y = 0; y < 8; y++)
                       if (interfaceBoard.attacks(x, y)) System.out.println("Attacks " + Chessio.numToChar(x) + Chessio.numToNumChar(y));
              }
              else if (str.matches("new"))   {
                interfaceBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
                engine1 = new Search();
                engine2 = new Search();
            }
             else if (str.startsWith("setboard")) {
                interfaceBoard = new Board(str.substring(9, str.length()));
                engine1       = new Search();
            } 
             else if (str.startsWith("alpha")) {
              searchMethod = Search.ALPHABETA;
             }
             else if (str.startsWith("minmax")) {
              searchMethod = Search.MINMAX;
             }
             else if (str.startsWith("random")) {
              searchMethod = Search.RANDOM;
             }
             else
            if (str.matches("quit") || str.matches("q") || str.matches("bye") || str.matches("exit")) {
                System.out.print("\nGoodbye\n\n");
                System.exit(0); }
              else if (str.startsWith("sd")) { plyDepth = Integer.parseInt(str.substring(3)); }
              else if (str.matches("help")) { io.printHelpText(); }
              else if (str.matches("print") || str.matches("p")) { System.out.println(interfaceBoard.toString()); }
              else if (str.matches("test")) {
               test();
              }
              else if (str.matches("bitboard")) {
               System.out.println(interfaceBoard.getBitboardString());
              }
              else {
                try {
                    m = io.parse_move(interfaceBoard, str);                    
                    if (!(interfaceBoard.isMoveLegal(m))) {  throw new NoMoveException(); }
                    interfaceBoard.performMove(m);
                                       
                    checkForDraw(interfaceBoard);
                    System.out.println(interfaceBoard.toString());

                } catch (NoMoveException e) { System.out.println("Not a valid move " + e.err); }
            }
        }
    }
}
