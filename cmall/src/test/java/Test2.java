import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by CJ on 2017/11/21.
 */
public class Test2 {

    public static void main(String[] args) {
//        int a;
//        Integer b;
//        a = Integer.valueOf(2);
//        b = new Integer(2);
//        System.out.println(a == b);
        Calendar cl = Calendar.getInstance();
        String year = String.valueOf(cl.get(Calendar.YEAR));
        String mon = String.valueOf(cl.get(Calendar.MONTH)+1);
        String day = String.valueOf(cl.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(cl.get(Calendar.HOUR_OF_DAY));
        Long currentTime = System.currentTimeMillis();
        String orderNo = year+mon+day+hour+currentTime%10000+new Random().nextInt(1000);
        System.out.println(Long.valueOf(orderNo));
        System.out.println(Long.MAX_VALUE);
    }

}
