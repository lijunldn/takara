package me.takara.client;

import junit.framework.TestCase;
import me.takara.shared.Entity;
import me.takara.shared.Instrument;
import org.junit.Assert;
import org.junit.Test;

public class TestTakaraRepository extends TestCase {

    boolean gemisReady() {
        try {
            return TakaraBuilder.create(Entity.BOND).heartbeat();
        } catch (Exception ex) {
            return false;
        }
    }

    @Test
    public void testGet() {

        if (gemisReady()) {
            var repo = TakaraBuilder.create(Entity.BOND);
            Instrument item = repo.get(12);
            Assert.assertEquals(Long.valueOf(12), item.getId());
        }
    }
}