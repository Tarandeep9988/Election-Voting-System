import java.util.*;

public class TidemanElection {
    private static final int MAX = 9;
    private final int[][] preferences;
    private final boolean[][] locked;
    private final List<String> candidates;
    private final List<Pair> pairs;

    public TidemanElection(List<String> candidates) {
        this.candidates = candidates;
        preferences = new int[MAX][MAX];
        locked = new boolean[MAX][MAX];
        pairs = new ArrayList<>();
    }

    record Pair(int winner, int loser) {}

    public void run(Scanner sc) {
        System.out.print("Number of voters: ");
        int voterCount = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < voterCount; i++) {
            int[] ranks = new int[candidates.size()];
            System.out.println("Voter #" + (i + 1));
            for (int j = 0; j < candidates.size(); j++) {
                System.out.print("Rank " + (j + 1) + ": ");
                String name = sc.nextLine();
                int index = candidates.indexOf(name);
                if (index != -1) {
                    ranks[j] = index;
                } else {
                    System.out.println("Invalid candidate.");
                    j--;
                }
            }
            recordPreferences(ranks);
        }

        addPairs();
        sortPairs();
        lockPairs();
        printWinner();
    }

    private void recordPreferences(int[] ranks) {
        for (int i = 0; i < ranks.length; i++) {
            for (int j = i + 1; j < ranks.length; j++) {
                preferences[ranks[i]][ranks[j]]++;
            }
        }
    }

    private void addPairs() {
        for (int i = 0; i < candidates.size(); i++) {
            for (int j = 0; j < candidates.size(); j++) {
                if (preferences[i][j] > preferences[j][i]) {
                    pairs.add(new Pair(i, j));
                }
            }
        }
    }

    private void sortPairs() {
        pairs.sort((a, b) -> preferences[b.winner()][b.loser()] - preferences[a.winner()][a.loser()]);
    }

    private void lockPairs() {
        for (Pair pair : pairs) {
            if (!createsCycle(pair.winner(), pair.loser())) {
                locked[pair.winner()][pair.loser()] = true;
            }
        }
    }

    private boolean createsCycle(int winner, int loser) {
        return checkCycle(loser, winner);
    }

    private boolean checkCycle(int start, int target) {
        if (start == target) return true;
        for (int i = 0; i < candidates.size(); i++) {
            if (locked[start][i] && checkCycle(i, target)) {
                return true;
            }
        }
        return false;
    }

    private void printWinner() {
        for (int i = 0; i < candidates.size(); i++) {
            boolean isSource = true;
            for (int j = 0; j < candidates.size(); j++) {
                if (locked[j][i]) {
                    isSource = false;
                    break;
                }
            }
            if (isSource) {
                System.out.println("Winner: " + candidates.get(i));
                return;
            }
        }
    }
}
