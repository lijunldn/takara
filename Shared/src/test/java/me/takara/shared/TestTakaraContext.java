package me.takara.shared;

import org.junit.Assert;
import org.junit.Test;

public class TestTakaraContext {

    @Test
    public void testToString() {
        Assert.assertEquals("Gemis<Bond> (LOCAL|MASTER:localhost|8090)", TakaraContext.BOND_MASTER_LOCAL.toString());
        Assert.assertEquals("Gemis<Bond> (LOCAL|SLAVE:localhost|8091 <- MASTER:localhost|8090)", TakaraContext.BOND_SLAVE_LOCAL.toString());
    }

    @Test
    public void testValueOf() {
        TakaraContext context = TakaraContext.valueOf("BOND_MASTER_LOCAL");
        Assert.assertEquals(TakaraContext.BOND_MASTER_LOCAL, context);
    }
}