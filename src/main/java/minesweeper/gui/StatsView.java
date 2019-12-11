
package minesweeper.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import java.text.DecimalFormat;
import minesweeper.model.GameStats;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class StatsView {
    private GameStats stats;
    private Label exportStatus;
    private Stage stage;

    public StatsView(GameStats stats) {
        this.stats = stats;
        stage = new Stage();
        stage.getIcons().add(new Image(StatsView.class.getResourceAsStream("/Mine.png"))); 
        Label cumulativeDistance = new Label("Cumulative Distance:");
        Label cumulativeTime = new Label("Cumulative Time:");

        VBox cumulativeStats = new VBox(cumulativeDistance, cumulativeTime);
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                DecimalFormat numberFormatTime = new DecimalFormat("0.0000");
                DecimalFormat numberFormatDistance = new DecimalFormat("0.00");
                cumulativeDistance.setText("Cumulative Distance: " 
                        + numberFormatDistance.format(stats.cumulativeEuclidianDistance));
                cumulativeTime.setText("Cumulative Time: " 
                        + numberFormatTime.format(stats.cumulativeTime / Math.pow(10, 6)) + " s");
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
        cumulativeDistance.getStyleClass().add("label-stats-header");
        cumulativeTime.getStyleClass().add("label-stats-header");
        export.getStyleClass().add("menu-button");
        stage.setTitle("Game Statistics");
        stage.setScene(scene);

        stage.show();
        timer.start();

        stage.setWidth(400);
        stage.setHeight(400);
    }

    private void logToFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export log");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text files", "*.txt"),
                new ExtensionFilter("Log files", "*.log"),
                new ExtensionFilter("All files", "*.*"));

        fileChooser.setInitialFileName("log.txt");
        
        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile == null) {
            this.exportStatus.setVisible(true);
            this.exportStatus.setText("Export cancelled");
            this.exportStatus.getStyleClass().add("label-stats-export");
            this.exportStatus.getStyleClass().remove("label-success-with-border");
            this.exportStatus.getStyleClass().add("label-failure-with-border");
            return;
        }

        try {
            FileWriter fileWriter = new FileWriter(selectedFile);
            DecimalFormat numberFormat = new DecimalFormat("0.00");
            fileWriter.write("Cumulative Distance: " + numberFormat.format(stats.cumulativeEuclidianDistance) + "\n");
            fileWriter.write("Time: " + numberFormat.format(stats.cumulativeTime) + " µs\n");
            for (int i = 0; i < this.stats.moves.size(); i++) {
                fileWriter.write(stats.moves.get(i).toString() + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
            this.exportStatus.getStyleClass().add("label-stats-export");
            this.exportStatus.getStyleClass().remove("label-failure-with-border");
            this.exportStatus.getStyleClass().add("label-success-with-border");
            this.exportStatus.setVisible(true);
            this.exportStatus.setText("Statistics exported to " + selectedFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
