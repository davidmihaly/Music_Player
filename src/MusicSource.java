import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface MusicSource {
    String getName();
    List<Track> loadTracks() throws IOException;
    InputStream openAudio(Track track) throws IOException;
}
