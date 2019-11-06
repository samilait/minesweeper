
package minesweeper.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

public class SquareTest {
    Square square;

    @Before
    public void setUp() {
        square = new Square();
    } 

    @After
    public void tearDown() {

    }

    @Test
    public void byDefaultSquareIsNotOpened() {
        assertEquals(false, square.getOpen());
    }

    @Test
    public void unopenedSquareWillNotRevealNumberOfSurrounding() {
        assertEquals(false, square.getOpen());

        try {
            square.surroundingMines();
            assert(false);
        } catch (AssertionError err) {
            assert(true);
        }
        
    }

    @Test
    public void unopenedSquareWillNotRevealIfMine() {
        square.setMine();

        try {
            square.isMine();
            assert(false);
        } catch (AssertionError err) {
            assert(true);
        }

    }
}
