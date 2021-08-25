package me.takara.gemis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.takara.gemis.entities.BondImp;
import me.takara.gemis.entities.EquityImp;
import me.takara.shared.TakaraContext;
import me.takara.shared.TakaraEntity;
import me.takara.shared.entities.Bond;
import me.takara.shared.entities.Equity;
import org.junit.Assert;
import org.junit.Test;

public class TestGemis {

    @Test
    public void testCreateGemis() {

        Gemis m = Gemis.forceCreate(TakaraContext.BOND_MASTER_LOCAL);
        Assert.assertEquals(TakaraEntity.BOND, m.getType());
        Assert.assertEquals(0, m.size());
        Assert.assertNull(m.get(1));

        Gemis s = Gemis.forceCreate(TakaraContext.BOND_SLAVE_LOCAL);
        Assert.assertEquals(TakaraEntity.BOND, s.getType());
        Assert.assertEquals(0, s.size());
        Assert.assertNull(s.get(1));
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
