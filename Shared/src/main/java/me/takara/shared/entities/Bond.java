package me.takara.shared.entities;

import lombok.Data;
import me.takara.shared.Entity;

@Data
public class Bond extends InstrumentBase {

    public Bond() { super(); }

    @Override
    public Entity getType() { return Entity.BOND; }

}
