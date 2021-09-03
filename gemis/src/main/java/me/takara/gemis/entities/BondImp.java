package me.takara.gemis.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.takara.gemis.id.GemisID;
import me.takara.core.TakaraEntity;
import me.takara.core.entities.Bond;

import java.util.logging.Logger;

public class BondImp extends Bond {

    private static Logger log = Logger.getLogger(BondImp.class.getName());

    public BondImp() {
    }

    public BondImp(String name) {
        this.id = GemisID.generator(TakaraEntity.BOND).apply(1).get(0);
        this.name = name;
    }

    public static BondImp valueOf(String json) {
        try {
            return new ObjectMapper().readValue(json, BondImp.class);
        } catch (JsonProcessingException ex) {
            log.warning("Cannot create Bond from " + json);
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("Bond[%s|%s|%s]", id, name, status);
    }
}
