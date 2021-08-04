import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TestGemis {

    private Gemis gemis;

    @Before
    public void init() {
        gemis = new Gemis("BOND");
        for (int i = 0; i < 100; i++)
            gemis.add(SyncStamp.create(i), i * 10);
    }

    @Test
    public void testGemisGet() {

        System.out.println(".... retrieve from gemis");
        for (int i = 0; i < 100; i++)
            System.out.println(gemis.get(i));

        Assert.assertEquals(100, gemis.size());
    }
}
