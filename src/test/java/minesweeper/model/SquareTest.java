
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
        assertEquals(0, square.surroundingMines());
    }

    @Test
    public void unopenedSquareWillNotRevealIfMine() {
        square.setMine();
        assertEquals(false, square.isMine());
    }
}
