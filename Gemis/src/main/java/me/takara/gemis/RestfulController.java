package me.takara.gemis;

import jersey.repackaged.com.google.common.base.Stopwatch;
import me.takara.shared.SyncStamp;
import me.takara.shared.entities.Bond;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Path("/gemis")
public class RestfulController {

    private static Logger log = Logger.getLogger(RestfulController.class.getName());
    private String logTime = "Took %s";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getHello() {

        final Stopwatch sw = new Stopwatch().start();
        sw.stop();
        log.info(String.format(logTime, sw.elapsed(TimeUnit.MILLISECONDS)));
        return "HELLO! I'm your Gemis.";
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Bond getById(@PathParam("id") long id) {

        final Stopwatch sw = new Stopwatch().start();

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
