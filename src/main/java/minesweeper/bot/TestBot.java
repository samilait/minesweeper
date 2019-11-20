
package minesweeper.bot;

import java.util.HashSet;
import java.util.Random;
//import java.util.HashSet;
//import minesweeper.model.Square;
import minesweeper.model.Board;
import minesweeper.model.Move;
import minesweeper.model.MoveType;
import minesweeper.model.Highlight;
import minesweeper.model.Pair;
import minesweeper.model.Square;

/**
 * A basic bot template for testing purposes
 */
public class TestBot implements Bot {
    
    @Override
    public Move makeMove(Board board) {
        Pair pair = findUnopenedSquare(board);  
        int x = (int) pair.first;
        int y = (int) pair.second;
        Random rng = new Random();
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
    
    public Pair findUnopenedSquare(Board board) {
        Random rng = new Random();
        Boolean unOpenedSquare = false;
        HashSet<Square> opened = board.getOpenSquares();
        int x;
        int y;
        Pair pair = new Pair(0, 0);
        while (!unOpenedSquare) {
            x = rng.nextInt(board.width);
            y = rng.nextInt(board.length); 
            if (!opened.contains(board.board[x][y])) {
                unOpenedSquare = true;
                pair = new Pair(x, y);
            }
        }
        return pair;
    } 
}
