package me.takara.shared.entities;

import lombok.Data;
import me.takara.shared.Instrument;

@Data
public abstract class InstrumentBase implements Instrument {

    protected long id;

    protected String name;

    protected String status;

    protected InstrumentBase() {
        this.status = "ACTIVE";
    }

    public void deactivate() {
        this.status = "INACTIVE";
    }

    @Override
    public int hashCode() {
        return String.valueOf(this.id).hashCode();
    }
}
