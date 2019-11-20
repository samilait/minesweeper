
package minesweeper.model;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertTrue;

public class GameStatsTest {
    private GameStats stats;

    @Before
    public void setUp() {
        stats = new GameStats();
    }

    @After
    public void tearDown() {
    // empty method
    }

    @Test
    public void updatingWithAMoveWorks() {
        Move move = new Move(MoveType.OPEN, 5, 5);

        stats.update(move);

        assertTrue(stats.moves.size() > 0);
    }

    @Test
    public void updatingWithMovesIncreasesCumulativeTime() {
        Move move = new Move(MoveType.OPEN, 5, 5);

        stats.update(move);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }

        move = new Move(MoveType.OPEN, 5, 5);

        stats.update(move);

        assertTrue(stats.cumulativeTime > 0);
    }

    @Test
    public void updatingWithMovesIncreasesCumulativeDistance() {
        Move move = new Move(MoveType.OPEN, 5, 5);

        stats.update(move);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }

        move = new Move(MoveType.OPEN, 10, 10);
        move.euclideanDistance = Math.hypot(10 - 5, 10 - 5);

        stats.update(move);

        assertTrue(stats.cumulativeEuclidianDistance > 0);
    }
}
