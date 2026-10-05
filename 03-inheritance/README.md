# 03: Inheritance

Run: `javac -d out *.java && java -cp out InheritanceDemo`

---

### Q1. What is inheritance? What types does Java support?

Inheritance lets a subclass acquire the fields and methods of a superclass, modeling an **is-a** relationship (`Dog extends Animal`).

| Type | Supported for classes? |
|---|---|
| Single (`B extends A`) | ✅ |
| Multilevel (`C extends B extends A`) | ✅ |
| Hierarchical (`B`, `C` both extend `A`) | ✅ |
| Multiple (`C extends A, B`) | ❌ (but a class can implement multiple interfaces) |

---

### Q2. Why doesn't Java support multiple inheritance of classes?

To avoid the **diamond problem**. If `B` and `C` both override `A.greet()` and `D extends B, C`, then it's ambiguous which `greet()` `D` gets, and which copy of `A`'s state it has. Java allows multiple inheritance of *type* through interfaces because interfaces have no instance state.

**Follow-up:** *Default methods brought back a version of the diamond. How does Java resolve it?*
1. A class's own method wins over an interface default.
2. A more specific interface wins (if `B extends A`, then `B`'s default wins).
3. If it's still ambiguous, the class **must** override the method, and it can call a specific parent with `InterfaceName.super.method()`.

---

### Q3. What does `super` do?

- `super.method()` calls the parent's version of an overridden method.
- `super.field` accesses a hidden parent field.
- `super(...)` calls a parent constructor and must be the **first statement** in a constructor.

If you don't write `super(...)`, the compiler inserts `super()`. That's a compile error if the parent has no no-arg constructor.

---

### Q4. Are constructors inherited? Are private members inherited?

- **Constructors** are not inherited, but they are always *invoked* through the chain.
- **Private members** are present in the object's memory but are **not accessible** in the subclass. Most interviewers accept "private members are not inherited" as the answer.

---

### Q5. What does `final` mean on a class, a method, and a variable?

- `final class`: can't be subclassed (`String`, `Integer`).
- `final method`: can't be overridden.
- `final variable`: can be assigned only once. For a reference, the *reference* is fixed, but the object it points to may still be mutable.

---

### Q6. What happens if a constructor calls an overridable method?

The subclass's override runs **before the subclass's fields are initialized**, so it sees default values (`null`, `0`). This is a classic bug, and `InheritanceDemo.java` shows it. Rule: never call overridable methods from a constructor.

---

### Q7. What are sealed classes (Java 17)?

A `sealed` class lists exactly which classes may extend it:

```java
sealed interface Shape permits Circle, Square {}
final class Circle implements Shape {}
final class Square implements Shape {}
```

Each permitted subclass must be `final`, `sealed`, or `non-sealed`. This gives you a closed hierarchy, which allows exhaustive `switch` pattern matching.
