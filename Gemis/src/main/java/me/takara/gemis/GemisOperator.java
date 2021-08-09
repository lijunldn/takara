package me.takara.gemis;

import me.takara.gemis.operation.Strategy;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;

import java.util.HashMap;

public class GemisOperator {

    private HashMap<SyncStamp, Instrument> data;

    public GemisOperator(HashMap<SyncStamp, Instrument> data) {
        this.data = data;
    }

    public SyncStamp execute(Strategy strategy, Instrument item) {
        return strategy.execute(data, item);
    }
}
