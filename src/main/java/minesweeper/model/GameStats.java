
package minesweeper.model;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameStats {
    public ArrayList<Move> moves;
    public double cumulativeEuclidianDistance;
    public long cumulativeTime;
    
    private boolean lastTimestampSet = false;
    private long lastTimestamp;

    public GameStats() {
        moves = new ArrayList<>();
    }
    
    public void update(Move move) {
        if (!lastTimestampSet) {
            lastTimestamp = move.timestamp;
            lastTimestampSet = true;
        } else {
            cumulativeTime += move.timestamp - lastTimestamp;
        }

        cumulativeEuclidianDistance += move.euclideanDistance;

        moves.add(move);
    }

    public double getTimeInSeconds() {
        return (double) TimeUnit.NANOSECONDS.toMillis(cumulativeTime) * 1000.0d;
    }

    /*
    public double getAdjustedTime() {
        return (double) TimeUnit.NANOSECONDS.toMillis(cumulativeTime) * 1000.0d;
    }*/
}
