package me.takara.gemis.entities;

import me.takara.gemis.id.GemisID;
import me.takara.shared.TakaraEntity;
import me.takara.shared.entities.Equity;

public class EquityImp extends Equity {

    public EquityImp(String name, String ric) {
        this.id = GemisID.generator(TakaraEntity.EQUITY).apply(1).get(0);
        this.name = name;
        this.ric = ric;
    }

    @Override
    public String toString() {
        return String.format("Equity[%s|%s|%s|%s]", id, name, ric, status);
    }
}
