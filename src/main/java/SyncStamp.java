import java.util.Calendar;

public class SyncStamp {

    private long timestamp;
    private long id;

    private SyncStamp() {
        timestamp = Calendar.getInstance().getTimeInMillis();
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
}
