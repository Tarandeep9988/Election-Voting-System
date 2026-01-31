package election.algorithm;

public class Tideman {
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
  }
  private Candidate[] candidates;

  public void run() {
    System.out.println("Running Tideman Election...");
    System.out.println("Tideman Election completed.");
  }
}
