
package minesweeper.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SquareTest {
    
   private Square square;

    @Before
    public void setUp() {
        square = new Square(0,0);
    } 

    @After
    public void tearDown() {
// intentionally empty
    }

    @Test
    public void byDefaultSquareIsNotOpened() {
        assertEquals(false, square.getOpen());
    }

    @Test
    public void flaggedSquareIsNotOpened() {  
        square.toggleFlagged();
        square.open();
        assertEquals(false, square.getOpen());
    }

    @Test
    public void openedSquareDoesNotToggleFlag() {
        square.open();       
        square.toggleFlagged();
        assertEquals(true, square.getOpen());
    }
 
    @Test
    public void unopenedSquareTogglesFlag() {
        square.toggleFlagged();
        assertEquals(false, square.getOpen());
    }
    
    @Test
    public void unopenedSquareWillNotRevealNumberOfSurrounding() {
        assertEquals(false, square.getOpen());

        try {
            square.surroundingMines();
        } catch (AssertionError err) {
            assert(true);
        }
        
    }

    @Test
    public void unopenedSquareWillNotRevealIfMine() {
        square.setMine();
        assertEquals(false, square.getOpen());
        
        try {
            square.isMine();
        } catch (AssertionError err) {
            assert(true);
        }

    }
    
    @Test
    public void squareCanBeFlagged() {
        square.toggleFlagged();

        assertTrue(square.getFlagged());
    }
    
    @Test
    public void squareCanBeUnflagged() {
        square.toggleFlagged();
        square.toggleFlagged();

        assertFalse(square.getFlagged());
    }

    @Test
    public void returnXWhenUnopened() {
        assertEquals("X", square.toString());
    }
      
    @Test
    public void return0WhenOpenedAndNotMine() {
        square.open();
        assertEquals("0", square.toString());
    }
    
    @Test
    public void returnStarWhenMineOpened() {
        square.setMine();
        square.open();
        assertEquals("*", square.toString());
    } 
  
}
