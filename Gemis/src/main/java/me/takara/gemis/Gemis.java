package me.takara.gemis;

import me.takara.shared.Entity;
import me.takara.shared.SyncStamp;
import me.takara.shared.entities.Bond;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;

//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

class Gemis {

    private static Logger log = Logger.getLogger(Gemis.class.getName());

    private volatile static Gemis instance;

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

    private HashMap<SyncStamp, Bond> data = new HashMap<>();

    public static void main(String[] args) throws Exception {

        assert args == null || args[0].length() == 0;

        Entity entity = Entity.valueOf(args[0]);
        Gemis gemis = create(entity);

        for (int i = 0; i < 100; i++)
            gemis.add(new Bond(i, "BOND-" + i));

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

    Gemis(Entity entity) {
        this.entity = entity;
        log.info(String.format("Gemis %s running ... ", entity));
    }

    private SyncStamp getKey(Bond bond) {
        Optional<SyncStamp> key = data.keySet().stream().filter(a -> a.getId() == bond.getId()).findFirst();
        return key.isPresent() ? key.get() : null;
    }

    public SyncStamp add(Bond bond) {

        SyncStamp key = getKey(bond);
        if (key != null) {
            data.remove(key);
        }

        // new
        key = SyncStamp.create(bond.getId());
        data.put(key, bond);

        Bond temp = data.get(key);
        assert temp != null;
        log.info(String.format("Successfully added %s", temp));
        return key;
    }

    public Bond get(long v) {
        Optional<SyncStamp> key = data.keySet().stream().filter(a -> a.getId() == v).findFirst();
        if (key.isPresent())
            return data.get(key.get());
        else
            return Bond.EMPTY;
    }

    public int size() {
        return data.size();
    }

    public String getName() {
        return this.entity.toString();
    }
}
