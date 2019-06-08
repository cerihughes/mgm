package uk.co.cerihughes.mgm.model.output;

import uk.co.cerihughes.mgm.model.AlbumType;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

public final class OutputAlbum {
    @XmlAttribute(name = "type")
    private AlbumType type;
    @XmlAttribute(name = "spotifyId")
    private String spotifyId;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "artist")
    private String artist;
    @XmlAttribute(name = "score")
    private Float score;
    @XmlAttribute(name = "images")
    private List<OutputImage> images;

    private OutputAlbum() {
        super();
    }

    public static final class Builder {
        private AlbumType type;
        private String spotifyId;
        private String name;
        private String artist;
        private Float score;
        private List<OutputImage> images;

        public Builder setType(AlbumType type) {
            this.type = type;
            return this;
        }

        public Builder setSpotifyId(String spotifyId) {
            this.spotifyId = spotifyId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setArtist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder setScore(Float score) {
            this.score = score;
            return this;
        }

        public Builder setImages(List<OutputImage> images) {
            this.images = images;
            return this;
        }

        public OutputAlbum build() {
            if (type == null || name == null || artist == null) {
                return null;
            }
            final OutputAlbum album = new OutputAlbum();
            album.type = type;
            album.name = name;
            album.artist = artist;
            album.spotifyId = spotifyId;
            album.score = score;
            album.images = images;
            return album;
        }
    }
}
