package me.takara.client;

import me.takara.shared.Entity;
import me.takara.shared.Instrument;
import me.takara.shared.entities.Bond;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class TakaraRepository {

    /**
     * Constructs a TakaraRepository with <B>TakaraBuilder</B>
     * @param entity
     */
    TakaraRepository(Entity entity) {
        this.entity = entity;
    }

    Entity entity;

    public Instrument get(long id) {
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(this.entity.getGemisURI()).path("" + id);
        var resp = target.request().accept(MediaType.APPLICATION_JSON).get();
//        System.out.println(resp.readEntity(Bond.class));

        System.out.println(resp.readEntity(String.class));
        return null;
//        return (Instrument) resp.readEntity(this.entity.getCls());
    }
}
