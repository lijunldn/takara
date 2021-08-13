package me.takara.gemis;

import me.takara.gemis.id.GemisID;
import me.takara.shared.Entity;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TestGemisID {

    @Test
    public void testVanilla() {

        List<Long> ids = GemisID.generator(Entity.BOND).apply(1);
        Assert.assertEquals(Long.valueOf(1), ids.get(0));

        Long expected = Long.valueOf(2);
        for (int i = 2; i <= 100; i++) {
            ids = GemisID.generator(Entity.BOND).apply(1);
            Assert.assertEquals(Long.valueOf(expected), ids.get(0));
            expected++;
        }

        ids = GemisID.generator(Entity.BOND).apply(100);
        Assert.assertEquals(100, ids.size());
        Assert.assertEquals(Long.valueOf(101), ids.get(0));
        Assert.assertEquals(Long.valueOf(200), ids.get(99));

        ids = GemisID.generator(Entity.EQUITY).apply(1);
        Assert.assertEquals(Long.valueOf(1), ids.get(0));

        ids = GemisID.generator(Entity.BOND).apply(1);
        Assert.assertEquals(Long.valueOf(201), ids.get(0));
    }

    @Test
    public void testMultiThreading() {

        Set<Long> ids = new ConcurrentHashSet<>();

        CompletableFuture[] future = new CompletableFuture[10];
        for (int i = 0; i < 10; i++) {
            future[i] = CompletableFuture.runAsync(() -> {

                var one = GemisID.generator(Entity.BOND).apply(10);
                one.forEach(o -> ids.add(o));

                var two = GemisID.generator(Entity.BOND).apply(20);
                two.forEach(o -> ids.add(o));

                System.out.println(one.size() + two.size() + " IDs generated by " + Thread.currentThread().getName());
            });
        }
        Arrays.stream(future).forEach(f -> f.join());

        Assert.assertEquals(300, ids.size());
        var smallest = ids.stream().filter(o -> o.equals(Long.valueOf(1))).findFirst();
        Assert.assertEquals(1, smallest.get().intValue());
        var biggest = ids.stream().filter(o -> o.equals(Long.valueOf(300))).findFirst();
        Assert.assertEquals(300, biggest.get().intValue());
    }
}
