import java.util.Iterator;

public class DoubleSentryLinkList {
    public static void main(String[] args) {
        DoublyLinkedListSentinel list = new DoublyLinkedListSentinel();
        list.insert(0,1);
        list.insert(1,2);
        list.addLast(3);
        list.addFirst(4);

//        list.remove(0);
//        list.removeLast();
//        list.removeFirst();


        for (Integer i : list){
            System.out.println(i);
        }

        list.loop();
    }
}

class DoublyLinkedListSentinel implements Iterable<Integer>{
    private final Node head;//头哨兵
    private final Node tail;//尾哨兵


    public  DoublyLinkedListSentinel() {
        head = new Node(null, 0, null);
        tail = new Node(null, 0, null);
        head.next = tail;
        tail.prev = head;
    }


    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
           Node p = head.next;
            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public Integer next() {
                int v = p.value;
                p = p.next;
                return v;
            }
        };
    }

    static class Node {
        Node prev;
        int value;
        Node next;


        public Node(Node prev, int value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }





    private Node findNode(int index) {
        int i = -1;
        for (Node p = head; p != tail; p = p.next, i++) {
            if (i == index) {
                return p;
            }
        }
        return null;

    }

    public void addFirst(int value) {
        insert(0,value);
    }

    public void addLast(int value){
        Node last = tail.prev;
        Node add = new Node(last,value,tail);
        last.next = add;
        tail.prev = add;
    }


    public void insert(int index, int value) {
        Node prev = findNode(index - 1);
        if (prev == null) {
            throw illegalIndex(index);
        }
        Node next = prev.next;
        Node inserted = new Node(prev, value, next);
        prev.next = inserted;
        next.prev = inserted;
    }

    public void removeFirst(){
        remove(0);
    }

    public void remove(int index) {
//        Node prev = findNode(index-1);
//        if (prev==null){
//            return;
//        }
//        Node remove = prev.next;
//        Node next = remove.next;

        Node prev = findNode(index - 1);
        if (prev == null) {
            throw illegalIndex(index);
        }
        Node removed = prev.next;
        if (removed == tail) {
            throw illegalIndex(index);
        }
        Node next = removed.next;
        prev.next = next;
        next.prev = prev;

//        Node remove = findNode(index);
//        if (remove==null){
//            return;
//        }
//        Node next = remove.next;
//        if (next==null){
//            return;
//        }
//        Node prev = remove.prev;
//
//        prev.next = next;
//        next.prev = prev;
    }

    public void removeLast(){
        Node last = tail.prev;
        if (last==head){
            return;
        }
        Node prev = last.prev;
        prev.next = tail;
        tail.prev = prev;
    }

    public  void loop(){
        recur(head.next);
    }

    public void recur(Node cur){
        if (cur.value==0){
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
