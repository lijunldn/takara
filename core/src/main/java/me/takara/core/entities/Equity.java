package me.takara.core.entities;

import lombok.Data;
import me.takara.core.TakaraEntity;

@Data
public class Equity extends InstrumentBase {

    @Override
    public TakaraEntity getType() { return TakaraEntity.EQUITY; }

    protected String ric;

}
