package me.takara.gemis;

import me.takara.shared.SyncStamp;
import me.takara.shared.entities.Bond;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestGemis {

    private Gemis gemis;

    @Before
    public void init() {
        gemis = new Gemis("BOND");
        for (int i = 0; i < 100; i++)
            gemis.add(SyncStamp.create(i), new Bond(i, "BOND-" + i));
    }

    @Test
    public void testGemisGet() {

        System.out.println(".... retrieve from gemis");
        for (int i = 0; i < 100; i++)
            System.out.println(gemis.get(i));

        Assert.assertEquals(100, gemis.size());
    }
}
