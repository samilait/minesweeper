package minesweeper.gui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StartSelectView {
    private HBox hbox;
    private VBox vbox;
    private Button[] buttons;
    private GameView gameView;
    private StackPane stackPane;

    
    public StartSelectView() {
        this.buttons = new Button[] {
            this.initButton("Easy ", 9, 9, 10), 
            this.initButton("Intermediate", 16, 16, 40),
            this.initButton("Hard", 16, 30, 99) };
        this.hbox = new HBox(buttons);
        this.vbox = new VBox(new Label("Select game type"), hbox);
        this.stackPane = new StackPane(this.vbox);
    }
    /**
     * Button that initiates a new game with the difficulty (based on size of the board)
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
            this.gameView = new GameView(height, width, 
                            new VBox(new Label("This has to be deleted"), 
                            newGameButton), mines);
            this.stackPane.getChildren().add(gameView.getView());
        });
        button.setWrapText(false);
        return button;
    }

    /**
     * Return the ObservableList of the root node of this class, used for resizing purposes in the App class
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
