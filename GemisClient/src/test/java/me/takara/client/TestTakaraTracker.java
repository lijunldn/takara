package me.takara.client;

import me.takara.shared.Entity;
import org.junit.Test;

public class TestTakaraTracker {

    boolean gemisReady() {
        try {
            return TakaraBuilder.create(Entity.BOND).heartbeat();
        } catch (Exception ex) {
            System.out.println("No Gemis service!");
            return false;
        }
    }

    @Test
    public void testPuller() {
        if (!gemisReady()) return;

        var tracker = TakaraBuilder.createTrackerSinceTimeZero(Entity.BOND);
        while (tracker.hasNext()) {
            var items = tracker.next(100);
        }

    }
}