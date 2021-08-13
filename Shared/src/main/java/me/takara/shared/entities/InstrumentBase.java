package me.takara.shared.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.takara.shared.Instrument;

public abstract class InstrumentBase implements Instrument {

    protected long id;

    protected String name;

    protected InstrumentBase() {}

    @Override
    public String toString() {

        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return String.format("%s{id=%s,name=%s}", this.getType(), this.id, this.name);
    }

    @Override
    public int hashCode() {
        return String.valueOf(this.id).hashCode();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
