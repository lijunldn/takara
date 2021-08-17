package me.takara.client;

import me.takara.shared.Entity;
import me.takara.shared.SyncStamp;

public class TakaraBuilder {

    public static TakaraRepository create(Entity entity) {
        return new TakaraRepository(entity);
    }

    public static TakaraTracker createTrackerSinceTimeZero(Entity entity) {
        return new TakaraTracker(entity, SyncStamp.ZERO);
    }

    public static TakaraTracker createTracker(Entity entity, SyncStamp stamp) {
        return new TakaraTracker(entity, stamp);
    }
}
