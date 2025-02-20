import java.util.Scanner;

public class RowColumnCipher {

    // Encrypt the plaintext using Row and Column Transformation
    public static String encrypt(String plainText, int rows, int columns) {
        plainText = plainText.replaceAll("\\s+", ""); // Remove spaces
        int length = plainText.length();

        // Padding with 'X' if necessary
        int totalCells = rows * columns;
        while (plainText.length() < totalCells) {
            plainText += 'X';
        }

        char[][] grid = new char[rows][columns];
        int index = 0;

        // Fill the grid row-wise
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = plainText.charAt(index++);
            }
        }

        // Read the grid column-wise to generate the encrypted text
        StringBuilder cipherText = new StringBuilder();
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                cipherText.append(grid[i][j]);
            }
        }

        return cipherText.toString();
    }

    // Decrypt the ciphertext using Row and Column Transformation
    public static String decrypt(String cipherText, int rows, int columns) {
        char[][] grid = new char[rows][columns];
        int index = 0;

        // Fill the grid column-wise
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                grid[i][j] = cipherText.charAt(index++);
            }
        }

        // Read the grid row-wise to generate the decrypted text
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                decryptedText.append(grid[i][j]);
            }
        }

        // Trim padding if any
        return decryptedText.toString().replace("X", "");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the plain text: ");
        String plainText = scanner.nextLine();

        System.out.print("Enter the number of rows: ");
        int rows = scanner.nextInt();

        System.out.print("Enter the number of columns: ");
        int columns = scanner.nextInt();

        String encryptedText = encrypt(plainText, rows, columns);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = decrypt(encryptedText, rows, columns);
        System.out.println("Decrypted Text: " + decryptedText);

        scanner.close();
    }
}
