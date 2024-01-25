import java.util.Iterator;

public class AnnularLinkedList {
    public static void main(String[] args) {
        AnnularLinkedListSentinel annularLinkedListSentinel = new AnnularLinkedListSentinel();
//        annularLinkedListSentinel.addFirst(1);
//        annularLinkedListSentinel.addFirst(2);
//        annularLinkedListSentinel.addFirst(3);
//        annularLinkedListSentinel.addFirst(4);

        annularLinkedListSentinel.addLast(1);
        annularLinkedListSentinel.addLast(2);
        annularLinkedListSentinel.addLast(3);
        annularLinkedListSentinel.addLast(4);
        annularLinkedListSentinel.addLast(5);

//        annularLinkedListSentinel.removeFirst();
//        annularLinkedListSentinel.removeFirst();
//        annularLinkedListSentinel.removeLast();
//        annularLinkedListSentinel.removeLast();

        annularLinkedListSentinel.remove(4);

        for (Integer i : annularLinkedListSentinel){
            System.out.println(i);
        }

        annularLinkedListSentinel.loop3();
    }
}
class  AnnularLinkedListSentinel implements Iterable<Integer>{

    private final Node sentinel = new Node(null, -1, null); // 哨兵

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            Node p = sentinel.next;
            @Override
            public boolean hasNext() {
                return p != sentinel;
            }

            @Override
            public Integer next() {
                int v = p.value;
                p = p.next;
                return v;
            }
        };
    }

    private static class Node{
        Node prev;
        int value;
        Node next;

        public Node(Node prev,int value,Node next){
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

    }

    public AnnularLinkedListSentinel(){
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public void addFirst(int value){
        Node p = new Node(sentinel,value,sentinel.next);
        sentinel.next = p;
        sentinel.prev = p;
    }

    public void addLast(int value){

        Node p = new Node(sentinel.prev,value,sentinel);
        sentinel.prev.next = p;
        sentinel.prev = p;

    }

    public void removeFirst(){
        Node remove = sentinel.next;
        if (remove==sentinel){
            return;
        }
        sentinel.next = remove.next;
        remove.next.prev = sentinel;
    }

    public void removeLast(){
        Node remove = sentinel.prev;
        if (remove==sentinel){
            return;
        }
        Node prev = remove.prev;
        prev.next = sentinel;
        sentinel.prev = prev;
    }

    //根据索引删除
    public void remove(int value){
        Node remove = findNode(value);
        if (remove==sentinel){
            return;
        }
        Node prev = remove.prev;
        Node next = remove.next;
        prev.next = next;
        next.prev = prev;
    }

    public Node findNode(int value){
        Node p = sentinel.next;
        while(p != sentinel){
            if (p.value == value){
                return p;
            }
            p = p.next;
        }
        return null;
    }

    public void loop3(){
        recur(sentinel.next);
    }

    public void recur(Node cur){
        if (cur.value==-1){
            return;
        }
        System.out.println(cur.value);
        recur(cur.next);
    }

    private IllegalArgumentException illegalIndex(int index) {
        return new IllegalArgumentException(
                String.format("index [%d] 不合法%n", index));
    }
}
