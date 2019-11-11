package minesweeper;

import java.util.concurrent.*;

public class TestCon extends Thread {

    int i = 0;
    BlockingQueue<String> bq;
    long timeout;
    int limit = 1000000;
    String[] testData = new String[limit];

    public TestCon(BlockingQueue<String> bq, long timeout) {
        this.bq = bq;
        this.timeout = timeout;
        for (int i = 0; i < limit; i++) {
            testData[i] = "" + i;
        }
    }

    private void produce() {
        try {
            if (i < limit) {
                bq.offer(testData[i]);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (i < limit) {
                System.out.println("Producing on " + timeout);
                produce();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
