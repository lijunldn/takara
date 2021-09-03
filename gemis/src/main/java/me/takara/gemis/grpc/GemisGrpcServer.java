package me.takara.gemis.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GemisGrpcServer {

    private static final Logger LOGGER = Logger.getLogger(GemisGrpcServer.class.getName());

    int port;

    public GemisGrpcServer(int port) {
        this.port = port;
    }

    private Server server;

    public void start() throws IOException, InterruptedException {
        /* The port on which the server should run */
        server = ServerBuilder.forPort(port)
                .addService(new RemotePullingServiceImpl())
                .build()
                .start();

        LOGGER.info("[GRPC] Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("[GRPC] shutting down gRPC server since JVM is shutting down");
                try {
                    GemisGrpcServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });

//        server.awaitTermination();
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public static class RemotePullingServiceImpl extends RemotePullingServiceGrpc.RemotePullingServiceImplBase {
        @Override
        public void pull(GrpcPullReq request, StreamObserver<GrpcPullResp> responseObserver) {

            try {
                LOGGER.info(String.format("Received remote Pull req: %s", request));

                GrpcBond bond = GrpcBond.newBuilder().setId(1).setName("TEST").build();
                GrpcSyncStamp stamp = GrpcSyncStamp.newBuilder().setId(12).setTimestamp(23).build();

                GrpcBond bond1 = GrpcBond.newBuilder().setId(1).setName("TEST").build();
                GrpcSyncStamp stamp1 = GrpcSyncStamp.newBuilder().setId(12).setTimestamp(23).build();

                GrpcPullResp resp = GrpcPullResp.newBuilder()
                        .addBonds(0, bond)
                        .addBonds(1, bond1)
                        .setSyncStamp(stamp1).build();
                responseObserver.onNext(resp);
                responseObserver.onCompleted();
            } catch (Exception ex) {
                LOGGER.severe("GRPC server response ERROR: " + ex);
            }
        }

    }


}
