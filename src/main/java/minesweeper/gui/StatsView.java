
package minesweeper.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import java.io.FileWriter;
import java.io.IOException;
import minesweeper.model.GameStats;

public class StatsView {
    private GameStats stats;
    private FileWriter logger;
    public StatsView(GameStats stats) {
        this.stats = stats;

        Stage stage = new Stage();
        VBox vb = new VBox();
        ListView list = new ListView(stats.moves);
        Button export = new Button("Export");
        export.setOnMouseClicked(e -> logToFile());
        vb.getChildren().addAll(export, list);
        Scene scene = new Scene(vb);
        scene.getStylesheets().add("stylesheet.css");
        export.getStyleClass().add("menu-button");
        stage.setTitle("Game Statistics");
        stage.setScene(scene);

        stage.show();

        stage.setWidth(400);
        stage.setHeight(400);
    }

    private void logToFile() {
        try {
            this.logger = new FileWriter("log.txt");
            for (int i=0; i<stats.moves.size(); i++) {
                this.logger.write(stats.moves.get(i).toString() + "\n");
            }
            this.logger.flush();
            this.logger.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
