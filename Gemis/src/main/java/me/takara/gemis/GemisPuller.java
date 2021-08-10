package me.takara.gemis;

import me.takara.gemis.operation.StrategyGet;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class GemisPuller {

    private static Logger log = Logger.getLogger(GemisPuller.class.getName());

    private SyncStamp begin = null;
    private HashMap<SyncStamp, Instrument> data;

    GemisPuller(HashMap<SyncStamp, Instrument> data) {
        this.data = data;
    }

    public GemisPuller of(SyncStamp syncStamp) {
        this.begin = syncStamp;
        return this;
    }

    private GemisPuller(SyncStamp stamp) {
        this.begin = stamp;
    }

    public List<Instrument> next(int page) {

        List<SyncStamp> keys = data.keySet().stream().filter(a -> a.compareTo(this.begin) > 0).limit(page).collect(Collectors.toList());

        List<Instrument> result = new ArrayList<>(page);
        keys.forEach(k -> {
            result.add(data.get(k));
        });

        // offset the starting point
        SyncStamp ex = this.begin;
        if (keys.size() > 0) {
            this.begin = keys.get(keys.size() - 1);
        }

        log.info(String.format("Pulled %s items - %s => %s", result.size(), ex, this.begin));

        return result;
    }

    public boolean hasMoreItems() {
        return data.keySet().stream().anyMatch(a -> a.compareTo(this.begin) > 0);
    }
}
