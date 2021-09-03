package me.takara.core.entities;

import lombok.Data;
import me.takara.core.TakaraEntity;

@Data
public class Bond extends InstrumentBase {

    public Bond() { super(); }

    @Override
    public TakaraEntity getType() { return TakaraEntity.BOND; }

}
