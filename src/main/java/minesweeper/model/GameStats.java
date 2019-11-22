
package minesweeper.model;

import java.util.ArrayList;
import javafx.util.Pair;
import java.util.concurrent.TimeUnit;

public class GameStats {
    public ArrayList<Pair<Move, Double>> moves;
    public double cumulativeEuclidianDistance = 0;
    public double cumulativeTime = 0;
    public long startTime = System.nanoTime();

    private boolean firstMove = true;
    private Move lastMove;

    public GameStats() {
        moves = new ArrayList<>();
    }

    public void update(Move move) {
        Double dtime;
        if (firstMove) {
            dtime = (Double) deltaTimeInSeconds(startTime, move);
            move.setEuclideanDistance(0d);
            firstMove = false;
        } else {
            dtime = (Double) deltaTimeInSeconds(lastMove, move);
            move.setEuclideanDistance(deltaEuclideanDistance(lastMove, move));
        }
        moves.add(new Pair<Move, Double>(move, dtime));
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
