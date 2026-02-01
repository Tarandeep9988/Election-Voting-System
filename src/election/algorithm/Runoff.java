package election.algorithm;

import election.util.InputUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Runoff election implementation (instant-runoff voting / ranked-choice).
 */
public class Runoff {

  private class Candidate {
    private final String name;
    private int votes;
    private boolean eliminated;

    Candidate(String name) {
      this.name = name;
      this.votes = 0;
      this.eliminated = false;
    }

    public String getName() {
      return name;
    }

    public int getVotes() {
      return votes;
    }

    public void setVotes(int votes) {
      this.votes = votes;
    }

    public void incrementVotes() {
      this.votes++;
    }

    public boolean isEliminated() {
      return eliminated;
    }

    public void eliminate() {
      this.eliminated = true;
    }

    @Override
    public String toString() {
      return name + (eliminated ? " (eliminated)" : "") + ": " + votes + " votes";
    }
  }

  // Core data structures
  private final List<Candidate> candidates = new ArrayList<>();
  // Each ballot is an array of candidate indices ordered by preference (0 = top choice)
  private List<int[]> ballots = new ArrayList<>();
  // Map candidate name -> index for quick lookup
  private final Map<String, Integer> nameToIndex = new HashMap<>();

  /** Entry point to run the runoff election flow. */
  public void run() {
    System.out.println("Running Runoff Election...");

    String[] candidateNames = InputUtil.getCandidateNames();
    while (true) {
      if (candidateNames.length < 2) {
        System.out.println("Please enter at least two candidates for a runoff election.");
        candidateNames = InputUtil.getCandidateNames();
      } else {
        break;
      }
    }

    initCandidates(candidateNames);
    ballots = InputUtil.getBallots(candidateNames);

    conduct();

    System.out.println("Runoff Election completed.");
  }

  private void initCandidates(String[] candidateNames) {
    candidates.clear();
    nameToIndex.clear();
    for (int i = 0; i < candidateNames.length; i++) {
      candidates.add(new Candidate(candidateNames[i]));
      nameToIndex.put(candidateNames[i], i);
    }
  }

  public void conduct() {
    while (true) {
      resetVotes();
      tallyVotes();

      printTally();

      int winnerIndex = checkForWinner();
      if (winnerIndex != -1) {
        System.out.println("Winner: " + candidates.get(winnerIndex).getName());
        return;
      }

      if (isTieAmongRemaining()) {
        System.out.println("Election results in a tie among remaining candidates:");
        for (Candidate c : candidates) {
          if (!c.isEliminated()) {
            System.out.println(c.getName());
          }
        }
        return;
      }

      eliminateLowestCandidates();
    }
  }

  private void resetVotes() {
    for (Candidate c : candidates) {
      c.setVotes(0);
    }
  }

  private void tallyVotes() {
    for (int[] ballot : ballots) {
      for (int pref : ballot) {
        if (pref < 0 || pref >= candidates.size()) {
          // invalid preference id, skip
          continue;
        }
        Candidate choice = candidates.get(pref);
        if (!choice.isEliminated()) {
          choice.incrementVotes();
          break;
        }
        // otherwise, continue down the ballot to next preference
      }
    }
  }

  private void printTally() {
    System.out.println("Vote tally:");
    for (Candidate c : candidates) {
      System.out.println(c.toString());
    }
  }

  private int checkForWinner() {
    int totalActiveBallots = ballots.size();
    for (int i = 0; i < candidates.size(); i++) {
      Candidate c = candidates.get(i);
      if (!c.isEliminated() && c.getVotes() > totalActiveBallots / 2) {
        return i;
      }
    }
    return -1;
  }

  private boolean isTieAmongRemaining() {
    Integer value = null;
    for (Candidate c : candidates) {
      if (c.isEliminated()) continue;
      if (value == null) value = c.getVotes();
      else if (c.getVotes() != value) return false;
    }
    return true;
  }

  private void eliminateLowestCandidates() {
    int minVotes = Integer.MAX_VALUE;
    for (Candidate c : candidates) {
      if (!c.isEliminated() && c.getVotes() < minVotes) {
        minVotes = c.getVotes();
      }
    }

    System.out.println("Eliminating candidates with " + minVotes + " votes:");
    for (Candidate c : candidates) {
      if (!c.isEliminated() && c.getVotes() == minVotes) {
        c.eliminate();
        System.out.println("- " + c.getName());
      }
    }
  }

  // Utility: find candidate index by name (unused currently but helpful for extension)
  private int findCandidateIndexByName(String name) {
    return nameToIndex.getOrDefault(name, -1);
  }
}
