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
            this.initButton("Beginner", 10, 10, 10), 
            this.initButton("Intermediate", 16, 16, 40),
            this.initButton("Expert", 30, 16, 99)
//            this.initButton("Custom", 0, 0, 0)
        };
        HBox hbox = new HBox(buttons);

        ToggleButton customBoard = new ToggleButton("Custom");
        customBoard.getStyleClass().add("menu-button");

        TextField customText = new TextField("5");
        customText.getStyleClass().add("custom-textfield");
        customText.setVisible(false);
        Label customErrorLabel = new Label("Width (min 5, max 40)");
        customErrorLabel.setVisible(false);

        TextField customText2 = new TextField("5");
        customText2.getStyleClass().add("custom-textfield");
        customText2.setVisible(false);
        Label customErrorLabel2 = new Label("Height (min 5, max 40)");
        customErrorLabel2.setVisible(false);

        TextField customText3 = new TextField("1");
        customText3.getStyleClass().add("custom-textfield");
        customText3.setVisible(false);
        Label customErrorLabel3 = new Label("Mines - (min 1)");
        customErrorLabel3.setVisible(false);

// Button useThese = new Button();

    customBoard.setOnAction(new EventHandler<ActionEvent>() {
            
        @Override
        public void handle(ActionEvent event) {
            boolean isNumeric1 = customText.getText().chars().allMatch(Character::isDigit);
            boolean isNumeric2 = customText2.getText().chars().allMatch(Character::isDigit);
            boolean isNumeric3 = customText3.getText().chars().allMatch(Character::isDigit);  

            if (isNumeric1 && isNumeric2 && isNumeric3) {
                long height = Long.parseLong(customText.getText());
                long width = Long.parseLong(customText2.getText());   
                long mines = Long.parseLong(customText3.getText());

            }   
            if (customBoard.isSelected()) {
                customText.setVisible(true); 
                customErrorLabel.setVisible(true);
                customText2.setVisible(true); 
                customErrorLabel2.setVisible(true);
                customText3.setVisible(true); 
                customErrorLabel3.setVisible(true);
            } else {
                customText.setVisible(false);
                customErrorLabel.setVisible(false); 
                customText2.setVisible(false);
                customErrorLabel2.setVisible(false);
                customText3.setVisible(false);
                customErrorLabel3.setVisible(false);
            }
        }
    });

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
        HBox customHBox0 = new HBox(customBoard);
        HBox customHBox1 = new HBox(customText, customErrorLabel);
        HBox customHBox2 = new HBox(customText2, customErrorLabel2);
        HBox customHBox3 = new HBox(customText3, customErrorLabel3);
        VBox customInput = new VBox(customHBox0, customHBox1, customHBox2, customHBox3);
        customInput.setVisible(true);
        HBox seedHBox = new HBox(seedToggle, seedText, seedErrorLabel);
        Label gameType = new Label("Select game type");
        gameType.getStyleClass().add("label-header");

        this.vbox = new VBox(gameType, hbox, new Separator(), customInput, seedHBox);
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
                if (label.equals("Custom")) {
                    System.out.println("Cistom!");
                    this.gameView = new GameView(5, 5, new VBox(newGameButton), 1);
                } else {
                    this.gameView = new GameView(height, width, new VBox(newGameButton), mines);
                }

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
