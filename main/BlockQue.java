public interface BlockQue<E>{
    void offer(E e) throws InterruptedException;
    void offer(E e, long timeout) throws InterruptedException;
    E poll() throws InterruptedException;
}
