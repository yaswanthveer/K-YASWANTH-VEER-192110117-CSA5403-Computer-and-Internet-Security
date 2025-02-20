import java.util.Scanner;

class TransCipher {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        // Input: Plain Text
        System.out.println("Enter the plain text:");
        String pl = sc.nextLine();
        sc.close();

        // Remove spaces from the input
        String s = pl.replaceAll(" ", "");
        System.out.println("Text after removing spaces: " + s);

        int k = s.length();
        int col = 4; // Fixed number of columns
        int row = (k + col - 1) / col; // Calculate rows required

        char ch[][] = new char[row][col];
        
        // Fill the matrix row-wise with characters or '#' for padding
        int l = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (l < k) {
                    ch[i][j] = s.charAt(l);
                    l++;
                } else {
                    ch[i][j] = '#'; // Padding
                }
            }
        }

        // Transposition: Create the transposed matrix (Column-wise read)
        System.out.println("\nEncrypted Text:");
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                System.out.print(ch[i][j]);
            }
        }
        System.out.println();
    }
}
