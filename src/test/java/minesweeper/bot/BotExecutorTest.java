
package minesweeper.bot;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import minesweeper.model.Move;
import minesweeper.model.Board;
import minesweeper.generator.MinefieldGenerator;

public class BotExecutorTest {
    private BotExecutor botEx;
    private BlockingQueue<Move> queue;

    @Before
    public void setUp() {
        queue = new LinkedBlockingQueue<>();
        botEx = new BotExecutor(queue, new TestBot(), new Board(new MinefieldGenerator(), 10, 10, 10));
    }
    
    @After
    public void tearDown() {
    // empty method
    }

    @Test
    public void botExecutorReturnsMovesInQueue() {
        botEx.run();
        try {
            Move move = queue.poll(100l, TimeUnit.MILLISECONDS);
            assertTrue(move.timestamp > 0);
        } catch (InterruptedException ex) {
            fail("Could not find move on the queue");
        }
    }
}
