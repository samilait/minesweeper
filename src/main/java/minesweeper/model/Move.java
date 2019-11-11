
package minesweeper.model;

/**
 * Class representing a move on the Minesweeper board
 */
public class Move {
    public MoveType type;

    public int x;
    public int y;

    public Highlight highlight = Highlight.NONE;

    /**
     * Create a Move of a specific type at given X.Y coordinates
     * @param type Move type
     */
    public Move(MoveType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a Highlight move at given X,Y coordinate with a given Highlight
     * @param highlight Highlight colour
     */
    public Move(int x, int y, Highlight highlight) {
        this.type = MoveType.HIGHLIGHT;
        this.highlight = highlight;
        this.x = x;
        this.y = y;
    }
}
