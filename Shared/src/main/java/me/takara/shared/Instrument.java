package me.takara.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name" })
public interface Instrument {

    @JsonIgnore
    Entity getType();

    long getId();

    String getName();
}
