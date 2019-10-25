package minesweeper.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseButton;
import minesweeper.model.*;
import minesweeper.generator.MinefieldGenerator;

public class GameView {
    private GridPane gameGP;
    private Board board;
    private VBox vbox;
    private int sizeX, sizeY;
    private MinefieldGenerator generator;
    private boolean firstclick = true;
    
    public GameView(int x, int y, VBox vbox) {
        this.vbox = vbox;
        sizeX = x;
        sizeY = y;
        gameGP = new GridPane();
        board = new Board(x, y);

        generator = new MinefieldGenerator();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Button button = buildButton(30, i, j);
                gameGP.add(button, i, j);
                
            }
        }
    }
    public GridPane getView() {
        return this.gameGP;
    }

    public Button buildButton(int size, int x, int y) {
        Button button = new Button();
        button.setMinWidth(size);
        button.setMaxWidth(size);
        button.setMinHeight(size);
        button.setMaxHeight(size);

        button.setOnMouseClicked((e) -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (firstclick) {
                    generator.generate(board, 10, x, y);
                    firstclick = false;
                }
                
                if (!board.open(x,y)) {
                    button.setText("☠");
                    gameOver();
                    return; 
                } else if (board.gameWon) {
                    gameWon();
                } else {
                    updateGameGP(false);
                }
                this.vbox.getChildren().remove(0);
                this.vbox.getChildren().add(new Label(""+board.getUnopenedSquaresCount()));

            } else if (e.getButton() == MouseButton.SECONDARY) {
                if (!board.board[x][y].getOpen()) {
                    board.board[x][y].toggleFlagged();
                    if (board.board[x][y].getFlagged()) {
                        button.setText("⚐");
                    } else {
                        button.setText("");
                    }
                }
               
            }
        });

        return button;
    }
    public void gameWon() {
        this.vbox.getChildren().remove(0);
        this.vbox.getChildren().add(new Label("You won. Congratulations!"));

        updateGameGP(true);
    }
    public void gameOver() {
        this.vbox.getChildren().remove(0);
        this.vbox.getChildren().add(new Label("You lost. Get rekt"));
        
        updateGameGP(true);
        
    }
    
    public void updateGameGP(Boolean end) {
        GridPane originalGP = this.gameGP;
        this.gameGP = new GridPane();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                Button newButton = new Button();
                if (end) {
                    newButton.setMinHeight(30);
                    newButton.setMaxHeight(30);
                    newButton.setMinWidth(30);
                    newButton.setMaxWidth(30);
                } else {
                    newButton = buildButton(30, i, j);
                }
                
                
                if (board.board[i][j].getOpen()) {
                    if (board.board[i][j].isMine()) {
                        newButton.setText("☠");

                    } else {
                        newButton.setText("" + board.board[i][j].surroundingMines());

                        if (board.board[i][j].surroundingMines() == 0) {
                            newButton.setText("✹");
                        }
                    }
                } else {
                    if (board.board[i][j].getFlagged()) {
                        newButton.setText("⚐");
                    }
                }

                gameGP.add(newButton, i, j);
            }
        }
        this.vbox.getChildren().remove(originalGP);
        this.vbox.getChildren().add(gameGP);
    }
    
}
