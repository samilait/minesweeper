
package minesweeper.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class StatsView {
    private ArrayList<Long> test;

    public StatsView(ArrayList<Long> test) {
        this.test = test;

        Stage stage = new Stage();

        TextArea text = new TextArea("");

        Scene scene = new Scene(text);
        scene.getStylesheets().add("stylesheet.css");
        stage.setTitle("Game Statistics");
        stage.setScene(scene);

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                text.setText("");

                StringBuilder builder = new StringBuilder();

                for (int i = test.size() - 1; i >= 0; i--) {
                    builder.append("" + test.get(i) + "\n");
                }

                text.setText(builder.toString());
            }
        };

        timer.start();

        stage.show();
    }
}
