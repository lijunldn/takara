package me.takara.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "name", "status" })
public interface Instrument {

    @JsonIgnore
    TakaraEntity getType();

    long getId();

    String getName();

    String getStatus();

    void deactivate();
}
