import java.util.HashMap;
import java.util.Optional;

public class Gemis {
    public static void main(String[] args) {

        System.out.println("Hello Gemis");

        Gemis gemis = new Gemis("BOND");

        System.out.println("... add 2 items");
        long key1 = 100, key2 = 200;
        gemis.add(SyncStamp.create(key1), 1);
        gemis.add(SyncStamp.create(key2), 2);

        System.out.println(".... retrieve from gemis");
        System.out.println(gemis.get(key1));
        System.out.println(gemis.get(key2));
    }

    private String name;

    private HashMap<SyncStamp, Integer> data = new HashMap<>();

    private Gemis(String name) {
        name = name;
    }

    public void add(SyncStamp stamp, Integer v) {
        data.put(stamp, v);
        Integer temp = data.get(stamp);
        assert temp != null;
        System.out.println(String.format("Successfully added %s", temp));
    }

    public Integer get(long v) {
        Optional<SyncStamp> key = data.keySet().stream().filter(a -> a.getId() == v).findFirst();
        if (key.isPresent())
            return data.get(key.get());
        else
            return null;
    }
}
