package me.takara.shared;

import java.util.Calendar;

public class SyncStamp implements Comparable<SyncStamp> {

    private long timestamp;
    private long id;

    private SyncStamp() {
        timestamp = Calendar.getInstance().getTimeInMillis();
    }

    public final static SyncStamp ZERO;

    static {
        ZERO = new SyncStamp();
        ZERO.timestamp = 0;
        ZERO.id = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SyncStamp syncStamp = (SyncStamp) o;

        if (timestamp != syncStamp.timestamp) return false;
        return id == syncStamp.id;
    }

    @Override
    public int hashCode() {
        int result = (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    public static SyncStamp create(long id) {
        SyncStamp me = new SyncStamp();
        me.id = id;
        return me;
    }

    public long getId() {
        return id;
    }

    @Override
    public int compareTo(SyncStamp o) {
        if (this.timestamp < o.timestamp) return -1;
        else if (this.timestamp > o.timestamp) return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return String.format("{SyncStamp:%s|%s}", id, timestamp);
    }
}
