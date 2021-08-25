package me.takara.gemis;

import me.takara.gemis.entities.BondImp;
import me.takara.gemis.operation.Strategy;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;
import me.takara.shared.TakaraContext;
import me.takara.shared.TakaraEntity;
import me.takara.shared.rest.SearchCriteria;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

//import org.eclipse.jetty.server.Server;
//import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
//import org.glassfish.jersey.server.ResourceConfig;

//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

public class Gemis {

    private static Logger log = Logger.getLogger(Gemis.class.getName());

    /**
     * Gemis is a singleton
     */
    private volatile static Gemis instance;

    /**
     * Gemis data context
     */
    private TakaraContext takaraContext;

    /**
     * Core dataset
     */
    private HashMap<SyncStamp, Instrument> data = new LinkedHashMap<>();

    private ScheduledExecutorService heartbeatService = Executors.newSingleThreadScheduledExecutor();

    private Gemis(TakaraContext context) {
        this.takaraContext = context;
        log.info(String.format("Gemis %s running ... ", context));

        heartbeatService.scheduleAtFixedRate(() -> {
            log.info("[HEARTBEAT] " + this.heartbeat());
        }, 3, 3, TimeUnit.MINUTES);
    }

    public static Gemis forceCreate(TakaraContext context) {
        instance = null;
        return create(context);
    }

    public static Gemis create(TakaraContext context) {
        if (instance == null) {
            synchronized (Gemis.class) {
                if (instance == null) {
                    instance = new Gemis(context);
                }
            }
        }
        return instance;
    }

    public static Gemis getInstance() {
        assert instance == null;
        return instance;
    }

    private GemisStrategy getStrategy(Strategy.Operators op) {
        return new GemisStrategy(data, op);
    }

    public GemisPuller getPuller() {
        return new GemisPuller(data);
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        assert args == null || args[0].length() == 0;
        TakaraContext context = TakaraContext.valueOf(args[0]);
        Gemis gemis = create(context);

        // populate sample data when it's a master
        if (context.isMaster())
        {
            for (int i = 0; i < 100; i++)
                gemis.add(new BondImp("BOND-" + i));
        }

        startJetty(context);
    }

    private static void startJetty(TakaraContext context) throws Exception {

        log.info("Starting jetty...");
        final ResourceConfig resourceConfig = new ResourceConfig(RestfulController.class);
//        final SimpleServer server = SimpleContainerFactory.create(BASE_URI, resourceConfig);
        final Server server = JettyHttpContainerFactory.createServer(context.getGemisURI(), resourceConfig, false);

//        com.sun.jersey.api.core.ResourceConfig rc = new ClassNamesResourceConfig(RestfulController.class.getName());
//        com.sun.jersey.api.core.ResourceConfig rc1 = new PackagesResourceConfig("me.takara.gemis");
//        HttpServer server = GrizzlyServerFactory.createHttpServer(entity.getGemisURI(), rc);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("Gemis is shutting down...");
                    server.stop();
                    log.info("Bye for now... ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
        server.start();

        log.info(String.format("Jetty started.\nTry out %s\nStop the application using CTRL+C", context.getGemisURI()));
        Thread.currentThread().join();
    }

    /**
     * Instrument can only be soft deleted from the Gemis
     * @param item
     * @return
     */
    public synchronized SyncStamp deactivate(Instrument item) {

        item.deactivate();

        var keys = this.getStrategy(Strategy.Operators.ADD).execute(item);
        if (keys.size() > 0) {
            log.info(String.format("Deactivated %s:%s - %s", takaraContext, item, keys.get(0)));
            return keys.get(0);
        }
        return null;
    }

    public GemisPuller pullSinceTimeZero() {
        return this.getPuller().of(SyncStamp.ZERO);
    }

    public GemisPuller pullSince(SyncStamp stamp) {
        return this.getPuller().of(stamp);
    }

    public synchronized SyncStamp add(Instrument item) {

        var keys = this.getStrategy(Strategy.Operators.ADD).execute(item);
        if (keys.size() > 0) {
            log.info(String.format("Added %s:%s - %s", takaraContext, item, keys.get(0)));
            return keys.get(0);
        }
        return null;
    }

    public Instrument get(long id) {

        var keys = this.getStrategy(Strategy.Operators.GET).execute(id);
        if (keys.size() > 0) {
            var result = data.get(keys.get(0));
            log.info(String.format("Found %s:%s - %s", takaraContext, result, keys.get(0)));
            return data.get(keys.get(0));
        }
        log.warning(String.format("Cannot find %s:[%s]", takaraContext, id));
        return null;
    }

    public List<Instrument> search(SearchCriteria wh) {

        String w = wh.toJson();
        log.info("Search criteria - " + w);
        var keys = this.getStrategy(Strategy.Operators.SEARCH).execute(wh);
        List<Instrument> results = new ArrayList<>();
        if (keys.size() > 0) {
            keys.forEach(k -> {
                results.add(data.get(k));
            });
        }
        log.info(String.format("Found %d %s(s)", results.size(), this.takaraContext));
        return results;
    }

    public String heartbeat() {
        return String.format("Gemis %s has %d items...", this.takaraContext, size());
    }

    public int size() {
        return data.size();
    }

    public TakaraEntity getType() { return this.takaraContext.getEntity(); }
}
