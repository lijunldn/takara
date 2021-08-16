package me.takara.gemis;

import me.takara.gemis.operation.Strategy;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;
import me.takara.shared.rest.SearchCriteria;

import java.util.HashMap;
import java.util.List;

public class GemisStrategy {

    private HashMap<SyncStamp, Instrument> data;

    public GemisStrategy(HashMap<SyncStamp, Instrument> data) {
        this.data = data;
    }

    public List<SyncStamp> execute(Strategy strategy, Instrument item) {
        return strategy.execute(data, item);
    }

    public List<SyncStamp> execute(Strategy strategy, long id) {
        return strategy.execute(data, id);
    }

    public List<SyncStamp> execute(Strategy strategy, SearchCriteria wh) {
        return strategy.execute(data, wh);
    }

}
