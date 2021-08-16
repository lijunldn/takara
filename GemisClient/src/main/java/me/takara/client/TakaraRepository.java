package me.takara.client;

import me.takara.client.query.CriteriaBuilder;
import me.takara.shared.Entity;
import me.takara.shared.Instrument;
import me.takara.shared.entities.Bond;
import me.takara.shared.entities.Equity;
import me.takara.shared.rest.SearchCriteria;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TakaraRepository {

    /**
     * Constructs a TakaraRepository with <B>TakaraBuilder</B>
     *
     * @param entity
     */
    TakaraRepository(Entity entity) {
        this.entity = entity;
    }

    Entity entity;

    public boolean heartbeat() {
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(this.entity.getGemisURI());
        String response = target.request().accept(MediaType.TEXT_PLAIN).get(String.class);
        return response != null && response.length() > 0;
    }

    public Instrument get(long id) {
        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(this.entity.getGemisURI()).path("" + id);
        var resp = target.request().accept(MediaType.APPLICATION_JSON).get();
        return (Instrument) resp.readEntity(this.entity.getCls());
    }

    public CriteriaBuilder where() {
        return new CriteriaBuilder(a -> this.queryGemis((SearchCriteria)a));
    }

    private List<Instrument> queryGemis(SearchCriteria wh) {

        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(this.entity.getGemisURI()).path("where");
        var resp = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(
                javax.ws.rs.client.Entity.entity(wh, MediaType.APPLICATION_JSON));

        if (resp.getStatus() != 200) {
            throw new RuntimeException("getFirst failed: HTTP error code 200");
        }

        List<Instrument> result = new ArrayList<>();
        switch (this.entity) {
            case BOND:
                var bonds = resp.readEntity(new GenericType<List<Bond>>(){});
                result.addAll(bonds);
                break;
            case EQUITY:
                var equities = resp.readEntity(new GenericType<List<Equity>>(){});
                result.addAll(equities);
                break;
        }
        return result;
    }
}
