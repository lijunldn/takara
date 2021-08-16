package me.takara.client;

import junit.framework.TestCase;
import me.takara.shared.Entity;
import me.takara.shared.Instrument;
import me.takara.shared.entities.fields.BondFields;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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
            Assert.assertEquals(12, item.getId());
        }
    }

    @Test
    public void testWhere() {

        if (!gemisReady()) return;

        var repo = TakaraBuilder.create(Entity.BOND);
        List<Instrument> a = repo.where().equal(BondFields.ID, 2).getResultList();
        a.forEach(b -> System.out.println(b.getId()));

        a.forEach(b -> System.out.println(b.getName()));
    }
}