
package minesweeper.model;

import java.util.Arrays;

public class Board {

    boolean gameEnd, gameWon = false;
    Square[][] board;
    Square[][] incompleteBoard;
    final int width, length;

    // For debugging purposes
    public Board() {
        this.width = this.length = 10;
        this.board = new Square[10][10];
        this.incompleteBoard = new Square[10][10];
        this.initialize();
    }

    public Board(int width, int length) {
        this.width = width;
        this.length = length;
        this.board = new Square[width][length];
        this.incompleteBoard = new Square[width][length];
        this.initialize();
    }

    public void addSquare(Square square, int xCoord, int yCoord) {
        this.board[xCoord][yCoord] = square;
    }

    public void setMinesInital(int mineCount) {
        if (mineCount > this.width * this.length) {
            throw new Error("More mine than squares in this board");
        }
        int row = 0;
        int col = 0;
        while (mineCount-- > 0) {
            this.board[row][col].isMine = true;
            if (++col >= this.width){
                col = 0;
                row++;
            }
        }
    }

    /**
     * Initializes the board with empty squares i.e. no mines.
     */
    public void initialize() {
        this.board = Arrays.stream(board)
        .map(row -> {
            return Arrays.stream(row)
            .map(squareElement -> new Square())
            .toArray(Square[]::new);
        }).toArray(Square[][]::new);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Field \n");
        Arrays.stream(board).forEach(row -> {
        Arrays.stream(row).forEach(square -> builder.append(square));
        builder.append("\n");
    });
        return builder.toString();
    }

}
