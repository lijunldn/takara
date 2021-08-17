package me.takara.client;

import me.takara.shared.Entity;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;
import me.takara.shared.entities.Bond;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TakaraTracker {

    public TakaraTracker(Entity entity, SyncStamp stamp) {
        this.entity = entity;
        this.stamp = stamp;
    }

    private Entity entity;
    private SyncStamp stamp;

    public final SyncStamp getSyncStamp() {
        return stamp;
    }

    public boolean hasNext() {

        Client client = ClientBuilder.newClient(new ClientConfig());
        WebTarget target = client.target(this.entity.getGemisURI()).path("tracker/hasNext");
        var resp = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(
                javax.ws.rs.client.Entity.entity(this.stamp, MediaType.APPLICATION_JSON));

        if (resp.getStatus() != 200) {
            throw new RuntimeException("getFirst failed: HTTP error code 200");
        }

        return resp.readEntity(Boolean.class);
    }

    public List<Instrument> next(int pageSize) {
        return Collections.emptyList();
    }
}
