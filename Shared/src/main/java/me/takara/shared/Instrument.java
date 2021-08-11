package me.takara.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Instrument {

    long getId();

    String getName();
    void setName(String name);

    @JsonIgnore
    Entity getType();
}
