package me.takara.shared.client;

import junit.framework.TestCase;
import me.takara.shared.Entity;
import me.takara.shared.Instrument;
import org.junit.Test;

public class TestTakaraRepository extends TestCase {

    @Test
    public void testGet() {

        TakaraRepository repository = TakaraBuilder.create(Entity.BOND);
        Instrument data = repository.get(1);
    }
}