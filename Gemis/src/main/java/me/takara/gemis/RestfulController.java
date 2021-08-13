package me.takara.gemis;

import com.google.common.base.Stopwatch;
import me.takara.gemis.entities.BondImp;
import me.takara.shared.Instrument;
import me.takara.shared.rest.WhereClause;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Path("/gemis")
public class RestfulController {

    private static Logger log = Logger.getLogger(RestfulController.class.getName());
    private String logTime = "Took %,ds";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String heartbeat() {

        final Stopwatch sw = Stopwatch.createStarted();

        String name = Gemis.getInstance().getType().getName();

        sw.stop();
        log.info(String.format(logTime, sw.elapsed(TimeUnit.MILLISECONDS)));
        return String.format("HELLO! I'm your %s Gemis.", name);
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Instrument getById(@PathParam("id") long id) {

        final Stopwatch sw = Stopwatch.createStarted();

        var obj = Gemis.getInstance().get(id);

        sw.stop();
        log.info(String.format(logTime, sw.elapsed(TimeUnit.MILLISECONDS)));
        return obj.orElse(null);
    }

//    @GET
//    @Path("/where")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    public List<Instrument> getWhere(WhereClause whereClause) throws InterruptedException {

        final Stopwatch sw = Stopwatch.createStarted();

        List<Instrument> results = new ArrayList<>();
        results.add(new BondImp(whereClause.toString()));
        Thread.sleep(1000L);

        sw.stop();
        log.info(String.format(logTime, sw.elapsed(TimeUnit.MILLISECONDS)));
        return results;
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
