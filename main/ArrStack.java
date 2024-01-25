import java.util.Iterator;

public class ArrStack {
    public static void main(String[] args) {
        Arraystack<Integer> arraystack = new Arraystack<Integer>(5);
        arraystack.push(1);
        arraystack.push(2);
        arraystack.push(3);
        arraystack.push(4);
        arraystack.push(5);
        arraystack.push(6);
        arraystack.push(7);

        System.out.println(arraystack.pop());
        System.out.println(arraystack.pop());
        System.out.println(arraystack.pop());
        System.out.println(arraystack.pop());
        System.out.println(arraystack.pop());
        System.out.println(arraystack.pop());
        System.out.println(arraystack.pop());
    }
}
class Arraystack<E> implements Stack<E>,Iterable<E>{

    E[] array;
    int top = 0;


    @SuppressWarnings("all")
    public Arraystack(int capaticy){
        this.array= (E[]) new Object[capaticy];
    }


    @Override
    public boolean push(E value) {
        if (isFull()){
            return false;
        }
//        array[top] = value;
//        top++;
        array[top++] = value;
        return true;
    }

    @Override
    public E pop() {
        if (isEmpty()){
            return null;
        }
//        E val = array[top-1];
//        top--;

        return array[--top];
    }

    @Override
    public E peek() {
        if (isEmpty()){
            return null;
        }
        return array[top-1];
    }

    @Override
    public boolean isEmpty() {
        return top==0;
    }

    @Override
    public boolean isFull() {
        return top==array.length;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int p = top;
            @Override
            public boolean hasNext() {
                return p>0;
            }

            @Override
            public E next() {
//                E val = array[top];
//                top--;
                return array[--top];
            }
        };
    }
}
