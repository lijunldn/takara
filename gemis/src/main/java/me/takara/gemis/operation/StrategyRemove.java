package me.takara.gemis.operation;

import me.takara.core.Instrument;
import me.takara.core.SyncStamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

class StrategyRemove implements Strategy {

    /**
     * Internal use ONLY
     * External soft delete (Instrument.status ACTIVE -> INACTIVE) ONLY
     * @param data
     * @param item
     * @return
     */
    public List<SyncStamp> execute(HashMap<SyncStamp, Instrument> data, Instrument item) {

        Optional<SyncStamp> key = data.keySet().stream().filter(a -> a.getId() == item.getId()).findFirst();

        List<SyncStamp> results = new ArrayList<>();
        key.ifPresent(p -> results.add(p));
        return results;
    }
}