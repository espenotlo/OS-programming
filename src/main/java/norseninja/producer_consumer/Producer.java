package norseninja.producer_consumer;

import java.util.Random;

public class Producer implements Runnable {
    private final char[] chars;
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.chars = new char[]{'a','b','c','d','e','f','g','h','i','j'};
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (char c : chars) {
            synchronized (buffer) {
                buffer.put(c);
                try {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted.");
                }
            }
            System.out.println(Thread.currentThread().getName() + " added " + c);
        }
        System.out.println(Thread.currentThread().getName() + " added " + Main.EOF);
        synchronized (buffer) {
            buffer.put(Main.EOF);
        }
    }
}
