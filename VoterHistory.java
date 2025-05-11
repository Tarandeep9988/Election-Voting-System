import java.util.*;

public class VoterHistory {
    private List<Voter> voters = new ArrayList<>();

    public void addVoter(Voter voter) {
        voters.add(voter);
    }

    public List<Voter> getVoters() {
        return voters;
    }

    public Voter getVoter(String name) {
        for (Voter voter : voters) {
            if (voter.getName().equals(name)) {
                return voter;
            }
        }
        return null;
    }
}
