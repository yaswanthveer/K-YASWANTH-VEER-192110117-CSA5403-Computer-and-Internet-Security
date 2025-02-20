import java.util.Scanner;
class HillCipher {

    // 3x3 key matrix for encryption
    public static int[][] keyMatrix = { { 1, 2, 1 }, { 2, 3, 2 }, { 2, 2, 1 } };

    // Inverse key matrix modulo 26
    public static int[][] invKeyMatrix = { { -1, 0, 1 }, { 2, -1, 0 }, { -2, 2, -1 } };

    public static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Function to encode a triplet of characters
    private static String encode(char a, char b, char c) {
        String result = "";

        int posa = a - 'A';
        int posb = b - 'A';
        int posc = c - 'A';

        int x = (posa * keyMatrix[0][0] + posb * keyMatrix[1][0] + posc * keyMatrix[2][0]) % 26;
        int y = (posa * keyMatrix[0][1] + posb * keyMatrix[1][1] + posc * keyMatrix[2][1]) % 26;
        int z = (posa * keyMatrix[0][2] + posb * keyMatrix[1][2] + posc * keyMatrix[2][2]) % 26;

        x = (x + 26) % 26;
        y = (y + 26) % 26;
        z = (z + 26) % 26;

        result += alphabet.charAt(x);
        result += alphabet.charAt(y);
        result += alphabet.charAt(z);

        return result;
    }

    // Function to decode a triplet of characters
    private static String decode(char a, char b, char c) {
        String result = "";

        int posa = a - 'A';
        int posb = b - 'A';
        int posc = c - 'A';

        int x = (posa * invKeyMatrix[0][0] + posb * invKeyMatrix[1][0] + posc * invKeyMatrix[2][0]) % 26;
        int y = (posa * invKeyMatrix[0][1] + posb * invKeyMatrix[1][1] + posc * invKeyMatrix[2][1]) % 26;
        int z = (posa * invKeyMatrix[0][2] + posb * invKeyMatrix[1][2] + posc * invKeyMatrix[2][2]) % 26;

        x = (x + 26) % 26;
        y = (y + 26) % 26;
        z = (z + 26) % 26;

        result += alphabet.charAt(x);
        result += alphabet.charAt(y);
        result += alphabet.charAt(z);

        return result;
    }

    public static void main(String[] args) {
        String msg = "SecurityLaboratory";
        String encryptedText = "";
        String decryptedText = "";

        System.out.println("Simulation of Hill Cipher");
        System.out.println("-------------------------");
        System.out.println("Input message: " + msg);

        msg = msg.toUpperCase().replaceAll("\\s+", "");

        // Padding if the length is not divisible by 3
        int paddingNeeded = msg.length() % 3;
        if (paddingNeeded != 0) {
            for (int i = 0; i < (3 - paddingNeeded); i++) {
                msg += 'X';
            }
        }
        System.out.println("Padded message: " + msg);

        // Encrypt message
        char[] messageChars = msg.toCharArray();
        for (int i = 0; i < messageChars.length; i += 3) {
            encryptedText += encode(messageChars[i], messageChars[i + 1], messageChars[i + 2]);
        }
        System.out.println("Encoded message: " + encryptedText);

        // Decrypt message
        char[] encryptedChars = encryptedText.toCharArray();
        for (int i = 0; i < encryptedChars.length; i += 3) {
            decryptedText += decode(encryptedChars[i], encryptedChars[i + 1], encryptedChars[i + 2]);
        }
        System.out.println("Decoded message: " + decryptedText);
    }
}
