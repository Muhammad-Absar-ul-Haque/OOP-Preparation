# 09: Design Patterns

Run: `javac -d out *.java && java -cp out PatternsDemo`

These are the patterns interviewers ask about most. You should be able to **write each one from memory** and explain *when* you'd use it.

---

### Q1. What are the three categories of GoF design patterns?

- **Creational** (how objects are created): Singleton, Factory Method, Abstract Factory, Builder, Prototype.
- **Structural** (how objects are composed): Adapter, Decorator, Proxy, Facade, Composite, Bridge, Flyweight.
- **Behavioral** (how objects communicate): Strategy, Observer, Command, Template Method, Iterator, State, Chain of Responsibility, Visitor, and others.

---

### Q2. Implement a thread-safe Singleton. What are the options?

1. **Eager initialization:** `private static final X INSTANCE = new X();`. Simple and thread-safe, but not lazy.
2. **Synchronized `getInstance()`:** lazy and safe, but locks on every call.
3. **Double-checked locking:** lazy and fast. The field **must be `volatile`**, otherwise another thread may see a partially constructed object.
4. **Initialization-on-demand holder:** lazy, thread-safe and lock-free, because the JVM guarantees class initialization is thread-safe. **This is usually the best answer.**
5. **Enum singleton:** `enum Config { INSTANCE; }`. Also safe against reflection and serialization attacks. *Effective Java* recommends it.

**Follow-up:** *What breaks a singleton?* Reflection (calling the private constructor), serialization (implement `readResolve`), cloning, and multiple class loaders. An enum resists the first three.

**Follow-up:** *Why are singletons criticized?* They're global mutable state, they hide dependencies, and they're hard to mock in tests. Prefer dependency injection with a single instance.

---

### Q3. Factory Method vs Abstract Factory?

- **Factory Method:** one method creates one product, and subclasses or a parameter decide which concrete class to create. Example: `NotificationFactory.create("email")`.
- **Abstract Factory:** an object that creates **families** of related products. Example: `GuiFactory` with `createButton()` and `createCheckbox()`, implemented by `WindowsFactory` and `MacFactory`.

---

### Q4. When would you use Builder?

When a constructor would have many parameters, especially optional ones. Builder avoids *telescoping constructors* and unreadable calls like `new Pizza(12, true, false, true, null)`. It also works well with immutable objects. JDK examples are `StringBuilder` and `HttpRequest.newBuilder()`, and Lombok provides `@Builder`.

---

### Q5. Explain Strategy. How does it relate to Open/Closed?

Strategy defines a family of interchangeable algorithms behind an interface and picks one at runtime: `PaymentStrategy`, `Comparator`, `SortStrategy`. You add a new algorithm by adding a class, which is OCP in practice. In modern Java, strategies are often written as lambdas.

---

### Q6. Explain Observer. Where is it used?

A **subject** keeps a list of **observers** and notifies them when its state changes. Examples are event listeners in GUIs, pub/sub messaging, and reactive streams. **Pitfall:** forgetting to unsubscribe causes memory leaks, the "lapsed listener" problem.

---

### Q7. Decorator vs Proxy vs Adapter?

All three wrap an object, but for different reasons:

- **Adapter:** *changes the interface* so incompatible classes can work together (`Arrays.asList`, `InputStreamReader`).
- **Decorator:** *keeps the interface* and **adds behavior**, and decorators can be stacked (`new BufferedReader(new FileReader(f))`).
- **Proxy:** *keeps the interface* and **controls access**: lazy loading, security, caching, or remote calls (Spring AOP, Hibernate lazy entities).
