
package minesweeper.bot;

import minesweeper.model.Board;
import minesweeper.model.GameStats;
import minesweeper.model.Move;

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
     * This method is used to pass GameStats-object to the bot,
     * which can be then saved by the bot if necessary. 
     * @param gameStats GameStats for current game
     */
    void setGameStats(GameStats gameStats);
}
