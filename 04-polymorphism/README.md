# 04: Polymorphism

Run: `javac -d out *.java && java -cp out PolymorphismDemo`

---

### Q1. What is polymorphism? What kinds does Java have?

Polymorphism means "many forms": one name or interface, multiple behaviors.

- **Compile-time (static):** method **overloading**. The compiler picks the method from the declared argument types.
- **Runtime (dynamic):** method **overriding**. The JVM picks the method from the object's actual type, which is called *dynamic dispatch*.

---

### Q2. What's the difference between overloading and overriding?

| | Overloading | Overriding |
|---|---|---|
| Where | Same class (or subclass) | Subclass |
| Signature | **Different** parameter lists | **Same** signature |
| Return type | Can differ | Same or covariant (subtype) |
| Access | Any | Same or **wider** (can't reduce visibility) |
| Exceptions | Any | Same, narrower, or none (checked) |
| Resolved | Compile time | Runtime |
| `static`/`private`/`final` | Can be overloaded | **Cannot** be overridden |

---

### Q3. Can you overload a method by changing only the return type?

No. The return type isn't part of the method signature, so `int f()` and `String f()` in the same class don't compile.

---

### Q4. Can you override a static method?

No. A static method with the same signature in a subclass **hides** the parent's method. Which one runs depends on the *reference type*, not the object type. See `10-tricky-questions` for a demo.

---

### Q5. What is a covariant return type?

An overriding method may return a **subtype** of the overridden method's return type:

```java
class Animal { Animal reproduce() { ... } }
class Dog extends Animal { @Override Dog reproduce() { ... } }
```

---

### Q6. What is upcasting and downcasting?

- **Upcasting** (`Animal a = new Dog();`) is implicit and always safe. You can only call `Animal` methods through `a`, but overrides still dispatch to `Dog`.
- **Downcasting** (`Dog d = (Dog) a;`) is explicit and can throw `ClassCastException`. Check it with `instanceof`, ideally with pattern matching:

```java
if (a instanceof Dog d) { d.fetch(); }
```

---

### Q7. With overloading, which method gets picked for `print(null)` or `print(5)`?

The compiler picks the **most specific** applicable method, in this order:
1. Exact match, or widening of primitives (`int` → `long`)
2. Boxing (`int` → `Integer`)
3. Varargs

For `null`, it chooses the most specific *reference* type. With `print(Object)` and `print(String)`, `print(null)` calls `print(String)`. With `print(String)` and `print(Integer)`, `print(null)` is **ambiguous** and doesn't compile. See `PolymorphismDemo.java`.

---

### Q8. What is the `@Override` annotation for?

It asks the compiler to verify that you really are overriding a method. Without it, a typo such as `equals(Person p)` instead of `equals(Object o)` silently creates an *overload*, and the bug shows up much later.
