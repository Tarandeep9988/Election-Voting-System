import java.util.*;

class Candidate {
    String name;
    boolean eliminated;

    Candidate(String name) {
        this.name = name;
        this.eliminated = false;
    }
}

public class RunoffElection {
    private Map<String, Candidate> candidateMap = new HashMap<>();
    private List<Queue<String>> ballots = new ArrayList<>();

    public RunoffElection(List<String> candidates) {
        for (String name : candidates) {
            candidateMap.put(name, new Candidate(name));
        }
    }

    public void run(Scanner sc) {
        System.out.print("Number of voters: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            Queue<String> ballot = new LinkedList<>();
            System.out.println("Voter #" + (i + 1));
            for (int j = 0; j < candidateMap.size(); j++) {
                System.out.print("Rank " + (j + 1) + ": ");
                String name = sc.nextLine();
                if (candidateMap.containsKey(name) && !ballot.contains(name)) {
                    ballot.offer(name);
                } else {
                    System.out.println("Invalid or duplicate vote. Try again.");
                    j--;
                }
            }
            ballots.add(ballot);
        }

        while (true) {
            Map<String, Integer> voteCount = new HashMap<>();
            for (Queue<String> ballot : ballots) {
                while (!ballot.isEmpty() && candidateMap.get(ballot.peek()).eliminated) {
                    ballot.poll(); // remove eliminated candidates from front
                }
                if (!ballot.isEmpty()) {
                    String choice = ballot.peek();
                    voteCount.put(choice, voteCount.getOrDefault(choice, 0) + 1);
                }
            }

            int majority = ballots.size() / 2 + 1;
            for (Map.Entry<String, Integer> entry : voteCount.entrySet()) {
                if (entry.getValue() >= majority) {
                    System.out.println("Winner: " + entry.getKey());
                    return;
                }
            }

            int minVotes = Collections.min(voteCount.values());
            List<String> toEliminate = new ArrayList<>();
            for (Candidate candidate : candidateMap.values()) {
                if (!candidate.eliminated && voteCount.getOrDefault(candidate.name, 0) == minVotes) {
                    toEliminate.add(candidate.name);
                }
            }

            if (toEliminate.size() == countRemainingCandidates()) {
                System.out.println("It's a tie!");
                return;
            }

            for (String name : toEliminate) {
                candidateMap.get(name).eliminated = true;
            }
        }
    }

    private int countRemainingCandidates() {
        int count = 0;
        for (Candidate c : candidateMap.values()) {
            if (!c.eliminated) count++;
        }
        return count;
    }
}
