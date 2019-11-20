
package minesweeper.generator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import minesweeper.model.Board;
import minesweeper.model.Square;
import minesweeper.model.Move;
import minesweeper.model.MoveType;
import static org.junit.Assert.assertTrue;

public class MinefieldGeneratorTest {
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
// empty method
    }

    @Test
    public void safeAreaAroundFirst() {
        generator.generate(board, 100, 5, 5);

        int[] displacement = new int[] { -1, 0, 1 };
        for (int dx : displacement) {
            for (int dy : displacement) {
                assertTrue(board.makeMove(new Move(MoveType.OPEN, 5+dx, 5+dy)));
            }
        }
    }

    @Test
    public void safeAreaAroundFirstEdgeCase() {    
        generator.generate(board, 100, 0, 0);
        int[] displacement = new int[] { -1, 0, 1 };
        for (int dx : displacement) {
            for (int dy : displacement) {
                if(board.withinBoard(dx, dy)){
                    assertTrue(board.makeMove(new Move(MoveType.OPEN, dx, dy)));
                }
            }
        }
    }

    @Test
    public void twoFieldsWithSameSeedAreEqual() {
        generator = new MinefieldGenerator(666);

        Board board1 = new Board(generator, 100, 100, 30);
        Board board2 = new Board(generator, 100, 100, 30);

        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                board1.makeMove(new Move(MoveType.OPEN, x, y));
                board2.makeMove(new Move(MoveType.OPEN, x, y));
            }
        }

        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                Square square1 = board1.getSquareAt(x, y);
                Square square2 = board2.getSquareAt(x, y);

                assertEquals(square1.isMine(), square2.isMine());
                assertEquals(square1.surroundingMines(), square2.surroundingMines());
            }
        }

    }
}
