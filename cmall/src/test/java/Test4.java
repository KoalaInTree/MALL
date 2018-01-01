/**
 * Created by CJ on 2017/12/6.
 */
public class Test4
{
    public static void main(String []args){
        int[] a={8,3,2,1,5};
        int temp=0;
        sort(a,0,4);
        temp=0;
        for(;temp<5;temp++){
            System.out.print(a[temp]);
        }
    }

    static void sort(int a[],int low,int high){
        if(low>high)
            return;
        int i,j,t,temp;
        t=a[low];
        i=low;
        j=high;

        while(i!=j){
            while(a[i]<t&&i<j){
                i++;
            }
            while(a[j]>t&&i<j){
                j--;
            }
            if(i!=j){
                temp=a[i];
                a[i]=a[j];
                a[j]=temp;
            }
        }
        sort(a,low,i-1);
        sort(a,i+1,high);
    }
}
