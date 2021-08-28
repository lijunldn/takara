package me.takara.client;

import me.takara.shared.TakaraContext;
import org.junit.Assert;
import org.junit.Test;

public class TestTakaraTracker {

    boolean gemisReady() {
        try {
            return TakaraBuilder.create(TakaraContext.BOND_PRIMARY_LOCAL).heartbeat();
        } catch (Exception ex) {
            System.out.println("No Gemis service!");
            return false;
        }
    }

    @Test
    public void testHasNext() {
        if (!gemisReady()) return;

        var tracker = TakaraBuilder.createTrackerSinceTimeZero(TakaraContext.BOND_PRIMARY_LOCAL);
        var r = tracker.hasNext();
        Assert.assertTrue(r);
    }

    @Test
    public void testPuller() {
        if (!gemisReady()) return;

        var tracker = TakaraBuilder.createTrackerSinceTimeZero(TakaraContext.BOND_PRIMARY_LOCAL);
        while (tracker.hasNext()) {
            var items = tracker.next(10);
        }

    }
}