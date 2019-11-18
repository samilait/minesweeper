package minesweeper.gui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Separator;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;

public class StartSelectView {
    private VBox vbox;
    private GameView gameView;
    private StackPane stackPane;
    private boolean seedSet;
    private long seed;

    public StartSelectView() {       
        Button[] buttons = new Button[] {
            this.initButton("Easy ", 9, 9, 10), 
            this.initButton("Intermediate", 16, 16, 40),
            this.initButton("Hard", 30, 16, 99)};
        HBox hbox = new HBox(buttons);
        
        ToggleButton seedToggle = new ToggleButton("Use a pre-set seed");
        TextField seedText = new TextField("...");
        seedText.setVisible(false);
        Label seedErrorLabel = new Label("");
        seedErrorLabel.setVisible(false);

        seedToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (seedToggle.isSelected()) {
                    seedText.setVisible(true); 
                    seedErrorLabel.setVisible(true); 
                    seedSet = true;
                } else {
                    seedText.setVisible(false);
                    seedErrorLabel.setVisible(false); 
                    seedSet = false;
                }
            }
        });

        seedText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                boolean isNumeric = seedText.getText().chars().allMatch( Character::isDigit );

                if (!isNumeric) {
                    seedErrorLabel.setText("Seed must be an integer value!");
                } else {
                    seedErrorLabel.setText("");
                    seed = Long.parseLong(seedText.getText());
                }
            }
        });

        HBox seedHBox = new HBox(seedToggle, seedText, seedErrorLabel);

        this.vbox = new VBox(new Label("Select game type"), hbox, new Separator(), seedHBox);
        this.stackPane = new StackPane(this.vbox);
    }

    /**
     * Button that initiates a new game with the difficulty (based on size of the
     * board)
     */
    private Button initButton(String label, int height, int width, int mines) {
        Button button = new Button(label);
        button.setOnMouseClicked(e -> {
            this.vbox.setVisible(false);
            Button newGameButton = new Button("New Game");
            newGameButton.setOnMouseClicked(ev -> {
                this.stackPane.getChildren().remove(1);
                this.vbox.setVisible(true);
            });

            if (this.seedSet) {
                this.gameView = new GameView(height, width, new VBox(newGameButton), mines, this.seed);
            } else {
                this.gameView = new GameView(height, width, new VBox(newGameButton), mines);
            }

            this.stackPane.getChildren().add(gameView.getView());
        });
        button.setWrapText(false);
        return button;
    }

    /**
     * Return the ObservableList of the root node of this class, used for resizing
     * purposes in the App class
     */
    public ObservableList<Node> rootChildren() {
        return this.stackPane.getChildren();
    }

    /**
     * Returns the underlying StackPane on which the rest of the elements are added.
     */
    public StackPane get() {
        return this.stackPane;
    }
}
