import java.util.*;

public class RunoffElection {
    private final List<String> candidates;
    private List<List<String>> preferences = new ArrayList<>();

    public RunoffElection(List<String> candidates) {
        this.candidates = new ArrayList<>(candidates);
    }

    public void run(Scanner sc) {
        System.out.print("Number of voters: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            List<String> ranks = new ArrayList<>();
            System.out.println("Voter #" + (i + 1));
            for (int j = 0; j < candidates.size(); j++) {
                System.out.print("Rank " + (j + 1) + ": ");
                String name = sc.nextLine();
                if (candidates.contains(name) && !ranks.contains(name)) {
                    ranks.add(name);
                } else {
                    System.out.println("Invalid or duplicate vote. Try again.");
                    j--;
                }
            }
            preferences.add(ranks);
        }

        while (true) {
            Map<String, Integer> voteCount = new HashMap<>();
            for (String c : candidates) voteCount.put(c, 0);

            for (List<String> ballot : preferences) {
                for (String name : ballot) {
                    if (candidates.contains(name)) {
                        voteCount.put(name, voteCount.get(name) + 1);
                        break;
                    }
                }
            }

            int majority = preferences.size() / 2 + 1;
            for (String candidate : candidates) {
                if (voteCount.get(candidate) >= majority) {
                    System.out.println("Winner: " + candidate);
                    return;
                }
            }

            int min = Collections.min(voteCount.values());
            List<String> toRemove = new ArrayList<>();
            for (String c : candidates) {
                if (voteCount.get(c) == min) toRemove.add(c);
            }

            if (toRemove.size() == candidates.size()) {
                System.out.println("It's a tie!");
                return;
            }

            candidates.removeAll(toRemove);
        }
    }
}
