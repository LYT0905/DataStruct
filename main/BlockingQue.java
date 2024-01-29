
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQue{
    public static void main(String[] args) throws InterruptedException{

    }
}


//单锁
class BlockingQueue1<E> implements BlockQue<E>{
    private E[] array;
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    public BlockingQueue1(int capacity) {
        this.array = (E[]) new Object[capacity];
    }

    ReentrantLock reentrantLock = new ReentrantLock();
    Condition headWaits = reentrantLock.newCondition();
    Condition tailWaits = reentrantLock.newCondition();


    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == array.length;
    }

    @Override
    public void offer(E e) throws InterruptedException {
        reentrantLock.lockInterruptibly();
        try {
            while (isFull()) {//阻塞
                tailWaits.await();
            }
            array[tail] = e;
            if (++tail == array.length) {
                tail = 0;
            }
            size++;
            headWaits.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public void offer(E e, long timeout) throws InterruptedException {
        reentrantLock.lockInterruptibly();
        try {
            long t = TimeUnit.MILLISECONDS.toNanos(timeout);
            while (isFull()) {
                if (t <= 0) {
                    return;
                }
                t = tailWaits.awaitNanos(t);
            }
            array[tail] = e;
            if (++tail == array.length) {
                tail = 0;
            }
            size++;
            headWaits.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public E poll() throws InterruptedException {
        reentrantLock.lockInterruptibly();
        try {
            while (isEmpty()) {
                tailWaits.await();
            }
            E e = array[head];
            if (++head == array.length) {
                head = 0;
            }
            size--;
            tailWaits.signal();
            return e;
        } finally {
            reentrantLock.unlock();
        }
    }
}

//双锁
class BlockingQueue2<E> implements BlockQue<E>{
    private E[] array;
    private int head = 0;
    private int tail = 0;
    private AtomicInteger size = new AtomicInteger(0);


    public BlockingQueue2(int capacity) {
        this.array = (E[]) new Object[capacity];
    }

    ReentrantLock headLock = new ReentrantLock();
    Condition headWaits = headLock.newCondition();


    ReentrantLock tailLock = new ReentrantLock();
    Condition tailWaits = tailLock.newCondition();


    public boolean isEmpty() {
        return size.get() == 0;
    }

    public boolean isFull() {
        return size.get() == array.length;
    }

    @Override
    public void offer(E e) throws InterruptedException {
        tailLock.lockInterruptibly();
        int  c;
        try{
            while (isFull()){
                tailWaits.await();
            }
            array[tail] = e;
            if (++tail == array.length){
                tail = 0;
            }
            c = size.getAndIncrement();
            // a. 队列不满, 但不是从满->不满, 由此offer线程唤醒其它offer线程
            if (c+1< array.length){
                tailWaits.signal();
            }
        }finally {
            tailLock.unlock();
        }

        // b. 从0->不空, 由此offer线程唤醒等待的poll线程
        if (c==0){
            headLock.lock();
            try {
                headWaits.signal();
            }finally {
                headLock.unlock();
            }
        }
    }

    @Override
    public void offer(E e, long timeout) throws InterruptedException {
        long t = TimeUnit.MILLISECONDS.toNanos(timeout);
        tailLock.lock();
        int c;
        try {
            while (isFull()){
                if (t<=0){
                    return;
                }
                t = tailWaits.awaitNanos(t);
            }
            array[tail] = e;
            if (++tail == array.length){
                tail = 0;
            }
            c = size.getAndIncrement();

            // a. 队列不满, 但不是从满->不满, 由此offer线程唤醒其它offer线程
            if (c+1 < array.length){
                tailWaits.signal();
            }

        }finally {
            tailLock.unlock();
        }

        // b. 从0->不空, 由此offer线程唤醒等待的poll线程
        if (c==0){
            headLock.lock();
            try{
                headWaits.signal();
            }finally {
                headLock.unlock();
            }
        }

    }

    @Override
    public E poll() throws InterruptedException {
        headLock.lockInterruptibly();
        int c;
        E e;
        try {
            while (isEmpty()){
                headWaits.await();
            }

            e = array[head];
            array[head] = null;

            if (++head == array.length){
                head = 0;
            }

            c = size.getAndDecrement();

            // b. 队列不空, 但不是从0变化到不空，由此poll线程通知其它poll线程
            if (c>1){
                headWaits.signal();
            }

        }finally {
            headLock.unlock();
        }


        // a. 从满->不满, 由此poll线程唤醒等待的offer线程
        if (c== array.length){
            tailLock.lock();
            try{
                tailWaits.signal();
            }finally {
                tailLock.unlock();
            }
        }

        return e;
    }
}
