package me.takara.gemis.entities;

import me.takara.gemis.id.GemisID;
import me.takara.shared.Entity;
import me.takara.shared.entities.Bond;

public class BondImp extends Bond {

    public final static BondImp EMPTY = new BondImp(Long.valueOf(0), "");

    public BondImp() {
    }

    public BondImp(String name) {

        this(GemisID.generator(Entity.BOND).apply(1).get(0), name);
    }

    public BondImp(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("[%s|%s|%s]", id, name, status);
    }
}
