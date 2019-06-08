package uk.co.cerihughes.mgm.model.output;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

public final class OutputPlaylist {
    @XmlAttribute(name = "spotifyId")
    private String spotifyId;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "owner")
    private String owner;
    @XmlAttribute(name = "images")
    private List<OutputImage> images;

    private OutputPlaylist() {
        super();
    }

    public static final class Builder {
        private String spotifyId;
        private String name;
        private String owner;
        private List<OutputImage> images;

        public Builder setSpotifyId(String spotifyId) {
            this.spotifyId = spotifyId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder setImages(List<OutputImage> images) {
            this.images = images;
            return this;
        }

        public OutputPlaylist build() {
            if (spotifyId == null || name == null || owner == null) {
                return null;
            }
            final OutputPlaylist playlist = new OutputPlaylist();
            playlist.spotifyId = spotifyId;
            playlist.name = name;
            playlist.owner = owner;
            playlist.images = images;
            return playlist;
        }
    }
}
