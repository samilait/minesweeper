
package minesweeper.bot;

import java.util.Random;

import minesweeper.model.Board;
import minesweeper.model.Highlight;

public class TestBot implements Bot {
    
    @Override
    public boolean makeMove(Board board) {
        Random rng = new Random();

        int x = rng.nextInt(board.width);
        int y = rng.nextInt(board.length);

        board.clearHighlights();
        board.board[x][y].highlight = Highlight.GREEN;

        return board.open(x, y);
    }
}
