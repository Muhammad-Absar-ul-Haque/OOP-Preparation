import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Shows the equals/hashCode contract, what breaks without it,
 * mutable HashMap keys, and shallow vs deep copy.
 */
public class ObjectMethodsDemo {

    /** BAD: overrides equals but not hashCode. */
    static class BrokenPoint {
        final int x, y;

        BrokenPoint(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BrokenPoint p)) return false;
            return x == p.x && y == p.y;
        }
    }

    /** GOOD: equals and hashCode are consistent. */
    static final class Point {
        final int x, y;

        Point(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point p = (Point) o;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }

        @Override
        public String toString() { return "Point(" + x + ", " + y + ")"; }
    }

    /** The modern alternative: the compiler generates equals, hashCode and toString. */
    record PointRecord(int x, int y) {}

    static class MutableKey {
        int id;

        MutableKey(int id) { this.id = id; }

        @Override public boolean equals(Object o) { return o instanceof MutableKey k && k.id == id; }

        @Override public int hashCode() { return Integer.hashCode(id); }
    }

    static class Playlist {
        String name;
        List<String> songs;

        Playlist(String name, List<String> songs) { this.name = name; this.songs = songs; }

        Playlist shallowCopy() { return new Playlist(name, songs); }

        Playlist deepCopy() { return new Playlist(name, new ArrayList<>(songs)); }   // copy constructor style
    }

    public static void main(String[] args) {
        System.out.println("--- equals without hashCode ---");
        Set<BrokenPoint> broken = new HashSet<>();
        broken.add(new BrokenPoint(1, 2));
        broken.add(new BrokenPoint(1, 2));
        System.out.println("BrokenPoint set size (expected 1): " + broken.size());

        Set<Point> good = new HashSet<>();
        good.add(new Point(1, 2));
        good.add(new Point(1, 2));
        System.out.println("Point set size: " + good.size() + " -> " + good);
        System.out.println("Record: " + new PointRecord(1, 2) + ", equal? "
                + new PointRecord(1, 2).equals(new PointRecord(1, 2)));

        System.out.println("\n--- Mutating a HashMap key ---");
        Map<MutableKey, String> map = new HashMap<>();
        MutableKey key = new MutableKey(1);
        map.put(key, "value");
        key.id = 99;
        System.out.println("map.get(key) = " + map.get(key) + ", map.size() = " + map.size());

        System.out.println("\n--- Shallow vs deep copy ---");
        Playlist original = new Playlist("Mix", new ArrayList<>(List.of("A", "B")));
        Playlist shallow = original.shallowCopy();
        Playlist deep = original.deepCopy();
        original.songs.add("C");
        System.out.println("shallow sees: " + shallow.songs + "  (shared list)");
        System.out.println("deep sees:    " + deep.songs + "  (independent list)");
    }
}
