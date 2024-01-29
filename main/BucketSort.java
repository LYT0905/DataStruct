import java.util.ArrayList;

public class BucketSort {
    public static int[] sort(int[] a, int range){
        int max = a[0];
        int min = a[0];
        for (int i = 1; i < a.length; i++){
            if (a[i] > max){
                max = a[i];
            }
            if (a[min] < min){
                min = a[i];
            }
        }
        // 1. 准备桶
        dynamicArray[] buckets = new dynamicArray[(max - min) / range + 1];
        for (int i = 0; i < buckets.length; i++){
            buckets[i] = new dynamicArray();
        }
        // 2. 放入年龄数据
        for (int age : a){
            buckets[(age - min) / range].addLast(age);
        }
        int k = 0;
        for (dynamicArray bucket : buckets){
            // 3. 排序桶内元素
            int[] array = bucket.array();
            InsertionSort.insertSort(array);
            // 4. 把每个桶排序好的内容，依次放入原始数组
            for (int v : array){
                a[k++] = v;
            }
        }
        return a;
    }
}
