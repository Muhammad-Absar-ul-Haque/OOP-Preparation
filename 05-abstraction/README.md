# 05: Abstraction

Run: `javac -d out *.java && java -cp out AbstractionDemo`

---

### Q1. What is abstraction?

Abstraction means exposing the essential behavior of something and hiding its implementation details. Callers code against *what* an object does (`PaymentMethod.pay(amount)`) without knowing *how* it does it (card network, wallet API, and so on). In Java, you achieve it with **abstract classes** and **interfaces**.

---

### Q2. Abstract class vs interface: what are the differences?

| | Abstract class | Interface |
|---|---|---|
| Instance state (fields) | ✅ Any fields | ❌ Only `public static final` constants |
| Constructors | ✅ | ❌ |
| Method kinds | Abstract and concrete, any access | Abstract, `default`, `static`, `private` (Java 9+) |
| Inheritance | A class extends **one** | A class implements **many** |
| Access of abstract methods | Any except `private` | Implicitly `public` |
| Models | **is-a** with shared code/state | **can-do** capability or contract |

---

### Q3. When would you choose an abstract class over an interface?

- **Abstract class:** when closely related classes share **state** or a **template** of behavior. The *Template Method* pattern is the textbook case: the base class fixes the algorithm skeleton and subclasses fill in the steps.
- **Interface:** when you're defining a capability that unrelated classes may have (`Comparable`, `Runnable`), or when you need multiple inheritance of type. Prefer interfaces by default, because they're more flexible.

A common combination: an interface for the contract, plus an abstract "skeletal" class that implements it partially (`List` and `AbstractList`).

---

### Q4. Can an abstract class have a constructor? Can you instantiate it?

It **can** have a constructor, which subclasses call through `super(...)` to initialize shared state. You **cannot** instantiate it directly with `new`. You can, however, create an anonymous subclass: `new Shape() { ... }`.

---

### Q5. Can an abstract class have no abstract methods?

Yes. Marking it `abstract` simply prevents instantiation. `HttpServlet` is a well-known example.

---

### Q6. Why were default methods added to interfaces in Java 8?

They allow **interface evolution**: adding a method to a widely implemented interface (like `Collection.stream()`) without breaking every existing implementation.

---

### Q7. What is a functional interface?

An interface with exactly **one abstract method** (`Runnable`, `Comparator`, `Function`). You can implement it with a lambda. The `@FunctionalInterface` annotation makes the compiler enforce the rule.

---

### Q8. What is a marker interface?

An interface with no methods that tags a class with a capability: `Serializable`, `Cloneable`, `RandomAccess`. Annotations often serve this purpose in modern code.

---

### Q9. Can an abstract method be `private`, `static`, or `final`?

None of them. All three prevent overriding, but an abstract method *must* be overridden.
