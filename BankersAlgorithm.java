import java.util.Scanner;

public class BankersAlgorithm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes and resources
        System.out.print("Enter the number of processes: ");
        int processes = sc.nextInt();
        System.out.print("Enter the number of resources: ");
        int resources = sc.nextInt();

        // Input Allocation matrix
        System.out.println("Enter the Allocation matrix (space-separated, row by row):");
        int[][] allocation = inputMatrix(sc, processes, resources);

        // Input Maximum matrix
        System.out.println("Enter the Maximum matrix (space-separated, row by row):");
        int[][] max = inputMatrix(sc, processes, resources);

        // Input Available resources
        System.out.print("Enter the Available resources (space-separated): ");
        int[] available = new int[resources];
        for (int i = 0; i < resources; i++) {
            available[i] = sc.nextInt();
        }

        // Calculate the Need matrix
        int[][] need = calculateNeedMatrix(processes, resources, allocation, max);

        // Check if the system is in a safe state
        boolean[] finished = new boolean[processes];
        int[] safeSequence = new int[processes];
        int count = 0;

        while (count < processes) {
            boolean found = false;
            for (int p = 0; p < processes; p++) {
                if (!finished[p]) {
                    boolean canProceed = true;
                    for (int r = 0; r < resources; r++) {
                        if (need[p][r] > available[r]) {
                            canProceed = false;
                            break;
                        }
                    }

                    if (canProceed) {
                        // Allocate resources
                        for (int r = 0; r < resources; r++) {
                            available[r] += allocation[p][r];
                        }
                        safeSequence[count++] = p;
                        finished[p] = true;
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("System is in an unsafe state!");
                sc.close();
                return;
            }
        }

        // Output the safe sequence
        System.out.println("System is in a safe state.");
        System.out.print("Safe sequence: ");
        for (int i = 0; i < processes; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }

        sc.close();
    }

    private static int[][] inputMatrix(Scanner sc, int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        return matrix;
    }

    private static int[][] calculateNeedMatrix(int processes, int resources, int[][] allocation, int[][] max) {
        int[][] need = new int[processes][resources];
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
        return need;
    }
}
