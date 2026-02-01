package election.algorithm;

import election.util.InputUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Tideman election implementation (ranked pairs / Condorcet method).
 * Uses pairwise comparisons to build a directed acyclic graph (DAG) and find winner.
 */
public class Tideman {

  private class Candidate {
    private final String name;
    private final int index;

    Candidate(String name, int index) {
      this.name = name;
      this.index = index;
    }

    public String getName() {
      return name;
    }

    public int getIndex() {
      return index;
    }

    @Override
    public String toString() {
      return name;
    }
  }

  private class Pair implements Comparable<Pair> {
    private final int winner;  // Candidate index with more votes
    private final int loser;   // Candidate index with fewer votes
    private final int strength; // Margin of victory

    Pair(int winner, int loser, int strength) {
      this.winner = winner;
      this.loser = loser;
      this.strength = strength;
    }

    public int getWinner() {
      return winner;
    }

    public int getLoser() {
      return loser;
    }

    public int getStrength() {
      return strength;
    }

    @Override
    public int compareTo(Pair other) {
      // Sort by strength in descending order (strongest pairs first)
      return Integer.compare(other.strength, this.strength);
    }

    @Override
    public String toString() {
      return candidates.get(winner).getName() + " beats " +
             candidates.get(loser).getName() + " (" + strength + " votes)";
    }
  }

  // Core data structures
  private final List<Candidate> candidates = new ArrayList<>();
  private List<int[]> ballots = new ArrayList<>();
  // Pairwise comparison matrix: preferences[i][j] = votes for i > j
  private int[][] preferences;
  // Directed graph: graph[i][j] = 1 if i beats j (edge exists)
  private int[][] graph;

  public void run() {
    System.out.println("Running Tideman Election...");

    String[] candidateNames = InputUtil.getCandidateNames();
    if (candidateNames.length < 2) {
      System.out.println("Need at least two candidates for a Tideman election.");
      return;
    }

    initCandidates(candidateNames);
    ballots = InputUtil.getBallots(candidateNames);

    conduct();

    System.out.println("Tideman Election completed.");
  }

  private void initCandidates(String[] candidateNames) {
    candidates.clear();
    for (int i = 0; i < candidateNames.length; i++) {
      candidates.add(new Candidate(candidateNames[i], i));
    }
    int n = candidateNames.length;
    preferences = new int[n][n];
    graph = new int[n][n];
  }

  public void conduct() {
    // Step 1: Build pairwise comparison matrix
    buildPreferences();

    // Step 2: Build list of pairs sorted by strength
    List<Pair> pairs = buildPairsList();

    // Step 3: Build graph by adding pairs (skip if creates cycle)
    buildGraph(pairs);

    // Step 4: Find and announce winner
    int winner = findWinner();
    if (winner != -1) {
      System.out.println("Winner: " + candidates.get(winner).getName());
    } else {
      System.out.println("No clear winner (tie or cycle).");
    }
  }

  private void buildPreferences() {
    System.out.println("\nBuilding pairwise preferences...");
    for (int[] ballot : ballots) {
      for (int i = 0; i < ballot.length; i++) {
        for (int j = i + 1; j < ballot.length; j++) {
          int pref1 = ballot[i];
          int pref2 = ballot[j];
          // voter prefers pref1 over pref2
          preferences[pref1][pref2]++;
        }
      }
    }

    // Display preference matrix
    System.out.println("Preference matrix:");
    System.out.print("   ");
    for (Candidate c : candidates) {
      System.out.printf("%6s", c.getName());
    }
    System.out.println();
    for (int i = 0; i < candidates.size(); i++) {
      System.out.printf("%3s", candidates.get(i).getName());
      for (int j = 0; j < candidates.size(); j++) {
        if (i == j) {
          System.out.print("     -");
        } else {
          System.out.printf("%6d", preferences[i][j]);
        }
      }
      System.out.println();
    }
  }

  private List<Pair> buildPairsList() {
    List<Pair> pairs = new ArrayList<>();
    int n = candidates.size();

    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (preferences[i][j] > preferences[j][i]) {
          // i beats j
          pairs.add(new Pair(i, j, preferences[i][j] - preferences[j][i]));
        } else if (preferences[j][i] > preferences[i][j]) {
          // j beats i
          pairs.add(new Pair(j, i, preferences[j][i] - preferences[i][j]));
        }
        // If equal, no pair added (tie)
      }
    }

    // Sort by strength (descending)
    pairs.sort(null);

    System.out.println("\nPairs (sorted by strength):");
    for (Pair p : pairs) {
      System.out.println("  " + p);
    }

    return pairs;
  }

  private void buildGraph(List<Pair> pairs) {
    System.out.println("\nBuilding graph (adding pairs, skipping cycles):");
    for (Pair p : pairs) {
      int winner = p.getWinner();
      int loser = p.getLoser();

      // Check if adding this edge would create a cycle
      if (!createsCycle(winner, loser)) {
        graph[winner][loser] = 1;
        System.out.println("  Added: " + candidates.get(winner).getName() +
                           " -> " + candidates.get(loser).getName());
      } else {
        System.out.println("  Skipped (would create cycle): " +
                           candidates.get(winner).getName() +
                           " -> " + candidates.get(loser).getName());
      }
    }
  }

  private boolean createsCycle(int source, int target) {
    // If there's a path from target to source, adding source -> target creates a cycle
    return hasPath(target, source);
  }

  private boolean hasPath(int start, int end) {
    if (start == end) return true;

    boolean[] visited = new boolean[candidates.size()];
    return dfs(start, end, visited);
  }

  private boolean dfs(int current, int target, boolean[] visited) {
    if (current == target) return true;
    if (visited[current]) return false;

    visited[current] = true;

    for (int next = 0; next < candidates.size(); next++) {
      if (graph[current][next] == 1 && dfs(next, target, visited)) {
        return true;
      }
    }

    return false;
  }

  private int findWinner() {
    System.out.println("\nFinding winner (candidate with no incoming edges)...");

    for (int i = 0; i < candidates.size(); i++) {
      boolean hasIncoming = false;
      for (int j = 0; j < candidates.size(); j++) {
        if (graph[j][i] == 1) {
          hasIncoming = true;
          break;
        }
      }
      if (!hasIncoming) {
        System.out.println("  " + candidates.get(i).getName() + " has no incoming edges.");
        return i;
      }
    }

    return -1;  // No clear winner
  }
}
