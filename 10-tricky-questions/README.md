# 10: Tricky Questions

Run: `javac -d out *.java && java -cp out TrickyDemo`

Predict the output of each one **before** running the demo.

---

### Q1. Is Java pass-by-value or pass-by-reference?

**Always pass-by-value.** For objects, the *value* that gets passed is a copy of the reference. So:
- A method **can mutate** the object the reference points to.
- A method **cannot reassign** the caller's variable to point at a different object.

```java
void reassign(StringBuilder sb) { sb = new StringBuilder("new"); }   // caller unaffected
void mutate(StringBuilder sb)   { sb.append("!"); }                  // caller sees the change
```

---

### Q2. What does this print?

```java
class A { static String hi() { return "A"; } }
class B extends A { static String hi() { return "B"; } }
A ref = new B();
System.out.println(ref.hi());
```

**`A`.** Static methods are **hidden**, not overridden, and they're resolved from the reference type at compile time.

---

### Q3. What does this print?

```java
class A { String name = "A"; String getName() { return name; } }
class B extends A { String name = "B"; @Override String getName() { return name; } }
A ref = new B();
System.out.println(ref.name + " " + ref.getName());
```

**`A B`.** Fields aren't polymorphic: `ref.name` uses the reference type `A`. Methods are polymorphic: `getName()` dispatches to `B`.

---

### Q4. Can you override a private method?

No. A private method isn't visible to the subclass, so a method with the same name in the subclass is a completely separate method. Adding `@Override` to it causes a compile error.

---

### Q5. Can an overriding method throw a broader checked exception?

No. It can throw the same exception, a narrower one, or none at all. Unchecked exceptions are unrestricted. Otherwise, code calling the parent type couldn't handle what the subclass throws.

---

### Q6. Can an overriding method reduce visibility (for example from `public` to `protected`)?

No. That would break substitutability: code that holds a parent reference expects to be able to call the method.

---

### Q7. What does `Integer a = 127, b = 127; a == b` give? And for 128?

`true` for 127 and `false` for 128. `Integer.valueOf` caches values from **-128 to 127**, so 127 refers to the same cached object while 128 creates a new one each time. **Always use `equals` for boxed types.**

---

### Q8. Can an interface have a constructor? Can it have instance fields?

Neither. Interface fields are implicitly `public static final`.

---

### Q9. What happens with `finally` and `return`?

`finally` runs even after a `return` in `try`. If `finally` has its own `return`, **it replaces** the `try`'s return value and even swallows exceptions. Never return from `finally`.

---

### Q10. Can you call a static method on a `null` reference?

Yes. `((A) null).staticMethod()` works, because the compiler binds the call to the class and never dereferences the reference. It's legal but bad style.

---

### Q11. Can `main` be overloaded? Can it be inherited?

Both. You can overload it, but the JVM only calls `public static void main(String[])`. A subclass inherits a parent's `main`, so `java Child` will run `Parent.main` if `Child` doesn't define its own.
