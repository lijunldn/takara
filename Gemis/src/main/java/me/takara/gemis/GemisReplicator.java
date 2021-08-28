package me.takara.gemis;

import me.takara.shared.SyncStamp;
import me.takara.shared.TakaraContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GemisReplicator {

    private static final Logger LOGGER = Logger.getLogger(GemisReplicator.class.getName());

    private final TakaraContext takaraContext;

    private ScheduledExecutorService replicaService = Executors.newSingleThreadScheduledExecutor();

    private SyncStamp syncStamp = SyncStamp.ZERO;

    private int pageSize = 10;

    GemisReplicator(TakaraContext takaraContext) {
        this.takaraContext = takaraContext;

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LOGGER.info(takaraContext + " replication is shutting down...");
                    LOGGER.info(String.format("SyncStamp %s saved (TODO)", syncStamp));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));

        replicaService.scheduleAtFixedRate(this::trackPrimary, 1, 1, TimeUnit.MINUTES);
    }

    private void trackPrimary() {
        // TODO: load saved SyncStamp
//        var tracker = TakaraBuilder.createTracker(this.takaraContext.getPrimaryContext(), this.syncStamp);
//        if (tracker.hasNext()) {
//            var items = tracker.next(pageSize);
//            log.info(String.format("Replicated %d items...", pageSize));
//        } else {
//            log.info(String.format("%s is in-sync with its primary", takaraContext));
//        }
    }
}
