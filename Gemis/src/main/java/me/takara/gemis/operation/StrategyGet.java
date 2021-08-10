package me.takara.gemis.operation;

import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;

import java.util.HashMap;
import java.util.Optional;

class StrategyGet implements Strategy {

    public SyncStamp execute(HashMap<SyncStamp, Instrument> data, Instrument item) {

        Optional<SyncStamp> key = data.keySet().stream().filter(a -> a.getId() == item.getId()).findFirst();
        return key.orElse(null);
    }
}
