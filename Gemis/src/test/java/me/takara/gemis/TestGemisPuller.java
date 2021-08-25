package me.takara.gemis;

import me.takara.gemis.entities.BondImp;
import me.takara.shared.TakaraContext;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TestGemisPuller {

    Gemis gemis;

    @Before
    public synchronized void init() {
        this.gemis = Gemis.forceCreate(TakaraContext.BOND_MASTER_LOCAL);
    }

    private SyncStamp createSampleData(int total) {
        SyncStamp stamp = SyncStamp.ZERO;
        for (int i = 0; i < total; i++) {
            stamp = this.gemis.add(new BondImp("BOND" + i));
        }
        return stamp;
    }

    private List<Instrument> pull(GemisPuller puller) {
        List<Instrument> list = new ArrayList<>(10);
        while (puller.hasMore()) {

            // verify the order
            var bds = puller.next(2);
            list.addAll(bds.getInstruments());
        }
        return list;
    }

    static class SyncStampTest extends SyncStamp {
        SyncStampTest(long stamp, long id) {
            this.timestamp = stamp;
            this.id = id;
        }
    }

    @Test
    public void testFilter() {
        HashMap<SyncStamp, Instrument> data = new HashMap<>();
        var t1 = new SyncStampTest(111, 1);
        var t2 = new SyncStampTest(111, 2);
        data.put(t1, null);
        data.put(t2, null);
        data.put(new SyncStampTest(111, 3), null);
        Assert.assertEquals(2, new GemisPuller(data).of(t1).applyFilter().count());
        Assert.assertEquals(1, new GemisPuller(data).of(t2).applyFilter().count());
    }

    @Test
    public void test_pull_from_zero() {

        int total = 9;
        // populate gemis with 10 value
        SyncStamp stampX = createSampleData(total);

        // tracker start from 0
        var puller = gemis.pullSinceTimeZero();
        List<Instrument> list = pull(puller);

        // verify - 10 pulled
        Assert.assertEquals(total, list.size());
    }

    @Test
    public void test_pull_from_an_existing_x() {

        System.out.println("Add 4 items");
        SyncStamp stampX = createSampleData(4);
        System.out.println("Add 6 items");
        SyncStamp stampY = createSampleData(6);

        // when x is between zero and max
        var puller = gemis.pullSince(stampX);
        List<Instrument> list = pull(puller);
        System.out.println("Results: ");
        list.forEach(System.out::println);
        // verify - 10 pulled
        Assert.assertEquals(6, list.size());

        // when x is beyond max
    }

}
