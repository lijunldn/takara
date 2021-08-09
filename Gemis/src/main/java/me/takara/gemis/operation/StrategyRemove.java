package me.takara.gemis.operation;

import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;

import java.util.HashMap;
import java.util.Optional;

public class StrategyRemove implements Strategy {

    public SyncStamp execute(HashMap<SyncStamp, Instrument> data, Instrument item) {
        Optional<SyncStamp> key = data.keySet().stream().filter(a -> a.getId() == item.getId()).findFirst();
        if (key.isPresent()) {
            data.remove(key.get());
        }
        return key.orElse(null);
    }
}