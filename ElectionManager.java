import java.util.*;

public class ElectionManager {
    private final Map<String, Integer> voteCount = new HashMap<>();
    private final List<String> candidates;

    public ElectionManager(List<String> candidates) {
        this.candidates = candidates;
        for (String candidate : candidates) {
            voteCount.put(candidate, 0);
        }
    }

    public void castVote(String candidateName) {
        if (voteCount.containsKey(candidateName)) {
            voteCount.put(candidateName, voteCount.get(candidateName) + 1);
        }
    }

    public void displayResults() {
        int maxVotes = Collections.max(voteCount.values());
        System.out.println("Winner(s):");
        for (String candidate : candidates) {
            if (voteCount.get(candidate) == maxVotes) {
                System.out.println(candidate);
            }
        }
    }
}
