package ass1;

/**
 * Created by liture on 2021/9/19 4:21 下午
 */
public class IntraConstantPropagationTest {

    public void nonDistributiveTest() {
        int a;
        int b;
        if (Math.random() > 0.5) {
            a = 1;
            b = 9;
        } else {
            a = 9;
            b = 1;
        }
        int c = a + b;
    }

    public void sameValueMeetTest() {
        int a;
        int b;
        if (Math.random() > 0.5) {
            a = 1; // a: same value as false branch
            b = 2; // b: not same value as false branch
        } else {
            a = 1;
            b = 1;
        }
        int c = a + b;
        int d = a + a;
        int e = b + b;
    }

    public void testParam(int x) {
        int a;
        a = x; // x is undefined
        int b = a; // b is undefined
    }
}
