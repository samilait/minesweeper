package minesweeper.generator;

import minesweeper.model.Square;
import minesweeper.model.Board;

public class MinefieldGenerator {

    public MinefieldGenerator() {
    }

    public void generate(Board board, int mines, int safeX, int safeY) {
        int width = board.length;
        int height = board.length;

        int squaresLeft = width * height - 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (Math.random() < ((float) mines / squaresLeft) && x != safeX && y != safeY) {
                    board.board[y][x].setMine();
                    board.incrementAdjacentSquares(x, y);
                    mines--;
                } 

                squaresLeft--;
            }
        }
    }
}
