import java.util.Arrays;

public class BubblingSorting {
    public static void main(String[] args) {
        int[] a = {3,2,6,1,8,2};
        System.out.println(Arrays.toString(a));
        bubble1(a);
        System.out.println(Arrays.toString(a));
    }
    //递归
    public static void bubble(int[] a,int j){
        if (j==0){
            return;
        }
        int x = 0;
        for (int i=0;i<j;i++){
            if (a[i]>a[i+1]){
                int temp = a[i];
                a[i] = a[i+1];
                a[i+1] = temp;
                x = i;
            }
        }
        bubble(a,x);
    }

    //非递归
    public static void bubble1(int[] a){
        int j = a.length-1;
        while (true){
            int x = 0;
            for (int i = 0; i < j; i++){
                if (a[i] > a[i+1]){
                    int temp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = temp;
                    x = i;
                }
            }
            j = x;
            if (j == 0){
                break;
            }
        }
    }
}
