import java.util.*;

public class FIFOPageReplacement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input the number of page frames
        System.out.print("Enter the number of page frames: ");
        int frames = sc.nextInt();

        // Input the reference string
        System.out.print("Enter the number of pages in the reference string: ");
        int n = sc.nextInt();

        System.out.println("Enter the reference string (space-separated):");
        int[] referenceString = new int[n];
        for (int i = 0; i < n; i++) {
            referenceString[i] = sc.nextInt();
        }

        // FIFO algorithm
        Queue<Integer> pageFrames = new LinkedList<>();
        int pageFaults = 0;

        System.out.println("\nPage Frame Status:");

        for (int page : referenceString) {
            if (!pageFrames.contains(page)) {
                // Page Fault
                if (pageFrames.size() == frames) {
                    // Remove the oldest page (FIFO)
                    pageFrames.poll();
                }
                pageFrames.add(page);
                pageFaults++;
                System.out.println(pageFrames + " <- Page Fault");
            } else {
                // Page Hit
                System.out.println(pageFrames + " <- Page Hit");
            }
        }

        // Output results
        System.out.println("\nTotal Page Faults: " + pageFaults);
        sc.close();
    }
}
