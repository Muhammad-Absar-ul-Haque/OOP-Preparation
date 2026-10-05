import java.util.List;

/**
 * Shows dynamic dispatch, overload resolution rules, covariant returns,
 * and pattern-matching downcasts.
 */
public class PolymorphismDemo {

    static abstract class Shape {
        abstract double area();

        Shape copy() { throw new UnsupportedOperationException(); }
    }

    static class Circle extends Shape {
        final double r;

        Circle(double r) { this.r = r; }

        @Override double area() { return Math.PI * r * r; }

        @Override Circle copy() { return new Circle(r); }   // covariant return
    }

    static class Square extends Shape {
        final double side;

        Square(double side) { this.side = side; }

        @Override double area() { return side * side; }
    }

    // Overload resolution
    static void print(Object o)  { System.out.println("print(Object)"); }
    static void print(String s)  { System.out.println("print(String)"); }
    static void print(long l)    { System.out.println("print(long)"); }
    static void print(Integer i) { System.out.println("print(Integer)"); }
    static void print(int... xs) { System.out.println("print(int...)"); }

    static void describe(Object o) { System.out.println("describe(Object)"); }
    static void describe(String s) { System.out.println("describe(String): String is more specific"); }

    public static void main(String[] args) {
        System.out.println("--- Dynamic dispatch ---");
        List<Shape> shapes = List.of(new Circle(1), new Square(2));
        for (Shape s : shapes) {
            System.out.printf("%s area = %.2f%n", s.getClass().getSimpleName(), s.area());
        }

        System.out.println("\n--- Covariant return: no cast needed ---");
        Circle c2 = new Circle(3).copy();
        System.out.println("Copied circle radius = " + c2.r);

        System.out.println("\n--- Overload resolution ---");
        print(5);          // widening (int -> long) beats boxing (int -> Integer)
        print((Integer) 5);
        print("hi");
        print((Object) "hi");   // the declared type decides, not the runtime type
        print();           // only varargs fits
        // print(null) would NOT compile here: String, Integer and int[] all accept null,
        // and none is more specific than the others. With just Object and String, it's fine:
        describe(null);

        System.out.println("\n--- Pattern-matching downcast ---");
        for (Shape s : shapes) {
            if (s instanceof Circle circle) {
                System.out.println("Circle with r=" + circle.r);
            } else if (s instanceof Square sq) {
                System.out.println("Square with side=" + sq.side);
            }
        }
    }
}
