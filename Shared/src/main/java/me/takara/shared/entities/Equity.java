package me.takara.shared.entities;

import lombok.Data;
import me.takara.shared.Entity;

@Data
public class Equity extends InstrumentBase {

    @Override
    public Entity getType() { return Entity.EQUITY; }

}
