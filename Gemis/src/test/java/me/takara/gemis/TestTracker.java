package me.takara.gemis;

import me.takara.shared.Entity;
import org.junit.Before;

public class TestTracker {

    Gemis gemis;

    public void init() {
        this.gemis = new Gemis(Entity.BOND);
    }

    public void test_pull_from_zero() {

        // populate gemis with 10 value

        // tracker start from 0

        // verify - 10 pulled

    }

    public void test_pull_from_an_existing_x() {

        // when x is before x

        // when x is between zero and max

        // when x is beyond max
    }

}
