
package minesweeper.generator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import minesweeper.model.Board;

public class MinefieldGeneratorTest {
    Board board;
    MinefieldGenerator generator;

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
    public void safeAreaAroundFirst() {
        generator.generate(board, 100, 5, 5);

        int[] displacement = new int[] { -1, 0, 1 };
        for (int dx : displacement) {
            for (int dy : displacement) {
                assert (board.open(5 + dx, 5 + dy));
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
                    assert (board.open(dx, dy));
                }
            }
        }
    }
}
