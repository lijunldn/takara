package me.takara.gemis.id;

import me.takara.shared.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class GemisID {

    private static Map<Entity, AtomicLong> ids = new HashMap<>();

    static {
        Entity.stream().forEach(e -> ids.put(e, new AtomicLong(0)));
    }

    public static Function<Integer, List<Long>> generator(Entity entity) {
        return n -> {
            List<Long> results = new ArrayList<>();
            AtomicLong r = ids.get(entity);
            for (int i = 0; i < n; i++) {
                results.add(r.incrementAndGet());
            }
            return results;
        };
    }

    static void reset() {
        ids = new HashMap<>();
        Entity.stream().forEach(e -> ids.put(e, new AtomicLong(0)));
    }

}
