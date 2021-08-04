import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TestGemis {

    private static int[] items;

    @Before
    void init() {
        items = new int[100];
        for (int i = 0; i < 101; i++)
            items[i] = i + 1;
    }

    @Test
    public void testGemisAdd() {
        Gemis gemis = new Gemis("BOND");
        Arrays.stream(items).forEach(v -> gemis.add(SyncStamp.create(v), v * 100));

        System.out.println(".... retrieve from gemis");

        Arrays.stream(items).forEach(key -> System.out.println(gemis.get(key)));

        Assert.assertEquals(100, gemis.size());
    }
}
