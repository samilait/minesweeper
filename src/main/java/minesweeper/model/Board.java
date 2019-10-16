
package minesweeper.model;


public class Board {

    Square[][] board;
    Square[][] incompleteBoard;

    public Board() {
        this.board = new Square[10][10];
        this.incompleteBoard = new Square[10][10];
    }
    
    
    
}
