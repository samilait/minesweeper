
package minesweeper.generator;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

import minesweeper.model.Board;

public class MinefieldGeneratorTest {
    Board board;
    MinefieldGenerator generator;

    @Before
    public void setUp() {
        board = new Board(10, 10);
        generator = new MinefieldGenerator();
    } 

    @After
    public void tearDown() {

    }

    @Test
    public void generatorLeavesSafeSquareEmpty() {
        generator.generate(board, 5, 5, 5);

        assert(board.open(5, 5));
    }
}
