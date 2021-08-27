package me.takara.client;

import me.takara.shared.SyncStamp;
import me.takara.shared.TakaraContext;

import javax.ws.rs.NotSupportedException;

public class TakaraBuilder {

    public static TakaraRepository create(TakaraContext context) {
        switch (context.getEntity()) {
            case BOND:
                return new TakaraRepository.BondRepository(context);
            case EQUITY:
                return new TakaraRepository.EquityRepository(context);
            default:
                throw new NotSupportedException(String.format("Takara %s repository not supported", context.getEntity()));
        }
    }

    public static TakaraTracker createTrackerSinceTimeZero(TakaraContext context) {
        return new TakaraTracker(context, SyncStamp.ZERO);
    }

    public static TakaraTracker createTracker(TakaraContext context, SyncStamp stamp) {
        return new TakaraTracker(context, stamp);
    }
}
