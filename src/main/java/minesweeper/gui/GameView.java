package minesweeper.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import minesweeper.model.Board;
import minesweeper.generator.MinefieldGenerator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import minesweeper.model.Highlight;
import minesweeper.bot.TestBot;
import minesweeper.bot.Bot;
import minesweeper.bot.BotExecutor;
import minesweeper.model.Move;

public class GameView {
    private GridPane gameGP;
    private Board board;
    private Board botBoard;
    private VBox vbox;
    private int sizeX;
    private int sizeY;
    private int remainingUnflaggedMines;
    private Bot bot;
    private Label endLabel = new Label("Mines: ");
    private Slider animationSlider;
    private Button botButton;
    public final long[] currentNanotime = new long[1];

    public GameView(int x, int y, VBox vbox, int mines) {
        MinefieldGenerator generator;
        Button botGame;
        this.vbox = vbox;
        sizeX = x;
        sizeY = y;
        remainingUnflaggedMines = mines;
        this.endLabel.setText(this.endLabel.getText() + remainingUnflaggedMines);

        this.bot = new TestBot();
        
        botButton = new Button("Help (bot)");
        botButton.setOnMouseClicked(e -> {
            this.board.clearHighlights();

            Move move = this.bot.makeMove(board);
            board.makeMove(move);

            if (!board.gameEnd) {
                this.updateGameGP(false);
            } else if (board.gameWon) {
                this.gameWon();
            } else {
                this.gameOver();
            }
        });
        botGame = new Button("Bot Game");
        botGame.setOnMouseClicked(e -> {
            this.botGameLoop();
        });
       
        Button newGame = new Button();
        for (Node n : vbox.getChildren()) {
            if (n instanceof Button) {
                newGame = (Button) n;
            }
        }
        HBox hb = new HBox();
        hb.getChildren().add(newGame);
        hb.getChildren().add(botButton);
        hb.getChildren().add(botGame);
        
        this.vbox.getChildren().add(hb);
      
        this.vbox.getChildren().add(new Label("Bot game animation speed: "));
        initializeSlider();
        this.vbox.getChildren().add(this.animationSlider);
        this.vbox.getChildren().add(this.endLabel);

        gameGP = new GridPane();
        gameGP.setMaxWidth(sizeX * 30);
        gameGP.getStyleClass().add("custom-gridpane");
        vbox.getChildren().add(gameGP);
        long seed = System.nanoTime() / 2L;
        System.out.println("" + seed);
        generator = new MinefieldGenerator(seed);
        board = new Board(generator, x, y, mines);
        botBoard = new Board(generator, x, y, mines);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Button button = buildButton(30, i, j);
                gameGP.add(button, i, j);
            }
        }
    }

    /**
     * Returns a VBox with the view including a GridPane with the gamestate
     */
    public VBox getView() {
        return this.vbox;
    }

    /**
     * Builds a new button with the required functionality for flagging and opening
     * squares
     */
    public Button buildButton(int size, int x, int y) {
        Button button = new Button();
        button.setMinWidth(size);
        button.setMaxWidth(size);
        button.setMinHeight(size);
        button.setMaxHeight(size);
        button.setOnMouseClicked((e) -> {
            if ((e.getButton() == MouseButton.PRIMARY && e.isSecondaryButtonDown()
                    || (e.getButton() == MouseButton.SECONDARY && e.isPrimaryButtonDown()))
                    && board.board[x][y].isOpened()) {
                if (!board.chordedOpen(x, y)) {
                    gameOver();
                } else {
                    updateGameGP(false);
                }
            } else if (e.getButton() == MouseButton.PRIMARY) {
                // If the first click of the game, generate a new board
                if (!board.open(x, y)) {
                    button.getStyleClass().add("mine");
                    gameOver();
                    return;
                } else if (board.gameWon) {
                    gameWon();
                } else {
                    updateGameGP(false);
                }

            } else if (e.getButton() == MouseButton.SECONDARY) {
                // If a right click, flag or unflag a Square
                if (!board.board[x][y].isOpened()) {
                    board.board[x][y].toggleFlagged();
                    if (board.board[x][y].getFlagged()) {
                        button.getStyleClass().add("flagged-button");
                        this.remainingUnflaggedMines--;
                        this.endLabel.setText("Mines: " + this.remainingUnflaggedMines);
                    } else {
                        button.getStyleClass().remove("flagged-button");
                        this.remainingUnflaggedMines++;
                        this.endLabel.setText("Mines: " + this.remainingUnflaggedMines);
                    }
                }

            }
        });

        return button;
    }

    /**
     * Updates the view to show that the game has been won.
     */
    public void gameWon() {
        this.endLabel.setText("You won. Congratulations!");
        updateGameGP(true);
    }

    /**
     * Updates the view to show that the game has been lost.
     */
    public void gameOver() {
        this.endLabel.setText("You lost. Get rekt");
        updateGameGP(true);
    }

    /**
     * Updates the view with the current boardstate. If the game is over buttons are
     * disabled.
     */
    public void updateGameGP(Boolean end) {
        GridPane originalGP = this.gameGP;
        this.gameGP = new GridPane();
        gameGP.setMaxWidth(sizeX * 30);
        gameGP.getStyleClass().add("custom-gridpane");
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                // Builds new buttons for each Square on the board.
                Button newButton = new Button();
                if (end) {
                    // Disabled buttons if the game is over.
                    newButton.setMinHeight(30);
                    newButton.setMaxHeight(30);
                    newButton.setMinWidth(30);
                    newButton.setMaxWidth(30);
                    botButton.setDisable(true);
                } else {
                    // Functional buttons when game is underway.
                    newButton = buildButton(30, i, j);
                }

                // Updates the button in the current location with the correct
                // visual representation of the Square.
                switch (board.board[i][j].highlight) {
                    case RED:
                        newButton.getStyleClass().add("red-highlight");
                        break;
                    case GREEN:
                        newButton.getStyleClass().add("green-highlight");
                        break;
                    case BLACK:
                        newButton.getStyleClass().add("black-highlight");
                        break;

                    default:
                        break;
                }

                if (board.board[i][j].isOpened()) {

                    newButton.getStyleClass().add("opened-button");
                    if (board.board[i][j].isMine()) {
                        newButton.getStyleClass().add("mine");

                    } else if (board.board[i][j].surroundingMines() != 0) {
                        newButton.setText("" + board.board[i][j].surroundingMines());
                        setOpenedButtonColor(newButton, board.board[i][j].surroundingMines());
                    }
                } else {
                    if (board.board[i][j].getFlagged()) {
                        newButton.getStyleClass().add("flagged-button");
                    }
                }

                gameGP.add(newButton, i, j);
            }
        }
        this.vbox.getChildren().remove(originalGP);
        this.vbox.getChildren().add(gameGP);
    }

    /**
     * This method is called when user presses the bot game button.
     */
    private void botGameLoop() {
        // Called as if game is over to disable human input
        updateGameGP(true);
        LinkedBlockingQueue<Move> moveQueue = new LinkedBlockingQueue<>();
        currentNanotime[0] = System.nanoTime();
        // This timer updates the gui board with the moves that bot makes
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // Time that has passed since last update
                long deltaTime = TimeUnit.MILLISECONDS.convert(currentNanoTime 
                        - currentNanotime[0], TimeUnit.NANOSECONDS);
                // Updates the board only if certain time has passed    
                if (deltaTime >= 2200 - animationSlider.getValue()) {
                    updater(moveQueue, board);
                    //Set the time since last update to current time
                    currentNanotime[0] = System.nanoTime();
                }
                // Kills the timer update routine if the game has ended
                if (board.gameEnd) {
                    this.stop();
                }

            }
        };
        // This encapsulates the bot as a thread, bot gets its own board 
        // (deep copy of the guis board) that it uses to make its moves
        
        BotExecutor botThread = new BotExecutor(moveQueue, bot, botBoard);

        // Starts the gui updater and the bot thread
        timer.start();
        botThread.start();
    }

    private void setOpenedButtonColor(Button button, int mines) {
        String labelStyle = "custom-label-";
        labelStyle = labelStyle.concat("" + mines);
        button.getStyleClass().add(labelStyle);
    }

    // Used by the gui updater timer to updat the board of the gui
    public void updater(LinkedBlockingQueue<Move> moveQueue, Board board) {
        // Takes a move that has bot has made
        Move move = moveQueue.poll();

        if (move == null) {
            return;
        }
        System.out.println("Updating");
        //Makes move to the gui board and updates the gui buttons
        board.makeMove(move);

        board.getSquareAt(move.x, move.y).highlight = Highlight.BLACK;
        updateGameGP(true);

        board.getSquareAt(move.x, move.y).highlight = Highlight.NONE;  
    }
    private void initializeSlider() {
        this.animationSlider = new Slider(100, 2000, 1050);
        this.animationSlider.setMajorTickUnit(200f);
        this.animationSlider.setBlockIncrement(10f);
        this.animationSlider.setMaxWidth(sizeX * 30);
    }
}
