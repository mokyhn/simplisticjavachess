package com.simplisticjavachess.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import com.simplisticjavachess.UnitTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Morten Kühnrich
 */
@UnitTest
public class IteratorUtilsTest {
    public IteratorUtilsTest() {
    }

    private Iterator<Integer> emptyIterator() {
        return new ArrayList<Integer>().iterator();
    }

    private Iterator<Integer> oneElementIterator() {
        return Collections.singletonList(1).iterator();
    }

    private Iterator<Integer> threeElementIterator() {
        return Arrays.asList(1, 2, 3).iterator();
    }

    private Iterator<Integer> reversedThreeElementIterator() {
        return Arrays.asList(3, 2, 1).iterator();
    }


    @Test
    public void testEmptyAndEmpty_compose_two() {
        Iterator result = IteratorUtils.compose(emptyIterator(), emptyIterator());
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testEmptyAndOne_compose_two() {
        Iterator result = IteratorUtils.compose(emptyIterator(), oneElementIterator());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testOneAndEmpty_compose_two() {
        Iterator result = IteratorUtils.compose(oneElementIterator(), emptyIterator());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testOneAndOne_compose_two() {
        Iterator result = IteratorUtils.compose(oneElementIterator(), oneElementIterator());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testThreeAndThree_compose_two() {
        Iterator result = IteratorUtils.compose(threeElementIterator(), threeElementIterator());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(2, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(3, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(2, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(3, result.next());
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testReversedThreeAndThree_compose_two() {
        Iterator result = IteratorUtils.compose(reversedThreeElementIterator(), threeElementIterator());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(3, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(2, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(2, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(3, result.next());
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testEmptyAndEmpty_compose_many() {
        Collection<Iterator<Integer>> input = Arrays.asList(emptyIterator(), emptyIterator());
        Iterator result = IteratorUtils.compose(input);
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testEmptyAndOne_compose_many() {
        Collection<Iterator<Integer>> input = Arrays.asList(emptyIterator(), oneElementIterator());
        Iterator result = IteratorUtils.compose(input);
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testOneAndEmpty_compose_many() {
        Collection<Iterator<Integer>> input = Arrays.asList(oneElementIterator(), emptyIterator());
        Iterator result = IteratorUtils.compose(input);
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testOneAndOne_compose_many() {
        Collection<Iterator<Integer>> input = Arrays.asList(oneElementIterator(), oneElementIterator());
        Iterator result = IteratorUtils.compose(input);
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testThreeAndThree_compose_many() {
        Collection<Iterator<Integer>> input = Arrays.asList(threeElementIterator(), threeElementIterator());
        Iterator result = IteratorUtils.compose(input);
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(2, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(3, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(2, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(3, result.next());
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testReversedThreeAndThree_compose_many() {
        Collection<Iterator<Integer>> input = Arrays.asList(reversedThreeElementIterator(), threeElementIterator());
        Iterator result = IteratorUtils.compose(input);
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(3, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(2, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(2, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(3, result.next());
        Assert.assertFalse(result.hasNext());
    }

    @Test
    public void testReversedThreeEmptyAndThreeAndThree_compose_many() {
        Collection<Iterator<Integer>> input = Arrays.asList(reversedThreeElementIterator(), emptyIterator(),
                threeElementIterator(), threeElementIterator());
        Iterator result = IteratorUtils.compose(input);
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(3, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(2, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(2, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(3, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(1, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(2, result.next());
        Assert.assertTrue(result.hasNext());
        Assert.assertEquals(3, result.next());
        Assert.assertFalse(result.hasNext());
        Assert.assertNull(result.next());
    }

}
