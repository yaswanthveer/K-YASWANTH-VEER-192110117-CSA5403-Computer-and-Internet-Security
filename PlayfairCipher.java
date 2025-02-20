import java.util.Scanner;

public class PlayfairCipher {

    private String key;
    private char[][] matrix;

    public PlayfairCipher(String key) {
        this.key = formatKey(key);
        this.matrix = generateMatrix(this.key);
    }

    private String formatKey(String key) {
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace('J', 'I');
        StringBuilder formattedKey = new StringBuilder();
        for (char c : key.toCharArray()) {
            if (formattedKey.indexOf(String.valueOf(c)) == -1) {
                formattedKey.append(c);
            }
        }
        return formattedKey.toString();
    }

    private char[][] generateMatrix(String key) {
        char[][] matrix = new char[5][5];
        boolean[] visited = new boolean[26];
        visited['J' - 'A'] = true;

        int row = 0, col = 0;
        for (char c : key.toCharArray()) {
            if (!visited[c - 'A']) {
                matrix[row][col++] = c;
                visited[c - 'A'] = true;
                if (col == 5) {
                    col = 0;
                    row++;
                }
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (!visited[c - 'A']) {
                matrix[row][col++] = c;
                visited[c - 'A'] = true;
                if (col == 5) {
                    col = 0;
                    row++;
                }
            }
        }
        return matrix;
    }

    private String formatText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace('J', 'I');
        StringBuilder formattedText = new StringBuilder(text);

        for (int i = 0; i < formattedText.length() - 1; i += 2) {
            if (formattedText.charAt(i) == formattedText.charAt(i + 1)) {
                formattedText.insert(i + 1, 'X');
            }
        }

        if (formattedText.length() % 2 != 0) {
            formattedText.append('X');
        }
        return formattedText.toString();
    }

    public String encrypt(String plainText) {
        plainText = formatText(plainText);
        StringBuilder cipherText = new StringBuilder();

        for (int i = 0; i < plainText.length(); i += 2) {
            char first = plainText.charAt(i);
            char second = plainText.charAt(i + 1);

            int[] pos1 = findPosition(first);
            int[] pos2 = findPosition(second);

            if (pos1[0] == pos2[0]) {
                cipherText.append(matrix[pos1[0]][(pos1[1] + 1) % 5]);
                cipherText.append(matrix[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) {
                cipherText.append(matrix[(pos1[0] + 1) % 5][pos1[1]]);
                cipherText.append(matrix[(pos2[0] + 1) % 5][pos2[1]]);
            } else {
                cipherText.append(matrix[pos1[0]][pos2[1]]);
                cipherText.append(matrix[pos2[0]][pos1[1]]);
            }
        }
        return cipherText.toString();
    }

    public String decrypt(String cipherText) {
        StringBuilder plainText = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i += 2) {
            char first = cipherText.charAt(i);
            char second = cipherText.charAt(i + 1);

            int[] pos1 = findPosition(first);
            int[] pos2 = findPosition(second);

            if (pos1[0] == pos2[0]) {
                plainText.append(matrix[pos1[0]][(pos1[1] + 4) % 5]);
                plainText.append(matrix[pos2[0]][(pos2[1] + 4) % 5]);
            } else if (pos1[1] == pos2[1]) {
                plainText.append(matrix[(pos1[0] + 4) % 5][pos1[1]]);
                plainText.append(matrix[(pos2[0] + 4) % 5][pos2[1]]);
            } else {
                plainText.append(matrix[pos1[0]][pos2[1]]);
                plainText.append(matrix[pos2[0]][pos1[1]]);
            }
        }
        return plainText.toString().replace("X", "");
    }

    private int[] findPosition(char c) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (matrix[row][col] == c) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the encryption key: ");
        String key = scanner.nextLine();

        PlayfairCipher cipher = new PlayfairCipher(key);

        System.out.print("Enter the plain text: ");
        String plainText = scanner.nextLine();
        String encryptedText = cipher.encrypt(plainText);
        System.out.println("Encrypted Text: " + encryptedText);

        System.out.print("Enter the cipher text to decrypt: ");
        String cipherText = scanner.nextLine();
        String decryptedText = cipher.decrypt(cipherText);
        System.out.println("Decrypted Text: " + decryptedText);

        scanner.close();
    }
}
