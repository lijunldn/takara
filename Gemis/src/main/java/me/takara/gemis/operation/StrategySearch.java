package me.takara.gemis.operation;

import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;
import me.takara.shared.rest.SearchCriteria;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class StrategySearch implements Strategy {

    public List<SyncStamp> execute(HashMap<SyncStamp, Instrument> data, SearchCriteria wh) {

//        if ("ID".equals(wh.getL_value()) && "==".equals(wh.getOperator())) {
        Stream<SyncStamp> stream = data.keySet().stream().filter(a -> a.getId() == Long.valueOf(wh.getR_value()));
//        }
        final List<SyncStamp> results = new ArrayList<>();
        switch (wh.getScope()) {
            case GET_FIRST:
                var key = stream.findFirst();
                key.ifPresent(p -> results.add(p));
                break;
            case GET_ALL:
            default:
                results.addAll(stream.collect(Collectors.toList()));

        }
        return results;
    }
}
