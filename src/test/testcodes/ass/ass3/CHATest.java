package ass3;

/**
 * Created by liture on 2021/9/20 4:25 下午
 */
class A {

    static void main() {
        A.foo();
    }

    static void foo() {
        A a = new A();
        a.bar();
    }

    void bar() {
        C c = new C();
        c.bar();
    }
}

class B extends A {
    void bar() { }
}

class C extends A {

    void bar() {
        if (Math.random() > 0.5) {
            A.foo();
        }
    }

    void m() {  }
}

public class CHATest {

}
