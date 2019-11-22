
package minesweeper.model;

import java.util.ArrayList;
import javafx.util.Pair;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.concurrent.TimeUnit;
import java.util.Collections;

public class GameStats {
    public ObservableList<Move> moves;
    public double cumulativeEuclidianDistance = 0;
    public double cumulativeTime = 0;
    public long startTime = System.nanoTime();

    private boolean firstMove = true;
    private Move lastMove;

    public GameStats() {
        moves = FXCollections.observableArrayList();
    }

    public void update(Move move) {
        //Collections.reverse(moves);

        Double dtime;
        if (firstMove) {
            dtime = (Double) deltaTimeInSeconds(startTime, move);
            move.setEuclideanDistance(0d);
            firstMove = false;
        } else {
            dtime = (Double) deltaTimeInSeconds(lastMove, move);
            move.setEuclideanDistance(deltaEuclideanDistance(lastMove, move));
        }
        move.deltaTime = dtime;

        moves.add(0, move);

        //Collections.reverse(moves);

        cumulativeTime += dtime;
        cumulativeEuclidianDistance += move.euclideanDistance;
        lastMove = move;
    }

    public double deltaEuclideanDistance(Move move1, Move move2) {
        return Math.hypot(move2.x - move1.x, move2.y - move1.y);
    }

    public static double deltaTimeInSeconds(Move lastMove, Move currentMove) {
        return (double) TimeUnit.NANOSECONDS.toMillis(currentMove.timestamp - lastMove.timestamp) / 1000.0d;
    }

    public static double deltaTimeInSeconds(long firstTime, Move move) {
        return (double) TimeUnit.NANOSECONDS.toMillis(move.timestamp - firstTime) / 1000.0d;
    }

    /*
     * public double getAdjustedTime() { return (double)
     * TimeUnit.NANOSECONDS.toMillis(cumulativeTime) * 1000.0d; }
     */
}
