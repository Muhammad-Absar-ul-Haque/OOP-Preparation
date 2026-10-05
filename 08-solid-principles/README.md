# 08: SOLID Principles

Run: `javac -d out *.java && java -cp out SolidDemo`

---

### Q1. What does SOLID stand for?

| Letter | Principle | One-liner |
|---|---|---|
| **S** | Single Responsibility | A class should have one reason to change. |
| **O** | Open/Closed | Open for extension, closed for modification. |
| **L** | Liskov Substitution | Subtypes must be usable anywhere their base type is expected. |
| **I** | Interface Segregation | Don't force clients to depend on methods they don't use. |
| **D** | Dependency Inversion | Depend on abstractions, not concretions. |

---

### Q2. Single Responsibility: give an example of a violation.

An `Invoice` class that calculates totals, **and** saves itself to a database, **and** formats itself as a PDF. It has three reasons to change: tax rules, the database schema, and the report layout. Split it into `Invoice`, `InvoiceRepository` and `InvoicePrinter`.

---

### Q3. Open/Closed: how do you add new behavior without changing existing code?

Replace `if/else` or `switch` on a type with polymorphism. Instead of:

```java
double area(Object s) {
    if (s instanceof Circle c) ... else if (s instanceof Square q) ...   // edit for every new shape
}
```

give each shape an `area()` method through a `Shape` interface. Adding a `Triangle` then means adding a class, not editing existing code. `SolidDemo.java` applies this to discount calculation.

---

### Q4. Liskov Substitution: why is `Square extends Rectangle` a classic violation?

`Rectangle` lets you set width and height independently. `Square` has to keep them equal, so `setWidth` also changes the height. Code written for `Rectangle` breaks:

```java
void resize(Rectangle r) {
    r.setWidth(5);
    r.setHeight(4);
    assert r.area() == 20;   // fails for a Square: area is 16
}
```

A square *is* a rectangle mathematically, but not *behaviorally* when the object is mutable. A fix is to make both immutable, or to have both implement a common `Shape`.

**Other LSP smells:** a subclass method throwing `UnsupportedOperationException`, strengthening preconditions, or weakening postconditions.

---

### Q5. Interface Segregation: give an example.

A fat `Worker` interface with `work()` and `eat()` forces a `Robot` to implement `eat()`. Split it into `Workable` and `Eatable`. In the JDK, `List.of(...)` throwing `UnsupportedOperationException` on `add` is often cited as a real-world compromise.

---

### Q6. Dependency Inversion vs Dependency Injection: what's the difference?

- **Dependency Inversion** is the *principle*: high-level modules (`OrderService`) and low-level modules (`MySqlRepository`) both depend on an abstraction (`OrderRepository`).
- **Dependency Injection** is a *technique* for achieving it: pass dependencies in, usually through the constructor, instead of creating them with `new` inside the class. Spring is a DI container that automates this.

The payoff is that you can pass an in-memory fake into `OrderService` in tests.
