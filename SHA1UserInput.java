import java.security.*;
import java.util.Scanner;

public class SHA1UserInput {
    public static void main(String[] args) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            Scanner sc = new Scanner(System.in);

            System.out.println("Message Digest Object Info:");
            System.out.println("---------------------------");
            System.out.println("Algorithm = " + md.getAlgorithm());
            System.out.println("Provider  = " + md.getProvider());
            System.out.println("ToString  = " + md.toString());

            System.out.print("\nEnter a string to hash: ");
            String input = sc.nextLine();
            
            md.update(input.getBytes());
            byte[] output = md.digest();

            System.out.println("\nSHA-1(\"" + input + "\") = " + bytesToHex(output));

            sc.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    private static String bytesToHex(byte[] b) {
        char[] hexDigit = "0123456789ABCDEF".toCharArray();
        StringBuilder buf = new StringBuilder();

        for (byte aB : b) {
            buf.append(hexDigit[(aB >> 4) & 0x0F]);
            buf.append(hexDigit[aB & 0x0F]);
        }

        return buf.toString();
    }
}