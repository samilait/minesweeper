
package minesweeper.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

import minesweeper.generator.MinefieldGenerator;

public class Board {

    public boolean gameEnd = false;
    public boolean gameWon = false;
    public Square[][] board;
    public int totalMines;
    public final int width;
    public final int length;
    
    private MinefieldGenerator generator;
    public boolean firstMove = true;

    private ArrayList<Square> mineSquares = new ArrayList<>();
    private int unflaggedMines;
    private Function<Square, Void> observerCallback;
    private boolean isObserved = false;
    private HashSet<Square> openSquares;

    public Board(MinefieldGenerator generator, int width, int length, int totalMines) {
        this.width = width;
        this.length = length;
        this.board = new Square[width][length];
        this.generator = generator;
        this.totalMines = totalMines;
        this.unflaggedMines = totalMines;
        this.openSquares = new HashSet<>();
        this.initialize();
    }

    /**
     * Sets an observer callback for communicating Board state changes
     * @param callbackFunction The function to be called upon a state change
     */
    public void setChangeObserver(Function<Square, Void> callbackFunction) {
        this.observerCallback = callbackFunction;
        this.isObserved = true;
    }

    /**
     * Sets the number of total mines, used by MinefieldGenerator when generating a
     * new board
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
     * Opens a square in the given X, Y coordinate and all surrounding squares that
     * are not mines
     */
    private boolean open(int x, int y) {
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
            runBFS(x, y);
        }
        if (getUnopenedSquaresCount() == this.totalMines) {
            this.gameWon = true;
        }
        return true;
    }

    /**
     * Run a BFS-style square opening algorithm.
     * <p>
     *
     * BFS implementation for opening squares:
     *
     * 1. Open current square
     * 2. If current square has surrounding mines, ignore
     *    surrounding tiles
     * 3. Else open all surrounding squares
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void runBFS(int x, int y) {

        HashSet<Pair<Integer>> visited = new HashSet<>();
        ArrayDeque<Pair<Integer>> toVisit = new ArrayDeque<>();

        toVisit.push(new Pair<Integer>(x, y));

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
                if (this.isObserved) {
                    this.observerCallback.apply(square);
                }

                // If current square has surrounding mines, ignore surrounding squares
//                if ((square.surroundingMines() == 0) && (!square.getFlagged())) {
                // KO Note: jos square.getFlagged() niin on jo tehty continue!
                if (square.surroundingMines() == 0) {
                    // No surrounding mines, all surrounding squares can be opened
                    for (int xInc = -1; xInc <= 1; xInc++) {
                        for (int yInc = -1; yInc <= 1; yInc++) {
                            if (withinBoard(v.first + xInc, v.second + yInc) 
                                && !board[v.first + xInc][v.second + yInc].isOpened()) {
                                toVisit.push(new Pair<Integer>(v.first + xInc, v.second + yInc));
                            }
                        }
                    }
                }
 
            }
        }
    } 
    
    /**
     * Execute a chorded open move on a previously opened square If the number of
     * adjacent flagged squares equals to the number of surrounding mines of a given
     * square, opens all adjacent, unflagged squares.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return True if no mines were hit, false otherwise
     */
    private boolean chordedOpen(int x, int y) {
        int surroundingFlagged = 0;

        for (int xInc = -1; xInc <= 1; xInc++) {
            for (int yInc = -1; yInc <= 1; yInc++) {
                if (withinBoard(x + xInc, y + yInc) && board[x + xInc][y + yInc].getFlagged()) {
                    surroundingFlagged++;
                }
            }
        }

        Square square = board[x][y];

        // If number of flagged squares equals number of surrounding mines
        // open all adjacent squares that are not flagged
        if (square.isOpened() && square.surroundingMines() == surroundingFlagged) {
            for (int xInc = -1; xInc <= 1; xInc++) {
                for (int yInc = -1; yInc <= 1; yInc++) {
                    if (withinBoard(x + xInc, y + yInc) && !board[x + xInc][y + yInc].getFlagged()
                            && !this.open(x + xInc, y + yInc)) {
                        // If we hit a mine, we return immediately
                        if (this.isObserved) { 
                            this.observerCallback.apply(this.board[x + xInc][y + yInc]);
                        }
                        return false;
                    }
                    if (this.isObserved && withinBoard(x + xInc, y + yInc)) { 
                        this.observerCallback.apply(this.board[x + xInc][y + yInc]);
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
                if (!board[x][y].isOpened()) {
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
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.length; y++) {
                this.board[x][y] = new Square(x, y);
            }
        }
        /*
         * this.board = Arrays.stream(board).map(row -> { return
         * Arrays.stream(row).map(squareElement -> new Square()).toArray(Square[]::new);
         * }).toArray(Square[][]::new);
         */
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
     * 
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
                this.unflaggedMines += this.board[move.x][move.y].getFlagged() ? -1 : 1;
                return true;
            case OPEN:
                return this.open(move.x, move.y);
            case CHORD:
                return this.chordedOpen(move.x, move.y);
            default:
                return false;
        }
    }

    /**
     * Adds square with mine to list, for GUI purposes only
     * @param square square to add
     */
    public void addMineSquareToList(Square square) {
        this.mineSquares.add(square);
    }

    /**
     * Opens all squares with mines, for GUI end game purposes
     */
    public void openAllMines() {
        if (this.isObserved) {
            this.mineSquares.stream().forEach(square -> {
                if (!square.getFlagged()) {
                    square.open();
                    this.observerCallback.apply(square);
                }
            });
        }
    }

    /**
     * Returns a number of currently unflagged mines
     * The value is based on the assumption that all flags
     * have been placed correctly
     *
     * @return Number of unflagged mines on the board
     */
    public int getUnflaggedMines() {
        return this.unflaggedMines;
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
