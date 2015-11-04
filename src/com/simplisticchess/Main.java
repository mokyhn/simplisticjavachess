package com.simplisticchess;

/**
 * @author Morten Kühnrich
 */
import com.simplisticchess.move.NoMoveException;
import com.simplisticchess.movegenerator.MoveGenerator;
import com.simplisticchess.evaluator.Evaluator;
import com.simplisticchess.board.Board;
import com.simplisticchess.search.RandomSearch;
import com.simplisticchess.search.AbstractSearch;
import com.simplisticchess.search.MinMaxSearch;
import com.simplisticchess.search.AlphaBetaSearch;
import com.simplisticchess.move.Move;
import com.simplisticchess.piece.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

//TODO: Refactor main
//Introduce our new nice CLI support.
class Main
{

    ChessGame chessGame = new ChessGame();
    
    public Main() 
    {
        chessGame.setBoard(new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
    }
    
    public void run(String param[]) throws java.io.IOException, NoMoveException, Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
       
        while (true)
        {
            String str = reader.readLine().trim().toLowerCase();

            if (str.equals("go"))
            {   
                AbstractSearch engine1 = new AlphaBetaSearch();

                engine1.setPlyDepth(chessGame.getSearchDepth());
                engine1.setBoard(chessGame.getBoard());
                engine1.dosearch();
                System.out.println(engine1.getStatistics());
                if (engine1.getStrongestMove() != null)
                {
                    chessGame.getBoard().performMove(engine1.getStrongestMove());
                    checkForDrawOrMate(chessGame.getBoard());
                    System.out.println(chessGame.getBoard().toString());
                }
            } else if (str.matches("undo"))
            {
                chessGame.getBoard().retractMove();
            } else if (str.matches("allmoves"))
            {
                ArrayList<Move> mlist = MoveGenerator.generateAllMoves(chessGame.getBoard());
                Move myMove;

                for (int i = 0; i < mlist.size(); i++)
                {
                    myMove = mlist.get(i);
                    System.out.println(myMove.toString());
                }

            } else if (str.matches("incheck"))
            {
                if (chessGame.getBoard().isInCheck(chessGame.getBoard().inMove()))
                {
                    System.out.println("Yes!");
                } else
                {
                    System.out.println("No...");
                }
            } else if (str.trim().equalsIgnoreCase("black"))
            {
                chessGame.getBoard().setBlackToMove();
            } else if (str.trim().equalsIgnoreCase("white"))
            {
                chessGame.getBoard().setWhiteToMove();
            } else if (str.matches("branching"))
            {
                System.out.println(new AlphaBetaSearch().findBranchingFactor(chessGame.getBoard(), 4));
            } else if (str.startsWith("sim "))
            {
                int simSteps = Integer.parseInt(str.substring(4));
                    AbstractSearch engine1 = new AlphaBetaSearch();
                AbstractSearch engine2 = new RandomSearch();

                System.out.println(chessGame.getBoard().toString());
                int res = 0;
                for (int i = 0; i < simSteps && (res != Evaluator.WHITE_IS_MATED || res != Evaluator.BLACK_IS_MATED
                        || !chessGame.getBoard().drawBy3RepetionsRule()
                        || !chessGame.getBoard().drawBy50MoveRule()
                        || !chessGame.getBoard().isDraw()
                        || !chessGame.getBoard().isMate()); i++)
                {
                    engine1.setPlyDepth(chessGame.getSearchDepth());
                    engine1.setBoard(chessGame.getBoard());
                    res = engine1.dosearch();
                    System.out.println(engine1.getStatistics());
                    if (engine1.getStrongestMove() == null)
                    {
                        System.out.println("Game ended....");
                    } else
                    {
                        chessGame.getBoard().performMove(engine1.getStrongestMove());
                    }
                    System.out.println(chessGame.getBoard().toString());
                    checkForDrawOrMate(chessGame.getBoard());
                    engine2.setPlyDepth(chessGame.getSearchDepth());
                    engine2.setBoard(chessGame.getBoard());
                    engine2.dosearch();
                    //engine2.dosearch(interfaceBoard, 2, Search.ALPHABETA);
                    System.out.println(engine2.getStatistics());
                    if (engine2.getStrongestMove() != null)
                    {
                        chessGame.getBoard().performMove(engine2.getStrongestMove());
                    } else
                    {
                        System.out.println("No move to perform in position!");
                    }
                    System.out.println(chessGame.getBoard().toString());
                    checkForDrawOrMate(chessGame.getBoard());
                }

            } else if (str.matches("attacks"))
            {
                System.out.println("White attacks the squares:");
                for (int x = 0; x < 8; x++)
                {
                    for (int y = 0; y < 8; y++)
                    {
                        if (chessGame.getBoard().attacks(x, y, Color.BLACK))
                        {
                            System.out.print(ChessIO.numToChar(x) + ChessIO.numToNumChar(y) + ", ");
                        }
                    }
                }
                System.out.println("\nBlack attacks the squares:");
                for (int x = 0; x < 8; x++)
                {
                    for (int y = 0; y < 8; y++)
                    {
                        if (chessGame.getBoard().attacks(x, y, Color.WHITE))
                        {
                            System.out.print(ChessIO.numToChar(x) + ChessIO.numToNumChar(y) + ", ");
                        }
                    }
                }
            } else if (str.matches("new"))
            {
                chessGame.setBoard(new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
                AbstractSearch engine1 = new AlphaBetaSearch();
                AbstractSearch engine2 = new AlphaBetaSearch();
            } else if (str.startsWith("setboard"))
            {
                chessGame.setBoard(new Board(str.substring(9, str.length())));
                AbstractSearch engine1 = new AlphaBetaSearch();
            } else if (str.startsWith("alpha"))
            {
                AbstractSearch engine1 = new AlphaBetaSearch();
            } else if (str.startsWith("minmax"))
            {
                AbstractSearch engine1 = new MinMaxSearch();
            } else if (str.startsWith("random"))
            {
                AbstractSearch engine1 = new RandomSearch();
            } else if (str.startsWith("telnet"))
            {
                Telnet telnet = new Telnet();
                telnet.test();
            } else if (str.trim().equalsIgnoreCase("quit") || str.matches("q") || str.matches("bye") || str.trim().equalsIgnoreCase("exit"))
            {
                System.out.print("\nGoodbye\n\n");
                System.exit(0);
            } else if (str.trim().startsWith("sd"))
            {
                chessGame.setSearchDepth(Integer.parseInt(str.replaceAll(" ", "").substring(2)));
            } else if (str.matches("help"))
            {
                ChessIO.printWelcomeText();
                ChessIO.printHelpText();
            } else if (str.matches("print") || str.matches("p"))
            {
                System.out.println(chessGame.getBoard().toString());
            } 
             else if (str.equalsIgnoreCase("xboard")
                    || str.equalsIgnoreCase("variant")
                    || str.equalsIgnoreCase("force")
                    || str.contains("protover"))
            {
            } else if (str.matches("bitboard"))
            {
                System.out.println(chessGame.getBoard().getBitboardString());
            } else
            {
                try
                {
                    Move m = ChessIO.parseMove(chessGame.getBoard(), str);
                    if (!chessGame.getBoard().isDraw() || !chessGame.getBoard().isMate())
                    {
                        Iterator<Move> theMoves = MoveGenerator.generateAllMoves(chessGame.getBoard()).listIterator();
                        // Check if move m is among the possible moves
                        while (theMoves.hasNext())
                        {
                            if (m.equal(theMoves.next()))
                            {
                                boolean result = chessGame.getBoard().performMove(m);
                                if (result == false)
                                {
                                    throw new NoMoveException();
                                }
                            }
                        }
                    } else
                    {
                        throw new NoMoveException();
                    }

                    checkForDrawOrMate(chessGame.getBoard());
                    System.out.println(chessGame.getBoard().toString());

                } catch (NoMoveException e)
                {
                    System.out.println("Not a valid move " + e.err);
                }
            }
        }
    }

    private static void checkForDrawOrMate(Board b)
    {
        if (b.isDraw())
        {
            System.out.println("Draw");
            //System.exit(0);
        }

        if (b.isMate())
        {
            System.out.println("Mate");
            //System.exit(0);
        }

        if (b.drawBy3RepetionsRule())
        {
            System.out.println("Draw by threefold repetition...");
            //System.exit(0);
        }

        if (b.drawBy50MoveRule())
        {
            System.out.println("Draw by 50 moves rule...");
            //System.exit(0);
        }
    }

    public static void main(String param[]) throws java.io.IOException, NoMoveException, Exception
    {
        Main m = new Main();
        m.run(param);
    }
}
// Garbage - leftovers from ealier
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

