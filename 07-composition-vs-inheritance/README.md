# 07: Composition vs Inheritance

Run: `javac -d out *.java && java -cp out CompositionDemo`

---

### Q1. What is the difference between composition and inheritance?

- **Inheritance (is-a):** `Car extends Vehicle`. The subclass *is* a kind of the parent and inherits its implementation.
- **Composition (has-a):** `Car` has an `Engine` field and **delegates** work to it.

---

### Q2. Why "favor composition over inheritance"?

- **Looser coupling:** inheritance exposes the parent's implementation to the subclass, so changes in the parent can break subclasses. This is the *fragile base class* problem.
- **Runtime flexibility:** you can swap a composed object at runtime (`car.setEngine(new ElectricEngine())`), but a superclass is fixed at compile time.
- **No hierarchy explosion:** combining features through inheritance leads to classes like `FlyingSwimmingRobotDog`. Composition mixes behaviors freely.
- **Multiple behaviors:** a class can compose many objects but extend only one class.

---

### Q3. What is the fragile base class problem? Give an example.

A subclass depends on *how* the parent implements things, not just *what* it does. The classic example from *Effective Java*: you extend `HashSet` and override both `add` and `addAll` to count insertions. `HashSet.addAll` internally calls `add`, so every element is counted **twice**. `CompositionDemo.java` reproduces this and fixes it with a wrapper (decorator) built on composition.

---

### Q4. When *is* inheritance the right choice?

When there's a genuine, stable **is-a** relationship, the subclass can honor the entire parent contract (see Liskov in topic 08), and the parent is *designed and documented* for extension. Or when a framework requires it.

---

### Q5. What are association, aggregation and composition?

All three are "has-a" relationships, with different strengths:

| Relationship | Lifetime | Example |
|---|---|---|
| **Association** | Independent; objects just know each other | `Teacher` ↔ `Student` |
| **Aggregation** (weak has-a) | Part can exist without the whole | `Department` has `Professor`s |
| **Composition** (strong has-a) | Part dies with the whole | `House` has `Room`s |
