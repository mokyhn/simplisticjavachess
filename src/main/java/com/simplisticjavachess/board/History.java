package com.simplisticjavachess.board;

import java.util.Iterator;
import java.util.LinkedList;

public class History implements Iterable<Position>
{
    LinkedList<Position> positions;

    public History()
    {
        positions = new LinkedList<>();
    }

    public History(String fen)
    {
        positions = new LinkedList<>();
        positions.add(BoardParser.FEN(fen));
    }

    private History(LinkedList<Position> positions)
    {
        this.positions = positions;
    }

    public History add(Position position)
    {
        LinkedList<Position> positionsList = new LinkedList<>(this.positions);
        positionsList.add(position);
        return new History(positionsList);
    }

    public Position getCurrent()
    {
        return positions.getLast();
    }

    @Override
    public Iterator<Position> iterator() {
        return positions.iterator();
    }

    public boolean isDrawByRepetition()
    {
        int count = 0;

        Position current = getCurrent();

        for (Position p : positions)
        {
            if (p != current && current.equals(p))
            {
                count++;
            }
        }
        return count >= 3;
    }
}
