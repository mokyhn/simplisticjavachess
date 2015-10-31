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

class main
{

    static Board GUIBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");


    public static void main(String param[]) throws java.io.IOException, NoMoveException, Exception
    {

        int plyDepth = 5;
        ChessIO io = new ChessIO();
        AbstractSearch engine1 = new AlphaBetaSearch();
        AbstractSearch engine2 = new AlphaBetaSearch();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str;
        int simSteps = 0;
        Boolean xboardMode = false;
        Move m;

        int x, y;

        if (param.length > 0)
        {
            if (param[0].contains("xboard"))
            {
                xboardMode = true;
            }
        }

        if (!xboardMode)
        {
            ChessIO.printWelcomeText();
        }

        while (true)
        {
            if (!xboardMode)
            {
                System.out.print("\n> ");
            }
            str = reader.readLine();

            if (str.trim().equalsIgnoreCase("go"))
            {
                engine1.setPlyDepth(plyDepth);
                engine1.setBoard(GUIBoard);
                engine1.dosearch();
                System.out.println(engine1.getStatistics());
                if (engine1.getStrongestMove() != null)
                {
                    GUIBoard.performMove(engine1.getStrongestMove());
                    checkForDrawOrMate(GUIBoard);
                    System.out.println(GUIBoard.toString());
                }
            } else if (str.matches("undo"))
            {
                GUIBoard.retractMove();
            } else if (str.matches("allmoves"))
            {
                ArrayList<Move> mlist = MoveGenerator.generateAllMoves(GUIBoard);
                Move myMove;

                for (int i = 0; i < mlist.size(); i++)
                {
                    myMove = mlist.get(i);
                    System.out.println(myMove.toString());
                }

            } else if (str.matches("incheck"))
            {
                if (GUIBoard.isInCheck(GUIBoard.inMove()))
                {
                    System.out.println("Yes!");
                } else
                {
                    System.out.println("No...");
                }
            } else if (str.trim().equalsIgnoreCase("black"))
            {
                GUIBoard.setBlackToMove();
            } else if (str.trim().equalsIgnoreCase("white"))
            {
                GUIBoard.setWhiteToMove();
            } else if (str.matches("branching"))
            {
                System.out.println(engine1.findBranchingFactor(GUIBoard, 4));
            } else if (str.startsWith("sim "))
            {
                simSteps = Integer.parseInt(str.substring(4));
                engine1 = new AlphaBetaSearch();
                engine2 = new RandomSearch();

                System.out.println(GUIBoard.toString());
                int res = 0;
                for (int i = 0; i < simSteps && (res != Evaluator.WHITE_IS_MATED || res != Evaluator.BLACK_IS_MATED
                        || !GUIBoard.drawBy3RepetionsRule()
                        || !GUIBoard.drawBy50MoveRule()
                        || !GUIBoard.isDraw()
                        || !GUIBoard.isMate()); i++)
                {
                    engine1.setPlyDepth(plyDepth);
                    engine1.setBoard(GUIBoard);
                    res = engine1.dosearch();
                    System.out.println(engine1.getStatistics());
                    if (engine1.getStrongestMove() == null)
                    {
                        System.out.println("Game ended....");
                    } else
                    {
                        GUIBoard.performMove(engine1.getStrongestMove());
                    }
                    System.out.println(GUIBoard.toString());
                    checkForDrawOrMate(GUIBoard);
                    engine2.setPlyDepth(plyDepth);
                    engine2.setBoard(GUIBoard);
                    engine2.dosearch();
                    //engine2.dosearch(interfaceBoard, 2, Search.ALPHABETA);
                    System.out.println(engine2.getStatistics());
                    if (engine2.getStrongestMove() != null)
                    {
                        GUIBoard.performMove(engine2.getStrongestMove());
                    } else
                    {
                        System.out.println("No move to perform in position!");
                    }
                    System.out.println(GUIBoard.toString());
                    checkForDrawOrMate(GUIBoard);
                }

            } else if (str.matches("attacks"))
            {
                System.out.println("White attacks the squares:");
                for (x = 0; x < 8; x++)
                {
                    for (y = 0; y < 8; y++)
                    {
                        if (GUIBoard.attacks(x, y, Color.BLACK))
                        {
                            System.out.print(ChessIO.numToChar(x) + ChessIO.numToNumChar(y) + ", ");
                        }
                    }
                }
                System.out.println("\nBlack attacks the squares:");
                for (x = 0; x < 8; x++)
                {
                    for (y = 0; y < 8; y++)
                    {
                        if (GUIBoard.attacks(x, y, Color.WHITE))
                        {
                            System.out.print(ChessIO.numToChar(x) + ChessIO.numToNumChar(y) + ", ");
                        }
                    }
                }
            } else if (str.trim().equalsIgnoreCase("new"))
            {
                GUIBoard = new Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
                engine1 = new AlphaBetaSearch();
                engine2 = new AlphaBetaSearch();
            } else if (str.startsWith("setboard"))
            {
                GUIBoard = new Board(str.substring(9, str.length()));
                engine1 = new AlphaBetaSearch();
            } else if (str.startsWith("alpha"))
            {
                engine1 = new AlphaBetaSearch();
            } else if (str.startsWith("minmax"))
            {
                engine1 = new MinMaxSearch();
            } else if (str.startsWith("random"))
            {
                engine1 = new RandomSearch();
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
                plyDepth = Integer.parseInt(str.replaceAll(" ", "").substring(2));
            } else if (str.matches("help"))
            {
                ChessIO.printWelcomeText();
                ChessIO.printHelpText();
            } else if (str.matches("print") || str.matches("p"))
            {
                System.out.println(GUIBoard.toString());
            } else if (str.equalsIgnoreCase("xboard"))
            {
                xboardMode = true;
            } else if (str.equalsIgnoreCase("xboard")
                    || str.equalsIgnoreCase("variant")
                    || str.equalsIgnoreCase("force")
                    || str.contains("protover"))
            {
            } else if (str.matches("bitboard"))
            {
                System.out.println(GUIBoard.getBitboardString());
            } else
            {
                try
                {
                    m = io.parseMove(GUIBoard, str);
                    if (!GUIBoard.isDraw() || !GUIBoard.isMate())
                    {
                        Iterator<Move> theMoves = MoveGenerator.generateAllMoves(GUIBoard).listIterator();
                        // Check if move m is among the possible moves
                        while (theMoves.hasNext())
                        {
                            if (m.equal(theMoves.next()))
                            {
                                boolean result = GUIBoard.performMove(m);
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

                    checkForDrawOrMate(GUIBoard);
                    System.out.println(GUIBoard.toString());

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

