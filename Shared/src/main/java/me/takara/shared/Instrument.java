package me.takara.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Instrument {

    Long getId();

    String getName();
    void setName(String name);

    @JsonIgnore
    Entity getType();
}
