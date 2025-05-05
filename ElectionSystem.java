import java.util.*;

public class ElectionSystem {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java ElectionSystem [candidate1] [candidate2] ...");
            return;
        }

        List<String> candidates = Arrays.asList(args);
        Scanner sc = new Scanner(System.in);

        System.out.println("Candidates: " + candidates);
        System.out.println("Choose election type:");
        System.out.println("1. Plurality");
        System.out.println("2. Runoff");
        System.out.println("3. Tideman");
        System.out.print("Enter your choice (1-3): ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> new PluralityElection(candidates).run(sc);
            case 2 -> new RunoffElection(candidates).run(sc);
            case 3 -> new TidemanElection(candidates).run(sc);
            default -> System.out.println("Invalid choice.");
        }

        sc.close();
    }
}
