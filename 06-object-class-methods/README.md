# 06: Object Class Methods

Run: `javac -d out *.java && java -cp out ObjectMethodsDemo`

---

### Q1. What methods does `java.lang.Object` provide?

`equals`, `hashCode`, `toString`, `getClass`, `clone`, `finalize` (deprecated), and the thread methods `wait`, `notify` and `notifyAll`. Every class inherits them.

---

### Q2. What's the difference between `==` and `equals()`?

- `==` compares **references** for objects (are they the same object?) and **values** for primitives.
- `equals()` compares **logical equality**. By default it behaves like `==`, so a class must override it to compare content.

```java
String a = new String("hi"), b = new String("hi");
a == b        // false: different objects
a.equals(b)   // true: same content
```

---

### Q3. What is the `equals`/`hashCode` contract?

`equals` must be:
- **Reflexive:** `x.equals(x)` is true.
- **Symmetric:** `x.equals(y)` if and only if `y.equals(x)`.
- **Transitive:** if `x.equals(y)` and `y.equals(z)`, then `x.equals(z)`.
- **Consistent:** repeated calls return the same result while the objects don't change.
- **Non-null:** `x.equals(null)` is false.

And: **if `a.equals(b)`, then `a.hashCode() == b.hashCode()`**. The reverse isn't required, because unequal objects may collide.

---

### Q4. What happens if you override `equals` but not `hashCode`?

Hash-based collections break. Two "equal" objects land in different buckets, so `HashSet` keeps duplicates and `map.get(equalKey)` returns `null`. `ObjectMethodsDemo.java` shows this.

---

### Q5. What happens if a `HashMap` key's fields change after insertion?

Its hash changes, so the map looks in the wrong bucket and the entry becomes effectively **lost**. It still takes up memory but can't be found. This is why keys should be immutable.

---

### Q6. Should `equals` use `instanceof` or `getClass()`?

- `getClass() != o.getClass()`: strict. A subclass instance is never equal to a parent instance. This keeps symmetry when subclasses add fields.
- `instanceof`: allows subclass instances to be equal. It's fine when the class is `final` or when subclasses don't add state that affects equality.

---

### Q7. Why override `toString`?

The default output is `ClassName@hexHashCode`, which tells you nothing. A meaningful `toString` helps with logging and debugging. Records generate `equals`, `hashCode` and `toString` for you.

---

### Q8. Shallow copy vs deep copy? Why is `clone()` considered broken?

- **Shallow copy** copies field values. Referenced objects are *shared*.
- **Deep copy** recursively copies referenced objects too.

`clone()` problems: `Cloneable` is a marker interface without a `clone` method, `Object.clone()` is `protected` and throws a checked exception, it bypasses constructors, and it doesn't work well with `final` fields. **Prefer copy constructors or static factory methods** (`new ArrayList<>(other)`).
