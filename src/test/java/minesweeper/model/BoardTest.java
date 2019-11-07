
package minesweeper.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import minesweeper.generator.MinefieldGenerator;

public class BoardTest {
    private Board board;
    private MinefieldGenerator generator;

    @Before
    public void setUp() {
        generator = new MinefieldGenerator();
        board = new Board(generator, 10, 10, 3);
        board.firstMove = false;
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
        board = new Board(generator, 10, 10, 3);

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

    @Test
    public void openingASquareDoesNotOpenFlagged() {
        board.board[4][5].toggleFlagged();
        
        assertEquals(true, board.board[4][5].getFlagged());

        board.open(5, 5);

        assertEquals(false, board.board[4][5].getOpen());
    }

    @Test
    public void chordedOpenDoesNotOpenFlagged() {
        board.board[4][5].setMine();
        board.board[4][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[4][4].setMine();
        board.board[4][4].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[6][5].setMine();
        board.board[6][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[5][5].open();
        board.chordedOpen(5, 5);

        assertEquals(false, board.board[4][5].getOpen());
        assertEquals(false, board.board[4][4].getOpen());
        assertEquals(false, board.board[6][5].getOpen());
    }

    @Test
    public void chordedOpenOpensUnflagged() {
        board.board[4][5].setMine();
        board.board[4][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[4][4].setMine();
        board.board[4][4].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[6][5].setMine();
        board.board[6][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[5][5].open();
        board.chordedOpen(5, 5);

        for (int xInc = -1; xInc <= 1; xInc++) {
            for (int yInc = -1; yInc <= 1; yInc++) {
                if (!board.board[5 + xInc][5 + yInc].getFlagged()) {
                    assertEquals(true, board.board[5 + xInc][5 + yInc].getOpen());
                }
            }
        }
    }

    @Test
    public void chordedOpenReturnsFalseForMineHit() {
        board.board[4][5].setMine();
        board.board[4][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[4][4].setMine();
        board.incrementAdjacentSquares(4, 5);

        board.board[6][6].toggleFlagged();

        board.board[6][5].setMine();
        board.board[6][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[5][5].open();
        assertEquals(false, board.chordedOpen(5, 5));
    }

    @Test
    public void chordedOpenWillNotRunIfSurroundingMinesAndFlagsDoNotMatch() {
        board.board[4][5].setMine();
        board.board[4][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[4][4].setMine();
        board.incrementAdjacentSquares(4, 5);

        board.board[6][5].setMine();
        board.board[6][5].toggleFlagged();
        board.incrementAdjacentSquares(4, 5);

        board.board[5][5].open();

        assertEquals(true, board.chordedOpen(5, 5));

        assertEquals(false, board.board[6][6].getOpen());
    }

    @Test
    public void boardHighlightsCanBeCleared() {
        board.board[5][5].highlight = Highlight.RED;

        board.clearHighlights();

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.width; x++) {
                assertEquals(Highlight.NONE, board.board[x][y].highlight);
            }
        }
    }
}
