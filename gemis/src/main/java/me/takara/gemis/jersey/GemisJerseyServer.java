package me.takara.gemis.jersey;

import me.takara.gemis.RestfulController;
import me.takara.core.TakaraContext;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

public class GemisJerseyServer {

    private static Logger log = Logger.getLogger(GemisJerseyServer.class.getName());

    TakaraContext context;

    public GemisJerseyServer(TakaraContext context) {
        this.context = context;
    }

    public void start() throws Exception {

        log.info("Starting jetty...");
        final ResourceConfig resourceConfig = new ResourceConfig(RestfulController.class).
                register(LoggingFeature.class);
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

        log.info(String.format("[RESTful] Jetty started.\nTry out %s\nStop the application using CTRL+C", context.getGemisURI()));
    }
}
