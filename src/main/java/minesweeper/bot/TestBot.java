
package minesweeper.bot;

import minesweeper.model.Board;
import minesweeper.model.Move;
import minesweeper.model.MoveType;

/**
 * A basic bot template for testing purposes
 */
public class TestBot implements Bot {
    
    @Override
    public Move makeMove(Board board) {
        int someCoord = board.findUnopenedNotFlaggedSquare();
        return new Move(MoveType.OPEN, someCoord / 1000, someCoord % 1000);
    }
    
}
