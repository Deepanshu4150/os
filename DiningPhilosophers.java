import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    // Number of philosophers
    private static final int NUM_PHILOSOPHERS = 5;

    // Array of semaphores representing forks
    private static final Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];

    // Maximum number of eating rounds
    private static final int MAX_ROUNDS = 3;

    // Philosopher threads
    static class Philosopher extends Thread {
        private int id;
        private int eatCount = 0;

        // Constructor
        Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                while (eatCount < MAX_ROUNDS) {
                    // Thinking
                    System.out.println("Philosopher " + id + " is thinking.");
                    Thread.sleep((int) (Math.random() * 1000));

                    // Picking up forks
                    int leftFork = id;
                    int rightFork = (id + 1) % NUM_PHILOSOPHERS;

                    // To prevent deadlock, pick the fork with a smaller index first
                    if (leftFork < rightFork) {
                        forks[leftFork].acquire();
                        forks[rightFork].acquire();
                    } else {
                        forks[rightFork].acquire();
                        forks[leftFork].acquire();
                    }

                    // Eating
                    System.out.println("Philosopher " + id + " is eating.");
                    Thread.sleep((int) (Math.random() * 1000));
                    eatCount++;

                    // Putting down forks
                    forks[leftFork].release();
                    forks[rightFork].release();

                    System.out.println("Philosopher " + id + " finished eating round " + eatCount + ".");
                }
                System.out.println("Philosopher " + id + " has finished all eating rounds and left the table.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Initialize the forks
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        // Create and start philosopher threads
        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i);
            philosophers[i].start();
        }

        // Wait for all philosophers to finish
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            try {
                philosophers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All philosophers have finished their meals.");
    }
}
