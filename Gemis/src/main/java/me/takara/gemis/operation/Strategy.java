package me.takara.gemis.operation;

import jdk.jshell.spi.ExecutionControl;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;

import java.util.HashMap;

public interface Strategy {

    default SyncStamp execute(HashMap<SyncStamp, Instrument> data, Instrument item)  { return SyncStamp.ZERO; }

    default SyncStamp execute(HashMap<SyncStamp, Instrument> data, long id) { return SyncStamp.ZERO; }

    enum Operators {ADD, GET, REMOVE};

    static Strategy getStrategy(Operators op) {
        switch (op) {
            case ADD:
                return new StrategyAdd();
            case GET:
                return new StrategyGet();
            case REMOVE:
                return new StrategyRemove();
        }
        return null;
    }

}
