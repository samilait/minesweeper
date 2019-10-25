package minesweeper.generator;

import minesweeper.model.Board;

public class MinefieldGenerator {

    public MinefieldGenerator() {
    }

    private boolean isSafeArea(int currentX, int currentY, int safeOriginX, int safeOriginY) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (safeOriginX + x == currentX || safeOriginX + y == currentY)
                    return true;
            }
        }
        return false;
    }

    public void generate(Board board, int mines, int safeX, int safeY) {
        int width = board.length;
        int height = board.length;

        int squaresLeft = width * height;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (isSafeArea(safeX + x, safeY + y , safeX, safeY) && board.withinBoard(safeX + x, safeY + y)){
                    squaresLeft--;
            }
        }
    }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (Math.random() < ((float) mines / squaresLeft)) {
                    if (isSafeArea(x, y, safeX, safeY)) {
                        continue;
                    }

                    board.board[x][y].setMine();
                    board.incrementAdjacentSquares(x, y);
                    mines--;
                }

                squaresLeft--;
            }
        }
    }
}
