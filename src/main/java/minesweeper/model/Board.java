
package minesweeper.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayDeque;

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

    public void setMinesInitial(int mineCount) {
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
        } else {
            HashSet<Pair<Integer>> visited = new HashSet<>(); 
            ArrayDeque<Pair<Integer>> toVisit = new ArrayDeque<>();

            toVisit.push(new Pair(x, y));
            
            while (!toVisit.isEmpty()) {
                Pair<Integer> v = toVisit.pop();
                
                if (visited.contains(v)) {
                    continue;
                }

                visited.add(v);

                if (withinBoard(v.first, v.second)) {
                    Square square = board[v.second][v.first];
                    
                    incompleteBoard[v.second][v.first].opened = true;
                    incompleteBoard[v.second][v.first].surrounding = square.surrounding;

                    if (square.surrounding > 0) {
                        continue;
                    } else if (square.surrounding == 0 && !square.isMine){
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
        return true;
    }


    /**
     * Initializes the board with empty squares i.e. no mines.
     */
    public void initialize() {
        this.board = Arrays.stream(board).map(row -> {
            return Arrays.stream(row).map(squareElement -> new Square()).toArray(Square[]::new);
        }).toArray(Square[][]::new);

        this.incompleteBoard = Arrays.stream(incompleteBoard).map(row -> {
            return Arrays.stream(row).map(squareElement -> new Square()).toArray(Square[]::new);
        }).toArray(Square[][]::new);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Field \n");
        Arrays.stream(incompleteBoard).forEach(row -> {
            Arrays.stream(row).forEach(square -> builder.append(square));
            builder.append("\n");
        });
        return builder.toString();
    }

    public void incrementAdjacentSquares(int x, int y) {
        for(int xInc = -1; xInc <= 1; xInc++){
            for(int yInc = -1; yInc <= 1; yInc++){
                System.out.println(xInc + ":" + yInc);
                if(withinBoard(x + xInc, y + yInc) && !(xInc == 0 && yInc == 0)){
                    board[x + xInc][y + yInc].surrounding++;
                }
            }
        }
    }

    private boolean withinBoard(int x, int y){
        return x >= 0 
        && x < this.width 
        && y >= 0 
        && y < this.length;
    }

}
