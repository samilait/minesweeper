
package minesweeper.model;

/**
 * Represent a single square on the board.
 * @see model.Board
 */
public class Square {
    private boolean isMine, opened = false;
    private int surrounding = 0;

    public Square() {
    }

    public Square(boolean isMine) {
        this.isMine = isMine;
    }

    public void open() {
        this.opened = true;
    }

    public boolean getOpen() {
        return this.opened;
    }

    public boolean isMine() {
        if (this.opened) {
            return isMine;
        }

        return false;
    }

    public int surrounding() {
        if (this.opened) {
            return surrounding;
        }

        return 0;
    }

    public void incrementSurrounding() {
        this.surrounding++;
    }

    @Override
    public String toString() {
        if (!this.opened) return "X";
        return this.isMine ? "*" : "" + surrounding;
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
