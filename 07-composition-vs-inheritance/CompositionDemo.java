import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Shows the fragile base class problem with an inheritance-based counter,
 * the composition-based fix, and swapping behavior at runtime.
 */
public class CompositionDemo {

    /** BAD: depends on HashSet's internal choice that addAll calls add. */
    static class CountingHashSet<E> extends HashSet<E> {
        int addCount = 0;

        @Override
        public boolean add(E e) {
            addCount++;
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);   // calls add() internally, so elements are counted twice
        }
    }

    /** GOOD: wraps any Set and delegates to it; nothing here depends on internals. */
    static class CountingSet<E> {
        private final Set<E> delegate;
        int addCount = 0;

        CountingSet(Set<E> delegate) { this.delegate = delegate; }

        boolean add(E e) {
            addCount++;
            return delegate.add(e);
        }

        boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return delegate.addAll(c);   // delegate.add is NOT our add, so no double count
        }

        int size() { return delegate.size(); }
    }

    // Runtime flexibility
    interface Engine { String start(); }

    static class PetrolEngine implements Engine { public String start() { return "Vroom"; } }

    static class ElectricEngine implements Engine { public String start() { return "Hmmm"; } }

    static class Car {
        private Engine engine;   // has-a

        Car(Engine engine) { this.engine = engine; }

        void setEngine(Engine engine) { this.engine = engine; }

        String start() { return "Car starts: " + engine.start(); }
    }

    public static void main(String[] args) {
        List<String> items = List.of("a", "b", "c");

        CountingHashSet<String> inherited = new CountingHashSet<>();
        inherited.addAll(items);
        System.out.println("Inheritance addCount (expected 3): " + inherited.addCount);

        CountingSet<String> composed = new CountingSet<>(new HashSet<>());
        composed.addAll(items);
        System.out.println("Composition addCount: " + composed.addCount);

        Car car = new Car(new PetrolEngine());
        System.out.println(car.start());
        car.setEngine(new ElectricEngine());   // impossible to do with inheritance
        System.out.println(car.start());
    }
}
