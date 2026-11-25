import java.nio.file.Path;

public class FilanameMetadataReader implements  MetadataReader{

    private final String SEPARATOR = " - ";
    public TrackMetadata readMetadata(Path file){
        String filePath = file.getFileName().toString();

        // Remove extension
        filePath = filePath.substring(0,filePath.lastIndexOf('.'));

        //Find the index of the separator and separate the artist and the title
        int separatorIndex = filePath.indexOf(SEPARATOR);

        if(separatorIndex == -1){
            return new TrackMetadata("Unkown Artist", filePath );
        } else {
            String artist = filePath.substring(0, separatorIndex);
            String title = filePath.substring(separatorIndex + SEPARATOR.length());

            if(artist.isEmpty()) artist = "Unkown Artist";
            if(title.isEmpty()) title = "Unknown Title";

            return new TrackMetadata(artist, title);
        }
    }

}
