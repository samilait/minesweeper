
package minesweeper.bot;

import minesweeper.model.Board;
import minesweeper.model.Move;
import java.util.ArrayList;

/**
 * Bot communication interface
 */
public interface Bot {

    /**
     * Ask a Bot for a move
     * @param board Current board state
     * @return The bot's move
     */
    Move makeMove(Board board);

    /**
     * Ask a Bot for a list of move, preferably highlights but not required
     * @param board Current board state
     * @return The bot's moves
     */
    ArrayList<Move> getPossibleMoves(Board board);
}
