/**
 * Runs each "predict the output" question from this topic's README.
 */
public class TrickyDemo {

    static class A {
        String name = "A";

        static String hi() { return "A"; }

        String getName() { return name; }

        private String secret() { return "A.secret"; }

        String callSecret() { return secret(); }
    }

    static class B extends A {
        String name = "B";   // hides A.name

        static String hi() { return "B"; }   // hides A.hi()

        @Override String getName() { return name; }

        String secret() { return "B.secret"; }   // NOT an override: A.secret is private
    }

    static void reassign(StringBuilder sb) { sb = new StringBuilder("replaced"); }

    static void mutate(StringBuilder sb) { sb.append(" + mutated"); }

    @SuppressWarnings("finally")
    static int finallyReturn() {
        try {
            return 1;
        } finally {
            return 2;   // overrides the try's return value
        }
    }

    public static void main(String[] args) {
        A ref = new B();

        System.out.println("Q1 pass-by-value:");
        StringBuilder sb = new StringBuilder("original");
        reassign(sb);
        mutate(sb);
        System.out.println("  " + sb);

        System.out.println("Q2 static hiding:      " + ref.hi());   // compiler warns, but it's legal
        System.out.println("Q3 field vs method:    " + ref.name + " " + ref.getName());
        System.out.println("Q4 private not overridden: " + ref.callSecret());

        Integer a = 127, b = 127, c = 128, d = 128;
        System.out.println("Q7 Integer cache:      127==127 " + (a == b) + ", 128==128 " + (c == d)
                + ", 128 equals " + c.equals(d));

        System.out.println("Q9 finally return:     " + finallyReturn());

        A nothing = null;
        System.out.println("Q10 static via null:   " + nothing.hi());
    }
}
