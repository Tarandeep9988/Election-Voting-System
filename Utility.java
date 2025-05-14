import java.util.InputMismatchException;
import java.util.Scanner;

public class Utility {
    public static int getNumberOfVoters(Scanner sc) {
        int n = 0;
        do {
            try {
                System.out.print("Enter Number of voters: ");
                n = sc.nextInt();
                if (n < 1) throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.out.println("Invalid voters number! Again");
                sc.nextLine();
                n = 0;
            }
        } while (n == 0);
        return n;
    }
}
