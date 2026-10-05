# Java OOP Interview Questions

A study repo of Object-Oriented Programming interview questions in Java. Each topic folder has:

- `README.md`: questions with short answers you could say in an interview, plus the follow-ups interviewers usually ask
- `*Demo.java`: a small runnable program that shows the concepts in code

## Topics

| # | Topic | What it covers |
|---|-------|----------------|
| 01 | [Classes & Objects](01-classes-and-objects/) | Constructors, `this`, `static`, initialization order |
| 02 | [Encapsulation](02-encapsulation/) | Access modifiers, getters/setters, immutability |
| 03 | [Inheritance](03-inheritance/) | `extends`, `super`, constructor chaining, `final` |
| 04 | [Polymorphism](04-polymorphism/) | Overloading vs overriding, dynamic dispatch, covariant returns |
| 05 | [Abstraction](05-abstraction/) | Abstract classes vs interfaces, default/static methods |
| 06 | [Object Class Methods](06-object-class-methods/) | `equals`/`hashCode` contract, `toString`, `clone` |
| 07 | [Composition vs Inheritance](07-composition-vs-inheritance/) | Has-a vs is-a, delegation, the fragile base class problem |
| 08 | [SOLID Principles](08-solid-principles/) | SRP, OCP, LSP, ISP, DIP with before/after code |
| 09 | [Design Patterns](09-design-patterns/) | Singleton, Factory, Builder, Strategy, Observer |
| 10 | [Tricky Questions](10-tricky-questions/) | Static method hiding, field hiding, pass-by-value, and other gotchas |

## Running the examples

You need JDK 17 or newer. Every topic compiles on its own:

```bash
cd 03-inheritance
javac -d out *.java
java -cp out InheritanceDemo
```

To compile and run every topic at once:

```bash
./run-all.sh        # macOS / Linux / Git Bash
```

```powershell
./run-all.ps1       # Windows PowerShell
```

## How to study

1. Read a question and answer it out loud before reading the answer.
2. Predict what the demo prints, then run it.
3. Go through the **Follow-ups** sections. They're where interviewers find out whether you understand the concept or memorized it.

## Contributing

To add a question, put it in the matching topic's `README.md` using the same format: a `###` question heading, a short answer, and code where it helps. If it needs a runnable example, add it to that topic's demo file.
