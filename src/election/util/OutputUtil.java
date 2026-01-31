package election.util;

public class OutputUtil {
    public static void printWelcomeMessage() {
        System.out.println("=".repeat(30));
        System.out.println("Election system starting...");
        System.out.println("Plurality - Runoff - Tideman");
        System.out.println("=".repeat(30));
    }

    public static void exitApplication() {
        System.out.println("Exiting the election system. Goodbye!");
        System.exit(0);
    }
}