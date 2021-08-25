package me.takara.client;

import me.takara.shared.TakaraContext;
import me.takara.shared.TakaraEntity;
import me.takara.shared.SyncStamp;
import me.takara.shared.rest.TrackerResponse;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class TakaraTracker {

    public TakaraTracker(TakaraContext context, SyncStamp stamp) {
        this.context = context;
        this.stamp = stamp;
    }

    private TakaraContext context;
    private SyncStamp stamp;

    public final SyncStamp getSyncStamp() {
        return stamp;
    }

    public boolean hasNext() {

        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(this.context.getGemisURI()).path("tracker/hasNext");
        var resp = target.request(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN).post(
                javax.ws.rs.client.Entity.entity(this.stamp, MediaType.APPLICATION_JSON));

        if (resp.getStatus() != 200) {
            throw new RuntimeException("hasNext failed: HTTP error code 200");
        }

        var result = resp.readEntity(String.class);
        return "true".equals(result);
    }

    public TrackerResponse next(int pageSize) {

        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(this.context.getGemisURI()).path("tracker/next/" + pageSize);
        var resp = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(
                javax.ws.rs.client.Entity.entity(this.stamp, MediaType.APPLICATION_JSON));

        if (resp.getStatus() != 200) {
            throw new RuntimeException("next failed: HTTP error code 200");
        }

        return resp.readEntity(TrackerResponse.class);

//        List<Instrument> result = new ArrayList<>();
//        switch (this.entity) {
//            case BOND:
//                var bonds = resp.readEntity(new GenericType<List<Bond>>(){});
//                result.addAll(bonds);
//                break;
//            case EQUITY:
//                var equities = resp.readEntity(new GenericType<List<Equity>>(){});
//                result.addAll(equities);
//                break;
//        }


    }
}
