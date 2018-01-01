/**
 * Created by CJ on 2017/11/15.
 */
public enum  Test {


    SUCC(1,"SUCC"),
        ERR(2,"ERR");

    private final int a;
    private final String b;
    private Test(int a, String b) {
        this.a = a;
        this.b = b;
    }
}
