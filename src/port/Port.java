package port;

import ship.Ship;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private int storage = 200;
    private Semaphore piers = new Semaphore(2);
    private Lock lock = new ReentrantLock();

    public Port() {

    }

    public int getStorage() {
        return storage;
    }

    public Semaphore getPiers() {
        return piers;
    }

    public void setPiers(Semaphore piers) {
        this.piers = piers;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public boolean isEmpty(Ship ship) {
        if (ship.getNumberOfContainers() == 0 || (ship.getNumberOfContainers() > 0 &&
                (ship.getMaxNumberOfContainers() / 2 >= ship.getNumberOfContainers()
                        && ship.getNumberOfContainers() != 20))) {
            return true;
        }
        return false;
    }

    public void putInShip(Ship ship) throws InterruptedException {
        int i = ship.getNumberOfContainers();

        System.out.println("Ship #" + ship.getId() + " with balance = " +
                i + " is loading on piers #" + Thread.currentThread().getName());

        Thread.sleep(2000);

        while (i != ship.getMaxNumberOfContainers()) {
            ship.setNumberOfContainers(i++);
            lock.lock();
            storage--;
            lock.unlock();
        }

        Thread.sleep(6000);

        System.out.println("Ship #" + ship.getId() + " with sum of containers = "
                + ship.getNumberOfContainers() + " is ready by " + Thread.currentThread().getName());
    }


    public void outputShip(Ship ship) throws InterruptedException {
        int i = ship.getNumberOfContainers();

        System.out.println("Ship #" + ship.getId() + " with balance = " +
                i + " is unloading on piers #" + Thread.currentThread().getName());

        Thread.sleep(4000);

        while (ship.getNumberOfContainers() != 0) {
            ship.setNumberOfContainers(i--);
            lock.lock();
            storage++;
            lock.unlock();
        }

        Thread.sleep(6000);

        System.out.println("Ship #" + ship.getId() + " with sum of containers = "
                + ship.getNumberOfContainers() + " is ready by " + Thread.currentThread().getName());
    }

}

