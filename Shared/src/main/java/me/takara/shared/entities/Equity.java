package me.takara.shared.entities;

import lombok.Data;
import me.takara.shared.TakaraEntity;

@Data
public class Equity extends InstrumentBase {

    @Override
    public TakaraEntity getType() { return TakaraEntity.EQUITY; }

}
