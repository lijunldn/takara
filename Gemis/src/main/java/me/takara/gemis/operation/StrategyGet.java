package me.takara.gemis.operation;

import jdk.jshell.spi.ExecutionControl;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

class StrategyGet implements Strategy {

    public List<SyncStamp> execute(HashMap<SyncStamp, Instrument> data, long id) {

        Optional<SyncStamp> key = data.keySet().stream().filter(a -> a.getId() == id).findFirst();

        List<SyncStamp> results = new ArrayList<>();
        key.ifPresent(p -> results.add(p));
        return results;
    }
}
