package com.simplisticjavachess.position;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class History implements Iterable<Position> {
    LinkedList<Position> positions;

    public History() {
        positions = new LinkedList<>();
    }

    public History(String fen) {
        positions = new LinkedList<>();
        positions.add(PositionIO.FEN(fen));
    }

    private History(LinkedList<Position> positions) {
        this.positions = positions;
    }

    public History add(Position position) {
        LinkedList<Position> positionsList = new LinkedList<>(this.positions);
        positionsList.add(position);
        return new History(positionsList);
    }

    public Position getCurrent() {
        return positions.getLast();
    }

    public History undo() {
        if (positions.size() > 1) {
            LinkedList<Position> positionsList = new LinkedList<>(this.positions);
            positionsList.removeLast();
            return new History(positionsList);
        } else {
            throw new NoSuchElementException("No positions to undo");
        }
    }

    @Override
    public Iterator<Position> iterator() {
        return positions.iterator();
    }

    /**
     * Check if the current position have occurred two times before in the history
     *
     * @return
     */
    public boolean isDrawByRepetition() {
        int count = 0;

        Position current = getCurrent();

        for (int i = 0; i < positions.size(); i++) {
            if (current.equals(positions.get(i))) {
                count++;
            }
        }
        if (count == 3) {
            return true;
        } else if (count > 3) {
            throw new IllegalStateException("The same position cannot have occurred more than three times " +
                    "- it is already a draw");
        } else {
            return false;
        }
    }
}
