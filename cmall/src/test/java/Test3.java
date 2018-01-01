/**
 * Created by CJ on 2017/11/22.
 */
public class Test3 {
    public static int add(int m,int n){
        if (m==0){
            return n+1;
        }
        if (n==0){
            return  add(m-1,1);
        }
        else{
            return add(m-1,add(m,n-1));
        }
    }

    public static void main(String[] args) {
        System.out.println(add(2,1));
    }
}
