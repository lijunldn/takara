package me.takara.gemis;

import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;
import me.takara.shared.rest.TrackerResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    Stream<SyncStamp> applyFilter() {
        return data.keySet().stream().filter(
                a -> a.compareTo(this.begin) >= 0 && a.getId() > this.begin.getId());
    }

    public TrackerResponse next(int page) {

        List<SyncStamp> keys = applyFilter().limit(page).collect(Collectors.toList());

        List<Instrument> results = new ArrayList<>(page);
        keys.forEach(k -> {
            results.add(data.get(k));
        });

        // offset the starting point
        SyncStamp ex = this.begin;
        if (keys.size() > 0) {
            this.begin = keys.get(keys.size() - 1);
        }

        log.info(String.format("Pulled %s items - %s => %s", results.size(), ex, this.begin));

        return new TrackerResponse() {{
            setStamp(begin);
            setInstruments(results);
        }};
    }

    public boolean hasMore() {
        return data.keySet().stream().anyMatch(a -> a.compareTo(this.begin) > 0);
    }
}
