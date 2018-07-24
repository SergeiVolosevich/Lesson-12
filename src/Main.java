import port.Port;
import ship.Ship;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Port port = new Port();

        System.out.println("Balance on storage before loading/unloading = " + port.getStorage());

        Queue<Ship> queue = new ArrayDeque<>();
        for (int i = 1; i < 9; i++) {
            queue.add(new Ship(i, ThreadLocalRandom.current().nextInt(20),
                    ThreadLocalRandom.current().nextInt(21, 25), port));
        }

        queue.add(new Ship(9, 20, 24, port));

        for (Ship ship:queue) {
            System.out.println(ship);
        }

        System.out.println();

        List<Thread> threads = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            threads.add(new Thread(queue.poll()));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Balance on storage after loading/unloading all ships = " + port.getStorage());
    }
}
