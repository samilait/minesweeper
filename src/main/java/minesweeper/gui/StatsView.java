
package minesweeper.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;

import minesweeper.model.GameStats;

public class StatsView {
    private GameStats stats;

    public StatsView(GameStats stats) {
        this.stats = stats;

        Stage stage = new Stage();

        Label cumulativeDistance = new Label("Cumulative Distance:");

        HBox hbox = new HBox(cumulativeDistance);

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                cumulativeDistance.setText("Cumulative Distance: " + stats.cumulativeEuclidianDistance);
            }
        };

        ListView list = new ListView(stats.moves);
        VBox vbox = new VBox(hbox, list);

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("stylesheet.css");
        stage.setTitle("Game Statistics");
        stage.setScene(scene);

        stage.show();
        timer.start();

        stage.setWidth(400);
        stage.setHeight(400);
    }
}
