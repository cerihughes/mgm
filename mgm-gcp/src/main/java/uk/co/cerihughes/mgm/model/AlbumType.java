package uk.co.cerihughes.mgm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AlbumType {
    @JsonProperty("classic")
    CLASSIC,
    @JsonProperty("new")
    NEW
}