
package minesweeper.bot;

import java.util.concurrent.BlockingQueue;
import minesweeper.model.*;

public class BotExecutor extends Thread {

    private BlockingQueue<Move> queue;
    private Bot bot;
    private Board board;


    public BotExecutor(BlockingQueue<Move> moveQueue, Bot bot, Board board){
        this.queue = moveQueue;
        this.bot = bot;
        this.board = board;
    }

    @Override
    public void run() {
        //Run bot while game has not ended
        while(!this.board.gameEnd){
            //Try to add new move from bot to queue
            System.out.println("Moving");
            try{
                this.sleep(100);
            } catch (Exception e){

            }
            Move move = this.bot.makeMove(this.board);
            this.queue.offer(move);
            this.board.makeMove(move);
        }
    }

}
