package norseninja.producer_consumer;

public class Main {
    public static final char EOF = '%';

    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);

        Thread producerThread = new Thread(new Producer(buffer));
        producerThread.setName("producerThread");

        Thread consumerThread1 = new Thread(new Consumer(buffer));
        consumerThread1.setName("consumerThread1");

        producerThread.start();
        consumerThread1.start();
    }
}
