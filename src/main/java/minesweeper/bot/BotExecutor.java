
package minesweeper.bot;

import java.util.concurrent.BlockingQueue;
import minesweeper.model.Board;
import minesweeper.model.Move;

public class BotExecutor extends Thread {

    private BlockingQueue<Move> queue;
    private Bot bot;
    private Board board;
    /**
     * This class is used to encapsulate the bot to a thread so it can be run concurrently with the gui updater
     */
    public BotExecutor(BlockingQueue<Move> moveQueue, Bot bot, Board board) {
        this.queue = moveQueue;
        this.bot = bot;
        this.board = board;
    }

    @Override
    public void run() {
        //Run bot while game has not ended
        while (!this.board.gameEnd) {
            // Try to add new move from bot to queue
            System.out.println("Moving");
            // This is currently just for testing purposes to slown the bot down a bit 
            try {
                this.sleep(100);
            } catch (Exception e) { }
            // Bot makes moves to its own board and then the move is added to the supply queue
            Move move = this.bot.makeMove(this.board);
            this.queue.offer(move);
            this.board.makeMove(move);
        }
    }

}
