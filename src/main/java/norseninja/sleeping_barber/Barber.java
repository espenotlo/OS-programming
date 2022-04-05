package norseninja.sleeping_barber;

/**
 * Represents a barber working in a hair saloon.
 */
public class Barber implements Runnable {
    private final Saloon saloon;

    public Barber(Saloon saloon) {
        this.saloon = saloon;
    }

    public void cutHair() {
        System.out.println("Barber: Cutting hair ------ " + Thread.currentThread().getName());
    }

    @Override
    public void run() {
        while (saloon.running) {
            try {
                //wait for customer to wake me up
                saloon.customerReady.acquire();
            } catch (InterruptedException e) {
                System.out.println("Barber wait interrupted.");
            }
            //Signal that I am ready to cut
            saloon.barberReady.release();
            cutHair();
            try {
                saloon.customerDone.acquire();
            } catch (InterruptedException e) {
                System.out.println("Interrupted while waiting for customer to approve the new haircut");
            }
            saloon.barberDone.release();
            System.out.println("Barber: Finished cutting -- " + Thread.currentThread().getName());
        }
    }
}
