# 01: Classes & Objects

Run: `javac -d out *.java && java -cp out ClassesDemo`

---

### Q1. What is the difference between a class and an object?

A **class** is a blueprint that declares state (fields) and behavior (methods). An **object** is an instance of that class created at runtime, with its own copy of the instance fields, living on the heap.

```java
class Car { String model; }          // class
Car c = new Car();                   // object (c is a reference to it)
```

**Follow-up:** *Where are objects and references stored?* Objects live on the heap. A local reference variable lives on the stack of the thread that declared it, and it holds the object's address.

---

### Q2. What are the four pillars of OOP?

1. **Encapsulation:** bundle data with the methods that operate on it and hide the internal state.
2. **Abstraction:** expose *what* an object does and hide *how* it does it.
3. **Inheritance:** a class acquires the members of another class (is-a relationship).
4. **Polymorphism:** one interface with many implementations, resolved at compile time (overloading) or at runtime (overriding).

---

### Q3. What is a constructor? How is it different from a method?

| Constructor | Method |
|---|---|
| Same name as the class | Any name |
| No return type, not even `void` | Must declare a return type |
| Called implicitly by `new` | Called explicitly |
| Not inherited | Inherited (unless `private`) |
| Can't be `abstract`, `static`, `final` | Can be |

If you write no constructor, the compiler adds a **default no-arg constructor**. Once you write *any* constructor, the default one disappears.

**Follow-up:** *Can a constructor be private?* Yes. Singletons, factory methods and utility classes use private constructors to stop outside instantiation.

---

### Q4. What does `this` mean? What does `this(...)` do?

- `this` is a reference to the current object. You use it to tell fields apart from parameters (`this.name = name`), to pass the current object somewhere, or to return it for method chaining.
- `this(...)` calls another constructor of the same class and **must be the first statement**. This is called constructor chaining.

---

### Q5. What is the difference between static and instance members?

- **Static** members belong to the class. There's one copy shared by every instance, and you can access them without creating an object.
- **Instance** members belong to each object.

A static method **cannot** access instance fields or use `this` directly, because it has no object to refer to.

**Follow-up:** *Why is `main` static?* The JVM calls it before any object of the class exists.

---

### Q6. What is the order of initialization when an object is created?

1. Static fields and static blocks, in textual order. This happens **once**, when the class is first loaded.
2. Then, for each `new`:
   1. The superclass constructor chain runs first.
   2. Instance field initializers and instance initializer blocks run, in textual order.
   3. The constructor body runs.

See `ClassesDemo.java`, which prints this order.

---

### Q7. Can you create an object without `new`?

Yes. You can use reflection (`Class.getDeclaredConstructor().newInstance()`), `clone()`, deserialization, or factory methods (which use `new` internally). String literals and autoboxing also produce objects without an explicit `new`.

---

### Q8. What is an anonymous object?

An object created without assigning it to a reference, such as `new Printer().print("hi");`. It becomes eligible for garbage collection right after use.
