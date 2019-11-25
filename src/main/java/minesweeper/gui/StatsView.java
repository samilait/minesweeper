
package minesweeper.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import java.io.FileWriter;
import java.io.IOException;
import minesweeper.model.GameStats;
import javafx.animation.AnimationTimer;
import java.text.DecimalFormat;
import minesweeper.model.GameStats;

public class StatsView {
    private GameStats stats;
    private FileWriter logger;
    private Label exportStatus;
    public StatsView(GameStats stats) {
        this.stats = stats;
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
       
        Button export = new Button("Export");
        export.setOnMouseClicked(e -> logToFile());
        this.exportStatus = new Label("");
        exportStatus.setVisible(false);
        HBox hbox = new HBox(export, exportStatus);
        VBox vbox = new VBox(cumulativeStats, list, hbox);
        
        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("stylesheet.css");
        export.getStyleClass().add("menu-button");
        stage.setTitle("Game Statistics");
        stage.setScene(scene);

        stage.show();
        timer.start();

        stage.setWidth(400);
        stage.setHeight(400);
    }

    private void logToFile() {
        try {
            this.logger = new FileWriter("log.txt");
            DecimalFormat numberFormat = new DecimalFormat("0.00");
            this.logger.write("Cumulative Distance: " + numberFormat.format(stats.cumulativeEuclidianDistance) + "\n");
            this.logger.write("Time: " + numberFormat.format(stats.cumulativeTime) + " sec\n");
            for (int i=0; i<this.stats.moves.size(); i++) {
                this.logger.write(stats.moves.get(i).toString() + "\n");
            }
            this.logger.flush();
            this.logger.close();
            this.exportStatus.setVisible(true);
            this.exportStatus.setText("Statistics succesfully exported to file log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
