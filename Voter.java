import java.util.List;

public class Voter {
    private String name;
    private List<String> preferences;

    public Voter(String name, List<String> preferences) {
        this.name = name;
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    public List<String> getPreferences() {
        return preferences;
    }
}
