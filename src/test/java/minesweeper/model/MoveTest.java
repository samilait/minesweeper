
package minesweeper.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class MoveTest {
    
   Move move;

    @Before
    public void setUp() {
        // intentionally empty
    } 

    @After
    public void tearDown() {
        // intentionally empty
    }

    @Test
    public void canCreateAnOpenMove() {
        move = new Move(MoveType.OPEN, 5, 5);

        assertEquals(MoveType.OPEN, move.type);
    }

    @Test
    public void canCreateAFlagMove() {
        move = new Move(MoveType.FLAG, 5, 5);

        assertEquals(MoveType.FLAG, move.type);
    }

    @Test
    public void canCreateAChordMove() {
        move = new Move(MoveType.CHORD, 5, 5);

        assertEquals(MoveType.CHORD, move.type);
    }

    @Test
    public void canCreateAHighlightMove() {
        move = new Move(5, 5, Highlight.GREEN);

        assertEquals(MoveType.HIGHLIGHT, move.type);
    }
}
