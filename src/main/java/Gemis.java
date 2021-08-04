import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

class Gemis {

    public static void main(String[] args) throws InterruptedException {

        Gemis gemis = new Gemis("Test Gemis");

        Object lock = new Object();
        synchronized(lock) {
            lock.wait();
        }

        System.out.println("THE END ... ");
    }

    private String name;

    private HashMap<SyncStamp, Integer> data = new HashMap<>();

    public Gemis(String name) {
        name = name;
        System.out.println(String.format("Gemis %s running ... ", name));
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

    public int size() {
        return data.size();
    }
}
