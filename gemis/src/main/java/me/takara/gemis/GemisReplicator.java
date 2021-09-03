package me.takara.gemis;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.AbstractStub;
import me.takara.core.SyncStamp;
import me.takara.core.TakaraContext;
import me.takara.gemis.grpc.*;
import me.takara.core.Instrument;
import me.takara.core.entities.Bond;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Logger;

public class GemisReplicator {

    private static final Logger LOGGER = Logger.getLogger(GemisReplicator.class.getName());

    private final TakaraContext takaraContext;

    private ScheduledExecutorService replicaService = Executors.newSingleThreadScheduledExecutor();

    private SyncStamp syncStamp = SyncStamp.ZERO;

    private int pageSize = 10;

    Function<Instrument, SyncStamp> adder;

    ManagedChannel channel = null;
    RemotePullingServiceGrpc.RemotePullingServiceBlockingStub stub;

    GemisReplicator(TakaraContext takaraContext, Function<Instrument, SyncStamp> adder) {
        this.takaraContext = takaraContext;
        this.adder = adder;

        if (!this.takaraContext.isPrimary()) {
            var primary = this.takaraContext.getPrimaryContext();

            // Create a communication channel to the server, known as a Channel. Channels are thread-safe
            // and reusable. It is common to create channels at the beginning of your application and reuse
            // them until the application shuts down.
            channel = ManagedChannelBuilder.forTarget(primary.getHost() + ":" + primary.getGrpcPort())
                    .usePlaintext()
                    .build();

            // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
            stub = RemotePullingServiceGrpc.newBlockingStub(channel);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LOGGER.info(takaraContext + " replication is shutting down...");

                    if (channel != null) {
                        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
                    }

                    LOGGER.info(String.format("SyncStamp %s saved (TODO)", syncStamp));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));

        replicaService.scheduleAtFixedRate(this::trackPrimary, 5, 10, TimeUnit.SECONDS);
    }

    private void trackPrimary() {
        // TODO: load saved SyncStamp
        GrpcSyncStamp grpcSyncStamp = GrpcSyncStamp.newBuilder()
                .setTimestamp((int)this.syncStamp.getTimestamp())
                .setId((int)this.syncStamp.getId())
                .build();

        GrpcPullReq req = GrpcPullReq.newBuilder()
                .setPagesize(10)
                .setSyncStamp(grpcSyncStamp)
                .build();

        try {
            GrpcPullResp resp = this.stub.pull(req);
            List<GrpcBond> bonds = resp.getBondsList();
            bonds.forEach(b -> adder.apply(new Bond() {{
                setId(b.getId());
                setName(b.getName());
                setStatus(b.getStatus());
            }}));
            this.syncStamp = this.syncStamp.reset(resp.getSyncStamp().getId(), resp.getSyncStamp().getTimestamp());
            LOGGER.info(String.format("%d items replicated from %s", bonds.size(), this.takaraContext.getPrimaryContext()));
        } catch (Exception ex) {
            LOGGER.severe("Gemis replication ERROR: " + ex);
        }
    }
}
