import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Minimal, interview-ready implementations of Singleton, Factory, Builder,
 * Strategy, and Observer.
 */
public class PatternsDemo {

    // ---------- Singleton: initialization-on-demand holder ----------
    static final class AppConfig {
        private AppConfig() { System.out.println("  AppConfig created (lazily, exactly once)"); }

        private static class Holder {
            static final AppConfig INSTANCE = new AppConfig();
        }

        static AppConfig getInstance() { return Holder.INSTANCE; }
    }

    // ---------- Singleton: double-checked locking (know why volatile is needed) ----------
    static final class Registry {
        private static volatile Registry instance;

        private Registry() {}

        static Registry getInstance() {
            Registry local = instance;
            if (local == null) {
                synchronized (Registry.class) {
                    local = instance;
                    if (local == null) {
                        instance = local = new Registry();
                    }
                }
            }
            return local;
        }
    }

    // ---------- Factory ----------
    interface Notification { void send(String msg); }

    static class EmailNotification implements Notification {
        public void send(String msg) { System.out.println("  Email: " + msg); }
    }

    static class SmsNotification implements Notification {
        public void send(String msg) { System.out.println("  SMS: " + msg); }
    }

    static class NotificationFactory {
        static Notification create(String channel) {
            return switch (channel) {
                case "email" -> new EmailNotification();
                case "sms" -> new SmsNotification();
                default -> throw new IllegalArgumentException("Unknown channel: " + channel);
            };
        }
    }

    // ---------- Builder ----------
    static final class Pizza {
        private final String size;
        private final boolean cheese, pepperoni, mushrooms;

        private Pizza(Builder b) {
            this.size = b.size;
            this.cheese = b.cheese;
            this.pepperoni = b.pepperoni;
            this.mushrooms = b.mushrooms;
        }

        static Builder builder(String size) { return new Builder(size); }

        @Override
        public String toString() {
            return size + " pizza [cheese=" + cheese + ", pepperoni=" + pepperoni + ", mushrooms=" + mushrooms + "]";
        }

        static final class Builder {
            private final String size;   // required
            private boolean cheese, pepperoni, mushrooms;   // optional

            private Builder(String size) { this.size = size; }

            Builder cheese() { cheese = true; return this; }

            Builder pepperoni() { pepperoni = true; return this; }

            Builder mushrooms() { mushrooms = true; return this; }

            Pizza build() { return new Pizza(this); }
        }
    }

    // ---------- Strategy ----------
    interface ShippingStrategy { double cost(double weightKg); }

    static class Checkout {
        private ShippingStrategy shipping;

        Checkout(ShippingStrategy shipping) { this.shipping = shipping; }

        void setShipping(ShippingStrategy shipping) { this.shipping = shipping; }

        double shippingCost(double weightKg) { return shipping.cost(weightKg); }
    }

    // ---------- Observer ----------
    static class StockTicker {
        private final List<Consumer<Double>> observers = new ArrayList<>();
        private double price;

        void subscribe(Consumer<Double> observer) { observers.add(observer); }

        void unsubscribe(Consumer<Double> observer) { observers.remove(observer); }

        void setPrice(double price) {
            this.price = price;
            observers.forEach(o -> o.accept(price));
        }
    }

    public static void main(String[] args) {
        System.out.println("Singleton:");
        System.out.println("  before first getInstance()");
        System.out.println("  same instance? " + (AppConfig.getInstance() == AppConfig.getInstance()));
        System.out.println("  registry same? " + (Registry.getInstance() == Registry.getInstance()));

        System.out.println("Factory:");
        NotificationFactory.create("email").send("Welcome!");
        NotificationFactory.create("sms").send("Your code is 1234");

        System.out.println("Builder:");
        System.out.println("  " + Pizza.builder("Large").cheese().mushrooms().build());

        System.out.println("Strategy:");
        Checkout checkout = new Checkout(kg -> 5.0);           // flat rate
        System.out.println("  flat:    " + checkout.shippingCost(10));
        checkout.setShipping(kg -> 1.5 * kg);                  // per kg, swapped at runtime
        System.out.println("  per kg:  " + checkout.shippingCost(10));

        System.out.println("Observer:");
        StockTicker ticker = new StockTicker();
        Consumer<Double> logger = p -> System.out.println("  logger saw " + p);
        Consumer<Double> alert = p -> { if (p > 100) System.out.println("  ALERT: price above 100!"); };
        ticker.subscribe(logger);
        ticker.subscribe(alert);
        ticker.setPrice(95);
        ticker.setPrice(105);
        ticker.unsubscribe(logger);
        ticker.setPrice(110);   // only alert fires
    }
}
