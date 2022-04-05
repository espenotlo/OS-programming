package norseninja.DiningPhilosopher;

public class Chopstick {
    private boolean isAvailable;

    public Chopstick() {
        this.isAvailable = true;
    }

    public void toggleInUse() {
        isAvailable = !isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}