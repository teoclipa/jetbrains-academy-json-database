import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] original = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                original[i][j] = sc.nextInt();
            }
        }

        int[][] rotated = rotateArray(original, n, m);

        for (int i = 0; i < rotated.length; i++) {
            for (int j = 0; j < rotated[i].length; j++) {
                System.out.print(rotated[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] rotateArray(int[][] original, int n, int m) {
        int[][] rotated = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rotated[i][j] = original[n - j - 1][i];
            }
        }

        return rotated;
    }
}
