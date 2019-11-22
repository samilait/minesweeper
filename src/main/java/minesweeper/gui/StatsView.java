
package minesweeper.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;

import minesweeper.model.GameStats;

public class StatsView {
    private GameStats stats;

    public StatsView(GameStats stats) {
        this.stats = stats;

        Stage stage = new Stage();

        ListView list = new ListView(stats.moves);

        Scene scene = new Scene(list);
        scene.getStylesheets().add("stylesheet.css");
        stage.setTitle("Game Statistics");
        stage.setScene(scene);

        stage.show();

        stage.setWidth(400);
        stage.setHeight(400);
    }
}
