package com.simplisticchess;

import com.simplisticchess.board.Board;
import com.simplisticchess.search.AbstractSearch;

/**
 *
 * @author Morten Kühnrich
 */
public class ChessGame
{
    private Board board;
    private AbstractSearch selectedEngine;
    private int searchDepth = 5;
    
    public ChessGame()
    {
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }
    
    public Board getBoard() 
    {
        return board;
    }
    
    public int getSearchDepth()
    {
        return searchDepth;
    }

    public void setSearchDepth(int searchDepth)
    {
        this.searchDepth = searchDepth;
    }

    public AbstractSearch getSelectedEngine()
    {
        return selectedEngine;
    }

    public void setSelectedEngine(AbstractSearch selectedEngine)
    {
        this.selectedEngine = selectedEngine;
    }



}
