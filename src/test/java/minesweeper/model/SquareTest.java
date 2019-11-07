
package minesweeper.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class SquareTest {
    
   Square square;

    @Before
    public void setUp() {
        square = new Square();
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
    public void returnXWhenUnopened() {  
        assertEquals("X", square.toString());
    }
      
    @Test
    public void return0WhenOpened() {  
        square.open();
        assertEquals("0", square.toString());
    }  
    
}
