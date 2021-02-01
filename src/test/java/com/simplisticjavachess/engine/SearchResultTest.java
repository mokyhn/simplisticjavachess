package com.simplisticjavachess.engine;

import com.simplisticjavachess.UnitTest;
import com.simplisticjavachess.evaluation.Evaluation;
import com.simplisticjavachess.move.MoveSequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class SearchResultTest {

    @Mock
    Evaluation evaluation;

    @Mock
    MoveSequence moveSequence;

    SearchResult searchResult1;

    SearchResult searchResult2;

    @Before
    public void before() {
        when(moveSequence.toString()).thenReturn("movesequence");
        when(evaluation.toString()).thenReturn("evaluation");
        searchResult1 = new SearchResult(evaluation);
        searchResult2 = new SearchResult(moveSequence, evaluation);
    }

    @Test
    public void getMoveSequence() {
        Assert.assertEquals(moveSequence, searchResult2.getMoveSequence());
    }

    @Test
    public void getEvaluation() {
        Assert.assertEquals(evaluation, searchResult1.getEvaluation());
        Assert.assertEquals(evaluation, searchResult2.getEvaluation());
    }

    @Test
    public void testToString() {
        Assert.assertEquals("Move movesequence with evaluation evaluation", searchResult2.toString());
    }
}