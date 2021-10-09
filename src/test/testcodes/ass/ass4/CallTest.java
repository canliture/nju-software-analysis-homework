package ass4;

/**
 * Created by liture on 2021/9/22 8:54 下午
 */
public class CallTest {
}

class A {
    static void main() {
        A a = new A();
        A b = new B();
        A c = b.foo(a);
    }

    A foo(A x) {
        return x;
    }
}

class B extends A {
    A foo(A y) {
        A r = new A();
        return r;
    }
}
