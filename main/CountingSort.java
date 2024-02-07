public class CountingSort {
    public static int[] sort(int[] arr){
        int max = arr[0];
        int min = arr[0];
        //拿到最大值和最小值
        for (int i = 1; i < arr.length; i++){
            if (arr[i] > max){
                max = arr[i];
            }
            if (arr[i] < min){
                min = arr[i];
            }
        }
        int[] n = new int[max - min + 1];
        for (int v : arr){//原始数组元素 - 最小值 = 索引
            n[v - min]++;
        }
        int k = 0;
        for (int i = 0; i < n.length; i++){
            while (n[i] > 0){
                arr[k++] = i + min;
                n[i]--;
            }
        }
        return arr;
    }
}
