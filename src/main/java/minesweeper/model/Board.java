
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
            this.incrementAdjacentSquares(row, col);
            if (++col >= this.width) {
                col = 0;
                row++;
            }
        }
    }

    public boolean clickOnSquare(int x, int y){
        this.board[x][y].opened = true;
        if(board[x][y].isMine){
            this.gameEnd = true;
            return false;
        }
        return true;
    }


    /**
     * Initializes the board with empty squares i.e. no mines.
     */
    public void initialize() {
        this.board = Arrays.stream(board).map(row -> {
            return Arrays.stream(row).map(squareElement -> new Square()).toArray(Square[]::new);
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

    public void incrementAdjacentSquares(int x, int y) {
        for(int xInc = -1; xInc <= 1; xInc++){
            for(int yInc = -1; yInc <= 1; yInc++){
                System.out.println(xInc + ":" + yInc);
                if(withingBoard(x + xInc, y + yInc) && !(xInc == 0 && yInc == 0)){
                    board[x + xInc][y + yInc].surrounding++;
                }
            }
        }
    }

    private boolean withingBoard(int x, int y){
        return x >= 0 
        && x < this.width 
        && y >= 0 
        && y < this.length;
    }

}
