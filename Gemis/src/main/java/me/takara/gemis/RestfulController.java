package me.takara.gemis;

import com.google.common.base.Stopwatch;
import me.takara.shared.entities.Bond;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Path("/gemis")
public class RestfulController {

    private static Logger log = Logger.getLogger(RestfulController.class.getName());
    private String logTime = "Took %,ds";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getHello() throws InterruptedException {

        final Stopwatch sw = Stopwatch.createStarted();
        Thread.sleep(1000L);
        sw.stop();
        log.info(String.format(logTime, sw.elapsed(TimeUnit.MILLISECONDS)));
        return "HELLO! I'm your Gemis.";
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Bond getById(@PathParam("id") long id) {

        final Stopwatch sw = Stopwatch.createStarted();

        Bond obj = Gemis.getInstance().get(id);

        sw.stop();
        log.info(String.format(logTime, sw.elapsed(TimeUnit.MILLISECONDS)));
        return obj;

    }

//    @Path("/add")
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public SyncStamp add(Bond bond) {
//
//        assert bond != null;
//        final Stopwatch sw = new Stopwatch().start();
//
//        SyncStamp stamp = Gemis.getInstance().add(bond);
//
//        log.info(String.format(logTime, sw.elapsed(TimeUnit.MILLISECONDS)));
//        return stamp;
//    }
}
