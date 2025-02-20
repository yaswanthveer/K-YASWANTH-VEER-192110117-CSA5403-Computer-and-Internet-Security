import java.security.*;
import java.util.Scanner;

public class DSS {
   public static void main(String args[]) throws Exception {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter some text");
      String msg = sc.nextLine();  

      KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
      keyPairGen.initialize(2048);
      KeyPair pair = keyPairGen.generateKeyPair();
      PrivateKey privKey = pair.getPrivate();

      Signature sign = Signature.getInstance("SHA256withDSA");
      sign.initSign(privKey);
      byte[] bytes = msg.getBytes(); // **Fixed: Now using user input**
      
      sign.update(bytes);
      byte[] signature = sign.sign();

      System.out.println("Digital signature for given text: " + bytesToHex(signature));
      sc.close();
   }

   private static String bytesToHex(byte[] bytes) {
      StringBuilder hexString = new StringBuilder();
      for (byte b : bytes) {
         hexString.append(String.format("%02X", b));
      }
      return hexString.toString();
   }
}