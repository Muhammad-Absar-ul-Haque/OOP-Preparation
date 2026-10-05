import java.util.ArrayList;
import java.util.List;

/**
 * Shows each SOLID principle in a small order-processing example.
 */
public class SolidDemo {

    // O: open/closed. New discounts are new classes; no existing code changes.
    interface Discount { double apply(double total); }

    static class NoDiscount implements Discount { public double apply(double t) { return t; } }

    static class PercentOff implements Discount {
        private final double percent;

        PercentOff(double percent) { this.percent = percent; }

        public double apply(double t) { return t * (1 - percent / 100); }
    }

    static class FlatOff implements Discount {
        private final double amount;

        FlatOff(double amount) { this.amount = amount; }

        public double apply(double t) { return Math.max(0, t - amount); }
    }

    // S: single responsibility. Order only knows about its own data and total.
    record Order(String id, double subtotal, Discount discount) {
        double total() { return discount.apply(subtotal); }
    }

    // I: interface segregation. Small, focused interfaces.
    interface OrderReader { List<Order> findAll(); }

    interface OrderWriter { void save(Order order); }

    interface OrderRepository extends OrderReader, OrderWriter {}

    // D: dependency inversion. The low-level detail implements the abstraction.
    static class InMemoryOrderRepository implements OrderRepository {
        private final List<Order> store = new ArrayList<>();

        public void save(Order order) { store.add(order); }

        public List<Order> findAll() { return List.copyOf(store); }
    }

    interface Notifier { void send(String message); }

    static class ConsoleNotifier implements Notifier {
        public void send(String message) { System.out.println("[notify] " + message); }
    }

    // The high-level module depends only on abstractions, injected through the constructor.
    static class OrderService {
        private final OrderWriter writer;
        private final Notifier notifier;

        OrderService(OrderWriter writer, Notifier notifier) {
            this.writer = writer;
            this.notifier = notifier;
        }

        void place(Order order) {
            writer.save(order);
            notifier.send("Order " + order.id() + " placed, total " + String.format("%.2f", order.total()));
        }
    }

    // S: reporting is a separate responsibility, and it only needs the reader (I).
    static class OrderReport {
        private final OrderReader reader;

        OrderReport(OrderReader reader) { this.reader = reader; }

        double revenue() { return reader.findAll().stream().mapToDouble(Order::total).sum(); }
    }

    public static void main(String[] args) {
        InMemoryOrderRepository repo = new InMemoryOrderRepository();
        OrderService service = new OrderService(repo, new ConsoleNotifier());

        service.place(new Order("A1", 100, new NoDiscount()));
        service.place(new Order("A2", 100, new PercentOff(15)));
        service.place(new Order("A3", 100, new FlatOff(30)));

        System.out.printf("Total revenue: %.2f%n", new OrderReport(repo).revenue());

        // L: Liskov. Any Discount can replace another without breaking Order.
        //    (A "Discount" that threw an exception or raised the price would violate the contract.)
    }
}
