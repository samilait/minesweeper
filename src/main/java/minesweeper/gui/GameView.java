package minesweeper.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;
public class GameView {
    private GridPane gameBoard;

    public GameView(int x, int y){
        gameBoard = new GridPane();
        for (int i=0; i<x; i++){
            for (int j=0; j<y; j++){
                Button button = buildButton(30);
                gameBoard.add(button, i,j);
                
            }
        }
    }
    public GridPane getView() {
        return this.gameBoard;
    }
    public Button buildButton(int size){
        Button button = new Button();
        button.setMinWidth(size);
        button.setMaxWidth(size);
        button.setMinHeight(size);
        button.setMaxHeight(size);

        return button;
    }
}