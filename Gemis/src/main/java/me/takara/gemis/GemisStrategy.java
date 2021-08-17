package me.takara.gemis;

import me.takara.gemis.operation.Strategy;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;
import me.takara.shared.rest.SearchCriteria;

import java.util.HashMap;
import java.util.List;

public class GemisStrategy {

    private HashMap<SyncStamp, Instrument> data;
    private Strategy strategy;

    public GemisStrategy(HashMap<SyncStamp, Instrument> data, Strategy.Operators op) {
        this.data = data;
        this.strategy = Strategy.getStrategy(op);
    }

    public List<SyncStamp> execute(Instrument item) {
        return strategy.execute(data, item);
    }

    public List<SyncStamp> execute(long id) {
        return strategy.execute(data, id);
    }

    public List<SyncStamp> execute(SearchCriteria wh) {
        return strategy.execute(data, wh);
    }

}
