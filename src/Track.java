import java.nio.file.Path;
import java.util.Objects;

public class Track {
    private final String id;
    private final String title;
    private final String artist;

    public static final long UNKNOWN_DURATION = -1;

    // Stores the song duration in microseconds
    private final long duration;

    public Track(String id, String title, String artist, long duration){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public long getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Track) o).id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return artist + " - " + title + " (" + Long.toString(duration) + ")";
    }

}
