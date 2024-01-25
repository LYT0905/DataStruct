import java.util.Iterator;

public class ArrayQue {
    public static void main(String[] args) {
        ArrayQueue3<Integer> queue = new ArrayQueue3<>(4);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);

        System.out.println(queue.peek());

        System.out.println(queue.isFull());
        for (Integer i : queue){
            System.out.println(i);
            queue.poll();
        }
        System.out.println(queue.isEmpty());
    }
}


class ArrayQueue<E> implements Queue<E>,Iterable<E>{
    int head = 0;
    int tail = 0;
    E[] array;


    public ArrayQueue(int capacity){
        array = (E[]) new Object[capacity+1];
    }


    @Override
    public boolean offer(E value) {
        if (isFull()){
            return false;
        }
        array[tail] = value;
        tail = (tail+1)%array.length;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()){
            return null;
        }
        E e = array[head];
        head = (head+1)%array.length;
        return e;
    }

    @Override
    public E peek() {
        if (isEmpty()){
            return null;
        }
        return array[head];
    }

    @Override
    public boolean isEmpty() {
        return head==tail;
    }

    @Override
    public boolean isFull() {
        return (tail+1)%array.length==head;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            int p = head;
            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public Object next() {
                E value = array[p];
                p = (p+1)%array.length;
                return value;
            }
        };
    }
}

class ArrayQueue2<E> implements Queue<E>,Iterable<E>{
    int head = 0;
    int tail = 0;
    E[] array;
    int size = 0;


    public ArrayQueue2(int capacity){
        array = (E[]) new Object[capacity];
    }


    @Override
    public boolean offer(E value) {
        if (isFull()){
            return false;
        }
        array[tail] = value;
        tail = (tail+1)%array.length;
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()){
            return null;
        }
        E e = array[head];
        head = (head+1)%array.length;
        size--;
        return e;
    }

    @Override
    public E peek() {
        if (isEmpty()){
            return null;
        }
        return array[head];
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean isFull() {
        return size== array.length;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            int p = head;
            int count = 0;
            @Override
            public boolean hasNext() {
                return count<size;
            }

            @Override
            public Object next() {
                E value = array[p];
                p = (p+1)%array.length;
                count++;
                return value;
            }
        };
    }
}


class ArrayQueue3<E> implements Queue<E>,Iterable<E>{
    int head = 0;
    int tail = 0;
    E[] array;


    public ArrayQueue3(int capacity){
        //方法1；
//        if ((capacity & capacity-1) !=0){
//            throw  new IllegalArgumentException("capacity must be power of 2");
//        }

        //方法2:找一个离它最近的2的幂
//        int n = (int) (Math.log10(capacity-1) / Math.log10(2))+1;
//        capacity = capacity<< n;

        capacity -= 1;
        capacity|=capacity>>1;
        capacity|=capacity>>2;
        capacity|=capacity>>4;
        capacity|=capacity>>8;
        capacity|=capacity>>16;
        array = (E[]) new Object[capacity];
    }


    @Override
    public boolean offer(E value) {
        if (isFull()){
            return false;
        }
        array[tail&array.length-1] = value;
        tail++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()){
            return null;
        }
        E e = array[head&array.length-1];
        head++;
        return e;
    }

    @Override
    public E peek() {
        if (isEmpty()){
            return null;
        }
        return array[head&array.length-1];
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public boolean isFull() {
        return tail-head == array.length;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            int p = head;
            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public Object next() {
                E value = array[p&array.length-1];
                p++;
                return value;
            }
        };
    }
}
