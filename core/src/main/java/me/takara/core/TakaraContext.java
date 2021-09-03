package me.takara.core;

import java.net.URI;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public enum TakaraContext {

    EQUITY_PRIMARY_LOCAL(TakaraEntity.EQUITY, TakaraRegion.LOCAL, "localhost", 8080, 8081, "", -1),
    EQUITY_SECONDARY_LOCAL(TakaraEntity.EQUITY, TakaraRegion.LOCAL, "localhost", 8082, 8083, EQUITY_PRIMARY_LOCAL.host, EQUITY_PRIMARY_LOCAL.httpPort),
    BOND_PRIMARY_LOCAL(TakaraEntity.BOND, TakaraRegion.LOCAL, "localhost", 8090, 8091, "", -1),
    BOND_SECONDARY_LOCAL(TakaraEntity.BOND, TakaraRegion.LOCAL, "localhost", 8092, 8093, BOND_PRIMARY_LOCAL.host, BOND_PRIMARY_LOCAL.httpPort);

    /**
     *
     * @param takaraEntity
     * @param region
     * @param host
     * @param hPort jetty port
     * @param gPort grpc port
     * @param primary_host source data host where replication pulls data from
     * @param primary_port
     */
    private TakaraContext(TakaraEntity takaraEntity, TakaraRegion region,
                         String host, int hPort, int gPort,
                         String primary_host, int primary_port) {
        this.entity = takaraEntity;
        this.region = region;
        this.host = host;
        this.httpPort = hPort;
        this.grpcPort = gPort;
        this.primary_host = primary_host;
        this.primary_port = primary_port;
    }

    public TakaraEntity getEntity() {
        return entity;
    }

    private TakaraEntity entity;
    private TakaraRegion region;
    private String host;
    private int httpPort;
    private int grpcPort;
    private String primary_host;
    private int primary_port;

    public URI getGemisURI() {
        return URI.create(String.format("http://%s:%d/gemis", host, httpPort));
    }

    public int getGrpcPort() { return this.grpcPort; }

    public URI getPrimaryURI() {
        return URI.create(String.format("http://%s:%d/gemis", this.primary_host, this.primary_port));
    }

    @Override
    public String toString() {
        if (isPrimary()) {
            return String.format("Gemis<%s> (%s|MASTER:%s|%s)", entity, region, host, httpPort);
        } else {
            return String.format("Gemis<%s> (%s|SLAVE:%s|%s <- MASTER:%s|%s)", entity, region, host, httpPort, primary_host, primary_port);
        }
    }

    public boolean isPrimary() {
        return StringUtils.isEmpty(this.primary_host);
    }

    public TakaraContext getPrimaryContext() {

        if (isPrimary()) return this;

        var r = Arrays.stream(TakaraContext.values()).filter(a -> a.host.equals(this.primary_host) && a.httpPort == this.primary_port).findFirst();
        return r.orElse(null);
    }
}
