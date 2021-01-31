package com.simplisticjavachess.position;

import com.simplisticjavachess.misc.IteratorUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Iterator;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HistoryTest {

    History history;

    @Mock
    Position position1;

    @Mock
    Position position2;


    @Before
    public void before() {
        history = new History();
    }

    @Test
    public void add() {
        history = history.add(position1).add(position2);
        assertEquals(position2, history.getCurrent());
    }

    @Test
    public void getCurrent() {
        history = history.add(position1);
        assertEquals(position1, history.getCurrent());
    }

    @Test
    public void undo() {
        history = history.add(position1).add(position2);
        assertEquals(position1, history.undo().getCurrent());
    }

    @Test
    public void iterator() {
        Iterator iterator = history.add(position1).add(position2).iterator();
        assertEquals("[position1, position2]", IteratorUtils.toString(iterator));
    }

    @Test
    public void isDrawByRepetition() {
        assertFalse(history.add(position1).add(position2).isDrawByRepetition());
        assertFalse(history.add(position1).add(position2).add(position1).add(position2).isDrawByRepetition());
        assertTrue(history.add(position1).add(position2).add(position1).add(position2).add(position1).isDrawByRepetition());
    }

    @Test(expected = IllegalStateException.class)
    public void illegalStateInHistory() {
        history.add(position1).add(position2).add(position1).add(position2).add(position1).add(position1).isDrawByRepetition();
    }
}