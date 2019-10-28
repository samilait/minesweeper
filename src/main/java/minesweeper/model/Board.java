
package minesweeper.model;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;

public class Board {

    public boolean gameEnd, gameWon = false;
    public Square[][] board;
    public int totalMines;
    public final int width, length;

    // For debugging purposes
    public Board() {
        this.width = 10;
        this.length = 10;
        this.board = new Square[10][10];
        int mineCount = 2;
        this.totalMines = 2;
        int row = 0;
        int col = 0;
        this.initialize();
        while (mineCount-- > 0) {
            this.board[row][col] = new Square(true);
            this.incrementAdjacentSquares(row, col);
            if (++col >= this.width) {
                col = 0;
                row++;
            }
        }
    }

    public Board(int width, int length) {
        this.width = width;
        this.length = length;
        this.board = new Square[width][length];
        this.initialize();
    }
    /** 
     * Sets the number of total mines, used by MinefieldGenerator when generating a new board
     */
    public void setTotalMines(int totalMines) {
        this.totalMines = totalMines;
    }
    /**
     * Set a Square at a given X, Y coordinate
     */
    public void addSquare(Square square, int xCoord, int yCoord) {
        this.board[xCoord][yCoord] = square;
    }

    /**
     * Opens a square in the given X, Y coordinate
     * and all surrounding squares that are not mines
     */
    public boolean open(int x, int y) {
        if (this.board[x][y].getFlagged()) {
            return true;
        }

        this.board[x][y].open();
        if (board[x][y].isMine()) {
            this.gameEnd = true;
            return false;
        } else {

            /*
             * BFS implementation for opening squares:
             *
             * 1. Open current square
             * 2. If current square has surrounding mines, ignore surrounding tiles
             * 3. Else open all surrounding squares
             */

            HashSet<Pair<Integer>> visited = new HashSet<>();
            ArrayDeque<Pair<Integer>> toVisit = new ArrayDeque<>();

            toVisit.push(new Pair(x, y));

            while (!toVisit.isEmpty()) {
                Pair<Integer> v = toVisit.pop();

                // Have we visited this square before?
                if (visited.contains(v)) {

                    // If yes, skip it
                    continue;
                }

                visited.add(v);

                if (withinBoard(v.first, v.second)) {
                    Square square = board[v.first][v.second];
                    
                    board[v.first][v.second].open();
                    // If current square has surrounding mines, ignore surrounding squares
                    if (square.surroundingMines() > 0 || square.getFlagged()) {
                        continue;
                    } else if (square.surroundingMines() == 0) {
                        // No surrounding mines, all surrounding squares can be opened
                        
                        toVisit.push(new Pair(v.first - 1, v.second));
                        toVisit.push(new Pair(v.first + 1, v.second));

                        toVisit.push(new Pair(v.first, v.second - 1));
                        toVisit.push(new Pair(v.first, v.second + 1));

                        toVisit.push(new Pair(v.first - 1, v.second - 1));
                        toVisit.push(new Pair(v.first - 1, v.second + 1));

                        toVisit.push(new Pair(v.first + 1, v.second - 1));
                        toVisit.push(new Pair(v.first + 1, v.second + 1));
                    }
                }
            }
        }
        if (getUnopenedSquaresCount() == this.totalMines) {
            this.gameWon = true;
        }
        return true;
    }
    public int getUnopenedSquaresCount() {
        int unopenedSquares = 0;
        for (int x = 0; x < this.width; x++) { 
            for (int y = 0; y < this.length; y++) {
                if (!board[x][y].getOpen()) {
                    unopenedSquares++;
                }
            }
        }
        return unopenedSquares;
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
    
    /**
     * Increments the surrounding mine counter for tiles surrounding given square
     */
    public void incrementAdjacentSquares(int x, int y) {
        for (int xInc = -1; xInc <= 1; xInc++) {
            for (int yInc = -1; yInc <= 1; yInc++) {
                if (xInc == 0 && yInc == 0) {
                    continue;
                }
                if (withinBoard(x + xInc, y + yInc)) {
                    board[x + xInc][y + yInc].incrementSurroundingMines();
                }
            }
        }
    }

    /**
     * Check if a given X,Y coordinate is within the board
     */
    public boolean withinBoard(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.length;
    }

}
