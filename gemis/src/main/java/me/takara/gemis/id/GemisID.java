package me.takara.gemis.id;

import me.takara.core.TakaraEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class GemisID {

    private static Map<TakaraEntity, AtomicLong> ids = new HashMap<>();

    static {
        TakaraEntity.stream().forEach(e -> ids.put(e, new AtomicLong(0)));
    }

    public static Function<Integer, List<Long>> generator(TakaraEntity takaraEntity) {
        return n -> {
            List<Long> results = new ArrayList<>();
            AtomicLong r = ids.get(takaraEntity);
            for (int i = 0; i < n; i++) {
                results.add(r.incrementAndGet());
            }
            return results;
        };
    }

    static void reset() {
        ids = new HashMap<>();
        TakaraEntity.stream().forEach(e -> ids.put(e, new AtomicLong(0)));
    }

}
