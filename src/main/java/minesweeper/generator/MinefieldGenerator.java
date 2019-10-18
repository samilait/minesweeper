
import minesweeper.model.Square;

class MinefieldGenerator {

    public MinefieldGenerator() {
    }

    public void generate(Square[][] board, int mines, int safeX, int safeY) {
        int width = board[0].length;
        int height = board.length;

        int squaresLeft = width * height - 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (Math.random() < ((float) mines / squaresLeft) && x != safeX && y != safeY) {
                    board[y][x].isMine = true;
                    mines--;
                } 

                squaresLeft--;
            }
        }
    }
}
