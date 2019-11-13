
package minesweeper.model;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;

import minesweeper.generator.MinefieldGenerator;

public class Board {

    public boolean gameEnd = false;
    public boolean  gameWon = false;
    public Square[][] board;
    public int totalMines;
    public final int width;
    public final int length;

    private MinefieldGenerator generator;
    public boolean firstMove = true;

    private HashSet<Square> openSquares;

    public Board(MinefieldGenerator generator, int width, int length, int totalMines) {
        this.width = width;
        this.length = length;
        this.board = new Square[width][length];
        this.generator = generator;
        this.totalMines = totalMines;
        this.openSquares = new HashSet<>();
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
     * Get a Square at a given X, Y coordinate
     */
    public Square getSquareAt(int x, int y) {
        return this.board[x][y];
    }

    public HashSet<Square> getOpenSquares() {
        return this.openSquares;
    }

    /**
     * Opens a square in the given X, Y coordinate
     * and all surrounding squares that are not mines
     */
    public boolean open(int x, int y) {
        if (this.firstMove) {
            generator.generate(this, totalMines, x, y);
            this.firstMove = false;
        }

        if (this.board[x][y].getFlagged()) {
            return true;
        }

        this.board[x][y].open();
        this.openSquares.add(board[x][y]);

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
                    
                    if (square.getFlagged()) {
                        continue;
                    }

                    square.open();
                    this.openSquares.add(square);

                    // If current square has surrounding mines, ignore surrounding squares
                    if ((square.surroundingMines() == 0) && (!square.getFlagged())) {
                        // No surrounding mines, all surrounding squares can be opened
                        for (int xInc = -1; xInc <= 1; xInc++) {
                            for (int yInc = -1; yInc <= 1; yInc++) {
                                if (withinBoard(v.first + xInc, v.second + yInc) 
                                    && !board[v.first + xInc][v.second + yInc].getOpen()) {
                                    toVisit.push(new Pair(v.first + xInc, v.second + yInc));
                                }
                            }
                        }
                    }
                }
            }
        }
        if (getUnopenedSquaresCount() == this.totalMines) {
            this.gameWon = true;
        }
        return true;
    }

    /**
     * Execute a chorded open move on a previously opened square
     * If the number of adjacent flagged squares equals to
     * the number of surrounding mines of a given square, opens
     * all adjacent, unflagged squares.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return True if no mines were hit, false otherwise
     */
    public boolean chordedOpen(int x, int y) {
        int surroundingFlagged = 0;

        for (int xInc = -1; xInc <= 1; xInc++) {
            for (int yInc = -1; yInc <= 1; yInc++) {
                if (withinBoard(x + xInc, y + yInc) 
                        && board[x + xInc][y + yInc].getFlagged()) {
                    surroundingFlagged++;
                }
            }
        }

        Square square = board[x][y];

        // If number of flagged squares equals number of surrounding mines
        // open all adjacent squares that are not flagged
        if (square.getOpen() && square.surroundingMines() == surroundingFlagged) {
            for (int xInc = -1; xInc <= 1; xInc++) {
                for (int yInc = -1; yInc <= 1; yInc++) {
                    if (withinBoard(x + xInc, y + yInc) 
                            && !board[x + xInc][y + yInc].getFlagged() 
                            && !this.open(x + xInc, y + yInc)) {
                        // If we hit a mine, we return immediately
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Removes all highlights from any of the Squares
     */
    public void clearHighlights() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.length; y++) {
                this.board[x][y].highlight = Highlight.NONE;
            }
        }
    }

    /**
     * Return the number of unopened squares left on the board
     */
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

    /**
     * Make move from enum classes 
     * @param move
     * @return true if the move is valid
     */
    public boolean makeMove(Move move) {
        switch (move.type) {
            case HIGHLIGHT:
                this.getSquareAt(move.x, move.y).highlight = move.highlight;
                return true;
            case FLAG:
                this.getSquareAt(move.x, move.y).toggleFlagged();
                return true;
            case OPEN:
                return this.open(move.x, move.y);
            case CHORD:
                return this.chordedOpen(move.x, move.y);
            default:
                return false;
        }
    }
}
