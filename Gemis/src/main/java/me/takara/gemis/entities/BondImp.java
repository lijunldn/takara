package me.takara.gemis.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.takara.gemis.id.GemisID;
import me.takara.shared.Entity;
import me.takara.shared.entities.Bond;

public class BondImp extends InstrumentBase implements Bond {

    public final static BondImp EMPTY = new BondImp(0, "");

    public BondImp() {
    }

    public BondImp(String name) {
        this(GemisID.generator(Entity.BOND).apply(1).get(0), name);
    }

    public BondImp(long id, String name) {
        this.id = id;
        this.name = name;
    }

//    public static Bond of(String json) throws JsonProcessingException {
//        return new ObjectMapper().readValue(json, Bond.class);
//    }
}
