
package minesweeper.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;



public class BoardTest {
    Board board;

    @Before
    public void setUp() {
        board = new Board(10, 10);
    } 

    @After
    public void tearDown() {

    }

    @Test
    public void boardInitializesAllSquares() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                assert(board.board[y][x] != null);
            }
        }
    }

    @Test
    public void boardIsInitializedWithCorrectWidthAndHeight() {
        board = new Board(10, 10);

        assertEquals(10, board.length);
        assertEquals(10, board.width);
    }

    @Test
    public void withinBoardReturnsTrueForWithinBoard() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                assert(board.withinBoard(x, y));
            }
        }
    }

    @Test
    public void withinBoardReturnsFalseForOutsideBoard() {
        assertEquals(false, board.withinBoard(20, 5));
        assertEquals(false, board.withinBoard(5, 20));
        assertEquals(false, board.withinBoard(-5, 5));
        assertEquals(false, board.withinBoard(5, -5));
    }

    @Test
    public void addingAMineWorks() {
        board.addSquare(new Square(true), 5, 5);

        board.open(5, 5);

        assert(board.board[5][5].isMine());
    }

    @Test
    public void clickinOnAMineReturnsFalse() {
        board.addSquare(new Square(true), 5, 5);

        assertEquals(false, board.open(5, 5));
    }

    @Test
    public void clickingOnAnEmptySquareReturnsTrue() {
        board.addSquare(new Square(true), 5, 5);

        assertEquals(true, board.open(2, 2));
    }

    @Test
    public void openingEmptyFieldOpensAllSquares() {
        board.open(5, 5);

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                assert(board.board[y][x].getOpen());
            }
        }
    }


    @Test
    public void openingASquareDoesNotOpenMines() {
        board.addSquare(new Square(true), 2, 2);
        board.incrementAdjacentSquares(2,2);

        board.open(5, 5);

        assertEquals(false, board.board[2][2].getOpen());
    }
}
