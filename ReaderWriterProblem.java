import java.util.concurrent.Semaphore;

class ReaderWriterProblem {
    // Semaphores
    private static final Semaphore readLock = new Semaphore(1); // Controls reader access
    private static final Semaphore writeLock = new Semaphore(1); // Controls writer access
    private static int readCount = 0; // Track the number of readers

    // Simulated resource
    private static int sharedResource = 0;

    // Reader class
    static class Reader extends Thread {
        @Override
        public void run() {
            try {
                // Reader requests access
                readLock.acquire();
                readCount++;
                if (readCount == 1) {
                    // First reader locks the write access
                    writeLock.acquire();
                }
                readLock.release();

                // Reading the resource
                System.out.println(Thread.currentThread().getName() + " is reading: " + sharedResource);
                Thread.sleep(1000); // Simulate reading time

                // Reader releases access
                readLock.acquire();
                readCount--;
                if (readCount == 0) {
                    // Last reader unlocks the write access
                    writeLock.release();
                }
                readLock.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Writer class
    static class Writer extends Thread {
        @Override
        public void run() {
            try {
                // Writer requests exclusive access
                writeLock.acquire();

                // Writing to the resource
                sharedResource++;
                System.out.println(Thread.currentThread().getName() + " is writing: " + sharedResource);
                Thread.sleep(1000); // Simulate writing time

                // Writer releases exclusive access
                writeLock.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        // Create and start threads
        Thread writer1 = new Writer();
        Thread writer2 = new Writer();
        Thread reader1 = new Reader();
        Thread reader2 = new Reader();
        Thread reader3 = new Reader();

        writer1.setName("Writer 1");
        writer2.setName("Writer 2");
        reader1.setName("Reader 1");
        reader2.setName("Reader 2");
        reader3.setName("Reader 3");

        writer1.start();
        reader1.start();
        writer2.start();
        reader2.start();
        reader3.start();
    }
}
