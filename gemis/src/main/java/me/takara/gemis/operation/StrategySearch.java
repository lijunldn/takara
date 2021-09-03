package me.takara.gemis.operation;

import me.takara.core.Instrument;
import me.takara.core.SyncStamp;
import me.takara.core.rest.SearchCriteria;

import javax.ws.rs.NotSupportedException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class StrategySearch implements Strategy {

    private static Logger log = Logger.getLogger(StrategySearch.class.getName());

    public List<SyncStamp> execute(HashMap<SyncStamp, Instrument> data, SearchCriteria wh) {

        final List<SyncStamp> results = new ArrayList<>();

        Stream<SyncStamp> stream;
        if ("ID".equals(wh.getL_value()) && SearchCriteria.Operator.EQ.equals(wh.getOperator())) {
            stream = data.keySet().stream().filter(a -> a.getId() == Long.valueOf(wh.getR_value()));
        } else if ("ID".equals(wh.getL_value()) && SearchCriteria.Operator.LT.equals(wh.getOperator())) {
            stream = data.keySet().stream().filter(a -> a.getId() <= Long.valueOf(wh.getR_value()));
        } else if ("ID".equals(wh.getL_value()) && SearchCriteria.Operator.GT.equals(wh.getOperator())) {
            stream = data.keySet().stream().filter(a -> a.getId() >= Long.valueOf(wh.getR_value()));
        } else {
            String msg = String.format("Field %s not supported", wh.getL_value());
            log.warning(msg);
            throw new NotSupportedException(msg);
        }
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
