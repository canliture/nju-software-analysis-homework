package ass4;

/**
 * Created by liture on 2021/10/3 9:02 上午
 */
public class CIProblem {
    void main() {
        CIProblem p = new CIProblem();
        Num n1, n2, x, y;
        n1 = new One();
        n2 = new Two();
        x = p.identity(n1);
        y = p.identity(n2);
        int i = x.get();
    }

    Num identity(Num n) {
        return n;
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

