package ass4;


/**
 * Created by liture on 2021/9/22 1:16 上午
 */
public class NoCallTest {

    void main() {
        C b = new C();
        C a = b;
        C c = new C();
        c.f = a;
        C d = c;
        c.f = d;
        C e = d.f;
    }
}

class C {
    C f;
}
