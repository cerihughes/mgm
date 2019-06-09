package uk.co.cerihughes.mgm.model.output;

import io.swagger.annotations.ApiModel;

import java.time.LocalDate;

@ApiModel("EventApiModel")
public final class OutputEvent {
    private int number;
    private LocalDate date;
    private OutputLocation location;
    private OutputAlbum classicAlbum;
    private OutputAlbum newAlbum;
    private OutputPlaylist playlist;

    private OutputEvent(int number) {
        super();

        this.number = number;
    }

    public static final class Builder {
        private int number;
        private LocalDate date;
        private OutputLocation location;
        private OutputAlbum classicAlbum;
        private OutputAlbum newAlbum;
        private OutputPlaylist playlist;

        public Builder(int number) {
            super();

            this.number = number;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder setLocation(OutputLocation location) {
            this.location = location;
            return this;
        }

        public Builder setClassicAlbum(OutputAlbum classicAlbum) {
            this.classicAlbum = classicAlbum;
            return this;
        }

        public Builder setNewAlbum(OutputAlbum newAlbum) {
            this.newAlbum = newAlbum;
            return this;
        }

        public Builder setPlaylist(OutputPlaylist playlist) {
            this.playlist = playlist;
            return this;
        }

        public OutputEvent build() {
            if (classicAlbum == null || newAlbum == null) {
                return null;
            }
            final OutputEvent event = new OutputEvent(number);
            event.date = date;
            event.location = location;
            event.classicAlbum = classicAlbum;
            event.newAlbum = newAlbum;
            event.playlist = playlist;
            return event;
        }

    }
}
