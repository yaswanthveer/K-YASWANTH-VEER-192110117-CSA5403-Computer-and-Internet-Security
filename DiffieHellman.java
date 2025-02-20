import java.util.Scanner;

public class DiffieHellman {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter prime number (p): ");
        int p = sc.nextInt();

        System.out.print("Enter primitive root (g): ");
        int g = sc.nextInt();

        System.out.print("Enter Alice's secret (x): ");
        int x = sc.nextInt();

        System.out.print("Enter Bob's secret (y): ");
        int y = sc.nextInt();

        double aliceSends = (Math.pow(g, x)) % p;
        double bobComputes = (Math.pow(aliceSends, y)) % p;
        double bobSends = (Math.pow(g, y)) % p;
        double aliceComputes = (Math.pow(bobSends, x)) % p;
        double sharedSecret = (Math.pow(g, (x * y))) % p;

        System.out.println("\nSimulation of Diffie-Hellman Key Exchange Algorithm");
        System.out.println("---------------------------------------------");
        System.out.println("Alice Sends : " + aliceSends);
        System.out.println("Bob Computes : " + bobComputes);
        System.out.println("Bob Sends : " + bobSends);
        System.out.println("Alice Computes : " + aliceComputes);
        System.out.println("Shared Secret : " + sharedSecret);

        if ((aliceComputes == sharedSecret) && (aliceComputes == bobComputes))
            System.out.println("Success: Shared Secrets Match! " + sharedSecret);
        else
            System.out.println("Error: Shared Secrets do not Match");

        sc.close();
    }
}