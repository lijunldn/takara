package me.takara.core;

import me.takara.core.entities.Bond;
import me.takara.core.entities.Equity;

import java.util.stream.Stream;

public enum TakaraEntity {

    BOND("Bond", Bond.class),
    EQUITY("Equity", Equity.class);

    private final String name;
    private final Class cls;

    private TakaraEntity(String name, Class cls) {
        this.name = name;
        this.cls = cls;
    }

    public String getName() {
        return this.name;
    }

    public Class getCls() {
        return cls;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Stream<TakaraEntity> stream() {
        return Stream.of(TakaraEntity.values());
    }

}
