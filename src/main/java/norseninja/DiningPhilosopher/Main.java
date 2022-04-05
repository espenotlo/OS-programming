package norseninja.DiningPhilosopher;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final List<Thread> philosophers = new ArrayList<>();

    public static void main(String[] args) {

        Chopstick firstStick = new Chopstick();
        Chopstick secondStick = new Chopstick();
        Chopstick thirdStick = new Chopstick();
        Chopstick fourthStick = new Chopstick();
        Chopstick fifthStick = new Chopstick();

        // Seating the philosophers and laying out the chopsticks on the table.
        philosophers.add(new Thread(new Philosopher(firstStick, secondStick)));
        philosophers.add(new Thread(new Philosopher(secondStick, thirdStick)));
        philosophers.add(new Thread(new Philosopher(thirdStick, fourthStick)));
        philosophers.add(new Thread(new Philosopher(fourthStick, fifthStick)));
        philosophers.add(new Thread(new Philosopher(fifthStick, firstStick)));

        philosophers.forEach(Thread::start);
    }

}
