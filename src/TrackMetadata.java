public class TrackMetadata {
    private final String artist;
    private final String title;

    public TrackMetadata(String artist, String title){
        this.artist = artist;
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return artist + " - " + title;
    }
}
