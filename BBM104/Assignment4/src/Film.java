import java.util.HashMap;

public class Film {
    private String name;
    private String path;
    private int duration;
    public HashMap<String,Hall> filmHalls = new HashMap<String,Hall>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Film(String name, String path, int duration) {
        this.name = name;
        this.path = path;
        this.duration = duration;
    }
}
