package me.takara.shared;

import java.net.URI;
import org.apache.commons.lang3.StringUtils;

public enum TakaraContext {

    EQUITY_MASTER_LOCAL(TakaraEntity.EQUITY, TakaraRegion.LOCAL, "localhost", 8080, "", -1),
    EQUITY_SLAVE_LOCAL(TakaraEntity.EQUITY, TakaraRegion.LOCAL, "localhost", 8081, EQUITY_MASTER_LOCAL.host, EQUITY_MASTER_LOCAL.port),
    BOND_MASTER_LOCAL(TakaraEntity.BOND, TakaraRegion.LOCAL, "localhost", 8090, "", -1),
    BOND_SLAVE_LOCAL(TakaraEntity.BOND, TakaraRegion.LOCAL, "localhost", 8091, BOND_MASTER_LOCAL.host, BOND_MASTER_LOCAL.port);

    /**
     *
     * @param takaraEntity
     * @param region
     * @param host
     * @param port
     * @param source_host source data host where replication pulls data from
     * @param source_port
     */
    private TakaraContext(TakaraEntity takaraEntity, TakaraRegion region,
                         String host, int port,
                         String source_host, int source_port) {
        this.entity = takaraEntity;
        this.region = region;
        this.host = host;
        this.port = port;
        this.source_host = source_host;
        this.source_port = source_port;
    }

    public TakaraEntity getEntity() {
        return entity;
    }

    private TakaraEntity entity;
    private TakaraRegion region;
    private String host;
    private int port;
    private String source_host;
    private int source_port;

    public URI getGemisURI() {
        return URI.create(String.format("http://%s:%d/gemis", host, port));
    }

    @Override
    public String toString() {
        if (isMaster()) {
            return String.format("%s (%s|MASTER:%s|%s)", entity, region, host, port);
        } else {
            return String.format("%s (%s|SLAVE:%s|%s <- MASTER:%s|%s)", entity, region, host, port, source_host, source_port);
        }
    }

    public boolean isMaster() {
        return StringUtils.isEmpty(this.source_host);
    }
}
