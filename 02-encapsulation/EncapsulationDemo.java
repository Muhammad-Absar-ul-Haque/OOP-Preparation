import java.util.ArrayList;
import java.util.List;

/**
 * Shows behavior-based encapsulation, a leaky getter, and an immutable class.
 */
public class EncapsulationDemo {

    /** Exposes behavior, not raw state, so the invariant (balance >= 0) always holds. */
    static class BankAccount {
        private double balance;

        void deposit(double amount) {
            if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive");
            balance += amount;
        }

        void withdraw(double amount) {
            if (amount > balance) throw new IllegalStateException("Insufficient funds");
            balance -= amount;
        }

        double getBalance() { return balance; }
    }

    /** BAD: the getter hands out the internal list. */
    static class LeakyTeam {
        private final List<String> members = new ArrayList<>();

        LeakyTeam(String... names) { members.addAll(List.of(names)); }

        List<String> getMembers() { return members; }
    }

    /** GOOD: an immutable class with defensive copies. */
    static final class Team {
        private final String name;
        private final List<String> members;

        Team(String name, List<String> members) {
            this.name = name;
            this.members = List.copyOf(members);   // copy in
        }

        String getName() { return name; }

        List<String> getMembers() { return members; }   // already unmodifiable

        Team withMember(String member) {                 // "modify" by returning a new object
            List<String> copy = new ArrayList<>(members);
            copy.add(member);
            return new Team(name, copy);
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        account.deposit(100);
        try {
            account.withdraw(500);
        } catch (IllegalStateException e) {
            System.out.println("Invariant protected: " + e.getMessage());
        }
        System.out.println("Balance: " + account.getBalance());

        LeakyTeam leaky = new LeakyTeam("Ann", "Bob");
        leaky.getMembers().clear();   // caller wipes internal state!
        System.out.println("Leaky team after outside clear(): " + leaky.getMembers());

        List<String> input = new ArrayList<>(List.of("Ann", "Bob"));
        Team team = new Team("Core", input);
        input.add("Mallory");         // doesn't affect team, because of the defensive copy
        System.out.println("Immutable team: " + team.getMembers());
        try {
            team.getMembers().add("Eve");
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify members through the getter");
        }
        Team bigger = team.withMember("Cid");
        System.out.println("Original: " + team.getMembers() + ", new: " + bigger.getMembers());
    }
}
