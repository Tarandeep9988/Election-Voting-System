package election.algorithm;

import election.util.InputUtil;

public class Plurality {
  private class Candidate {
    private String name;
    private int votes;

    Candidate(String name) {
      this.name = name;
      this.votes = 0;
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
  }
  private Candidate[] candidates;
  private String[] candidateNames;
  public void run() {
    System.out.println("Running Plurality Election...");
  
    // Taking candidates input
    candidateNames = InputUtil.getCandidateNames();
    // Create Candidate objects
    candidates = new Candidate[candidateNames.length];
    for (int i = 0; i < candidateNames.length; i++) {
      candidates[i] = new Candidate(candidateNames[i]);
    }

    conduct();

    System.out.println("Plurality Election completed.");
  }
  public void conduct() {
    int[] votes = InputUtil.getVotes(candidateNames);
    System.out.println("Vote tally:");
    for (int i = 0; i < candidates.length; i++) {
      candidates[i].setVotes(votes[i]);
      System.out.println(candidates[i].getName() + ": " + candidates[i].getVotes() + " votes");
    }
    int maxVotes = 0;
    for (int i = 0; i < candidates.length; i++) {
      if (candidates[i].getVotes() > maxVotes) {
        maxVotes = candidates[i].getVotes();
      }
    }
    System.out.println("Winner(s):");
    for (int i = 0; i < candidates.length; i++) {
      if (candidates[i].getVotes() == maxVotes) {
        System.out.println(candidates[i].getName());
      }
    }
  }
}
