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
    int cheight = 5;
    int cwidth = 5;
    int cmines = 5;
    boolean isNumeric1 = true;
    boolean isNumeric2 = true;
    boolean isNumeric3 = true;
    boolean isValidBoard = true;
    private TextField customText1;
    private TextField customText2;
    private TextField customText3;
    private final String guideText1 = "Heigth (min 3, max 40)";
    private final String guideText2 = "Width (min 3, max 40)";
    private final String guideText3 = "Mine count";
    private Label customErrorLabel1;
    private Label customErrorLabel2;
    private Label customErrorLabel3;
                    
    public StartSelectView() {
        Button[] buttons = new Button[] {
            this.initButton("Beginner", 10, 10, 10), 
            this.initButton("Intermediate", 16, 16, 40),
            this.initButton("Expert", 30, 16, 99)
        };
        HBox hbox = new HBox(buttons);

        ToggleButton customBoard = new ToggleButton("Set Custom Board");
        customBoard.getStyleClass().add("menu-button");
        
        customText1 = new TextField("5");
        customText1.getStyleClass().add("custom-textfield");
        customText1.setVisible(false);
        customErrorLabel1 = new Label(guideText1);
        customErrorLabel1.setVisible(false);

        customText2 = new TextField("5");
        customText2.getStyleClass().add("custom-textfield");
        customText2.setVisible(false);
        customErrorLabel2 = new Label(guideText2);
        customErrorLabel2.setVisible(false);

        customText3 = new TextField("5");
        customText3.getStyleClass().add("custom-textfield");
        customText3.setVisible(false);
        customErrorLabel3 = new Label(guideText3);
        customErrorLabel3.setVisible(false);

        Button acceptButton = acceptCustomButton("Use Custom Board");
        acceptButton.setVisible(false); 
        
        customBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                public void handle(ActionEvent event) {
                isNumeric1 = customText1.getText().chars().allMatch(Character::isDigit);
                isNumeric2 = customText2.getText().chars().allMatch(Character::isDigit);
                isNumeric3 = customText3.getText().chars().allMatch(Character::isDigit);  

                if (isNumeric1 && isNumeric2 && isNumeric3) {
                    cheight = Integer.parseInt(customText1.getText());
                    cwidth = Integer.parseInt(customText2.getText());   
                    cmines = Integer.parseInt(customText3.getText());
                }   
                if (customBoard.isSelected()) {
                    customText1.setVisible(true);
                    customErrorLabel1.setVisible(true);
                    customText2.setVisible(true);    
                    customErrorLabel2.setVisible(true);
                    customText3.setVisible(true);  
                    customErrorLabel3.setVisible(true); 
                    acceptButton.setVisible(true);            
                } else {
                    customText1.setVisible(false);
                    customErrorLabel1.setVisible(false); 
                    customText2.setVisible(false);
                    customErrorLabel2.setVisible(false);
                    customText3.setVisible(false);          
                    customErrorLabel3.setVisible(false);
                    acceptButton.setVisible(false);    
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

        // Create event handler for toggling the pre-set seed on and off
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

        checkCustomTextField(1);
        checkCustomTextField(2);
        checkCustomTextField(3);
        
        HBox customHBox0 = new HBox(customBoard);
        HBox customHBox1 = new HBox(customText1, customErrorLabel1);
        HBox customHBox2 = new HBox(customText2, customErrorLabel2);
        HBox customHBox3 = new HBox(customText3, customErrorLabel3);
        HBox acceptHBox = new HBox(acceptButton);
        VBox customInput = new VBox(customHBox0, customHBox1, customHBox2, customHBox3, acceptHBox);
        customInput.setVisible(true);

        HBox seedHBox = new HBox(seedToggle, seedText, seedErrorLabel);
        Label gameType = new Label("Select game type");
        gameType.getStyleClass().add("label-header");
        this.vbox = new VBox(gameType, hbox, new Separator(), seedHBox, customInput);
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
    
    private Button acceptCustomButton(String label) {
        Button button = new Button(label);
        button.getStyleClass().add("menu-button");
        button.setOnMouseClicked(e -> {
            if (isValidBoard) {
                this.vbox.setVisible(false);
                Button newGameButton = new Button("New Game");
                newGameButton.setOnMouseClicked(ev -> {
                    this.stackPane.getChildren().remove(1);
                    this.vbox.setVisible(true);
                });
                if (this.seedSet) {
                    this.gameView = new GameView(cheight, cwidth, new VBox(newGameButton), cmines, this.seed);
                } else {
                    this.gameView = new GameView(cheight, cwidth, new VBox(newGameButton), cmines);
                }
                this.stackPane.getChildren().add(gameView.getView());
            }
        });
        return button;
    }
    
    /**
     * Return the ObservableList of the root node of this class, used for resizing
     * purposes in the App class
     * @return 
     */
    public ObservableList<Node> rootChildren() {
        return this.stackPane.getChildren();
    }

    /**
     * Returns the underlying StackPane on which the rest of the elements are added.
     * @return 
     */
    public StackPane get() {
        return this.stackPane;
    }

    /**
    * Checks that custom sized minesweeper is a reasonably playable one.
    */
    private void checkCustomTextField(int fieldNumber) {

        switch (fieldNumber) {
            case 1 :
                customText1.textProperty().addListener((observable, oldValue, newValue) -> {
                    isNumeric1 = customText1.getText().chars().allMatch(Character::isDigit);
                    if (!isNumeric1 || customText1.getText().isEmpty()) {
                        customErrorLabel1.setText("!! Height must be an integer!");
                    } else {
                        cheight = Integer.parseInt(customText1.getText());
                        if (cheight > 40) {
                            customErrorLabel1.setText("!! Width max is 40!");
                            isValidBoard = false;
                        } else if (cheight < 3) {
                            customErrorLabel1.setText("!! Width min is 3!");
                            isValidBoard = false;
                        } else {
                            cmines = Integer.parseInt(customText3.getText());
                            if (cmines > ((cheight * cwidth) - 9)) {
                                isValidBoard = false;
                                customErrorLabel3.setText("!! Too many mines!");
                            } else {
                                customErrorLabel1.setText(guideText1);
                                isValidBoard = true;
                                customErrorLabel3.setText(guideText3);
                            }
                        }
                    }
                });

            case 2 :
                customText2.textProperty().addListener((observable, oldValue, newValue) -> {
                    isNumeric2 = customText2.getText().chars().allMatch(Character::isDigit);
                    if (!isNumeric2 || customText2.getText().isEmpty()) {
                        customErrorLabel2.setText("!! Width must be an integer!");
                    } else {
                        cwidth = Integer.parseInt(customText2.getText());
                        if (cwidth > 40) {
                            isValidBoard = false;
                            customErrorLabel2.setText("!! Width max is 40!");
                        } else if (cwidth < 3) {
                            isValidBoard = false;
                            customErrorLabel2.setText("!! Width min is 3!");
                        } else {
                            cmines = Integer.parseInt(customText3.getText());
                            if (cmines > ((cheight * cwidth) - 9)) {
                                isValidBoard = false;
                                customErrorLabel3.setText("!! Too many mines!");
                            } else {
                                isValidBoard = true;  
                                customErrorLabel2.setText(guideText2);
                                customErrorLabel3.setText(guideText3);
                            }
                        }
                    }
                });

            case 3 :
                customText3.textProperty().addListener((observable, oldValue, newValue) -> {
                    isNumeric3 = customText3.getText().chars().allMatch(Character::isDigit);
                    if (!isNumeric3 || customText3.getText().isEmpty()) {
                        customErrorLabel3.setText("!! Mines must be an integer!");
                    } else {
                        cmines = Integer.parseInt(customText3.getText());
                        if (cmines > ((cheight * cwidth) - 9)) {
                            isValidBoard = false;
                            customErrorLabel3.setText("!! Too many mines!");
                        } else {
                            isValidBoard = true;
                            customErrorLabel3.setText(guideText3);
                        }
                    }
                });          
                    
            default :
                break;
        }

    }
}
