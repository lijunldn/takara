package me.takara.shared;

import org.junit.Assert;
import org.junit.Test;

public class TestTakaraContext {

    @Test
    public void testToString() {
        Assert.assertEquals("Gemis<Bond> (LOCAL|MASTER:localhost|8090)", TakaraContext.BOND_PRIMARY_LOCAL.toString());
        Assert.assertEquals("Gemis<Bond> (LOCAL|SLAVE:localhost|8091 <- MASTER:localhost|8090)", TakaraContext.BOND_SECONDARY_LOCAL.toString());
    }

    @Test
    public void testValueOf() {
        TakaraContext context = TakaraContext.valueOf("BOND_PRIMARY_LOCAL");
        Assert.assertEquals(TakaraContext.BOND_PRIMARY_LOCAL, context);
    }

    @Test
    public void testGetPrimaryContext() {

        Assert.assertEquals(TakaraContext.EQUITY_PRIMARY_LOCAL, TakaraContext.EQUITY_PRIMARY_LOCAL.getPrimaryContext());
        Assert.assertEquals(TakaraContext.EQUITY_PRIMARY_LOCAL, TakaraContext.EQUITY_SECONDARY_LOCAL.getPrimaryContext());

        Assert.assertEquals(TakaraContext.BOND_PRIMARY_LOCAL, TakaraContext.BOND_SECONDARY_LOCAL.getPrimaryContext());


    }
}