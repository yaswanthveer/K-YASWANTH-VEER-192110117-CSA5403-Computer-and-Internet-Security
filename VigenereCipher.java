
public class VigenereCipher {

    // Encode the message using Vigenere Cipher
    static String encode(String text, final String key) {
        StringBuilder res = new StringBuilder();
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // Encrypt only alphabetic characters
            if (c >= 'A' && c <= 'Z') {
                res.append((char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A'));
                j = ++j % key.length();
            } else {
                res.append(c); // Append special characters as is
            }
        }
        return res.toString();
    }

    // Decode the message using Vigenere Cipher
    static String decode(String text, final String key) {
        StringBuilder res = new StringBuilder();
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // Decrypt only alphabetic characters
            if (c >= 'A' && c <= 'Z') {
                res.append((char) ((c - key.charAt(j) + 26) % 26 + 'A'));
                j = ++j % key.length();
            } else {
                res.append(c); // Append special characters as is
            }
        }
        return res.toString();
    }

    public static void main(String[] args) {
        String key = "VIGENERECIPHER";
        String msg = "Security Laboratory";

        System.out.println("Simulating Vigenere Cipher\n------------------------");
        System.out.println("Input Message: " + msg);

        String encryptedMessage = encode(msg, key);
        System.out.println("Encrypted Message: " + encryptedMessage);

        String decryptedMessage = decode(encryptedMessage, key);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }
}
