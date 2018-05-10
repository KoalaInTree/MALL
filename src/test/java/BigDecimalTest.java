import org.junit.*;

import java.math.BigDecimal;

/**
 * Created by CJ on 2017/11/22.
 */
public class BigDecimalTest {

    @org.junit.Test
    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal(String.valueOf(new Double(0.01)));
        BigDecimal b2 = new BigDecimal(String.valueOf(new Double(0.04)));
        System.out.println(b1.add(b2));
    }
}
