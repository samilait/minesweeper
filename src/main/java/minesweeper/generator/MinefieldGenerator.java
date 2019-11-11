package minesweeper.generator;

import java.util.ArrayList;
import java.util.Random;

import minesweeper.model.Board;
import minesweeper.model.Pair;

public class MinefieldGenerator {

// Empty constructor - is unnecessary

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

    public void generate(Board board, int mines, int safeX, int safeY) {
        board.setTotalMines(mines);

        int width = board.width;
        int height = board.length;

        ArrayList<Pair<Integer>> squares = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!isSafeArea(x, y, safeX, safeY)) {
                    squares.add(new Pair(x, y));
                }
            }
        }

        Random rng = new Random();

        for (int i = 0; i < mines; i++) {
            if (squares.isEmpty()) {
                break;
            }

            int index = rng.nextInt(squares.size());

            Pair<Integer> coordinate = squares.remove(index);

            board.board[coordinate.first][coordinate.second].setMine();
            board.incrementAdjacentSquares(coordinate.first, coordinate.second);
        }
    }
}
