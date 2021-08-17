package me.takara.gemis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.takara.gemis.entities.BondImp;
import me.takara.gemis.entities.EquityImp;
import me.takara.shared.Entity;
import me.takara.shared.SyncStamp;
import me.takara.shared.entities.Bond;
import me.takara.shared.entities.Equity;
import org.junit.Assert;
import org.junit.Test;

public class TestGemis {

    @Test
    public void testCreateGemis() {

        Gemis gms = Gemis.create(Entity.EQUITY);
        Assert.assertEquals(Entity.EQUITY, gms.getType());
        Assert.assertEquals(0, gms.size());
        Assert.assertNull(gms.get(1));
    }

    @Test
    public void testGemisItemJson() throws JsonProcessingException {
        Bond bond = new BondImp(Long.valueOf(1), "A BOND");

        String msg = new ObjectMapper().writeValueAsString(bond);
        Assert.assertEquals("{\"id\":1,\"name\":\"A BOND\",\"status\":\"ACTIVE\"}", msg);
    }

    @Test
    public void testGemisItemIncrementalID() {
        Bond bd1 = new BondImp("A");
        Bond bd2 = new BondImp("B");
        Assert.assertTrue(bd1.getId() < bd2.getId());

        Equity eq1 = new EquityImp("E");
        Assert.assertTrue(eq1.getId() < bd2.getId());

    }

}
