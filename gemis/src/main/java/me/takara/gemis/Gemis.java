package me.takara.gemis;

import me.takara.gemis.entities.BondImp;
import me.takara.gemis.entities.EquityImp;
import me.takara.gemis.grpc.GemisGrpcServer;
import me.takara.gemis.jersey.GemisJerseyServer;
import me.takara.gemis.operation.Strategy;
import me.takara.core.Instrument;
import me.takara.core.SyncStamp;
import me.takara.core.TakaraContext;
import me.takara.core.TakaraEntity;
import me.takara.core.entities.Bond;
import me.takara.core.entities.Equity;
import me.takara.core.rest.SearchCriteria;

import javax.ws.rs.NotSupportedException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
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

    private GemisReplicator replicator;

    Gemis(TakaraContext context) {
        this.takaraContext = context;
        log.info(String.format("Gemis %s running ... ", context));

        if (!context.isPrimary()) {
            // secondary replicate data from its primary
            replicator = new GemisReplicator(context, this::add);
        }

        heartbeatService.scheduleAtFixedRate(() -> {
            log.info("[HEARTBEAT] " + this.heartbeat());
        }, 3, 3, TimeUnit.MINUTES);
    }

    public static Gemis instanceOf(TakaraContext context) {
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
        Gemis gemis = Gemis.instanceOf(context);

        // populate sample data when it's a master
        if (context.isPrimary())
        {
            if (context.getEntity() == TakaraEntity.BOND) {
                for (int i = 0; i < 100; i++) {
                    gemis.add(new BondImp("BOND-" + i));
                }
            } else if (context.getEntity() == TakaraEntity.EQUITY) {
                for (int i = 0; i < 100; i++) {
                    gemis.add(new EquityImp("EQUITY-" + i, "RIC-" + i));
                }
            } else {
                throw new NotSupportedException(context.getEntity() + " not supported");
            }

        }

        new GemisJerseyServer(context).start();

        new GemisGrpcServer(context.getGrpcPort()).start();

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
            log.info(String.format("Deactivated %s - %s", item, keys.get(0)));
            return keys.get(0);
        }
        return null;
    }

    boolean canAdd(Instrument item) {
        if (!takaraContext.isPrimary())
            return false;

        switch (takaraContext.getEntity()) {
            case BOND:
                if (item instanceof Bond == false) {
                    log.warning(String.format("Cannot add %s into %s", item, this.takaraContext));
                    return false;
                }
                break;
            case EQUITY:
                if (item instanceof Equity == false) {
                    log.warning(String.format("Cannot add %s into %s", item, this.takaraContext));
                    return false;
                }
                break;
            default:
                throw new NotSupportedException(takaraContext.getEntity() + " not supported");
        }
        return true;
    }

    public synchronized SyncStamp add(Instrument item) {

        if (!canAdd(item))
            return SyncStamp.ZERO;

        var keys = this.getStrategy(Strategy.Operators.ADD).execute(item);
        if (keys.size() > 0) {
            log.info(String.format("Added %s - %s", item, keys.get(0)));
            return keys.get(0);
        }
        return null;
    }

    public Instrument get(long id) {

        var keys = this.getStrategy(Strategy.Operators.GET).execute(id);
        if (keys.size() > 0) {
            var result = data.get(keys.get(0));
            log.info(String.format("Found %s - %s", result, keys.get(0)));
            return data.get(keys.get(0));
        }
        log.warning(String.format("Cannot find %s[%s]", takaraContext.getEntity(), id));
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
        return String.format("%s has %d items...", this.takaraContext, size());
    }

    public int size() {
        return data.size();
    }

    public TakaraEntity getType() { return this.takaraContext.getEntity(); }
}
