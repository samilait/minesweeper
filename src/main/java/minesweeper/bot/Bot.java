
package minesweeper.bot;

import minesweeper.model.Board;
import minesweeper.model.Move;

public interface Bot {
    Move makeMove(Board board);
}
