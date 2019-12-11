
package minesweeper.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

import minesweeper.generator.MinefieldGenerator;

/**
 * Class representing the game board
 */
public class Board {


    public boolean gameLost = false;
    public boolean gameWon = false;
    /**
     * The squares of this board in 2d array, with dimensions corresponding to this boards width/height.
     */
    public Square[][] board;
    public int totalMines;
    public final int width;
    public final int height;
    
    private MinefieldGenerator generator;
    /**
     * True if no moves has been made to this board, otherwise false
     */
    public boolean firstMove = true;

    private ArrayList<Square> mineSquares = new ArrayList<>();
    private int unflaggedMines;
    private Function<Square, Void> observerCallback;
    private boolean isObserved = false;
    private HashSet<Square> openSquares;

    /**
     * Create a new Board using a specified MinefieldGenerator and given size and mine parameters.
     *
     * @param generator A MinefieldGenerator object that has possibly been constructed 
     * with specific seeds or other configurations
     * @param width The width of the Board
     * @param height The height of the Board
     * @param totalMines The maximum number of mines on the Board
     */
    public Board(MinefieldGenerator generator, int width, int height, int totalMines) {
        this.width = width;
        this.height = height;
        this.board = new Square[width][height];
        this.generator = generator;
        this.totalMines = totalMines;
        this.unflaggedMines = totalMines;
        this.openSquares = new HashSet<>();
        this.initialize();
    }

    /**
     * Sets an observer callback for communicating Board state changes
     * This is used by the GUI to recognize which squares need to be redrawn
     * @param callbackFunction The function to be called upon a state change
     */
    public void setChangeObserver(Function<Square, Void> callbackFunction) {
        this.observerCallback = callbackFunction;
        this.isObserved = true;
    }

    /**
     * Sets the number of total mines, used by MinefieldGenerator when generating a
     * new board. This value can be higher than the number of squares on the Board,
     * but the MinefieldGenerator will ensure that those extra mines are ignored
     *
     * @param totalMines The maximum number of mines on the Board
     */
    public void setTotalMines(int totalMines) {
        this.totalMines = totalMines;
    }

    /**
     * Set a Square at a given X, Y coordinate
     * @param square Square object to be placed onto the board
     * @param x X coordinate of the Square
     * @param y Y coordinate of the Square
     */
    public void addSquare(Square square, int x, int y) {
        square.setX(x);
        square.setY(y);

        this.board[x][y] = square;
    }

    /**
     * Get a Square at a given X, Y coordinate
     *
     * @param x X coordinate of the Square
     * @param y Y coordinate of the Square
     *
     * @return Square object in the given coordinates
     */
    public Square getSquareAt(int x, int y) {
        return this.board[x][y];
    }

    /**
     * Get the remaining open squares
     *
     * @return HashSet containing the remaining open Squares
     */
    public HashSet<Square> getOpenSquares() {
        return this.openSquares;
    }

    /**
     * Opens a square in the given X, Y coordinate and all surrounding squares that
     * are not mines
     *
     * @param x X coordinate of the Square
     * @param y Y coordinate of the Square
     * @return True if the move doesn't open a mine, false otherwise
     */
    private boolean open(int x, int y) {
        if (this.firstMove) {
            generator.generate(this, totalMines, x, y);
            this.firstMove = false;
        }

        if (this.board[x][y].isFlagged()) {
            return true;
        }

        this.board[x][y].open();
        this.openSquares.add(board[x][y]);

        if (board[x][y].isMine()) {
            this.gameLost = true;
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
     *<ol>
     *    <li>Open current square</li>
     *    <li>If current square has surrounding mines, ignore all surrounding tiles</li>
     *    <li>Else open all surrounding squares</li>
     *</ol>
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void runBFS(int x, int y) {

        HashSet<Pair<Integer>> visited = new HashSet<>();
        ArrayDeque<Pair<Integer>> toVisit = new ArrayDeque<>();

        toVisit.push(new Pair<Integer>(x, y));

        while (!toVisit.isEmpty()) {
            // Get a coordinate from the front of the queue
            Pair<Integer> v = toVisit.remove();

            // Have we visited this square before?
            if (visited.contains(v)) {

                // If yes, skip it
                continue;
            }

            visited.add(v);

            if (withinBoard(v.first, v.second)) {
                Square square = board[v.first][v.second];

                // We don't process flagged squares
                if (square.isFlagged()) {
                    continue;
                }

                square.open();
                this.openSquares.add(square);
                if (this.isObserved) {
                    this.observerCallback.apply(square);
                }

                // If current square has surrounding mines, ignore surrounding squares
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
                if (withinBoard(x + xInc, y + yInc) && board[x + xInc][y + yInc].isFlagged()) {
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
                    if (withinBoard(x + xInc, y + yInc) && !board[x + xInc][y + yInc].isFlagged()
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
     * Removes all highlights from all of the Squares
     */
    public void clearHighlights() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.board[x][y].highlight = Highlight.NONE;
            }
        }
    }

    /**
     * Get the number of unopened squares left on the board
     * @return Number of unopened squares
     */
    public int getUnopenedSquaresCount() {
        int unopenedSquares = 0;
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
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
            for (int y = 0; y < this.height; y++) {
                this.board[x][y] = new Square(x, y);
            }
        }
    }

    /**
     * Increments the mine counter for each square that surrounds the given square
     *
     * @param x X coordinate
     * @param y Y coordinate
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
     *
     * @param x X coordinate
     * @param y Y coordinate
     *
     * @return True if the coordinates fall within the board, false otherwise
     */
    public boolean withinBoard(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }

    /**
     * Update the board based on a given Move
     * 
     * @param move Move object representing an action taken
     * @return True if the move didn't open a mine, false otherwise
     */
    public boolean makeMove(Move move) {
        switch (move.type) {
            case HIGHLIGHT:
                this.getSquareAt(move.x, move.y).highlight = move.highlight;
                return true;
            case FLAG:
                this.getSquareAt(move.x, move.y).toggleFlagged();
                this.unflaggedMines += this.board[move.x][move.y].isFlagged() ? -1 : 1;
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
     * Adds square to the private mine list
     *
     * This is used by the GUI to display all the mines upon failure
     *
     * @param square Square to be added to the list
     */
    public void addMineSquareToList(Square square) {
        this.mineSquares.add(square);
    }

    /**
     * Opens all squares with mines
     *
     * Used by the GUI to display all mines to the player when player loses
     */
    public void openAllMines() {
        if (this.isObserved) {
            this.mineSquares.stream().forEach(square -> {
                if (!square.isFlagged()) {
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
