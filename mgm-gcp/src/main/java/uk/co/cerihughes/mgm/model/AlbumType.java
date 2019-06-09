package uk.co.cerihughes.mgm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

@ApiModel("AlbumTypeApiModel")
public enum AlbumType {
    @JsonProperty("classic")
    CLASSIC,
    @JsonProperty("new")
    NEW
}