package me.takara.gemis;

import me.takara.gemis.entities.BondImp;
import me.takara.shared.TakaraContext;
import me.takara.shared.TakaraEntity;
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
    public synchronized void init() {
        gms = new Gemis(TakaraContext.BOND_MASTER_LOCAL);
    }

    @Test
    public void testAdd() {
        var nomura = new BondImp("Nomura");
        gms.add(nomura);
        Assert.assertEquals(1, gms.size());

        gms.add(new BondImp("Barcap"));
        Assert.assertEquals(2, gms.size());

        nomura.setName("Nomura Sucks");
        gms.add(nomura);
        Assert.assertEquals(2, gms.size());
    }

    @Test
    public void testGet() {
        Bond b1 = new BondImp("Morgan Stanley");
        SyncStamp stamp1 = gms.add(b1);

        var bond = gms.get(b1.getId());
        Assert.assertNotNull(bond);

        bond = gms.get(200);
        Assert.assertNull(bond);
    }

    @Test
    public void testDeactivate() {
        Bond b1 = new BondImp("Morgan Stanley");
        SyncStamp stamp1 = gms.add(b1);
        Assert.assertEquals(1, gms.size());
        Assert.assertEquals("ACTIVE", b1.getStatus());

        SyncStamp stamp2 = gms.deactivate(b1);
        Assert.assertTrue(stamp1.compareTo(stamp2) == -1); // syncstamp should be increased
        var b2 = gms.get(b1.getId());
        Assert.assertEquals("INACTIVE", b1.getStatus());
        Assert.assertEquals(1, gms.size());
    }

    @Test
    public void testSearch() {
        Bond b1 = new BondImp("Morgan Stanley");
        SyncStamp stamp1 = gms.add(b1);
        Bond b2 = new BondImp("Nomura");
        SyncStamp stamp2 = gms.add(b2);

        SearchCriteria wh = new SearchCriteria() {{
           setL_value(BondFields.ID.toString());
           setR_value(String.valueOf(b1.getId()));
           setOperator(Operator.EQ);
           setScope(Scope.GET_FIRST);
        }};
        var bonds = gms.search(wh);
        Assert.assertEquals(1, bonds.size());

        wh.setR_value("300");
        bonds = gms.search(wh);
        Assert.assertEquals(0, bonds.size());

        wh.setOperator(SearchCriteria.Operator.LT);
        wh.setScope(SearchCriteria.Scope.GET_ALL);
        bonds = gms.search(wh);
        Assert.assertEquals(2, bonds.size());

        wh.setOperator(SearchCriteria.Operator.GT);
        bonds = gms.search(wh);
        Assert.assertEquals(0, bonds.size());
    }
}