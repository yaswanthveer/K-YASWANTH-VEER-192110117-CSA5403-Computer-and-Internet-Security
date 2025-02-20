import java.util.*;

public class CombinedCiphers {

    // Caesar Cipher
    public static String caesarEncrypt(String plaintext, int shift) {
        StringBuilder ciphertext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                ciphertext.append((char) ((c - base + shift) % 26 + base));
            } else {
                ciphertext.append(c);
            }
        }
        return ciphertext.toString();
    }

    public static String caesarDecrypt(String ciphertext, int shift) {
        return caesarEncrypt(ciphertext, 26 - shift);
    }

    // Playfair Cipher
    private static final int SIZE = 5;

    private static char[][] generateKeySquare(String key) {
        key = key.replace("J", "I").toUpperCase();
        Set<Character> used = new HashSet<>();
        char[][] keySquare = new char[SIZE][SIZE];
        int row = 0, col = 0;

        for (char c : key.toCharArray()) {
            if (!used.contains(c)) {
                keySquare[row][col] = c;
                used.add(c);
                col++;
                if (col == SIZE) {
                    col = 0;
                    row++;
                }
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !used.contains(c)) {
                keySquare[row][col] = c;
                col++;
                if (col == SIZE) {
                    col = 0;
                    row++;
                }
            }
        }

        return keySquare;
    }

    private static String prepareText(String text) {
        text = text.replace("J", "I").toUpperCase();
        StringBuilder preparedText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            preparedText.append(text.charAt(i));
            if (i + 1 < text.length() && text.charAt(i) == text.charAt(i + 1)) {
                preparedText.append('X');
            }
        }
        if (preparedText.length() % 2 != 0) {
            preparedText.append('X');
        }
        return preparedText.toString();
    }

    private static String playfairEncrypt(String plaintext, char[][] keySquare) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            char a = plaintext.charAt(i);
            char b = plaintext.charAt(i + 1);
            int[] posA = findPosition(a, keySquare);
            int[] posB = findPosition(b, keySquare);

            if (posA[0] == posB[0]) { // Same row
                ciphertext.append(keySquare[posA[0]][(posA[1] + 1) % SIZE]);
                ciphertext.append(keySquare[posB[0]][(posB[1] + 1) % SIZE]);
            } else if (posA[1] == posB[1]) { // Same column
                ciphertext.append(keySquare[(posA[0] + 1) % SIZE][posA[1]]);
                ciphertext.append(keySquare[(posB[0] + 1) % SIZE][posB[1]]);
            } else { // Rectangle
                ciphertext.append(keySquare[posA[0]][posB[1]]);
                ciphertext.append(keySquare[posB[0]][posA[1]]);
            }
        }
        return ciphertext.toString();
    }

    private static int[] findPosition(char c, char[][] keySquare) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (keySquare[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    // Hill Cipher
    private static int[][] keyMatrix = {{6, 24, 1}, {13, 16, 10}, {20, 17, 15}}; // Example 3x3 key matrix

    private static String hillEncrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        int n = keyMatrix.length;
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        // Pad with 'X' if necessary
        while (plaintext.length() % n != 0) {
            plaintext += 'X';
        }

        for (int i = 0; i < plaintext.length(); i += n) {
            int[] block = new int[n];
            for (int j = 0; j < n; j++) {
                block[j] = plaintext.charAt(i + j) - 'A';
            }

            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += keyMatrix[j][k] * block[k];
                }
                ciphertext.append((char) ((sum % 26) + 'A'));
            }
        }

        return ciphertext.toString();
    }

    // Vigenère Cipher
    public static String vigenereEncrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();

        for (int i = 0, j = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                ciphertext.append((char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A'));
                j = (j + 1) % key.length();
            } else {
                ciphertext.append(c);
            }
        }
        return ciphertext.toString();
    }

    public static String vigenereDecrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();

        for (int i = 0, j = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                plaintext.append((char) ((c - key.charAt(j) + 26) % 26 + 'A'));
                j = (j + 1) % key.length();
            } else {
                plaintext.append(c);
            }
        }
        return plaintext.toString();
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter cipher type (CC, PC, HC, VC):");
        String cipherType = scanner.nextLine().toUpperCase();
        System.out.println("Enter plaintext:");
        String plaintext = scanner.nextLine();

        switch (cipherType) {
            case "CC":
                System.out.println("Enter shift value:");
                int shift = scanner.nextInt();
                String ccEncrypted = caesarEncrypt(plaintext, shift);
                String ccDecrypted = caesarDecrypt(ccEncrypted, shift);
                System.out.println("Caesar Cipher:");
                System.out.println("Encrypted: " + ccEncrypted);
                System.out.println("Decrypted: " + ccDecrypted);
                break;

            case "PC":
                System.out.println("Enter key:");
                String key = scanner.nextLine();
                char[][] keySquare = generateKeySquare(key);
                String preparedText = prepareText(plaintext);
                String pcEncrypted = playfairEncrypt(preparedText, keySquare);
                System.out.println("Playfair Cipher:");
                System.out.println("Encrypted: " + pcEncrypted);
                break;

            case "HC":
                String hcEncrypted = hillEncrypt(plaintext);
                System.out.println("Hill Cipher:");
                System.out.println("Encrypted: " + hcEncrypted);
                break;

            case "VC":
                System.out.println("Enter key:");
                String vcKey = scanner.nextLine();
                String vcEncrypted = vigenereEncrypt(plaintext, vcKey);
                String vcDecrypted = vigenereDecrypt(vcEncrypted, vcKey);
                System.out.println("Vigenère Cipher:");
                System.out.println("Encrypted: " + vcEncrypted);
                System.out.println("Decrypted: " + vcDecrypted);
                break;

            default:
                System.out.println("Invalid cipher type!");
        }

        scanner.close();
    }
}