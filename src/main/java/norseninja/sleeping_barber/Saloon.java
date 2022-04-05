package norseninja.sleeping_barber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Saloon {
    private final int customerLimit;
    private final Barber barber;
    private final ArrayList<Customer> customers;

    Semaphore barberReady;
    Semaphore barberDone;

    Semaphore customerReady;
    Semaphore customerDone;

    boolean running = true;

    /**
     * A Hair saloon with one barber and given amount of chairs for customers to wait in.
     * @param customerLimit number of chairs in the waiting room.
     */
    public Saloon(int customerLimit) {
        this.barber = new Barber(this);
        this.customerLimit = customerLimit;
        customers = new ArrayList<>();

        this.customerReady = new Semaphore(0);
        this.customerDone = new Semaphore(0);

        this.barberReady = new Semaphore(0);
        this.barberDone = new Semaphore(0);
    }

    /**
     * Populates the hair saloon and runs simulation.
     */
    public void openForBusiness() {
        //Hiring a barber
        Thread barberThread = new Thread(barber);
        barberThread.setDaemon(true);
        barberThread.start();

        //Add a counter to stop the program after a certain amount of time.
        Counter counter = new Counter(this);
        Thread counterThread = new Thread(counter);
        counterThread.setDaemon(true);
        counterThread.start();

        //Send in a new customer at random interval up to 35 milliseconds.
        Random r = new Random();
        while (running) {
            try {
                sendInCustomer();
                Thread.sleep(r.nextInt(35));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends in a new customer.
     */
    private void sendInCustomer() {
        Thread customerThread = new Thread(new Customer(this));
        customerThread.setDaemon(true);
        customerThread.start();
    }

    /**
     * Gets a list of customers waiting to get a haircut.
     * @return {@code List<Customer>} of customers waiting for haircut.
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Getter for number of chairs in the waiting room-
     * @return {@code int} number of chairs in the waiting room.
     */
    public int getCustomerLimit() {
        return this.customerLimit;
    }
}
