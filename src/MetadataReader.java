import java.nio.file.Path;

public interface MetadataReader {
    TrackMetadata readMetadata(Path file);
}
