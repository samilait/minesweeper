
package minesweeper.model;

/**
 * Represent a single square on the board.
 * All the methods regarding the information of this square, 
 * i.e. amount of surrounding mines and whether this Square has a mine are only accessible if the square has been opened
 * @see model.Board
 */
public class Square {
    private boolean isMine;
    private boolean opened; 
    private boolean isFlagged;
    private int surroundingMines = 0; //Number of surrounding squares with mines

    public Highlight highlight = Highlight.NONE;

    /**
     * Generates a new Square with no mines
     */
    public Square() {
        isMine = false;
        opened = false;
        isFlagged = false;
    }

    /**
     * Generates a new Square as Square with mine.
     * @param isMine
     */
    public Square(boolean isMine) {
        this.isMine = isMine;
    }

    /**
     * Represents a "click" on this particular square, and sets the state to opened
     */
    public void open() {
        if (!isFlagged) {
            this.opened = true;
        }
    }

    /**
     * Whether this Square is opened
     * @return true if it is opened already, else false
     */
    public boolean getOpen() {
        return this.opened;
    }

    /**
     * Whether this Square is a Square with mine
     * @return true if this is a mine Square and it has been opened already, false otherwise
     */
    public boolean isMine() {
        assert (this.opened);

        return isMine;
    }

    public void setMine() {
        this.isMine = true;
    }

    /**
     * Whether this Square is flagged 
     * @return true if this Square has been flagged by the user
     */
    public boolean getFlagged() {
        return this.isFlagged;
    }
    
    public void toggleFlagged() {
        if (!this.opened) { 
            this.isFlagged = !this.isFlagged;
        }
    }

    /**
     * amount of surrounding Squares that have a mine.
     * @return 0 if this square has not been opened, otherwise the amount of surrounding Squares that have a mine.
     */
    public int surroundingMines() {
        assert (this.opened);

        return surroundingMines;
    }

    /**
     * Increments the amount this Square has other Squares surrounding it, with mines in them
     */
    public void incrementSurroundingMines() {
        this.surroundingMines++;
    }

    /**
     * Text representation for debugging purposes.
     * @return String
     */
    @Override
    public String toString() {
        if (!this.opened) {
            return "X";
        }

        return this.isMine ? "*" : "" + surroundingMines;
    }



/*
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Square newObj = new Square();
        newObj.isMine = this.isMine;
        newObj.surrounding = this.surrounding;
        return (Object) newObj;
    }
*/
    
}
