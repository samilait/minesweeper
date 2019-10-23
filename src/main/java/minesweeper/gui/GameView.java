package minesweeper.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
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
    
    public GameView(int x, int y, VBox vbox){
        vbox = vbox;
        sizeX = x;
        sizeY = y;
        gameGP = new GridPane();
        board = new Board(x, y);

        generator = new MinefieldGenerator();
        for (int i=0; i<x; i++){
            for (int j=0; j<y; j++){
                Button button = buildButton(30, i, j, vbox);
                gameGP.add(button, i,j);
                
            }
        }
    }
    public GridPane getView() {
        return this.gameGP;
    }

    public Button buildButton(int size, int x, int y, VBox vbox) {
        Button button = new Button();
        button.setMinWidth(size);
        button.setMaxWidth(size);
        button.setMinHeight(size);
        button.setMaxHeight(size);

        button.setOnMouseClicked((e) -> {
            if (e.getButton() == MouseButton.PRIMARY){
                if (firstclick) {
                    generator.generate(board, 10, x, y);
                    firstclick = false;
                }
                board.open(x, y);
                GridPane originalGP = this.gameGP;
                gameGP = new GridPane();
                for (int i = 0; i < sizeX; i++) {
                    for (int j = 0; j < sizeY; j++) {
                        Button newButton = buildButton(30, i, j, vbox);
                        
                        if (board.board[i][j].getOpen()) {
                            if (board.board[i][j].isMine()) {
                                newButton.setText("x");
                            } else {
                                newButton.setText("" + board.board[i][j].surroundingMines());
                            }
                        } else {
                            if (board.board[i][j].getFlagged()){
                                newButton.setText("!");
                            }
                        }

                        gameGP.add(newButton, i,j);
                    }
                }
                vbox.getChildren().remove(originalGP);
                vbox.getChildren().add(gameGP);
            } else if (e.getButton() == MouseButton.SECONDARY) {
                board.board[x][y].toggleFlagged();
                
                if (board.board[x][y].getFlagged()){
                    button.setText("!");
                } else {
                    button.setText("");
                }
                
               
            }
        });

        return button;
    }

    
    
}