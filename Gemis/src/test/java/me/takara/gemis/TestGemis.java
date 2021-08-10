package me.takara.gemis;

import me.takara.shared.Entity;
import me.takara.shared.SyncStamp;
import me.takara.shared.entities.Bond;
import org.junit.Assert;
import org.junit.Test;

public class TestGemis {

    @Test
    public void testCreateGemis() {

        Entity.stream().forEach(e -> {
            Gemis gms = new Gemis(e);
            Assert.assertEquals(e.getName(), gms.getType().getName());
            Assert.assertEquals(0, gms.size());
            Assert.assertFalse(gms.get(1).isPresent());
        });
    }

    @Test
    public void testGemisRemove() {

        Gemis gms = new Gemis(Entity.BOND);

        Bond b1 = new Bond(100, "Morgan Stanley");
        SyncStamp stamp1 = gms.add(b1);
        Bond b2 = new Bond(200, "Nomura");
        SyncStamp stamp2 = gms.add(b2);
        Bond b3 = new Bond(300, "Barcap");

        Assert.assertEquals(2, gms.size());
        gms.remove(b1); // remove b1, expect -1
        Assert.assertEquals(1, gms.size());
        gms.remove(b1); // remove b1 again, expect no impact
        Assert.assertEquals(1, gms.size());
        gms.remove(b3); // remove b3, expect no impact
        Assert.assertEquals(1, gms.size());
        gms.remove(b2); // remove b2, expect -1
        Assert.assertEquals(0, gms.size());
    }

    @Test
    public void testGemisAdd() {

        Gemis gms = new Gemis(Entity.BOND);

        Bond b1 = new Bond(100, "Morgan Stanley");
        SyncStamp stamp = gms.add(b1);
        Assert.assertEquals(1, gms.size());
        var tmp = gms.get(b1.getId());
        Assert.assertTrue(tmp.isPresent());
        Assert.assertEquals(b1.getName(), tmp.get().getName());
        Assert.assertEquals(b1.getId(), tmp.get().getId());

        // add same bond again
        SyncStamp stampX = gms.add(b1);
        Assert.assertEquals(1, gms.size());
        var tmpX = gms.get(100);
        Assert.assertEquals("Morgan Stanley", tmpX.get().getName());
        Assert.assertEquals(1, stampX.compareTo(stamp));

        // add same bond with a new name (same ID)
        b1.setName("Merrill Lynch");
        SyncStamp stampMerrill = gms.add(b1);
        Assert.assertEquals(1, gms.size());
        var tmpMerrill = gms.get(100);
        Assert.assertEquals("Merrill Lynch", tmpX.get().getName());
        Assert.assertEquals(1, stampMerrill.compareTo(stamp));

        Bond b2 = new Bond(200, "Nomura");
        SyncStamp stamp2 = gms.add(b2);
        Assert.assertEquals(2, gms.size());
        var tmp2 = gms.get(b2.getId());
        Assert.assertEquals(b2.getName(), tmp2.get().getName());
        Assert.assertEquals(b2.getId(), tmp2.get().getId());

    }

}
