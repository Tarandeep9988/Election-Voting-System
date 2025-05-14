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
        int voterCount = Utility.getNumberOfVoters(sc);
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
        for (int i = 0; i < pairs.size() - 1; i++) {
            for (int j = 0; j < pairs.size() - i - 1; j++) {
                int strength1 = preferences[pairs.get(j).winner][pairs.get(j).loser];
                int strength2 = preferences[pairs.get(j + 1).winner][pairs.get(j + 1).loser];

                if (strength1 < strength2) {
                    // Swap pairs[j] and pairs[j + 1]
                    Pair temp = pairs.get(j);
                    pairs.set(j, pairs.get(j + 1));
                    pairs.set(j + 1, temp);
                }
            }
        }
    }


    private void lockPairs() {
        for (Pair pair : pairs) {
            if (!createsCycle(pair.winner, pair.loser)) {
                locked[pair.winner][pair.loser] = true;
            }
        }
    }

    private boolean createsCycle(int winner, int loser) {
        boolean[] visited = new boolean[candidates.size()];
        return createsCycleRec(winner, loser, visited);
    }

    private boolean createsCycleRec(int start, int current, boolean[] visited) {
        if (visited[current]) return false;
        visited[current] = true;
        for (int i = 0; i < candidates.size(); i++) {
            if (locked[current][i] && (i == start || createsCycleRec(start, i, visited))) {
                return true;
            }
        }
        return false;
    }

    private void printWinner() {
        for (int i = 0; i < candidates.size(); i++) {
            boolean isWinner = true;
            for (int j = 0; j < candidates.size(); j++) {
                if (locked[j][i]) {
                    isWinner = false;
                    break;
                }
            }
            if (isWinner) {
                System.out.println("Winner: " + candidates.get(i));
                return;
            }
        }
    }
}
