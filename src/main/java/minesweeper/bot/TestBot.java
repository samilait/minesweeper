
package minesweeper.bot;

import java.util.Random;

import java.util.HashSet;
import minesweeper.model.Square;
import minesweeper.model.Board;
import minesweeper.model.Move;
import minesweeper.model.MoveType;
import minesweeper.model.Highlight;

/**
 * A basic bot template for testing purposes
 */
public class TestBot implements Bot {
    
    @Override
    public Move makeMove(Board board) {
        Random rng = new Random();
        Boolean unOpenedSquare = false;
        HashSet<Square> opened = board.getOpenSquares();
        int x = -1;
        int y = -1;
        while (!unOpenedSquare) {
            x = rng.nextInt(board.width);
            y = rng.nextInt(board.length); 
            if (!opened.contains(board.board[x][y])) {
                unOpenedSquare = true;
            }
        }
        Integer type = rng.nextInt(10);
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
}
