//-enableassertions

// Add support st the can play matches against itself...
import java.io.*;

class main {


    public static void main(String param[]) throws java.io.IOException {
        int     plyDepth      = 5;
        int     searchResult  = 0;
        Chessio io            = new Chessio();
        Search  engine1       = new Search();
        Search  engine2       = new Search();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str            = null;
        int simSteps             = 0;

        Move    m;

        int x, y;

        // board interfaceBoard = new board(board.NORMAL_SETUP);

        //Board interfaceBoard = new Board("4k3/p7/8/8/8/8/P7/4K3 w - 0 1");

        //Board interfaceBoard = new Board("4k3/7p/7K/8/8/8/8/8 w - 0 1");

	//Board interfaceBoard = new Board("2k5/3pK3/8/4p3/4P3/8/8/8 w - - 0 1");

        // Do a simple setup with pawns.
        Board interfaceBoard = new Board("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w KQkq - 0 1");
    
        
        //Board interfaceBoard = new Board("8/3K4/Pk6/4P3/1PP2P2/P7/4P3/8 b - - 0 1");
        //Board interfaceBoard = new Board("k7/4K3/4p3/8/8/8/PP6/8 w - 0 1");

        // A simple rook setup
        //Board interfaceBoard = new Board("r3k2r/pppppppp/8/8/8/8/PPPPPPPP/R3K2R w - 0 1");


       // Board interfaceBoard = new Board("4k3/6p1/ppp1pp2/3p3K/4PP1P/8/PPP5/8 w - - 0 1");

        // Board interfaceBoard = new Board("4k3/pppppppP/8/8/8/8/PPPPPPPP/4K3 w - 0 1");

        //Board interfaceBoard = new Board("4k3/8/3pp3/4p3/3P1P2/4K3/8/8 w - - 0 1");

        //Board interfaceBoard = new Board("k7/8/7P/8/8/1p6/P7/7K w - - 0 1");

        //Board interfaceBoard = new Board("8/p7/1p3k1P/8/1PP5/8/8/K7 w - - 0 1");

        
        io.printWelcomeText();

        while (true) {
            System.out.print("\n> "); 
            str = reader.readLine();

            if (str.matches("go")) {
                searchResult = engine1.dosearch(interfaceBoard, plyDepth, Search.ALPHABETA);
                System.out.println(engine1.moveAndStatistics());
                interfaceBoard.performMove(engine1.getStrongestMove());
            } else if (str.matches("undo")) { interfaceBoard.retractMove(); }
              else if (str.matches("allmoves")) {
                Movegenerator mg = new Movegenerator();
                mg.printMoves(mg.generateAllMoves(interfaceBoard));
            } else if (str.matches("black")) { interfaceBoard.setBlackToMove(); }
              else if (str.matches("white")) { interfaceBoard.setWhiteToMove(); }
              else if (str.matches("branching")) {System.out.println(engine1.findBranchingFactor(interfaceBoard, 4));}
              else if (str.startsWith("sim ")) {
                  simSteps = Integer.parseInt(str.substring(4));
                  engine1 = new Search();
                  engine2 = new Search();
                  
                  interfaceBoard.print();

                  for (int i = 0; i < simSteps; i++) {
                      engine1.dosearch(interfaceBoard, plyDepth, Search.ALPHABETA);
                      System.out.println(engine1.moveAndStatistics());
                      interfaceBoard.performMove(engine1.getStrongestMove());
                      interfaceBoard.print();

                      engine2.dosearch(interfaceBoard, plyDepth, Search.RANDOM);
                      System.out.println(engine2.moveAndStatistics());
                      interfaceBoard.performMove(engine2.getStrongestMove());
                      interfaceBoard.print();
                      
                  }
              
              }
              else if (str.matches("attacks")) {
               for (x = 0; x < 8; x++)
                   for (y = 0; y < 8; y++)
                       if (interfaceBoard.attacks(x, y)) System.out.println("Attacks " + Chessio.numToChar(x) + Chessio.numToNumChar(y));
              }
              else if (str.matches("new"))   {
                interfaceBoard = new Board(Board.NORMAL_SETUP);
                engine1 = new Search();
                engine2 = new Search();
            }
                else if (str.matches("state")) {
                    // TODO: Print all variable states from board...
                    interfaceBoard.printState();
                    str = "";

                }
             else if (str.startsWith("setboard")) {
                interfaceBoard = new Board(str.substring(9, str.length()));
                engine1       = new Search();
            } else
            if (str.matches("quit") || str.matches("q") || str.matches("bye") || str.matches("exit")) {
                System.out.print("\nGoodbye\n\n");
                System.exit(0); }
              else if (str.startsWith("sd")) { plyDepth = Integer.parseInt(str.substring(3)); }
              else if (str.matches("help")) { io.printHelpText(); }
              else if (str.matches("print") || str.matches("p")) { interfaceBoard.print(); }
              else if (str.startsWith("usermove")) {
                String moveStr = str.substring(9);
                try {
                    m = io.parse_move(interfaceBoard, moveStr);
                    // Check if the move is legal
                    if (!(interfaceBoard.isMoveLegal(m))) {
                        throw new NoMoveException();
                    }
                    interfaceBoard.performMove(m);
                    searchResult = engine1.dosearch(interfaceBoard, plyDepth, Search.ALPHABETA);
                    System.out.println(engine1.moveAndStatistics());
                    interfaceBoard.performMove(engine1.getStrongestMove());
                } catch (NoMoveException e) { System.out.println("Not a valid move"); }

            } else {
                try {
                    m = io.parse_move(interfaceBoard, str);
                    //System.out.println(m.getMoveStr());
                    if (!(interfaceBoard.isMoveLegal(m))) {  throw new NoMoveException(); }
                    interfaceBoard.performMove(m);
                    
                    
                    //searchResult = engine1.dosearch(interfaceBoard, plyDepth, Search.ALPHABETA);
                    
                    //interfaceBoard.performMove(engine1.getStrongestMove());
                    //                    System.out.println(engine1.moveAndStatistics());

                    interfaceBoard.print();
                   


                } catch (NoMoveException e) { System.out.println("Not a valid move"); }
            }
        }
    }
}