import java.util.*;

public class PluralityElection {
    private final Map<String, Integer> votes = new HashMap<>();
    private final List<String> candidates;

    public PluralityElection(List<String> candidates) {
        this.candidates = candidates;
        for (String candidate : candidates) {
            votes.put(candidate, 0);
        }
    }

    public void run(Scanner sc) {
        System.out.print("Number of voters: ");
        int n = sc.nextInt();
        sc.nextLine(); // Consume newline

        for (int i = 0; i < n; ) {
            System.out.print("Vote #" + (i + 1) + ": ");
            String name = sc.nextLine();
            if (votes.containsKey(name)) {
                votes.put(name, votes.get(name) + 1);
                i++;
            } else {
                System.out.println("Invalid vote! Again");
            }
        }

        int maxVotes = Collections.max(votes.values());
        System.out.println("Winner(s):");
        for (String candidate : candidates) {
            if (votes.get(candidate) == maxVotes) {
                System.out.println(candidate);
            }
        }
    }
}
