package norseninja.sleeping_barber;

public class Customer implements Runnable {
    private final Saloon saloon;

    public Customer(Saloon saloon) {
        this.saloon = saloon;
    }

    public boolean enter() {
        System.out.println("Customer: Entering -------- " + Thread.currentThread().getName());
        boolean success = false;
        synchronized (saloon) {
            if (saloon.getCustomers().size() >= saloon.getCustomerLimit()) {
                System.out.println("Saloon is full.");
            } else {
                saloon.getCustomers().add(this);
                success = true;
            }
        }
        return success;
    }

    public void getHairCut() {
        System.out.println("Customer: Getting haircut - " + Thread.currentThread().getName());
    }

    public void leave() {
        System.out.println("Customer: Leaving --------- " + Thread.currentThread().getName());
    }

    @Override
    public void run() {
        if (enter()) {
            boolean firstInQueue = false;
            while (!firstInQueue) {
                synchronized (saloon) {
                    firstInQueue = saloon.getCustomers().get(0) == this;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Signal that the customer is ready to get hair cut.
            saloon.customerReady.release();
            try {
                saloon.barberReady.acquire();
                saloon.getCustomers().remove(0);
            } catch (InterruptedException e) {
                System.out.println("Customer waiting for barber to be ready to cut was interrupted.");
            }
            getHairCut();
            //Signal that customer is happy with the haircut.
            saloon.customerDone.release();
            try {
                saloon.barberDone.acquire();
            } catch (InterruptedException e) {
                System.out.println("Customer waiting for barber to be satisfied with the cut was interrupted.");
            }
        }
        leave();
    }
}
