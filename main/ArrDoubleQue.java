import java.util.Iterator;

public class ArrDoubleQue {
    public static void main(String[] args) {
         ArrayDeque1<Integer> arrayDeque1 = new ArrayDeque1<>(3);
         arrayDeque1.offerFirst(2);
        arrayDeque1.offerFirst(3);
        arrayDeque1.offerFirst(4);
        arrayDeque1.offerFirst(5);
        arrayDeque1.pollFirst();
        arrayDeque1.pollLast();
        arrayDeque1.offerLast(10);
        for (Integer i : arrayDeque1){
            System.out.println(i);
        }
        System.out.println(arrayDeque1.isFull());
    }
}


class ArrayDeque1<E> implements Deque<E>,Iterable<E>{
    E[] array;
    int head;
    int tail;

    @SuppressWarnings("all")
    public ArrayDeque1(int capacity){
        this.array = (E[]) new Object[capacity+1];
    }

    //加法防止越界
    static int inc(int i,int length){
        if (i+1>=length){
            return 0;
        }
        return i+1;
    }
    //减法防止越界
    static int dec(int i,int length){
        if (i-1<0){
            return length-1;
        }
        return i-1;
    }

    @Override
    public boolean offerFirst(E e) {
        if (isFull()){
            return false;
        }
        head = dec(head,array.length);
        array[head] = e;
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        if (isFull()){
            return false;
        }
        array[tail] = e;
        tail = inc(tail,array.length);
        return true;
    }

    @Override
    public E pollFirst() {
        if (isEmpty()){
            return null;
        }
        E val = array[head];
        array[head] = null;  //引用数据类型，垃圾回收
        head = inc(head,array.length);
        return val;
    }

    @Override
    public E pollLast() {
        if (isEmpty()){
            return null;
        }
        tail = dec(tail,array.length);
        E e = array[tail];
        array[tail] = null;
        return e;
    }

    @Override
    public E peekFirst() {
        if (isEmpty()){
            return null;
        }
        return array[head];
    }

    @Override
    public E peekLast() {
        if (isEmpty()){
            return null;
        }
        return array[dec(tail, array.length)];
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public boolean isFull() {
        if (tail>head){
            return tail-head==array.length-1;
        }else if(head>tail){
            return head-1 == tail;
        }else {
            return false;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int p = head;
            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public E next() {
                E val = array[p];
                p = inc(p,array.length);
                return val;
            }
        };
    }
}
