package ship;

import port.Port;

public class Ship implements Runnable {
    private int id;
    private int numberOfContainers;
    private int maxNumberOfContainers;
    private Port port;

    public Ship() {

    }

    public Ship(int id, int numberOfContainers, int maxNumberOfContainers, Port port) {
        this.id = id;
        this.numberOfContainers = numberOfContainers;
        this.maxNumberOfContainers = maxNumberOfContainers;
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    public int getMaxNumberOfContainers() {
        return maxNumberOfContainers;
    }

    public void setMaxNumberOfContainers(int maxNumberOfContainers) {
        this.maxNumberOfContainers = maxNumberOfContainers;
    }


    @Override
    public void run() {
        try {
            port.getPiers().acquire();

            Ship ship = Ship.this;

            System.out.println("Ship #" + getId() + " go to dock# " + Thread.currentThread().getName());

            if (ship.getNumberOfContainers() == 20) {
                port.outputShip(ship);
            } else if (port.isEmpty(ship)) {
                port.putInShip(ship);
            } else if (!port.isEmpty(ship)) {
                port.outputShip(ship);
                port.putInShip(ship);
            }

            Thread.sleep(3000);

            System.out.println("Ship #"+ship.getId()+ " go to another port.");

            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            port.getPiers().release();
        }
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", numberOfContainers=" + numberOfContainers +
                ", maxNumberOfContainers=" + maxNumberOfContainers +
                "}";
    }
}
