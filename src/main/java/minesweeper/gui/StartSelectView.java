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

public class StartSelectView {
    private VBox vbox;
    private GameView gameView;
    private StackPane stackPane;
    private boolean seedSet;
    private long seed;

    public StartSelectView() {       
        Button[] buttons = new Button[] {
            this.initButton("Easy ", 10, 10, 10), 
            this.initButton("Intermediate", 16, 16, 40),
            this.initButton("Hard", 30, 16, 99)};
        HBox hbox = new HBox(buttons);
        
        ToggleButton seedToggle = new ToggleButton("Use a pre-set seed");
        seedToggle.getStyleClass().add("menu-button");
        TextField seedText = new TextField("1234");
        seedText.getStyleClass().add("custom-textfield");
        seedText.setVisible(false);
        Label seedErrorLabel = new Label("");
        seedErrorLabel.setVisible(false);
       
        // Create event handler for toggling the pre-set seed 
        // on and off
        seedToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean isNumeric = seedText.getText().chars().allMatch(Character::isDigit);
                if (isNumeric) {
                    seed = Long.parseLong(seedText.getText());
                }
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

        // Check on each keypress if the text in the seed TextField is numeric
        seedText.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isNumeric = seedText.getText().chars().allMatch(Character::isDigit);
            if (!isNumeric || seedText.getText().isEmpty()) {
                seedErrorLabel.setText("Seed must be an integer value!");
            } else {
                seedErrorLabel.setText("");
                seed = Long.parseLong(newValue);
            }
        }); 
      

        HBox seedHBox = new HBox(seedToggle, seedText, seedErrorLabel);
        Label gameType = new Label("Select game type");
        gameType.getStyleClass().add("label-header");

        this.vbox = new VBox(gameType, hbox, new Separator(), seedHBox);
        this.stackPane = new StackPane(this.vbox);
    }

    /**
     * Button that initiates a new game with the difficulty (based on size of the
     * board)
     */
    private Button initButton(String label, int height, int width, int mines) {
        Button button = new Button(label);
        button.getStyleClass().add("menu-button");
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
