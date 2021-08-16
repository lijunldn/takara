package me.takara.gemis;

import junit.framework.TestCase;
import me.takara.gemis.entities.BondImp;
import me.takara.gemis.operation.Strategy;
import me.takara.shared.Entity;
import me.takara.shared.SyncStamp;
import me.takara.shared.entities.Bond;
import me.takara.shared.entities.fields.BondFields;
import me.takara.shared.rest.SearchCriteria;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestGemisStrategy {

    private Gemis gms;

    @Before
    public void init() {
        gms = new Gemis(Entity.BOND);
    }

    @Test
    public void testAdd() {
        gms.add(new BondImp(Long.valueOf(200), "Nomura"));
        Assert.assertEquals(1, gms.size());

        gms.add(new BondImp(Long.valueOf(300), "Barcap"));
        Assert.assertEquals(2, gms.size());

        gms.add(new BondImp(Long.valueOf(200), "Nomura Sucks"));
        Assert.assertEquals(2, gms.size());
    }

    @Test
    public void testGet() {
        Bond b1 = new BondImp(Long.valueOf(100), "Morgan Stanley");
        SyncStamp stamp1 = gms.add(b1);

        var bond = gms.get(100);
        Assert.assertNotNull(bond);

        bond = gms.get(200);
        Assert.assertNull(bond);
    }

    @Test
    public void testRemove() {
        Bond b1 = new BondImp(Long.valueOf(100), "Morgan Stanley");
        SyncStamp stamp1 = gms.add(b1);

        Assert.assertEquals(1, gms.size());
        gms.remove(b1);
        Assert.assertEquals(0, gms.size());
        gms.remove(b1);
        Assert.assertEquals(0, gms.size());
    }

    @Test
    public void testSearch() {
        Bond b1 = new BondImp(Long.valueOf(100), "Morgan Stanley");
        SyncStamp stamp1 = gms.add(b1);
        Bond b2 = new BondImp(Long.valueOf(200), "Nomura");
        SyncStamp stamp2 = gms.add(b2);

        SearchCriteria wh = new SearchCriteria() {{
           setL_value(BondFields.ID.toString());
           setR_value("100");
           setOperator(Operator.EQ);
           setScope(Scope.GET_FIRST);
        }};
        var bonds = gms.search(wh);
        Assert.assertEquals(1, bonds.size());

        wh.setR_value("300");
        bonds = gms.search(wh);
        Assert.assertEquals(0, bonds.size());
    }
}