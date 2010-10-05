
import java.io.*;

class main {


	private static void printWelcomeText() {
		System.out.println("----------------------------------------------------"); 
		System.out.println("A Simplistic Chessprogram, under serious development"); 
		System.out.println("Morten Kuhnrich (for now) 2007"); 
		System.out.println("Type help if you need help"); 
		System.out.println("----------------------------------------------------"); 


        }

	private static void printHelpText() {
		System.out.println("\n----------------------------------------------------"); 
		System.out.println("Action                           Key stroke"); 
		System.out.println("Quit                             quit, bye, exit, q "); 
		System.out.println("Entering a move: d2d4 or promotion d7d8Q   "); 
		System.out.println("----------------------------------------------------"); 
	}

	public static void main(String param[]) throws java.io.IOException {
		int plyDepth = 4;
		int searchResult = 0;
		Chessio io = new Chessio();

                // Apparently unused...
		//int engineColor = Board.BLACK;

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

		Search searcher = new Search();
		Move m;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String str = null;

		printWelcomeText();

		while (true) {
				System.out.print("\n> "); // Display command promt
				str = reader.readLine();

				if (str.matches("go")) {
					// Unused ?! engineColor = interfaceBoard.whoIsInMove();

					analyzeBoard = (Board) interfaceBoard.clone();
					searchResult = searcher.dosearch(analyzeBoard, plyDepth);
					System.out.println(searcher.moveAndStatistics());
					interfaceBoard.performMove(searcher.getStrongestMove());
				} else

				if (str.matches("undo")) { 
					interfaceBoard.retractMove();
				} else

                                if (str.matches("allmoves")) {
                                  Movegenerator mg = new Movegenerator();
                                    mg.printMoves( mg.generateAllMoves(interfaceBoard));
                                } else

				if (str.matches("black")) { 
					interfaceBoard.setBlackToMove();
					// Unused?! engineColor = Board.BLACK;
				} else

				if (str.matches("white")) { 
					interfaceBoard.setWhiteToMove();
					// Unused?! engineColor = Board.WHITE;
				} else

				if (str.matches("new")) { 
					// TODO: Should maby be extended.
					interfaceBoard = new Board(Board.NORMAL_SETUP);
					analyzeBoard = new Board(Board.NORMAL_SETUP);
					searcher = new Search();
				} else

				if (str.startsWith("setboard")) { 
					interfaceBoard = new Board(str.substring(9, str.length()));
					analyzeBoard = new Board(str.substring(9, str.length()));
					searcher = new Search();
				}

				if (str.matches("quit") || str.matches("q") || 
                                    str.matches("bye") || str.matches("exit")) { 
					System.out.print("\nGoodbye\n\n"); 
					System.exit(0);
				} else

				if (str.startsWith("sd")) { 
					plyDepth = Integer.parseInt(str.substring(4));
				} else

				
				if (str.matches("help")) { printHelpText(); } 
				else

				if (str.matches("print") || str.matches("p")) {  //$NON-NLS-2$
					io.printBoard(interfaceBoard); }
				else

				if (str.startsWith("usermove")) { 
					String moveStr = str.substring(9);
					try {
						m = io.parse_move(interfaceBoard, moveStr);
						// Check if the move is legal
						if (!(interfaceBoard.isMoveLegal(m)))
							{ throw new NoMoveException(); }
						interfaceBoard.performMove(m);
						analyzeBoard = (Board) interfaceBoard.clone();
						searchResult = searcher
								.dosearch(analyzeBoard, plyDepth);
						System.out.println(searcher.moveAndStatistics());
						interfaceBoard.performMove(searcher.getStrongestMove());
					} catch (NoMoveException e) {
						System.out.println("Not a valid move");
					}

				}
 else
     try {
						m = io.parse_move(interfaceBoard, str);
						// Check if the move is legal
						if (!(interfaceBoard.isMoveLegal(m)))
							{ throw new NoMoveException(); }
						interfaceBoard.performMove(m);
						analyzeBoard = (Board) interfaceBoard.clone();
						searchResult = searcher
								.dosearch(analyzeBoard, plyDepth);
						System.out.print("move " +  searcher.getStrongestMove().getMoveStr());
						// TODO: Conform with xboard standard
						 System.out.println(" Evaluation " + searchResult +
                                                         " at " + plyDepth + " ply in " +
						 searcher.getNoPositions() + " positions");
						interfaceBoard.performMove(searcher.getStrongestMove());
					} catch (NoMoveException e) {
						System.out.println("Not a valid move");
					}
		}
	}
    
}
