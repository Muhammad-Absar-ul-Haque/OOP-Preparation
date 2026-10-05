/**
 * Shows constructor chaining, super calls, diamond resolution with default methods,
 * and the "overridable method called from a constructor" trap.
 */
public class InheritanceDemo {

    static class Animal {
        protected final String name;

        Animal(String name) {
            this.name = name;
            System.out.println("Animal constructor for " + name);
        }

        String sound() { return "..."; }

        String describe() { return name + " says " + sound(); }
    }

    static class Dog extends Animal {
        Dog(String name) {
            super(name);   // must come first; Animal has no no-arg constructor
            System.out.println("Dog constructor for " + name);
        }

        @Override
        String sound() { return "Woof"; }

        @Override
        String describe() { return super.describe() + " (and wags its tail)"; }
    }

    // Diamond with default methods
    interface Walker { default String move() { return "walk"; } }
    interface Swimmer { default String move() { return "swim"; } }

    static class Duck implements Walker, Swimmer {
        @Override
        public String move() {   // REQUIRED, otherwise it doesn't compile
            return Walker.super.move() + " and " + Swimmer.super.move();
        }
    }

    // Constructor trap
    static class Base {
        Base() { init(); }   // calls an overridable method!

        void init() {}
    }

    static class Derived extends Base {
        private String config = "loaded";

        @Override
        void init() { System.out.println("Derived.init sees config = " + config); }
    }

    public static void main(String[] args) {
        Animal dog = new Dog("Rex");
        System.out.println(dog.describe());

        System.out.println("\nDuck can: " + new Duck().move());

        System.out.println("\nConstructor trap:");
        new Derived();   // prints null, because config isn't assigned yet
    }
}
