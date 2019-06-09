package uk.co.cerihughes.mgm.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("LocationApiModel")
public class OutputLocation {
    private String name;
    private double latitude;
    private double longitude;

    private OutputLocation(double latitude, double longitude) {
        super();

        this.latitude = latitude;
        this.longitude = longitude;
    }

    @ApiModelProperty(required = true)
    public String getName() {
        return name;
    }

    @ApiModelProperty(required = true)
    public double getLatitude() {
        return latitude;
    }

    @ApiModelProperty(required = true)
    public double getLongitude() {
        return longitude;
    }

    public static final class Builder {
        private String name;
        private Double latitude;
        private Double longitude;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public OutputLocation build() {
            if (name == null || latitude == null || longitude == null) {
                return null;
            }
            final OutputLocation location = new OutputLocation(latitude, longitude);
            location.name = name;
            return location;
        }
    }
}
