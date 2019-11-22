/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper.model;

import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ktojala
 */
public class PairTest {
    
    private Pair pair;

    @Before
    public void setUp() {
        pair = new Pair(5,5);
    } 

    @After
    public void tearDown() {
// intentionally empty
    }

    @Test
    public void twoSimilarPairsEqual() {
        Pair pair2 = new Pair(5,5);
        assertTrue(pair.equals(pair2));
        
        assertTrue(pair.equals(pair));
    }

    @Test
    public void nonEquals() {
        Pair pair2 = new Pair(5,6);
        assertFalse(pair.equals(pair2));
        
        Pair pair3 = new Pair(4,5);
        assertFalse(pair.equals(pair3));
        
        Pair pair4 = null;
        assertFalse(pair.equals(pair4));
        
        int notPair = 1;
        assertFalse(pair.equals(notPair));
    }
    
}
