
package minesweeper.bot;

import java.util.HashSet;
import java.util.Random;
//import java.util.HashSet;
//import minesweeper.model.Square;
import minesweeper.model.Board;
import minesweeper.model.GameStats;
import minesweeper.model.Move;
import minesweeper.model.MoveType;
import minesweeper.model.Highlight;
import minesweeper.model.Pair;
import minesweeper.model.Square;

/**
 * A basic bot template for testing purposes and as an example
 *
 * <p>
 * This class is meant to demonstrate the basic outline of a bot implementation
 * and isn't itself a very good Minesweeper bot. Basically, this implementation
 * can work as a template for Data Structures and Algorithms projects for actual
 * Bot implementation.
 * </p>
 *
 * <p>
 * The Bot will be called externally (the interface for this is the makeMove()
 * function) and be given the current game state represented by a Board object.
 * <b>THE BOT DOES NOT MODIFY THIS BOARD OBJECT.</b> Instead the bot needs
 * determine the best action to take by returning a Move object, which represents
 * one action that can be executed onto the board. Refer to model/Move.java for
 * details.
 * </p>
 *
 * <p>
 * For Data Structures and Algorithms, you can copy this bot implementation into
 * your own class (e.g. MyBot.java) and implement your AI algorithm within the
 * makeMove() method.
 * </p>
 */
public class TestBot implements Bot {

    private GameStats gameStats;

    /**
     * Make a single decision based on the given Board state
     * @return Move to be made onto the board
     */
    @Override
    public Move makeMove(Board board) {
        // Find the coordinate of an unopened square
        Pair<Integer> pair = findUnopenedSquare(board);
        int x = (int) pair.first;
        int y = (int) pair.second;

        // The TestBot isn't very smart and randomly
        // decides what move should be made using java.util.Random
        Random rng = new Random();
        Integer type = rng.nextInt(10);

        // Certain move types are given more weight
        // but these moves are still extremely random
        // and most likely not correct
        if (type < 5) {
            return new Move(MoveType.OPEN, x, y);
        } else if (type < 8) {
            return new Move(MoveType.FLAG, x, y);
        } else {
            if (rng.nextInt(2) == 0) {
                return new Move(x, y, Highlight.GREEN);
            } else {
                return new Move(x, y, Highlight.RED);
            }
        }
    }

    @Override
    public void setGameStats(GameStats gameStats) {
        this.gameStats = gameStats;
    }

    /**
     * Find the (X, Y) coordinate pair of an unopened square
     * from the current board
     * @param board The current board state
     * @return An (X, Y) coordinate pair
     */
    public Pair<Integer> findUnopenedSquare(Board board) {
        Random rng = new Random();
        Boolean unOpenedSquare = false;

        // board.getOpenSquares allows us to access those squares
        // that have already been opened
        HashSet<Square> opened = board.getOpenSquares();
        int x;
        int y;

        Pair<Integer> pair = new Pair<>(0, 0);

        // Randomly generate X,Y coordinate pairs that
        while (!unOpenedSquare) {
            x = rng.nextInt(board.width);
            y = rng.nextInt(board.length); 
            if (!opened.contains(board.board[x][y])) {
                unOpenedSquare = true;
                pair = new Pair<Integer>(x, y);
            }
        }

        // This pair should point to an unopened square now
        return pair;
    } 
}
