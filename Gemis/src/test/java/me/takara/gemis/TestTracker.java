package me.takara.gemis;

import me.takara.shared.Entity;
import me.takara.shared.Instrument;
import me.takara.shared.SyncStamp;
import me.takara.shared.entities.Bond;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestTracker {

    Gemis gemis;

    @Before
    public void init() {
        this.gemis = new Gemis(Entity.BOND);
    }

    private SyncStamp createSampleData(int total) {
        SyncStamp stamp = SyncStamp.ZERO;
        for (int i = 0; i < total; i++) {
            stamp = this.gemis.add(new Bond(i, "BOND" + i));
        }
        return stamp;
    }

    private List<Instrument> pull(GemisPuller puller, int expectedId) {
        List<Instrument> list = new ArrayList<>(10);
        int i = expectedId;
        while (puller.hasMoreItems()) {

            // verify the order
            var bds = puller.next(2);

            for (int n = 0; n < bds.size(); n++) {
                Assert.assertEquals(i, bds.get(n).getId());
                i++;
            }

            list.addAll(bds);
        }
        return list;
    }

    @Test
    public void test_pull_from_zero() {

        int total = 9;
        // populate gemis with 10 value
        SyncStamp stampX = createSampleData(total);

        // tracker start from 0
        var puller = gemis.pullSinceTimeZero();
        List<Instrument> list = pull(puller, 0);

        // verify - 10 pulled
        Assert.assertEquals(total, list.size());
    }

    public void test_pull_from_an_existing_x() {

        SyncStamp stampX = createSampleData(4);
        SyncStamp stampY = createSampleData(6);
        // when x is before x
        var puller = gemis.pullSince(stampX);
        List<Instrument> list = pull(puller, 4);

        // when x is between zero and max

        // when x is beyond max
    }

}
