
package minesweeper.bot;

import java.util.Random;

import minesweeper.model.Board;
import minesweeper.model.Move;
import minesweeper.model.MoveType;

public class TestBot implements Bot {
    
    @Override
    public Move makeMove(Board board) {
        Random rng = new Random();

        int x = rng.nextInt(board.width);
        int y = rng.nextInt(board.length);

        return new Move(MoveType.FLAG, x, y);
    }
}
