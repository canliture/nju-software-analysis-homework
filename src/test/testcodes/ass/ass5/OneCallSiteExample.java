package ass5;

/**
 * Created by liture on 2021/10/10 6:23 下午
 */
public class OneCallSiteExample {

    void main() {
        OneCallSiteExample c = new OneCallSiteExample();
        c.m();
    }

    void m() {
        Num n1, n2, x, y;
        n1 = new One();
        n2 = new Two();
        x = id(n1);
        y = id(n2);
        x.get();
        y.get();
    }

    Num id(Num num) {
        return num;
    }
}

interface Num {
    int get();
}

class One implements Num {

    @Override
    public int get() {
        return 1;
    }
}

class Two implements Num {

    @Override
    public int get() {
        return 2;
    }
}