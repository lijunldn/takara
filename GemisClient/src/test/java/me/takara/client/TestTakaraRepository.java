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
            System.out.println("No Gemis service!");
            return false;
        }
    }

    @Test
    public void testGet() {
        if (!gemisReady()) return;

        var repo = TakaraBuilder.create(Entity.BOND);
        Instrument item = repo.get(12);
        Assert.assertEquals(12, item.getId());
    }

    @Test
    public void testWhere() {
        if (!gemisReady()) return;

        var repo = TakaraBuilder.create(Entity.BOND);
        List<Instrument> results = repo.where().equal(BondFields.ID, 22).fetchFirstOnly();
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(22, results.get(0).getId());

        results = repo.where().lessThan(BondFields.ID, 22).fetchAll();
        Assert.assertEquals(22, results.size());

        results = repo.where().lessThan(BondFields.ID, 22).fetchFirstOnly();
        Assert.assertEquals(1, results.size());
    }
}