package me.takara.client;

import me.takara.client.query.CriteriaBuilder;
import me.takara.core.Instrument;
import me.takara.core.SyncStamp;
import me.takara.core.TakaraContext;
import me.takara.core.entities.Bond;
import me.takara.core.entities.Equity;
import me.takara.core.rest.SearchCriteria;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class TakaraRepository implements Closeable {

    private static Logger log = Logger.getLogger(TakaraRepository.class.getName());

    /**
     * Constructs a TakaraRepository with <B>TakaraBuilder</B>
     *
     * @param context
     */
    protected TakaraRepository(TakaraContext context) {
        this.context = context;
        this.client = ClientBuilder.newClient(new ClientConfig());
    }


    @Override
    public void close() {
        client.close();
        client = null;
        log.info(String.format("Repository %s closed", this));
    }

    TakaraContext context;
    private Client client;

    public boolean heartbeat() {
        WebTarget target = client.target(this.context.getGemisURI());
        String response = target.request().accept(MediaType.TEXT_PLAIN).get(String.class);
        return response != null && response.length() > 0;
    }

    public <T> T get(long id) {
        WebTarget target = client.target(this.context.getGemisURI()).path("" + id);
        var resp = target.request().accept(MediaType.APPLICATION_JSON).get();
        return (T) resp.readEntity(this.context.getEntity().getCls());
    }

    public CriteriaBuilder where() {
        return new CriteriaBuilder(a -> this.queryGemis((SearchCriteria) a));
    }

    private List<Instrument> queryGemis(SearchCriteria wh) {

        WebTarget target = client.target(this.context.getGemisURI()).path("where");
        var resp = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(
                javax.ws.rs.client.Entity.entity(wh, MediaType.APPLICATION_JSON));

        if (resp.getStatus() != 200) {
            throw new RuntimeException("getFirst failed: HTTP error code 200");
        }

        List<Instrument> result = new ArrayList<>();
        switch (this.context.getEntity()) {
            case BOND:
                var bonds = resp.readEntity(new GenericType<List<Bond>>() {
                });
                result.addAll(bonds);
                break;
            case EQUITY:
                var equities = resp.readEntity(new GenericType<List<Equity>>() {
                });
                result.addAll(equities);
                break;
        }
        return result;
    }

    public SyncStamp add(Instrument instrument) {

        WebTarget target = client.target(this.context.getGemisURI()).path("add").path(((Enum)instrument.getType()).name());
        var resp = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(
                javax.ws.rs.client.Entity.entity(instrument, MediaType.APPLICATION_JSON));
        return SyncStamp.ZERO;
    }

    public static class BondRepository extends TakaraRepository {
        BondRepository(TakaraContext context) {
            super(context);
        }
    }
    public static class EquityRepository extends TakaraRepository {
        EquityRepository(TakaraContext context) {
            super(context);
        }
    }
}
