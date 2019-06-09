package uk.co.cerihughes.mgm.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.co.cerihughes.mgm.model.AlbumType;

import java.util.List;

@ApiModel("AlbumApiModel")
public final class OutputAlbum {
    private AlbumType type;
    private String spotifyId;
    private String name;
    private String artist;
    private Float score;
    private List<OutputImage> images;

    private OutputAlbum() {
        super();
    }

    @ApiModelProperty(required = true)
    public AlbumType getType() {
        return type;
    }

    @ApiModelProperty
    public String getSpotifyId() {
        return spotifyId;
    }

    @ApiModelProperty(required = true)
    public String getName() {
        return name;
    }

    @ApiModelProperty(required = true)
    public String getArtist() {
        return artist;
    }

    @ApiModelProperty
    public Float getScore() {
        return score;
    }

    @ApiModelProperty
    public List<OutputImage> getImages() {
        return images;
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
