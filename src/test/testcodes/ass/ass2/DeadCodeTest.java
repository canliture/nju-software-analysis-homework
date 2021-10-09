package ass2;

/**
 * Created by liture on 2021/9/19 9:50 下午
 */
public class DeadCodeTest {

    int unrechableBranch() {
        int a = 1, b = 0, c;
        if (a > b)
            c = 2333;
        else
            c = 666; // unreachable branch
        return c;
    }

    int deadAssign() {
        int a, b, c;
        a = 0; // dead assignment
        a = 1;
        b = a * 2; // dead assignment (思考？在检测到当前死代码之后, 删除死代码, 上面一行的a = 1又变成了dead assignment)
        c = 3;
        return c;
    }

    void unrechableBranch2() {
        int a = 1, b = 0, x = 10;
        int y = 0;
        while (a > b) {
            x = 0;
        }
        y = 1;
    }
}
