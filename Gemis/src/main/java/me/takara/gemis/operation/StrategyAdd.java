package me.takara.gemis.operation;

import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;

import java.util.HashMap;

class StrategyAdd implements Strategy {

    public SyncStamp execute(HashMap<SyncStamp, Instrument> data, Instrument item) {

        // remove existing
        new StrategyRemove().execute(data, item);

        // new
        SyncStamp newKey = SyncStamp.create(item.getId());
        data.put(newKey, item);
        return newKey;
    }
}
