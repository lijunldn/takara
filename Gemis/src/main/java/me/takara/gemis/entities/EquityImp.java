package me.takara.gemis.entities;

import me.takara.gemis.id.GemisID;
import me.takara.shared.TakaraEntity;
import me.takara.shared.entities.Equity;

public class EquityImp extends Equity {

    public EquityImp(String name) {
        this(GemisID.generator(TakaraEntity.EQUITY).apply(1).get(0), name);
    }

    public EquityImp(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("[%s|%s|%s]", id, name, status);
    }
}
