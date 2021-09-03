package me.takara.gemis.operation;

import me.takara.core.Instrument;
import me.takara.core.SyncStamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class StrategyAdd implements Strategy {

    public List<SyncStamp> execute(HashMap<SyncStamp, Instrument> data, Instrument item) {

        // remove existing
        var stamps = new StrategyRemove().execute(data, item);
        stamps.forEach(s -> data.remove(s));

        // new
        SyncStamp newKey = SyncStamp.create(item.getId());
        data.put(newKey, item);
        return new ArrayList<>() {{
            add(newKey);
        }};
    }
}
