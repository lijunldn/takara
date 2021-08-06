package me.takara.shared.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@XmlRootElement(name = "bond")
public class Bond {

    public final static Bond EMPTY = new Bond(0, "");

    public Bond() {
    }

    public Bond(long id, String name) {
        this.id = id;
        this.name = name;
    }

//    public static Bond of(String json) throws JsonProcessingException {
//        return new ObjectMapper().readValue(json, Bond.class);
//    }

    @Override
    public String toString() {

        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "Bond{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    private long id;

    private String name;
}
