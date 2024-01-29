import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Btr {
    @Test
    @DisplayName("split(t=2)")
    void split1() {
        /*
                5               2|5
              /   \     ==>    / | \
           1|2|3   6          1  3  6

         */
        BTree tree = new BTree();
        BTree.Node root = tree.root;
        root.leaf = false;
        root.keys[0] = 5;
        root.keyNumber = 1;

        root.childrens[0] = new BTree.Node(new int[]{1, 2, 3});
        root.childrens[0].keyNumber = 3;

        root.childrens[1] = new BTree.Node(new int[]{6});
        root.childrens[1].keyNumber = 1;

        tree.split(root.childrens[0], root, 0);
        Assertions.assertEquals("[2, 5]", root.toString());
        assertEquals("[1]", root.childrens[0].toString());
        assertEquals("[3]", root.childrens[1].toString());
        assertEquals("[6]", root.childrens[2].toString());
    }

    @Test
    void testPut3() {
        BTree tree = new BTree();
        tree.put(6);
        tree.put(3);
        tree.put(8);
        tree.put(1);
        tree.put(2);
        tree.put(5);
        tree.put(4);
        assertEquals("[4]", tree.root.toString());
        assertEquals("[2]", tree.root.childrens[0].toString());
        assertEquals("[6]", tree.root.childrens[1].toString());
        assertEquals("[1]", tree.root.childrens[0].childrens[0].toString());
        assertEquals("[3]", tree.root.childrens[0].childrens[1].toString());
        assertEquals("[5]", tree.root.childrens[1].childrens[0].toString());
        assertEquals("[8]", tree.root.childrens[1].childrens[1].toString());
    }

    @Test
    @DisplayName("case1: leaf && not found")
    public void testRemove0() {
        /*
             1|2|3|4
         */
        BTree tree = new BTree(3);
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);

        tree.remove(0);
        tree.remove(8);
        assertEquals("[1, 2, 3, 4]", tree.root.toString());
    }

    @Test
    @DisplayName("case3: non-leaf && not found")
    public void testRemove1() {
        /*
                 3
               /   \
             1|2  4|5|6|7
         */
        BTree tree = new BTree(3);
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(5);
        tree.put(6);
        tree.put(7);

        tree.remove(0);
        tree.remove(8);
        assertEquals("[3]", tree.root.toString());
        assertEquals("[1, 2]", tree.root.childrens[0].toString());
        assertEquals("[4, 5, 6, 7]", tree.root.childrens[1].toString());
    }

    @Test
    @DisplayName("case2: remove directly")
    public void testRemove2() {
        /*
                 3
               /   \
             1|2  4|5|6|7
         */
        BTree tree = new BTree(3);
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);

        tree.remove(3);
        assertEquals("[1, 2, 4]", tree.root.toString());
        tree.remove(1);
        assertEquals("[2, 4]", tree.root.toString());
    }

    @Test
    @DisplayName("case4: replace with successor")
    public void testRemove3() {
        /*
                 3
               /   \
             1|2  4|5|6|7
         */
        BTree tree = new BTree(3);
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(5);
        tree.put(6);
        tree.put(7);

        tree.remove(3);
        assertEquals("[4]", tree.root.toString());
        assertEquals("[1, 2]", tree.root.childrens[0].toString());
        assertEquals("[5, 6, 7]", tree.root.childrens[1].toString());
    }

    @Test
    @DisplayName("case5: balance right rotate")
    public void testRemove4() { // 右旋
        /*
                  4
                /   \
             1|2|3  5|6
         */
        BTree tree = new BTree(3);
        tree.put(1);
        tree.put(2);
        tree.put(4);
        tree.put(5);
        tree.put(6);
        tree.put(3);

        tree.remove(5);
        assertEquals("[3]", tree.root.toString());
        assertEquals("[1, 2]", tree.root.childrens[0].toString());
        assertEquals("[4, 6]", tree.root.childrens[1].toString());
    }

    @Test
    @DisplayName("case5: balance left rotate")
    public void testRemove5() {
        /*
                  3
                /   \
             1|2   4|5|6

                  4
                /   \
              1|3   5|6
         */
        BTree tree = new BTree(3);
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(5);
        tree.put(6);

        tree.remove(2);
        assertEquals("[4]", tree.root.toString());
        assertEquals("[1, 3]", tree.root.childrens[0].toString());
        assertEquals("[5, 6]", tree.root.childrens[1].toString());
    }

    @Test
    @DisplayName("case5: balance merge a")
    public void testRemove6() { // 合并
        /*
                  3
                /   \
             1|2    4|5
         */
        BTree tree = new BTree(3);
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(5);

        tree.remove(4);
        assertEquals("[1, 2, 3, 5]", tree.root.toString());
    }

    @Test
    @DisplayName("case5: balance merge b")
    public void testRemove7() { // 合并
        /*
                  3
                /   \
             1|2    4|5
         */
        BTree tree = new BTree(3);
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(5);

        tree.remove(2);
        assertEquals("[1, 3, 4, 5]", tree.root.toString());
    }

    @Test
    @DisplayName("case6: from right to left")
    void testRemove8() {
        /*

                4|8                    4
               / | \                  / \
              2  6  10        =>     2  6|8
             /\  /\  /\             /\ / | \
            1 3 5 7 9 11           1 3 5 7 9|10


                4                       4
               / \                     / \
              2  6|8        =>        2   6
             /\ / | \                /\  / \
            1 3 5 7  9              1 3 5  7|8


                4                       4
               / \                     / \
              2   6         =>        2   _      =>     2|4
             /\   /\                 /\  /             / | \
            1  3 5  7               1 3 5|6           1 3 5|6

              2|4                       2
             / | \          =>         / \
            1 3  5                    1  3|4

        */
        BTree tree = new BTree();
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(5);
        tree.put(6);
        tree.put(7);
        tree.put(8);
        tree.put(9);
        tree.put(10);
        tree.put(11);
        tree.remove(11);
        assertEquals("[4]", tree.root.toString());
        assertEquals("[2]", tree.root.childrens[0].toString());
        assertEquals("[6, 8]", tree.root.childrens[1].toString());
        assertEquals("[1]", tree.root.childrens[0].childrens[0].toString());
        assertEquals("[3]", tree.root.childrens[0].childrens[1].toString());
        assertEquals("[5]", tree.root.childrens[1].childrens[0].toString());
        assertEquals("[7]", tree.root.childrens[1].childrens[1].toString());
        assertEquals("[9, 10]", tree.root.childrens[1].childrens[2].toString());
        tree.remove(10);
        assertEquals("[9]", tree.root.childrens[1].childrens[2].toString());
        tree.remove(9);
        assertEquals("[6]", tree.root.childrens[1].toString());
        assertEquals("[5]", tree.root.childrens[1].childrens[0].toString());
        assertEquals("[7, 8]", tree.root.childrens[1].childrens[1].toString());
        tree.remove(8);
        assertEquals("[7]", tree.root.childrens[1].childrens[1].toString());
        tree.remove(7);
        assertEquals("[2, 4]", tree.root.toString());
        assertEquals("[1]", tree.root.childrens[0].toString());
        assertEquals("[3]", tree.root.childrens[1].toString());
        assertEquals("[5, 6]", tree.root.childrens[2].toString());
        tree.remove(6);
        assertEquals("[5]", tree.root.childrens[2].toString());
        tree.remove(5);
        assertEquals("[2]", tree.root.toString());
        assertEquals("[1]", tree.root.childrens[0].toString());
        assertEquals("[3, 4]", tree.root.childrens[1].toString());
        tree.remove(4);
        assertEquals("[3]", tree.root.childrens[1].toString());
        tree.remove(3);
        assertEquals("[1, 2]", tree.root.toString());
        tree.remove(2);
        assertEquals("[1]", tree.root.toString());
        tree.remove(1);
        assertEquals("[]", tree.root.toString());
    }

    @Test
    @DisplayName("case6: from left to right")
    void testRemove9() {
        /*

                4|8                    4|8                  8
               / | \                  / | \                / \
              2  6  10        =>     _  6  10       =>   4|6  10
             /\  /\  /\             /   /\  /\          / | \  /\
            1 3 5 7 9 11           2|3 5 7 9 11       2|3 5 7 9 11


            remove(2,3) =>  8
                           / \
                          6   10
                         / \  /\
                        4|5 7 9 11

            remove(4,5) =>  8         =>        8|10
                           / \                 / | \
                          _   10             6|7 9 11
                         / \  /\
                        6|7   9 11

            remove(6,7)   =>   10
                               / \
                             8|9 11
        */
        BTree tree = new BTree();
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(5);
        tree.put(6);
        tree.put(7);
        tree.put(8);
        tree.put(9);
        tree.put(10);
        tree.put(11);
        tree.remove(1);
        assertEquals("[8]", tree.root.toString());
        assertEquals("[4, 6]", tree.root.childrens[0].toString());
        assertEquals("[10]", tree.root.childrens[1].toString());
        assertEquals("[2, 3]", tree.root.childrens[0].childrens[0].toString());
        assertEquals("[5]", tree.root.childrens[0].childrens[1].toString());
        assertEquals("[7]", tree.root.childrens[0].childrens[2].toString());
        assertEquals("[9]", tree.root.childrens[1].childrens[0].toString());
        assertEquals("[11]", tree.root.childrens[1].childrens[1].toString());
        tree.remove(2);
        tree.remove(3);
        assertEquals("[8]", tree.root.toString());
        assertEquals("[6]", tree.root.childrens[0].toString());
        assertEquals("[10]", tree.root.childrens[1].toString());
        assertEquals("[4, 5]", tree.root.childrens[0].childrens[0].toString());
        assertEquals("[7]", tree.root.childrens[0].childrens[1].toString());
        assertEquals("[9]", tree.root.childrens[1].childrens[0].toString());
        assertEquals("[11]", tree.root.childrens[1].childrens[1].toString());
        tree.remove(4);
        tree.remove(5);
        assertEquals("[8, 10]", tree.root.toString());
        assertEquals("[6, 7]", tree.root.childrens[0].toString());
        assertEquals("[9]", tree.root.childrens[1].toString());
        assertEquals("[11]", tree.root.childrens[2].toString());
        tree.remove(6);
        tree.remove(7);
        assertEquals("[10]", tree.root.toString());
        assertEquals("[8, 9]", tree.root.childrens[0].toString());
        assertEquals("[11]", tree.root.childrens[1].toString());
        tree.remove(8);
        tree.remove(9);
        assertEquals("[10, 11]", tree.root.toString());
        assertTrue(tree.root.leaf);
        tree.remove(10);
        tree.remove(11);
        assertEquals("[]", tree.root.toString());
    }

    @Test
    @DisplayName("case6: delete middle")
    void testRemove11() {
        /*

                4|8                5|8               5|8                8
               / | \              / | \             / | \              / \
              2  6  10      =>   2  6  10      =>  2  _  10       => 2|5  10
             /\  /\  /\         /\  /\  /\        /\  |  /\         / | \  /\
            1 3 5 7 9 11       1 3 _ 7 9 11      1 3 6|7 9 11      1 3 6|7 9 11


                 8                    9                 9                5
                / \                /    \            /    \           /    \
              2|5  10     =>     2|5    10     =>  2|5     _    =>   2      9
             / | \  /\          / | \   /\        / | \    |        /\    /  \
            1 3 6|7 9 11       1 3 6|7 _ 11      1 3 6|7 10|11     1 3  6|7 10|11


                  5
               /    \
              2      9     =>
             /\    /  \
            1 3  6|7 10|11

                  6                    7                7
               /    \               /    \           /    \
              2      9     =>      2      9     =>  2     10
             /\    /  \           /\    /  \       /\    /  \
            1 3   7  10|11       1 3   _  10|11   1 3   9   11

                 7                   9                9
               /  \                /  \              / \
              2   10       =>     2   10     =>     2   _      =>   2|9
             /\   / \            /\   / \          /\   |          / | \
            1 3  9  11          1 3  _  11        1 3 10|11       1  3 10|11

               2|9                 3|9                3|10
              / | \         =>    / | \       =>     / | \
             1  3 10|11          1  _ 10|11         1  9  11

               3|10                9|10               10
              / | \         =>    / | \       =>     / \
             1  9  11            1  _  11          1|9  11

               10                   11               9
              / \           =>     / \        =>    / \
            1|9  11              1|9  _            1  11

               9                    11
              / \         =>       / \        =>    1|11
             1  11                1   _
        */
        BTree tree = new BTree();
        tree.put(1);
        tree.put(2);
        tree.put(3);
        tree.put(4);
        tree.put(5);
        tree.put(6);
        tree.put(7);
        tree.put(8);
        tree.put(9);
        tree.put(10);
        tree.put(11);
        tree.remove(4);
        assertEquals("[8]", tree.root.toString());
        assertEquals("[2, 5]", tree.root.childrens[0].toString());
        assertEquals("[10]", tree.root.childrens[1].toString());
        assertEquals("[1]", tree.root.childrens[0].childrens[0].toString());
        assertEquals("[3]", tree.root.childrens[0].childrens[1].toString());
        assertEquals("[6, 7]", tree.root.childrens[0].childrens[2].toString());
        assertEquals("[9]", tree.root.childrens[1].childrens[0].toString());
        assertEquals("[11]", tree.root.childrens[1].childrens[1].toString());
        tree.remove(8);
        assertEquals("[5]", tree.root.toString());
        assertEquals("[2]", tree.root.childrens[0].toString());
        assertEquals("[9]", tree.root.childrens[1].toString());
        assertEquals("[1]", tree.root.childrens[0].childrens[0].toString());
        assertEquals("[3]", tree.root.childrens[0].childrens[1].toString());
        assertEquals("[6, 7]", tree.root.childrens[1].childrens[0].toString());
        assertEquals("[10, 11]", tree.root.childrens[1].childrens[1].toString());
        tree.remove(5);
        assertEquals("[6]", tree.root.toString());
        assertEquals("[2]", tree.root.childrens[0].toString());
        assertEquals("[9]", tree.root.childrens[1].toString());
        assertEquals("[1]", tree.root.childrens[0].childrens[0].toString());
        assertEquals("[3]", tree.root.childrens[0].childrens[1].toString());
        assertEquals("[7]", tree.root.childrens[1].childrens[0].toString());
        assertEquals("[10, 11]", tree.root.childrens[1].childrens[1].toString());
        tree.remove(6);
        assertEquals("[7]", tree.root.toString());
        assertEquals("[2]", tree.root.childrens[0].toString());
        assertEquals("[10]", tree.root.childrens[1].toString());
        assertEquals("[1]", tree.root.childrens[0].childrens[0].toString());
        assertEquals("[3]", tree.root.childrens[0].childrens[1].toString());
        assertEquals("[9]", tree.root.childrens[1].childrens[0].toString());
        assertEquals("[11]", tree.root.childrens[1].childrens[1].toString());
        tree.remove(7);
        assertEquals("[2, 9]", tree.root.toString());
        assertEquals("[1]", tree.root.childrens[0].toString());
        assertEquals("[3]", tree.root.childrens[1].toString());
        assertEquals("[10, 11]", tree.root.childrens[2].toString());
        tree.remove(2);
        assertEquals("[3, 10]", tree.root.toString());
        assertEquals("[1]", tree.root.childrens[0].toString());
        assertEquals("[9]", tree.root.childrens[1].toString());
        assertEquals("[11]", tree.root.childrens[2].toString());
        tree.remove(3);
        assertEquals("[10]", tree.root.toString());
        assertEquals("[1, 9]", tree.root.childrens[0].toString());
        assertEquals("[11]", tree.root.childrens[1].toString());
        tree.remove(10);
        assertEquals("[9]", tree.root.toString());
        assertEquals("[1]", tree.root.childrens[0].toString());
        assertEquals("[11]", tree.root.childrens[1].toString());
        tree.remove(9);
        assertEquals("[1, 11]", tree.root.toString());
    }

    @Test
    @DisplayName("case5: balance left rotate")
    public void testRemove5_2() {
    /*
        4|5|6  ==>  5
                   / \
                  4   6
       5
      / \        ==>      5|7    ==>    5|7
     4   6|7|8           / | \         / | \
                        4  6  8      2|3|4  6 8

     3|5|7                     5                             5
    / | | \  ==>             /   \                        /     \
   2  4 6  8                3     7              ==>     3      7|9
                           / \   / \                    / \    / | \
                          2   4 6   8|9|10             2   4  6  8  10

     5                           7
    / \                        /   \
   _   7|9      ==>           5     9
   |   /|\                   / \   / \
  2|4 6 8 10                2|4 6 8  10
    */
        BTree tree = new BTree(2);
        tree.put(4);
        tree.put(5);
        tree.put(6);
        tree.put(7);
        tree.put(8);
        tree.put(3);
        tree.put(2);
        tree.put(9);
        tree.put(10);

        tree.remove(3);
        assertEquals("[7]", tree.root.toString());
        assertEquals("[5]", tree.root.childrens[0].toString());
        assertEquals("[9]", tree.root.childrens[1].toString());
        assertEquals("[2, 4]", tree.root.childrens[0].childrens[0].toString());
        assertEquals("[6]", tree.root.childrens[0].childrens[1].toString());
        assertEquals("[8]", tree.root.childrens[1].childrens[0].toString());
        assertEquals("[10]", tree.root.childrens[1].childrens[1].toString());

    }

    class BTree {


        static class Node {
            int[] keys;
            Node[] childrens;
            boolean leaf = true; //判断是否是叶子节点
            int keyNumber;
            int t;//度

            public Node(int t) {
                this.t = t;
                this.keys = new int[2 * t - 1];
                this.childrens = new Node[2 * t];
            }

            public Node(int[] keys) {
                this.keys = keys;
            }


            @Override
            public String toString() {
                return Arrays.toString(Arrays.copyOfRange(keys, 0, keyNumber));
            }

            Node get(int key) {
                int i = 0;
                while (i < keyNumber) {
                    if (keys[i] == key) {
                        return this;
                    }
                    if (keys[i] > key) {
                        break;
                    }
                    i++;
                }
                // 执行到此时 keys[i]>key 或 i==keyNumber
                if (leaf) {
                    return null;
                }
                // 非叶子情况
                return childrens[i].get(key);
            }


            //插入key
            void insertKey(int key, int index) {
                System.arraycopy(keys, index, keys, index + 1, keyNumber - index);
                keys[index] = key;
                keyNumber++;
            }

            //插入children
            void insertChildren(Node children, int index) {
                System.arraycopy(childrens, index, childrens, index + 1, keyNumber - index);
                childrens[index] = children;
            }

            int removeKey(int index) {
                int t = keys[index];
                System.arraycopy(keys, index + 1, keys, index, --keyNumber - index);
                return t;
            }

            int removeLeftmostKey() {
                return removeKey(0);
            }

            int removeRightmostKey() {
                return removeKey(keyNumber - 1);
            }

            Node removeChild(int index) {
                Node t = childrens[index];
                System.arraycopy(childrens, index + 1, childrens, index, keyNumber - index);
                childrens[keyNumber] = null;
                return t;
            }

            Node removeLeftmostChild() {
                return removeChild(0);
            }

            Node removeRightmostChild() {
                return removeChild(keyNumber);
            }

            //移动到目标节点
            void moveToTarget(Node left) {
                int start = left.keyNumber;
                if (!leaf) {
                    for (int i = 0; i <= keyNumber; i++) {
                        left.childrens[start + i] = childrens[i];
                    }
                }
                for (int i = 0; i < keyNumber; i++) {
                    left.keys[left.keyNumber++] = keys[i];
                }
            }

            Node leftSibling(int index) {
                return index > 0 ? childrens[index - 1] : null;
            }

            Node rightSibling(int index) {
                return index == keyNumber ? null : childrens[index + 1];
            }
        }

        Node root;
        final int t;
        final int Max_Key_Number;
        final int Min_Key_Number;

        public BTree() {
            this(2);
        }

        public BTree(int t) {
            this.t = t;
            root = new Node(t);
            Min_Key_Number = t - 1;
            Max_Key_Number = 2 * t - 1;
        }

        //判断是否存在
        public boolean contains(int key) {
            return root.get(key) != null;
        }

        //插入
        public void put(int key) {
            doPut(root, key, null, 0);
        }

        private void doPut(Node node, int key, Node parent, int index) {
            int i = 0;
            while (i < node.keyNumber) {
                if (node.keys[i] == key) {
                    return;//更新
                }
                if (node.keys[i] > key) {
                    break;//找到了插入位置，即i
                }
                i++;
            }
            if (node.leaf) {
                node.insertKey(key, i);
            } else {
                doPut(node.childrens[i], key, node, i);
            }
            if (node.keyNumber == Max_Key_Number) {
                split(node, parent, index);
            }
        }

        public void split(Node left, Node parent, int index) {
            //分裂是根节点
            if (parent == null) {
                Node newRoot = new Node(t);
                newRoot.leaf = false;
                newRoot.insertChildren(left, 0);
                this.root = newRoot;
                parent = newRoot;
            }
            Node right = new Node(t);
            right.leaf = left.leaf;
            System.arraycopy(left.keys, t, right.keys, 0, t - 1);
            //分裂如果是叶子节点
            if (!left.leaf) {
                System.arraycopy(left.childrens, t, right.childrens, 0, t);
                for (int i = t; i <= left.keyNumber; i++) {
                    left.childrens[i] = null;
                }
            }
            right.keyNumber = t - 1;
            left.keyNumber = t - 1;
            int mid = left.keys[t - 1];
            parent.insertKey(mid, index);
            parent.insertChildren(right, index + 1);
        }

        public void remove(int key) {
            doRemove(null, root, 0, key);
        }

        private void doRemove(Node parent, Node node, int index, int key) {
            int i = 0;
            while (i < node.keyNumber) {
                if (node.keys[i] >= key) {
                    break;
                }
                i++;
            }
            if (node.leaf) {
                if (!found(node, key, i)) {//case1
                    return;
                } else {//case2
                    node.removeKey(i);
                }
            } else {
                if (!found(node, key, i)) {//case3
                    doRemove(node, node.childrens[i], i, key);
                } else {//case4
                    Node s = node.childrens[i + 1];
                    while (!s.leaf) {
                        s = s.childrens[0];
                    }
                    int skey = s.keys[0];
                    node.keys[i] = skey;
                    doRemove(node, node.childrens[i + 1], i + 1, skey);
                }
            }
            if (node.keyNumber < Min_Key_Number) {
                balance(node, parent, index);
            }
        }

        private boolean found(Node node, int key, int i) {
            return i < node.keyNumber && node.keys[i] == key;
        }

        private void balance(Node node, Node parent, int i) {
            if (node == root) {
                if (root.keyNumber == 0 && root.childrens[0] != null) {
                    root = root.childrens[0];
                }
                return;
            }
            Node left = parent.leftSibling(i);
            Node right = parent.rightSibling(i);
            if (left != null && left.keyNumber > Min_Key_Number) {
                node.insertKey(parent.keys[i - 1], 0);
                if (!left.leaf) {
                    node.insertChildren(left.removeRightmostChild(), 0);
                }
                parent.keys[i - 1] = left.removeRightmostKey();
                return;
            }
            if (right != null && right.keyNumber > Min_Key_Number) {
                node.insertKey(parent.keys[i], node.keyNumber);
                if (!right.leaf) {
                    node.insertChildren(right.removeRightmostChild(), node.keyNumber + 1);
                }
                parent.keys[i] = right.removeLeftmostKey();
                return;
            }
            if (left != null) {
                //向左兄弟合并
                parent.removeChild(i);
                left.insertKey(parent.removeKey(i - 1), left.keyNumber);
                node.moveToTarget(left);
            } else {
                //向自己合并
                parent.removeChild(i + 1);
                node.insertKey(parent.removeKey(i), node.keyNumber);
                right.moveToTarget(node);
            }
        }
    }
}
