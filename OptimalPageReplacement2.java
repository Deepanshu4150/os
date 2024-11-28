import java.util.*;

public class OptimalPageReplacement {
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

        // Optimal algorithm
        List<Integer> pageFrames = new ArrayList<>();
        int pageFaults = 0;

        System.out.println("\nPage Frame Status:");

        for (int i = 0; i < n; i++) {
            int page = referenceString[i];

            if (!pageFrames.contains(page)) {
                // Page Fault
                if (pageFrames.size() == frames) {
                    // Replace a page using the Optimal strategy
                    int indexToReplace = findOptimalReplacement(pageFrames, referenceString, i + 1);
                    pageFrames.set(indexToReplace, page);
                } else {
                    pageFrames.add(page);
                }
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

    // Find the index of the page to replace using the Optimal replacement strategy
    private static int findOptimalReplacement(List<Integer> pageFrames, int[] referenceString, int currentIndex) {
        int maxDistance = -1;
        int indexToReplace = -1;

        for (int i = 0; i < pageFrames.size(); i++) {
            int page = pageFrames.get(i);
            int distance = Integer.MAX_VALUE;

            // Find the next occurrence of the current page
            for (int j = currentIndex; j < referenceString.length; j++) {
                if (referenceString[j] == page) {
                    distance = j - currentIndex;
                    break;
                }
            }

            // If the page is not used again, replace it
            if (distance == Integer.MAX_VALUE) {
                return i;
            }

            // Update the page with the farthest usage
            if (distance > maxDistance) {
                maxDistance = distance;
                indexToReplace = i;
            }
        }

        return indexToReplace;
    }
}
