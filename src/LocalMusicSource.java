import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class LocalMusicSource implements MusicSource{

    private final FilanameMetadataReader metadataReader;
    private final Path path;
    public LocalMusicSource(Path path){
        metadataReader = new FilanameMetadataReader();
        this.path = path;
    }

    public String getName(){
        return path.toString();
    }

    public List<Track> loadTracks() throws IOException{
        if(!Files.isDirectory(path))
            throw new IOException("Folder dosn't exist");

        List <Track> tracks = new ArrayList<>();
        try(Stream<Path> paths = Files.walk(path)){
            List<Path> files = paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".wav"))
                    .sorted()
                    .toList();
            for(Path file: files){
                try{
                    tracks.add(createTrack(file));
                } catch (UnsupportedAudioFileException | IOException e){
                    System.err.println("Skipping: " + file + ": " + e.getMessage());
                }
            }

        }
        return tracks;
    }

    private Track createTrack(Path file) throws UnsupportedAudioFileException, IOException{
        // Try to get the audio file format, if unsupported throws UnsupportedAudioFileException
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file.toFile());
        AudioFormat format = fileFormat.getFormat();

        // Calculate the duration in microseconds
        long frames = fileFormat.getFrameLength();
        float frameRate = format.getFrameRate();
        long durationMicros;

        // Check if the frames are specified if not set to UNKNOWN_DURATION
        if (frames == AudioSystem.NOT_SPECIFIED || frameRate <= 0) {
            durationMicros = Track.UNKNOWN_DURATION;
        } else {
            // Calculate duration in microseconds
            durationMicros = (long) (frames * 1_000_000.0 / frameRate);
        }

        TrackMetadata data = metadataReader.readMetadata(file);
        return new Track(file.toAbsolutePath().toString(),data.getTitle(), data.getArtist(), durationMicros);
    }

    public InputStream openAudio(Track track) throws IOException {
        Path path = Path.of(track.getId());
        return new BufferedInputStream(Files.newInputStream(path));
    }
}
