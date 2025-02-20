import java.util.Scanner;

public class RailFenceCipher {

    // Encrypt the plaintext using Rail Fence Cipher
    public static String encrypt(String plainText, int key) {
        if (key <= 1) return plainText;

        StringBuilder[] rails = new StringBuilder[key];
        for (int i = 0; i < key; i++) {
            rails[i] = new StringBuilder();
        }

        int row = 0;
        boolean downDirection = false;

        for (char c : plainText.toCharArray()) {
            rails[row].append(c);

            // Reverse direction if hitting the top or bottom rail
            if (row == 0 || row == key - 1) {
                downDirection = !downDirection;
            }
            row += downDirection ? 1 : -1;
        }

        // Combine rows to get the encrypted text
        StringBuilder encryptedText = new StringBuilder();
        for (StringBuilder rail : rails) {
            encryptedText.append(rail);
        }

        return encryptedText.toString();
    }

    // Decrypt the ciphertext using Rail Fence Cipher
    public static String decrypt(String cipherText, int key) {
        if (key <= 1) return cipherText;

        char[] decryptedText = new char[cipherText.length()];
        boolean[] railMarks = new boolean[cipherText.length()];
        int row = 0;
        boolean downDirection = false;
        int index = 0;

        // Mark positions for rails
        for (int railCycle = 0; railCycle < key; railCycle++) {
            row = railCycle;
            downDirection = (row == 0) || (row == key - 1);

            for (int j = 0; j < cipherText.length(); j++) {
                if (row == 0 || row == key - 1 || railMarks[j]) {
                    if (index < cipherText.length() && !railMarks[j]) {
                        decryptedText[j] = cipherText.charAt(index++);
                        railMarks[j] = true;
                    }
                }
                row += downDirection ? 1 : -1;
            }
        }
        return String.valueOf(decryptedText);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the plain text: ");
        String plainText = scanner.nextLine();

        System.out.print("Enter the key (number of rails): ");
        int key = scanner.nextInt();

        String encryptedText = encrypt(plainText, key);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = decrypt(encryptedText, key);
        System.out.println("Decrypted Text: " + decryptedText);

        scanner.close();
    }
}
