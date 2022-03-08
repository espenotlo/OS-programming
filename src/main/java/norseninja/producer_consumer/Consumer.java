package norseninja.producer_consumer;

import java.util.Random;

public class Consumer implements Runnable {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (buffer) {
                char c = buffer.get();
                if (c == Main.EOF) {
                    System.out.println(Thread.currentThread().getName() + " exiting.");
                    break;
                } else {
                    if (c != 0) {
                        System.out.println(Thread.currentThread().getName() + " removed " + c);
                    }
                    try {
                        Random random = new Random();
                        Thread.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + " interrupted.");
                    }
                }
            }
        }
    }
}