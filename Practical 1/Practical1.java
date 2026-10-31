
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Practical1 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        // Input dimensions of first matrix
        System.out.print("Enter rows of first matrix: ");
        int r1 = Integer.parseInt(br.readLine());

        System.out.print("Enter columns of first matrix: ");
        int c1 = Integer.parseInt(br.readLine());

        // Input dimensions of second matrix
        System.out.print("Enter rows of second matrix: ");
        int r2 = Integer.parseInt(br.readLine());

        System.out.print("Enter columns of second matrix: ");
        int c2 = Integer.parseInt(br.readLine());

        // Check multiplication compatibility
        if (c1 != r2) {
            System.out.println("Matrix multiplication is not possible.");
            return;
        }

        int[][] a = new int[r1][c1];
        int[][] b = new int[r2][c2];
        int[][] result = new int[r1][c2];

        // Input first matrix
        System.out.println("Enter elements of first matrix:");

        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c1; j++) {
                a[i][j] = Integer.parseInt(br.readLine());
            }
        }

        // Input second matrix
        System.out.println("Enter elements of second matrix:");

        for (int i = 0; i < r2; i++) {
            for (int j = 0; j < c2; j++) {
                b[i][j] = Integer.parseInt(br.readLine());
            }
        }

        // Matrix multiplication
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        // Display result
        System.out.println("Result of matrix multiplication:");

        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                System.out.print(result[i][j] + "\t");
            }
            System.out.println();
        }
    }
}

