/**
 * Shows constructors, this(...) chaining, static vs instance state,
 * and the order in which objects are initialized.
 */
public class ClassesDemo {

    static class Parent {
        static { System.out.println("1. Parent static block"); }
        { System.out.println("3. Parent instance block"); }

        Parent() { System.out.println("4. Parent constructor"); }
    }

    static class Child extends Parent {
        static { System.out.println("2. Child static block"); }
        { System.out.println("5. Child instance block"); }

        Child() { System.out.println("6. Child constructor"); }
    }

    static class Counter {
        static int created = 0;   // shared by all instances
        final int id;             // one per instance
        final String label;

        Counter() {
            this("unnamed");      // constructor chaining, must be first
        }

        Counter(String label) {
            this.label = label;   // 'this' tells the field apart from the parameter
            this.id = ++created;
        }

        @Override
        public String toString() {
            return "Counter#" + id + "(" + label + ")";
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Initialization order (first object) ---");
        new Child();

        System.out.println("\n--- Second object: static blocks do NOT run again ---");
        new Child();

        System.out.println("\n--- Static vs instance ---");
        Counter a = new Counter();
        Counter b = new Counter("custom");
        System.out.println(a + ", " + b);
        System.out.println("Counter.created = " + Counter.created);
    }
}
