package me.takara.gemis;

import com.google.common.base.Stopwatch;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;
import me.takara.shared.rest.SearchCriteria;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

        Instrument result = Gemis.getInstance().get(id);

        sw.stop();

        if (result != null) {
            log.info(String.format("getById returned %s[%s:%s] | Cost:%,ds", result.getType(), result.getId(), result.getName(), sw.elapsed(TimeUnit.MILLISECONDS)));
        } else {
            log.info(String.format("getById returned NULL | Cost:%,ds", sw.elapsed(TimeUnit.MILLISECONDS)));
        }
        return result;
    }

    @POST
    @Path("/where")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Instrument> getWhere(SearchCriteria whereClause) {

        final Stopwatch sw = Stopwatch.createStarted();

        List<Instrument> results = Gemis.getInstance().search(whereClause);

        sw.stop();
        log.info(String.format("getWhere (%s) returned %d %s(s) | Cost:%,ds ",
                whereClause, results.size(), Gemis.getInstance().getType(), sw.elapsed(TimeUnit.MILLISECONDS)));
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

    @POST
    @Path("/tracker/hasNext")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean hasNext(SyncStamp stamp) {

        final Stopwatch sw = Stopwatch.createStarted();

        Boolean result = Gemis.getInstance().getPuller().of(stamp).hasMore();;

        sw.stop();
        log.info(String.format("hasNext (%s) returned %s | Cost:%,ds ",
                stamp, result, sw.elapsed(TimeUnit.MILLISECONDS)));
        return result;
    }
}
