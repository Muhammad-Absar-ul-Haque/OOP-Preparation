import java.util.List;

/**
 * Shows an interface contract, an abstract class using the Template Method pattern,
 * default/static interface methods, and a functional interface implemented as a lambda.
 */
public class AbstractionDemo {

    /** Contract: what a payment method can do. */
    interface PaymentMethod {
        boolean pay(double amount);

        default String receipt(double amount) {        // default method
            return "Paid " + format(amount) + " via " + getClass().getSimpleName();
        }

        static String format(double amount) {         // static helper
            return String.format("$%.2f", amount);
        }
    }

    /** Template Method: the algorithm skeleton is fixed, and subclasses supply the steps. */
    static abstract class OnlinePayment implements PaymentMethod {
        private final String accountId;                // shared state

        protected OnlinePayment(String accountId) { this.accountId = accountId; }

        @Override
        public final boolean pay(double amount) {      // final: subclasses can't change the flow
            if (!authenticate(accountId)) return false;
            transfer(amount);
            return true;
        }

        protected abstract boolean authenticate(String accountId);

        protected abstract void transfer(double amount);
    }

    static class CardPayment extends OnlinePayment {
        CardPayment(String card) { super(card); }

        @Override protected boolean authenticate(String id) { return id.length() == 16; }

        @Override protected void transfer(double amount) { System.out.println("  charging card..."); }
    }

    static class WalletPayment extends OnlinePayment {
        WalletPayment(String wallet) { super(wallet); }

        @Override protected boolean authenticate(String id) { return id.startsWith("W-"); }

        @Override protected void transfer(double amount) { System.out.println("  debiting wallet..."); }
    }

    @FunctionalInterface
    interface DiscountRule {
        double apply(double amount);
    }

    public static void main(String[] args) {
        DiscountRule tenPercentOff = amount -> amount * 0.9;   // lambda implements the interface
        double total = tenPercentOff.apply(100);

        List<PaymentMethod> methods = List.of(
                new CardPayment("1234567812345678"),
                new WalletPayment("W-42"),
                new WalletPayment("bad-id"));

        for (PaymentMethod m : methods) {
            boolean ok = m.pay(total);
            System.out.println(ok ? m.receipt(total) : "Payment failed for " + m.getClass().getSimpleName());
        }
    }
}
