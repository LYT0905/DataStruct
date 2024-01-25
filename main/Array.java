import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Array {
    public static void main(String[] args) {
        dynamicArray dynamicArray = new dynamicArray();
        dynamicArray.addLast(1);
        dynamicArray.addLast(2);
        dynamicArray.addLast(3);
        dynamicArray.addLast(4);


        dynamicArray.add(4,2);
        dynamicArray.add(5,2);
        dynamicArray.add(6,2);
        dynamicArray.add(7,2);
        dynamicArray.add(8,2);
        dynamicArray.add(9,2);
        dynamicArray.add(10,2);
        dynamicArray.add(11,2);
        dynamicArray.add(12,2);
        dynamicArray.add(13,2);
        dynamicArray.add(14,2);
        dynamicArray.add(15,2);


        dynamicArray.remove(0);
        System.out.println("---------");
        System.out.println(dynamicArray.get(3));
        System.out.println("---------");
        dynamicArray.foreach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });
        System.out.println("---------");
        for (Integer integer : dynamicArray){
            System.out.println(integer);
        }
        System.out.println("---------");
        dynamicArray.stream().forEach(element -> {
            System.out.println(element);
        });
    }

}
class DynamicArray  implements Iterable<Integer>{
    private int capticy = 8;//容量
    private int size = 0;
    private int[] array= {};

//加入最后
    public void addLast(int element){
        add(size,element);
    }
//加入指定位置
    public  void add(int index,int element){
        checkAndGrow();
        if (index>=0 && index<size){
            System.arraycopy(array,index,array,index+1,size-index);
        }
        array[index] = element;
        size++;
    }

    private void checkAndGrow() {
        if (size==0){
            array = new int[capticy];
        }else if (size==capticy){  //判断是否需要扩容
            capticy += capticy>>>2;
            int[] newArray = new int[capticy];
            System.arraycopy(array,0,newArray,0,size);
            array = newArray;
        }

    }

    //删除元素
    public int remove(int index){
        int remove = array[index];
        if(index<size-1){
            System.arraycopy(array,index+1,array,index,size-index-1);
        }
        size--;
        return remove;
    }

    //查询元素
    public int get(int index){
        return array[index];
    }



    //遍历
    public void foreach(Consumer<Integer> consumer){
        for(int i = 0;i < size; i++){
//            System.out.println(array[i]);
            consumer.accept(array[i]);
        }
    }

    public IntStream stream(){
        return IntStream.of(Arrays.copyOfRange(array,0,size));
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int i =0;
            @Override
            public boolean hasNext() {
                return i<size;
            }

            @Override
            public Integer next() {
                return array[i++];
            }
        };
    }
}
