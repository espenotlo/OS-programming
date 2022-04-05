package norseninja.sleeping_barber;

public class Counter implements Runnable {
    private final Saloon saloon;

    public Counter(Saloon saloon) {
        this.saloon = saloon;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saloon.running = false;
    }
}
