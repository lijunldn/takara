package me.takara.shared.entities;

import me.takara.shared.Entity;
import me.takara.shared.Instrument;

public interface Equity extends Instrument {

    default Entity getType() { return Entity.EQUITY; }

}
