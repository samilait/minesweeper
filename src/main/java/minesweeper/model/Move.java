
package minesweeper.model;

public class Move {
    public MoveType type;

    public int x;
    public int y;

    public Highlight highlight = Highlight.NONE;

    public Move(MoveType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public Move(int x, int y, Highlight highlight) {
        this.type = MoveType.HIGHLIGHT;
        this.highlight = highlight;
        this.x = x;
        this.y = y;
    }
}
