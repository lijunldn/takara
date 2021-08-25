package me.takara.shared.entities;

import lombok.Data;
import me.takara.shared.TakaraEntity;

@Data
public class Bond extends InstrumentBase {

    public Bond() { super(); }

    @Override
    public TakaraEntity getType() { return TakaraEntity.BOND; }

}
