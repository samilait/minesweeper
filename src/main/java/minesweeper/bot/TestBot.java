
package minesweeper.bot;

import java.util.Random;

import minesweeper.model.Board;
import minesweeper.model.Square;
import minesweeper.model.Move;
import minesweeper.model.MoveType;

/**
 * A basic bot template for testing purposes
 */
public class TestBot implements Bot {
    
    @Override
    public Move makeMove(Board board) {
        int someCoord = findUnopenedSquare(board);
        return new Move(MoveType.OPEN, someCoord / 1000, someCoord % 1000);
    }
    
    public int findUnopenedSquare(Board board) {
        Random rng = new Random();
        Square square;
        int x = -1;
        int y = -1;
        Boolean wasOpened = true;
        while (wasOpened) {
            x = rng.nextInt(board.width);
            y = rng.nextInt(board.length);
            square = board.getSquareAt(x, y);
            wasOpened = square.isOpened();
        }
        
        return 1000 * x + y;
    }
    
}
