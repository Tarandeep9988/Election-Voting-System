import java.util.*;

public class CandidateBST {
    private Node root;

    class Node {
        Candidate candidate;
        Node left, right;

        Node(Candidate candidate) {
            this.candidate = candidate;
            this.left = this.right = null;
        }
    }

    public void insert(Candidate candidate) {
        root = insertRec(root, candidate);
    }

    private Node insertRec(Node root, Candidate candidate) {
        if (root == null) {
            root = new Node(candidate);
            return root;
        }
        if (candidate.getName().compareTo(root.candidate.getName()) < 0)
            root.left = insertRec(root.left, candidate);
        else if (candidate.getName().compareTo(root.candidate.getName()) > 0)
            root.right = insertRec(root.right, candidate);

        return root;
    }

    public Candidate search(String name) {
        return searchRec(root, name);
    }

    private Candidate searchRec(Node root, String name) {
        if (root == null || root.candidate.getName().equals(name))
            return root == null ? null : root.candidate;

        if (name.compareTo(root.candidate.getName()) < 0)
            return searchRec(root.left, name);

        return searchRec(root.right, name);
    }
}
