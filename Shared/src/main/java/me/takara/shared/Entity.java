package me.takara.shared;

import me.takara.shared.entities.Bond;
import me.takara.shared.entities.Equity;

import java.net.URI;

public enum Entity {

    BOND(1, "Bond", 8080, Bond.class),
    EQUITY(2, "Equity", 8081, Equity.class);

    private final int id;
    private final String name;
    private final int port;
    private final URI gemisURI;
    private final Class cls;

    private Entity(int id, String name, int port, Class cls) {
        this.id = id;
        this.name = name;
        this.port = port;
        this.cls = cls;
        this.gemisURI = URI.create(String.format("http://localhost:%d/gemis", getPort()));
    }

    public int getPort() {
        return port;
    }

    public URI getGemisURI() {
        return gemisURI;
    }

    public Class getCls() {
        return cls;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
