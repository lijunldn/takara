package me.takara.gemis;

import me.takara.shared.Env;
import me.takara.shared.SyncStamp;
import me.takara.shared.entities.Bond;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.HashMap;
import java.util.Optional;

//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

class Gemis {

    private volatile static Gemis instance;

    public static Gemis getInstance(){
        if(instance == null){
            synchronized(Gemis.class){
                if(instance == null){
                    instance = new Gemis("Bond");
                }
            }
        }
        return instance;
    }

    private String name;

    private HashMap<SyncStamp, Bond> data = new HashMap<>();

    public static void main(String[] args) throws Exception {

        Gemis gemis = getInstance();
        for (int i = 0; i < 100; i++)
            gemis.add(SyncStamp.create(i), new Bond(i, "BOND-" + i));

        System.out.println("Starting jetty...");
        final ResourceConfig resourceConfig = new ResourceConfig(RestfulController.class);
//        final SimpleServer server = SimpleContainerFactory.create(BASE_URI, resourceConfig);
        final Server server = JettyHttpContainerFactory.createServer(Env.GEMIS_BOND_URI, resourceConfig, false);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Shutting down the application...");
                    server.stop();
                    System.out.println("THE END ... ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
        server.start();

        System.out.println(String.format("Jetty started.\nTry out %s\nStop the application using CTRL+C", Env.GEMIS_BOND_URI));
        Thread.currentThread().join();
    }

    Gemis(String name) {
        name = name;
        System.out.println(String.format("Gemis %s running ... ", name));
    }

    public void add(SyncStamp stamp, Bond bond) {
        data.put(stamp, bond);
        Bond temp = data.get(stamp);
        assert temp != null;
        System.out.println(String.format("Successfully added %s", temp));
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
}
