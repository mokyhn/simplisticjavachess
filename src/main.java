
import java.io.*;

class main {


    public static void main(String param[]) throws java.io.IOException {
        int     plyDepth      = 4;
        int     searchResult  = 0;
        Chessio io            = new Chessio();
        Search  searcher      = new Search();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str            = null;

        Move    m;



        // board interfaceBoard = new board(board.NORMAL_SETUP);
        // board analyzeBoard = new board(board.NORMAL_SETUP);

        // Do a simple setup with pawns.
        Board interfaceBoard = new Board("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w - 0 1");
        Board analyzeBoard   = new Board("4k3/pppppppp/8/8/8/8/PPPPPPPP/4K3 w - 0 1");

        //Board interfaceBoard = new Board("4k3/8/3pp3/4p3/3P1P2/4K3/8/8 w - - 0 1");
        //Board analyzeBoard   = new Board("4k3/8/3pp3/4p3/3P1P2/4K3/8/8 w - - 0 1");

        //Board interfaceBoard = new Board("k7/8/7P/8/8/1p6/P7/7K w - - 0 1");
        //Board analyzeBoard = new Board("k7/8/7P/8/8/1p6/P7/7K w - - 0 1");

        //Board interfaceBoard = new Board("8/p7/1p3k1P/8/1PP5/8/8/K7 w - - 0 1");
        //Board analyzeBoard = new Board("8/p7/1p3k1P/8/1PP5/8/8/K7 w - - 0 1");

        
        io.printWelcomeText();

        while (true) {
            System.out.print("\n> "); 
            str = reader.readLine();

            if (str.matches("go")) {
                analyzeBoard = (Board) interfaceBoard.clone();
                searchResult = searcher.dosearch(analyzeBoard, plyDepth);
                System.out.println(searcher.moveAndStatistics());
                interfaceBoard.performMove(searcher.getStrongestMove());
            } else if (str.matches("undo")) { interfaceBoard.retractMove(); }
              else if (str.matches("allmoves")) {
                Movegenerator mg = new Movegenerator();
                mg.printMoves(mg.generateAllMoves(interfaceBoard));
            } else if (str.matches("black")) { interfaceBoard.setBlackToMove(); }
              else if (str.matches("white")) { interfaceBoard.setWhiteToMove(); }
              else if (str.matches("new"))   {
                interfaceBoard = new Board(Board.NORMAL_SETUP);
                analyzeBoard = new Board(Board.NORMAL_SETUP);
                searcher = new Search();
            } else if (str.startsWith("setboard")) {
                interfaceBoard = new Board(str.substring(9, str.length()));
                analyzeBoard   = new Board(str.substring(9, str.length()));
                searcher       = new Search();
            }
            if (str.matches("quit") || str.matches("q") || str.matches("bye") || str.matches("exit")) {
                System.out.print("\nGoodbye\n\n");
                System.exit(0); }
              else if (str.startsWith("sd")) { plyDepth = Integer.parseInt(str.substring(4)); }
              else if (str.matches("help")) { io.printHelpText(); }
              else if (str.matches("print") || str.matches("p")) { io.printBoard(interfaceBoard); }
              else if (str.startsWith("usermove")) {
                String moveStr = str.substring(9);
                try {
                    m = io.parse_move(interfaceBoard, moveStr);
                    // Check if the move is legal
                    if (!(interfaceBoard.isMoveLegal(m))) {
                        throw new NoMoveException();
                    }
                    interfaceBoard.performMove(m);
                    analyzeBoard = (Board) interfaceBoard.clone();
                    searchResult = searcher.dosearch(analyzeBoard, plyDepth);
                    System.out.println(searcher.moveAndStatistics());
                    interfaceBoard.performMove(searcher.getStrongestMove());
                } catch (NoMoveException e) { System.out.println("Not a valid move"); }

            } else {
                try {
                    m = io.parse_move(interfaceBoard, str);
                    System.out.println(m.getMoveStr());
                    if (!(interfaceBoard.isMoveLegal(m))) {  throw new NoMoveException(); }
                    interfaceBoard.performMove(m);
                    
                    analyzeBoard = (Board) interfaceBoard.clone();
                    io.printBoard(analyzeBoard);
                     
                    searchResult = searcher.dosearch(analyzeBoard, plyDepth);
                    io.printBoard(interfaceBoard);

                    interfaceBoard.performMove(searcher.getStrongestMove());
                                        System.out.println(searcher.moveAndStatistics());

                    io.printBoard(interfaceBoard);
                   


                } catch (NoMoveException e) {  }
            }
        }
    }
}
