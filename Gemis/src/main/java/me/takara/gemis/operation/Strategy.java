package me.takara.gemis.operation;

import jdk.jshell.spi.ExecutionControl;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;
import me.takara.shared.rest.SearchCriteria;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public interface Strategy {

    default List<SyncStamp> execute(HashMap<SyncStamp, Instrument> data, Instrument item)  { return Collections.emptyList(); }

    default List<SyncStamp> execute(HashMap<SyncStamp, Instrument> data, long id) { return Collections.emptyList(); }

    default List<SyncStamp> execute(HashMap<SyncStamp, Instrument> data, SearchCriteria wh) { return Collections.emptyList(); }

    enum Operators {ADD, GET, REMOVE, SEARCH};

    static Strategy getStrategy(Operators op) {
        switch (op) {
            case ADD:
                return new StrategyAdd();
            case GET:
                return new StrategyGet();
            case REMOVE:
                return new StrategyRemove();
            case SEARCH:
                return new StrategySearch();
        }
        return null;
    }

}
