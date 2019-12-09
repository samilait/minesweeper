
package minesweeper.generator;

import java.util.ArrayList;
import java.util.Random;

import minesweeper.model.Board;
import minesweeper.model.Pair;

/**
 * Generator for initializing board with mines
 */
public class MinefieldGenerator {
    private boolean seedSet = false;
    private long seed;

    public MinefieldGenerator() {
    }

    public MinefieldGenerator(long seed) {
        this.seed = seed;
        this.seedSet = true;
    }

    /**
     * Checks if a given X,Y coordinate is within the safe area
     */
    private boolean isSafeArea(int currentX, int currentY, int safeOriginX, int safeOriginY) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (safeOriginX + x == currentX && safeOriginY + y == currentY) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Generates a minefield on a given board while leaving a 3x3 safe area
     * around the given X,Y coordinate
     * @param board The Board to be updated
     * @param mines The number of mines to be placed
     * @param safeX The X coordinate of the safe area
     * @param safeY The Y coordinate of the safe area
     */
    public void generate(Board board, int mines, int safeX, int safeY) {
        board.setTotalMines(mines);

        int width = board.width;
        int height = board.height;

        ArrayList<Pair<Integer>> squares = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!isSafeArea(x, y, safeX, safeY)) {
                    squares.add(new Pair(x, y));
                }
            }
        }

        Random rng = new Random();

        if (this.seedSet) {
            rng.setSeed(this.seed);
        }

        for (int i = 0; i < mines; i++) {
            if (squares.isEmpty()) {
                break;
            }

            int index = rng.nextInt(squares.size());

            Pair<Integer> coordinate = squares.remove(index);

            board.board[coordinate.first][coordinate.second].setMine();
            board.addMineSquareToList(board.board[coordinate.first][coordinate.second]);
            board.incrementAdjacentSquares(coordinate.first, coordinate.second);
        }
    }
}
