package me.takara.gemis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.takara.gemis.entities.BondImp;
import me.takara.gemis.entities.EquityImp;
import me.takara.shared.SyncStamp;
import me.takara.shared.TakaraContext;
import me.takara.shared.TakaraEntity;
import me.takara.shared.entities.Bond;
import me.takara.shared.entities.Equity;
import org.junit.Assert;
import org.junit.Test;

public class TestGemis {

    @Test
    public void testCreateGemis() {

        Gemis m = new Gemis(TakaraContext.BOND_PRIMARY_LOCAL);
        Assert.assertEquals(TakaraEntity.BOND, m.getType());
        Assert.assertEquals(0, m.size());
        Assert.assertNull(m.get(1));

        var nomura = new BondImp("Nomura");
        m.add(nomura);
        Assert.assertEquals(1, m.size());
        var bond = m.get(nomura.getId());
        Assert.assertNotNull(bond);
        Assert.assertEquals("Nomura", bond.getName());
        Assert.assertEquals(TakaraEntity.BOND, bond.getType());

        Gemis s = new Gemis(TakaraContext.BOND_SECONDARY_LOCAL);
        Assert.assertEquals(TakaraEntity.BOND, s.getType());
        Assert.assertEquals(0, s.size());
        Assert.assertNull(s.get(1));

        Gemis eq = new Gemis(TakaraContext.EQUITY_PRIMARY_LOCAL);
        var stamp = eq.add(new BondImp("Nomura"));
        Assert.assertEquals(SyncStamp.ZERO, stamp);
        Assert.assertEquals(0, eq.size());
        stamp = eq.add(new EquityImp("HSBC", "HSBA.L"));
        Assert.assertNotEquals(SyncStamp.ZERO, stamp);
        Assert.assertEquals(1, eq.size());
    }

    @Test
    public void testGemisItemJson() throws JsonProcessingException {
        Bond bond = new BondImp("A BOND");

        String msg = new ObjectMapper().writeValueAsString(bond);
        Assert.assertEquals(
                String.format("{\"id\":%d,\"name\":\"A BOND\",\"status\":\"ACTIVE\"}", bond.getId()), msg);
    }

    @Test
    public void testGemisItemIncrementalID() {
        Bond bd1 = new BondImp("A");
        Bond bd2 = new BondImp("B");
        Assert.assertTrue(bd1.getId() < bd2.getId());

        Equity eq1 = new EquityImp("E", "R");
        Assert.assertTrue(eq1.getId() < bd2.getId());

    }

}
