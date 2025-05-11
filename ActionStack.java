import java.util.*;

public class ActionStack {
    private Stack<String> actions;

    public ActionStack() {
        actions = new Stack<>();
    }

    public void pushAction(String action) {
        actions.push(action);
    }

    public String popAction() {
        return actions.isEmpty() ? null : actions.pop();
    }

    public boolean isEmpty() {
        return actions.isEmpty();
    }
}
