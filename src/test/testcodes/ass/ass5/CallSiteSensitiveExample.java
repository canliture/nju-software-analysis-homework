package ass5;

/**
 * Created by liture on 2021/10/10 3:37 下午
 */
public class CallSiteSensitiveExample {

    void main() {
        CallSiteSensitiveExample a = new CallSiteSensitiveExample();
        a.foo();
    }

    void foo() {
        CallSiteSensitiveExample b = new CallSiteSensitiveExample();
        b.bar();
    }

    void bar() {
        CallSiteSensitiveExample c = new CallSiteSensitiveExample();
        c.tar();
    }

    void tar() {
        CallSiteSensitiveExample d = new CallSiteSensitiveExample();
        d.gar();
    }

    void gar() {
        CallSiteSensitiveExample e = new CallSiteSensitiveExample();
        e.gar();
    }
}
