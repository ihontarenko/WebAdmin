package guru.users.borisovich;

import java.util.*;
import java.util.stream.Stream;

public class JavaRun2 {

    public static void main(String[] args) {
        Map<Key, String> map = new HashMap<>();
        Key              key = new Key(1);
        Key              key2 = new Key(1);

        map.put(key, "val");
        map.put(key2, "val2");

        System.out.println(map.get(key2));
    }

    public static class Key {

        private int id;

        public Key(int id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            System.out.println("equals...");
            return super.equals(obj);
        }
    }


}
