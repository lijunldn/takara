package me.takara.gemis;

import me.takara.shared.entities.Bond;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/gemis")
public class RestfulController {

    @GET
    public String getHello() {
        return "HELLO! I'm your Gemis.";
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Bond hello(@PathParam("id") long id) {

        Bond obj = Gemis.getInstance().get(id);
        return obj;

    }
}
