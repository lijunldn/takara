package me.takara.shared.client;

import me.takara.shared.Entity;

public class TakaraBuilder {

    public static TakaraRepository create(Entity entity) {

        return new TakaraRepository(entity);

    }
}
