
package minesweeper.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;


public class StatsView {
    private ObservableList<Long> test;

    public StatsView(ObservableList<Long> test) {
        this.test = test;

        Stage stage = new Stage();

        ListView list = new ListView(test);

        Scene scene = new Scene(list);
        scene.getStylesheets().add("stylesheet.css");
        stage.setTitle("Game Statistics");
        stage.setScene(scene);

        stage.show();
    }
}
