import java.io.IOException;
import java.nio.file.Path;

public class Test {
    public static void main(String[] args) throws IOException {
        MusicSource source = new LocalMusicSource(Path.of("test"));
        for (Track t : source.loadTracks()) {
            System.out.println(t);
        }
    }
}
