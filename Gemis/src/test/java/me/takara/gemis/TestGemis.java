package me.takara.gemis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import me.takara.shared.SyncStamp;
import me.takara.shared.entities.Bond;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestGemis {

    private Gemis gemis;
    private Bond b100, b200, b300;

    @Before
    public void init() {
        gemis = new Gemis("BOND");
        for (int i = 0; i < 100; i++)
            gemis.add(new Bond(i, "BOND-" + i));

        b100 = new Bond(100, "Morgan Stanley");
        b200 = new Bond(200, "Nomura");
        b300 = new Bond(300, "Merrill Lynch");
    }

    @Test
    public void testGemisAdd() {

        SyncStamp stamp = gemis.add(b100);
        Bond tmp = gemis.get(b100.getId());
        Assert.assertEquals(b100.getName(), tmp.getName());
        Assert.assertEquals(b100.getId(), tmp.getId());

        // add same bond again
        SyncStamp stampX = gemis.add(b100);
        Bond tmpX = gemis.get(b100.getId());
        Assert.assertEquals(tmp.getName(), tmpX.getName());
        Assert.assertEquals(tmp.getId(), tmpX.getId());
        Assert.assertEquals(1, stampX.compareTo(stamp) );

        SyncStamp stamp2 = gemis.add(b200);
        Bond tmp2 = gemis.get(b200.getId());
        Assert.assertEquals(b200.getName(), tmp2.getName());
        Assert.assertEquals(b200.getId(), tmp2.getId());

    }

    @Test
    public void testGemisGet() {

        System.out.println(".... retrieve from gemis");
        for (int i = 0; i < 100; i++)
            System.out.println(gemis.get(i));

        Assert.assertEquals(100, gemis.size());

    }
}
