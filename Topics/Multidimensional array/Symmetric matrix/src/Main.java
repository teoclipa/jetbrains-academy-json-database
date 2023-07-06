import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int[][] squareMatrix = new int[n][n];

        for (int i = 0; i < squareMatrix.length; i++) {
            for (int j = 0; j < squareMatrix[0].length; j++) {
                squareMatrix[i][j] = scanner.nextInt();
            }
        }

        System.out.println(checkSymmetry(squareMatrix));
    }


    public static String checkSymmetry(int[][] squareMatrix) {
        for (int i = 0; i < squareMatrix.length; i++) {
            for (int j = 0; j < squareMatrix[0].length; j++) {
                if (squareMatrix[i][j] != squareMatrix[j][i]) {
                    return "NO";
                }
            }
        }
        return "YES";
    }
}