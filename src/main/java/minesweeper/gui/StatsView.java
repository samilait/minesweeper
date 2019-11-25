
package minesweeper.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.animation.AnimationTimer;

import java.text.DecimalFormat;

import minesweeper.model.GameStats;

public class StatsView {

    public StatsView(GameStats stats) {
        Stage stage = new Stage();

        Label cumulativeDistance = new Label("Cumulative Distance:");
        Label cumulativeTime = new Label("Cumulative Time:");

        VBox cumulativeStats = new VBox(cumulativeDistance, cumulativeTime);

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                DecimalFormat numberFormat = new DecimalFormat("0.00");
                cumulativeDistance.setText("Cumulative Distance: " + numberFormat.format(stats.cumulativeEuclidianDistance));
                cumulativeTime.setText("Time: " + numberFormat.format(stats.cumulativeTime) + " sec");
            }
        };

        ListView list = new ListView(stats.moves);
        VBox vbox = new VBox(cumulativeStats, list);

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
