package me.takara.client;

import me.takara.shared.TakaraContext;
import me.takara.shared.TakaraEntity;
import me.takara.shared.SyncStamp;

public class TakaraBuilder {

    public static TakaraRepository create(TakaraContext context) {
        return new TakaraRepository(context);
    }

    public static TakaraTracker createTrackerSinceTimeZero(TakaraContext context) {
        return new TakaraTracker(context, SyncStamp.ZERO);
    }

    public static TakaraTracker createTracker(TakaraContext context, SyncStamp stamp) {
        return new TakaraTracker(context, stamp);
    }
}
