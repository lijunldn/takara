package me.takara.gemis;

import me.takara.gemis.operation.StrategyAdd;
import me.takara.gemis.operation.StrategyGet;
import me.takara.gemis.operation.StrategyRemove;
import me.takara.shared.Entity;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;
import me.takara.shared.entities.Bond;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.logging.Logger;

//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

public class Gemis {

    private static Logger log = Logger.getLogger(Gemis.class.getName());

    private volatile static Gemis instance;

    Gemis(Entity entity) {
        this.entity = entity;
        this.operator = new GemisOperator(data);
        this.puller = new GemisPuller(data);
        log.info(String.format("Gemis %s running ... ", entity));
    }

    public static Gemis getInstance() {
        assert instance == null;
        return instance;
    }

    public static Gemis create(Entity entity) {
        if (instance == null) {
            synchronized (Gemis.class) {
                if (instance == null) {
                    instance = new Gemis(entity);
                }
            }
        }
        return instance;
    }

    private Entity entity;
    private GemisOperator operator;
    private GemisPuller puller;
    private HashMap<SyncStamp, Instrument> data = new LinkedHashMap<>();

    public static void main(String[] args) throws Exception {

        assert args == null || args[0].length() == 0;
        Entity entity = Entity.valueOf(args[0]);
        Gemis gemis = create(entity);

        for (int i = 0; i < 100; i++)
            gemis.add(new Bond(i, "BOND-" + i));

        startJetty(entity);
    }

    private static void startJetty(Entity entity) throws Exception {
        log.info("Starting jetty...");
        final ResourceConfig resourceConfig = new ResourceConfig(RestfulController.class);
//        final SimpleServer server = SimpleContainerFactory.create(BASE_URI, resourceConfig);
        final Server server = JettyHttpContainerFactory.createServer(entity.getGemisURI(), resourceConfig, false);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("Shutting down the application...");
                    server.stop();
                    log.info("THE END ... ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
        server.start();

        log.info(String.format("Jetty started.\nTry out %s\nStop the application using CTRL+C", entity.getGemisURI()));
        Thread.currentThread().join();
    }

    public synchronized SyncStamp remove(Instrument item) {
        var stamp = this.operator.execute(new StrategyRemove(), item);
        if (stamp != null) {
            log.info(String.format("Removed %s - %s", item, stamp));
        } else {
            log.info(String.format("Cannot find %s", item));
        }
        return stamp;
    }

    public GemisPuller pullSinceTimeZero() {
        return this.puller.of(SyncStamp.ZERO);
    }

    public GemisPuller pullSince(SyncStamp stamp) {
        return this.puller.of(stamp);
    }

    public synchronized SyncStamp add(Instrument item) {

        SyncStamp key = this.operator.execute(new StrategyAdd(), item);
        log.info(String.format("Added %s - %s", item, key));
        return key;
    }

    public Optional<Instrument> get(long v) {

        SyncStamp key = this.operator.execute(new StrategyGet(), new Bond(v, ""));
        if (key != null)
            return Optional.of(data.get(key));
        else
            return Optional.empty();
    }

    public int size() {
        return data.size();
    }

    public Entity getType() { return this.entity; }
}
