import java.util.*;

public class VoterQueue {
    private Queue<Voter> queue;

    public VoterQueue() {
        queue = new LinkedList<>();
    }

    public void addVoter(Voter voter) {
        queue.add(voter);
    }

    public Voter removeVoter() {
        return queue.isEmpty() ? null : queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
