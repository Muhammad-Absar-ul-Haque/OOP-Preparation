# 02: Encapsulation

Run: `javac -d out *.java && java -cp out EncapsulationDemo`

---

### Q1. What is encapsulation and why does it matter?

Encapsulation means keeping an object's state `private` and exposing only controlled operations on it. It lets the class:

- **enforce invariants**, for example that a balance never goes negative
- **change its internals** without breaking callers
- **reduce coupling**, because callers depend on behavior rather than data layout

**Follow-up:** *Is a class with private fields plus a public getter and setter for each field encapsulated?* Barely. If every field has a public setter with no validation, the class is a data bag with extra steps. Real encapsulation exposes *behavior* (`account.withdraw(50)`), not raw state (`account.setBalance(...)`).

---

### Q2. What are Java's access modifiers?

| Modifier | Class | Package | Subclass (other pkg) | World |
|---|:-:|:-:|:-:|:-:|
| `private` | ✅ | ❌ | ❌ | ❌ |
| *(default / package-private)* | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

**Follow-up:** *Can a top-level class be `private` or `protected`?* No. A top-level class can only be `public` or package-private. Nested classes can use any modifier.

---

### Q3. What is the difference between encapsulation and abstraction?

- **Encapsulation** is about *hiding data* and protecting state, using access modifiers.
- **Abstraction** is about *hiding complexity* by exposing a simplified model, using interfaces and abstract classes.

They work together: abstraction decides what to show, and encapsulation enforces that the rest stays hidden.

---

### Q4. How do you make a class immutable?

1. Declare the class `final`, or make its constructors private and use factories, so subclasses can't add mutable state.
2. Make all fields `private final`.
3. Provide no setters.
4. **Defensively copy** mutable inputs in the constructor.
5. **Defensively copy** (or return unmodifiable views of) mutable fields in getters.

Or, from Java 16 onward, use a `record`. It is shallowly immutable, so you still need to copy mutable components.

**Follow-up:** *Why are immutable objects useful?* They're thread-safe without locks, safe to cache and share, and safe to use as `HashMap` keys. `String` is the classic example.

---

### Q5. What happens if a getter returns a mutable field directly?

The caller can modify the object's internal state and bypass every validation. `EncapsulationDemo.java` shows this "leaky getter" bug next to the fixed version.

---

### Q6. Why is `protected` sometimes considered a weak form of encapsulation?

Any subclass, including one in another package written by someone else, can read and change `protected` state. That makes the field part of your public contract for subclassers, so you can't change it freely.
