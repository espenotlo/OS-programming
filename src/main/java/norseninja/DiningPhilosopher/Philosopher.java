package norseninja.DiningPhilosopher;

import java.time.LocalDateTime;
import java.util.Random;

public class Philosopher implements Runnable {
    private final Chopstick firstStick;
    private final Chopstick secondStick;
    private final Random r;
    private boolean chop1;
    private boolean chop2;

    public Philosopher(Chopstick firstStick, Chopstick secondStick) {
        this.firstStick = firstStick;
        this.secondStick = secondStick;
        this.r = new Random(LocalDateTime.now().getNano());
        this.chop1 = false;
        this.chop2 = false;
    }

    /**
     * The philosopher will think for a random amount of time,
     * then try to pick up the chopsticks nearest to him,
     * and start eating for a random amount of time.
     * After eating they put down the chopsticks and repeat this cycle.
     */
    @Override
    public void run() {
        while (true) {
            // Thinking..
            try {
                System.out.println(Thread.currentThread().getName() + " is thinking...");
                Thread.sleep(getLongRandomInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int timesWaited = 0;
            // Look for each chopstick three times, before giving up, and dropping held chopsticks. (To avoid deadlocks)
            while ((!chop1 || !chop2) && timesWaited < 3) {
                // Wait before checking the chopsticks again.
                try {
                    Thread.sleep(getRandomInterval());
                } catch (InterruptedException e) {
                    System.out.println("Philosopher thinking was interrupted unexpectedly.");
                }
                synchronized (firstStick) {
                    if (firstStick.isAvailable()) {
                        firstStick.toggleInUse();
                        chop1 = true;
                        System.out.println(Thread.currentThread().getName() + " Picked up a chopstick.");
                        timesWaited = 0;
                    }
                }
                synchronized (secondStick) {
                    if (secondStick.isAvailable()) {
                        secondStick.toggleInUse();
                        chop2 = true;
                        System.out.println(Thread.currentThread().getName() + " Picked up a chopstick.");
                        timesWaited = 0;
                    }
                }
                timesWaited++;
            }
            if (chop1 && chop2) {
                // Eating
                System.out.println(Thread.currentThread().getName() + " is eating. Nom nom!");
                try {
                    Thread.sleep(getRandomInterval());
                } catch (InterruptedException e) {
                    System.out.println("Philosopher looking for chopsticks was interrupted unexpectedly.");
                }
                synchronized (firstStick) {
                    firstStick.toggleInUse();
                    chop1 = false;
                }
                synchronized (secondStick) {
                    secondStick.toggleInUse();
                    chop2 = false;
                }
            } else if (chop1) {
                synchronized (firstStick) {
                    firstStick.toggleInUse();
                }
                chop1 = false;
                System.out.println(Thread.currentThread().getName() + " gave up and drop their chopstick.");
            } else if (chop2) {
                synchronized (secondStick) {
                    secondStick.toggleInUse();
                }
                chop2 = false;
                System.out.println(Thread.currentThread().getName() + " gave up and drop their chopstick.");
            }
        }
    }

    private int getRandomInterval() {
        return 3000 + r.nextInt(2000);
    }

    private int getLongRandomInterval() {
        return 7000 + r.nextInt(2000);
    }
}