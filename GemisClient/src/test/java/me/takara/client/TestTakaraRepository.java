package me.takara.client;

import junit.framework.TestCase;
import me.takara.shared.Entity;
import org.junit.Test;

public class TestTakaraRepository extends TestCase {

    @Test
    public void testGet() {
        var repo = TakaraBuilder.create(Entity.BOND);
        repo.get(12);
    }
}