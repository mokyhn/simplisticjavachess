//-enableassertions



import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import sjc.*;


class main {
    static Board interfaceBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

    
    public static void checkForDrawOrMate(Board b) {      
        if (b.isDraw()) {
         System.out.println("Draw");
         //System.exit(0);
        }
        
        if (b.isMate()) {
         System.out.println("Mate");
         //System.exit(0);
        }
        
        if (b.drawBy3RepetionsRule()) {
            System.out.println("Draw by threefold repetition...");
            //System.exit(0);
        }

        if (b.drawBy50MoveRule()) {
            System.out.println("Draw by 50 moves rule...");
            //System.exit(0);
        }

    }

    
    

    
    
    public static void test() throws NoMoveException {     
         interfaceBoard = new Board("4k3/5R1/5Q2/8/8/8/8/4K3 w - - 5 21");
    }
    
    
    public static void main(String param[]) throws java.io.IOException, NoMoveException, Exception {

        int     plyDepth      = 5;
        Chessio io            = new Chessio();
        Search  engine1       = new Search();
        Search  engine2       = new Search();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str            = null;
        int simSteps          = 0;
        int searchMethod      = Search.MINMAX;
        Boolean xboardMode    = false;
        Move    m;

        int x, y;

       
         
        if (param.length > 0)  {
            if (param[0].contains("xboard")) 
                xboardMode = true;
        }
                
        
        // Do a simple setup with pawns and knights.
        //Board interfaceBoard = new Board("1n2k1n1/pppppppp/8/8/8/8/PPPPPPPP/1N2K1N1 w KQkq - 0 1");

       // Do a simple setup with pawns and bishops.
        //Board interfaceBoard = new Board("2b1kb2/p7/8/8/8/8/P7/2B1KB2 w KQkq - 0 1");
        
         // Do a simple setup with pawns and knights and bishops.
        //Board interfaceBoard = new Board("1nb1kbn1/pppppppp/8/8/8/8/PPPPPPPP/1NB1KBN1 w KQkq - 0 1");
        
        // A test setup
        //Board interfaceBoard = new Board("4k1n1/pppppppp/n7/8/1P6/P1N2N2/2PPPPPP/4K3 b - - 0 1");


        // A simple knight setup.
        //Board interfaceBoard = new Board("k7/4R3/8/3n4/8/2Q5/8/K7 b KQkq - 0 1");
        
        // A simple rook setup
        //Board interfaceBoard = new Board("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w - 0 1");


        if (!xboardMode) io.printWelcomeText();

        while (true) {
            if (!xboardMode) System.out.print("\n> "); 
            str = reader.readLine();

            if (str.trim().equalsIgnoreCase("go")) {
                engine1.setPlyDepth(plyDepth);
                engine1.dosearch(interfaceBoard, searchMethod);
                System.out.println(engine1.moveAndStatistics());
                if (engine1.getStrongestMove() != null) {
                interfaceBoard.performMove(engine1.getStrongestMove());
                checkForDrawOrMate(interfaceBoard);
                System.out.println(interfaceBoard.toString()); }                
            } else if (str.matches("undo")) { interfaceBoard.retractMove(); }
              else if (str.matches("allmoves")) {
                Movegenerator mg = new Movegenerator();
                ArrayList<Move> mlist = mg.generateAllMoves(interfaceBoard);
                Move myMove;

                for (int i = 0; i < mlist.size(); i++) {
                    myMove = mlist.get(i);
                    System.out.println(myMove.toString());
                }
                
            } else if (str.matches("incheck")) { 
                if (interfaceBoard.isInCheck(interfaceBoard.inMove())) System.out.println("Yes!");
                else System.out.println("No...");}
              else if (str.trim().equalsIgnoreCase("black")) { interfaceBoard.setBlackToMove(); }
              else if (str.trim().equalsIgnoreCase("white")) { interfaceBoard.setWhiteToMove(); }
              else if (str.matches("branching")) {System.out.println(engine1.findBranchingFactor(interfaceBoard, 4));}
              else if (str.startsWith("sim ")) {
                  simSteps = Integer.parseInt(str.substring(4));
                  engine1 = new Search();
                  engine2 = new Search();
                  
                  System.out.println(interfaceBoard.toString());
                  int res = 0;
                  for (int i = 0; i < simSteps && (res != Evaluator.WHITE_IS_MATED || res != Evaluator.BLACK_IS_MATED || 
                          !interfaceBoard.drawBy3RepetionsRule()||
                          !interfaceBoard.drawBy50MoveRule() ||
                          !interfaceBoard.isDraw() ||
                          !interfaceBoard.isMate()); i++) {
                      engine1.setPlyDepth(plyDepth);
                      res = engine1.dosearch(interfaceBoard, Search.ALPHABETA);
                      System.out.println(engine1.moveAndStatistics());
                      if (engine1.getStrongestMove() == null) {
                       System.out.println("Game ended....");
                      } else
                      interfaceBoard.performMove(engine1.getStrongestMove());
                      System.out.println(interfaceBoard.toString());
                      checkForDrawOrMate(interfaceBoard);
                      engine2.setPlyDepth(plyDepth);
                      engine2.dosearch(interfaceBoard,  Search.RANDOM);
                      //engine2.dosearch(interfaceBoard, 2, Search.ALPHABETA);
                      System.out.println(engine2.moveAndStatistics());
                      if (engine2.getStrongestMove() != null)
                       interfaceBoard.performMove(engine2.getStrongestMove());
                      else System.out.println("No move to perform in position!");
                      System.out.println(interfaceBoard.toString());
                      checkForDrawOrMate(interfaceBoard);
                  }
              
              }
              else if (str.matches("attacks")) {
                  System.out.println("White attacks the squares:");
                  for (x = 0; x < 8; x++) {
                   for (y = 0; y < 8; y++) {
                       if (interfaceBoard.attacks(x, y, Piece.BLACK)) System.out.print(Chessio.numToChar(x) + Chessio.numToNumChar(y)+ ", ");
                   }
                  }
                  System.out.println("\nBlack attacks the squares:"); 
                  for (x = 0; x < 8; x++) {
                   for (y = 0; y < 8; y++) {
                       if (interfaceBoard.attacks(x, y, Piece.WHITE)) System.out.print(Chessio.numToChar(x) + Chessio.numToNumChar(y) + ", ");
                   }                  
               }
              }
              else if (str.trim().equalsIgnoreCase("new"))   {
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
             else if (str.startsWith("telnet")) {
              Telnet telnet = new Telnet();
              telnet.test();
             }          
             else
            if (str.trim().equalsIgnoreCase("quit") || str.matches("q") || str.matches("bye") || str.trim().equalsIgnoreCase("exit")) {
                System.out.print("\nGoodbye\n\n");
                System.exit(0); }
              else if (str.trim().startsWith("sd")) { plyDepth = Integer.parseInt(str.replaceAll(" ", "").substring(2)); }
              else if (str.matches("help")) { io.printWelcomeText(); io.printHelpText(); }
              else if (str.matches("print") || str.matches("p")) { System.out.println(interfaceBoard.toString()); }
              else if (str.matches("test")) {
               test();
              }
              else if (str.equalsIgnoreCase("xboard")) {
               xboardMode = true;
              }
              else if (str.equalsIgnoreCase("xboard") ||
                       str.equalsIgnoreCase("variant") ||
                       str.equalsIgnoreCase("force") ||
                       str.contains("protover")) {}
              else if (str.matches("bitboard")) {
               System.out.println(interfaceBoard.getBitboardString());
              }
              else {
                try {
                    m = io.parseMove(interfaceBoard, str);                    
                    if (!interfaceBoard.isDraw() || !interfaceBoard.isMate()) {
                       Iterator<Move> theMoves = Movegenerator.generateAllMoves(interfaceBoard).listIterator();
                       // Check if move m is among the possible moves
                      while (theMoves.hasNext()) {
                        if (m.equal(theMoves.next())) {
                        interfaceBoard.performMove(m);
                        if (interfaceBoard.isInCheck(m.whoMoves))  throw new NoMoveException();        
                      }
                    }
                    } else  throw new NoMoveException();
                                      
                    checkForDrawOrMate(interfaceBoard);
                    System.out.println(interfaceBoard.toString());

                } catch (NoMoveException e) { System.out.println("Not a valid move " + e.err); }
            }
        }
    }
}
