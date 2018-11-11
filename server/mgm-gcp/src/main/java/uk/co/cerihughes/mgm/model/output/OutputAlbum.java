package uk.co.cerihughes.mgm.model.output;

import com.google.gson.annotations.SerializedName;
import uk.co.cerihughes.mgm.model.AlbumType;

public class OutputAlbum {
    private transient AlbumType type;
    @SerializedName("spotifyId")
    private String spotifyId;
    @SerializedName("name")
    private String name;
    @SerializedName("artist")
    private String artist;
    @SerializedName("score")
    private Float score;
    @SerializedName("images")
    private OutputImages images;

    public AlbumType getType() {
        return type;
    }

    public void setType(AlbumType type) {
        this.type = type;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyID) {
        this.spotifyId = spotifyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public OutputImages getImages() {
        return images;
    }

    public void setImages(OutputImages images) {
        this.images = images;
    }
}
