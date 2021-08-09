import com.fasterxml.jackson.annotation.JsonTypeInfo;
import junit.framework.TestCase;
import me.takara.shared.Entity;
import me.takara.shared.entities.Bond;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class TestGemisClient extends TestCase {

    @Test
    public void testHello() throws IOException {
        String message = new GemisClient(Entity.BOND).hello();
        Assert.assertEquals(String.format("HELLO! I'm your %s Gemis.", "Bond"), message);
    }

    @Test
    public void testGet() {
//        try {
//            Bond bond = new GemisClient().get(1);
//            Assert.assertEquals(1, bond.getId());
//            Assert.assertEquals("BOND-1", bond.getName());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testSet() throws IOException {
//        Bond bond = new Bond(100, "Morgan Stanley");
//        new GemisClient().set(bond);
    }
}