import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.close();

        int[][] matrix = fillMatrix(n);

        // Output the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] fillMatrix(int n) {
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int diff = Math.abs(i - j);
                if (diff == 0) {
                    matrix[i][j] = 0;
        
               
                } else {
                    matrix[i][j] = diff;
                }
            }
        }

        return matrix;
    }
}
