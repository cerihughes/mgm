package uk.co.cerihughes.mgm.model.interim;

import org.openapitools.model.AlbumApiModel;

public final class InterimAlbum {
    private AlbumApiModel.TypeEnum type;
    private String albumData;
    private Float score;

    private InterimAlbum() {
        super();
    }

    public AlbumApiModel.TypeEnum getType() {
        return type;
    }

    public String getAlbumData() {
        return albumData;
    }

    public Float getScore() {
        return score;
    }

    public static final class Builder {
        private AlbumApiModel.TypeEnum type;
        private String albumData;
        private Float score;

        public Builder setType(AlbumApiModel.TypeEnum type) {
            this.type = type;
            return this;
        }

        public Builder setAlbumData(String albumData) {
            this.albumData = albumData;
            return this;
        }

        public Builder setScore(Float score) {
            this.score = score;
            return this;
        }

        public Builder setScore(String scoreString) {
            try {
                setScore(new Float(scoreString));
            } catch (NullPointerException | NumberFormatException e) {
                // Swallow
            }
            return this;
        }

        public InterimAlbum build() {
            if (type == null || albumData == null || albumData.trim().length() == 0) {
                return null;
            }
            final InterimAlbum album = new InterimAlbum();
            album.type = type;
            album.albumData = albumData;
            album.albumData = albumData;
            album.score = score;
            return album;
        }
    }
}